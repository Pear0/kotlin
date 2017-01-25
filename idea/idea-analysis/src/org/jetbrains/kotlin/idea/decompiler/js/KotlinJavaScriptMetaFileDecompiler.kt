/*
 * Copyright 2010-2016 JetBrains s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.jetbrains.kotlin.idea.decompiler.js

import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.FileViewProvider
import com.intellij.psi.PsiManager
import com.intellij.psi.compiled.ClassFileDecompilers
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor
import org.jetbrains.kotlin.idea.decompiler.KotlinDecompiledFileViewProvider
import org.jetbrains.kotlin.idea.decompiler.KtDecompiledFile
import org.jetbrains.kotlin.idea.decompiler.common.createIncompatibleAbiVersionDecompiledText
import org.jetbrains.kotlin.idea.decompiler.textBuilder.DecompiledText
import org.jetbrains.kotlin.idea.decompiler.textBuilder.buildDecompiledText
import org.jetbrains.kotlin.idea.decompiler.textBuilder.defaultDecompilerRendererOptions
import org.jetbrains.kotlin.name.FqName
import org.jetbrains.kotlin.renderer.DescriptorRenderer
import org.jetbrains.kotlin.serialization.deserialization.NameResolverImpl
import org.jetbrains.kotlin.serialization.js.JsProtoBuf
import org.jetbrains.kotlin.serialization.js.JsSerializerProtocol
import org.jetbrains.kotlin.utils.JsBinaryVersion
import java.io.ByteArrayInputStream

class KotlinJavaScriptMetaFileDecompiler : ClassFileDecompilers.Full() {
    private val stubBuilder = KotlinJavaScriptStubBuilder()

    override fun accepts(file: VirtualFile): Boolean {
        return file.fileType == KotlinJavaScriptMetaFileType
    }

    override fun getStubBuilder() = stubBuilder

    override fun createFileViewProvider(file: VirtualFile, manager: PsiManager, physical: Boolean): FileViewProvider {
        return KotlinDecompiledFileViewProvider(manager, file, physical) { provider ->
            KtDecompiledFile(provider, ::buildDecompiledTextFromJsMetadata)
        }
    }
}

private val decompilerRendererForJS = DescriptorRenderer.withOptions { defaultDecompilerRendererOptions() }

// TODO: deduplicate code with KotlinBuiltInDecompiler
fun buildDecompiledTextFromJsMetadata(kjsmFile: VirtualFile): DecompiledText {
    if (kjsmFile.fileType != KotlinJavaScriptMetaFileType) {
        error("Unexpected file type ${kjsmFile.fileType}")
    }

    val file = KjsmFile.read(kjsmFile)
               ?: error("Unexpectedly empty file: $kjsmFile")

    when (file) {
        is KjsmFile.Incompatible -> {
            return createIncompatibleAbiVersionDecompiledText(JsBinaryVersion.INSTANCE, file.version)
        }
        is KjsmFile.Compatible -> {
            val packageFqName = file.packageFqName
            val resolver = KotlinJavaScriptDeserializerForDecompiler(packageFqName, file.proto, file.nameResolver)
            val declarations = arrayListOf<DeclarationDescriptor>()
            declarations.addAll(resolver.resolveDeclarationsInFacade(packageFqName))
            for (klass in file.classesToDecompile) {
                val classId = file.nameResolver.getClassId(klass.fqName)
                declarations.add(resolver.resolveTopLevelClass(classId)!!)
            }
            return buildDecompiledText(packageFqName, declarations, decompilerRendererForJS)
        }
    }
}

// TODO: deduplicate code with BuiltInDefinitionFile
sealed class KjsmFile {
    class Incompatible(val version: JsBinaryVersion) : KjsmFile()

    class Compatible(val header: JsProtoBuf.Header, val proto: JsProtoBuf.Library.Part) : KjsmFile() {
        val packageFqName = FqName(header.packageFqName)
        val nameResolver = NameResolverImpl(proto.strings, proto.qualifiedNames)

        val classesToDecompile =
                proto.class_List.filterNot { proto -> nameResolver.getClassId(proto.fqName).isNestedClass }
    }

    companion object {
        fun read(file: VirtualFile): KjsmFile? {
            if (!file.isValid) {
                return null
            }

            val stream = ByteArrayInputStream(file.contentsToByteArray())

            val version = JsBinaryVersion.readFrom(stream)
            if (!version.isCompatible()) {
                return Incompatible(version)
            }

            val header = JsProtoBuf.Header.parseDelimitedFrom(stream)
            val proto = JsProtoBuf.Library.Part.parseFrom(stream, JsSerializerProtocol.extensionRegistry)
            val result = Compatible(header, proto)

            return result
        }
    }
}
