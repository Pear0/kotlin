/*
 * Generated file
 * DO NOT EDIT
 * 
 * See libraries/tools/idl2k for details
 */

package org.w3c.workers

import org.khronos.webgl.*
import org.w3c.dom.*
import org.w3c.dom.css.*
import org.w3c.dom.events.*
import org.w3c.dom.parsing.*
import org.w3c.dom.svg.*
import org.w3c.dom.url.*
import org.w3c.fetch.*
import org.w3c.files.*
import org.w3c.notifications.*
import org.w3c.performance.*
import org.w3c.xhr.*

public external abstract class ServiceWorkerRegistration : EventTarget {
    open val installing: ServiceWorker?
    open val waiting: ServiceWorker?
    open val active: ServiceWorker?
    open val scope: String
    open var onupdatefound: ((Event) -> dynamic)?
    open val APISpace: dynamic
    fun update(): Promise<dynamic>
    fun unregister(): Promise<Boolean>
    fun methodName(): Promise<dynamic>
    fun showNotification(title: String, options: NotificationOptions = definedExternally): Promise<dynamic>
    fun getNotifications(filter: GetNotificationOptions = definedExternally): Promise<dynamic>
}

public external abstract class ServiceWorkerGlobalScope : WorkerGlobalScope {
    open val clients: Clients
    open val registration: ServiceWorkerRegistration
    open var oninstall: ((Event) -> dynamic)?
    open var onactivate: ((Event) -> dynamic)?
    open var onfetch: ((Event) -> dynamic)?
    open var onforeignfetch: ((Event) -> dynamic)?
    open var onmessage: ((Event) -> dynamic)?
    open var onfunctionalevent: ((Event) -> dynamic)?
    open var onnotificationclick: ((Event) -> dynamic)?
    open var onnotificationclose: ((Event) -> dynamic)?
    fun skipWaiting(): Promise<dynamic>
}

public external abstract class ServiceWorker : EventTarget, AbstractWorker, UnionMessagePortOrServiceWorker, UnionClientOrMessagePortOrServiceWorker {
    open val scriptURL: String
    open val state: String
    open var onstatechange: ((Event) -> dynamic)?
    fun postMessage(message: Any?, transfer: Array<dynamic> = definedExternally): Unit
}

public external abstract class ServiceWorkerContainer : EventTarget {
    open val controller: ServiceWorker?
    open val ready: Promise<ServiceWorkerRegistration>
    open var oncontrollerchange: ((Event) -> dynamic)?
    open var onmessage: ((Event) -> dynamic)?
    fun register(scriptURL: String, options: RegistrationOptions = definedExternally): Promise<ServiceWorkerRegistration>
    fun getRegistration(clientURL: String = definedExternally): Promise<dynamic>
    fun getRegistrations(): Promise<dynamic>
    fun startMessages(): Unit
}

public external interface RegistrationOptions {
    var scope: String?
        get() = definedExternally
        set(value) = definedExternally
    var type: String? /* = "classic" */
        get() = definedExternally
        set(value) = definedExternally
}

public inline fun RegistrationOptions(scope: String? = null, type: String? = "classic"): RegistrationOptions {
    val o = js("({})")

    o["scope"] = scope
    o["type"] = type

    return o
}

public external open class ServiceWorkerMessageEvent(type: String, eventInitDict: ServiceWorkerMessageEventInit = definedExternally) : Event {
    open val data: Any?
    open val origin: String
    open val lastEventId: String
    open val source: UnionMessagePortOrServiceWorker?
    open val ports: dynamic
}

public external interface ServiceWorkerMessageEventInit : EventInit {
    var data: Any?
        get() = definedExternally
        set(value) = definedExternally
    var origin: String?
        get() = definedExternally
        set(value) = definedExternally
    var lastEventId: String?
        get() = definedExternally
        set(value) = definedExternally
    var source: UnionMessagePortOrServiceWorker?
        get() = definedExternally
        set(value) = definedExternally
    var ports: Array<MessagePort>?
        get() = definedExternally
        set(value) = definedExternally
}

public inline fun ServiceWorkerMessageEventInit(data: Any? = null, origin: String? = null, lastEventId: String? = null, source: UnionMessagePortOrServiceWorker? = null, ports: Array<MessagePort>? = null, bubbles: Boolean? = false, cancelable: Boolean? = false, composed: Boolean? = false): ServiceWorkerMessageEventInit {
    val o = js("({})")

    o["data"] = data
    o["origin"] = origin
    o["lastEventId"] = lastEventId
    o["source"] = source
    o["ports"] = ports
    o["bubbles"] = bubbles
    o["cancelable"] = cancelable
    o["composed"] = composed

    return o
}

public external abstract class Client : UnionClientOrMessagePortOrServiceWorker {
    open val url: String
    open val frameType: String
    open val id: String
    fun postMessage(message: Any?, transfer: Array<dynamic> = definedExternally): Unit
}

public external abstract class WindowClient : Client {
    open val visibilityState: dynamic
    open val focused: Boolean
    fun focus(): Promise<WindowClient>
    fun navigate(url: String): Promise<WindowClient>
}

public external abstract class Clients {
    fun get(id: String): Promise<dynamic>
    fun matchAll(options: ClientQueryOptions = definedExternally): Promise<dynamic>
    fun openWindow(url: String): Promise<dynamic>
    fun claim(): Promise<dynamic>
}

public external interface ClientQueryOptions {
    var includeUncontrolled: Boolean? /* = false */
        get() = definedExternally
        set(value) = definedExternally
    var type: String? /* = "window" */
        get() = definedExternally
        set(value) = definedExternally
}

public inline fun ClientQueryOptions(includeUncontrolled: Boolean? = false, type: String? = "window"): ClientQueryOptions {
    val o = js("({})")

    o["includeUncontrolled"] = includeUncontrolled
    o["type"] = type

    return o
}

public external open class ExtendableEvent(type: String, eventInitDict: ExtendableEventInit = definedExternally) : Event {
    fun waitUntil(f: Promise<dynamic>): Unit
}

public external interface ExtendableEventInit : EventInit {
}

public inline fun ExtendableEventInit(bubbles: Boolean? = false, cancelable: Boolean? = false, composed: Boolean? = false): ExtendableEventInit {
    val o = js("({})")

    o["bubbles"] = bubbles
    o["cancelable"] = cancelable
    o["composed"] = composed

    return o
}

public external open class InstallEvent(type: String, eventInitDict: ExtendableEventInit = definedExternally) : ExtendableEvent {
    fun registerForeignFetch(options: ForeignFetchOptions): Unit
}

public external interface ForeignFetchOptions {
    var scopes: Array<String>?
        get() = definedExternally
        set(value) = definedExternally
    var origins: Array<String>?
        get() = definedExternally
        set(value) = definedExternally
}

public inline fun ForeignFetchOptions(scopes: Array<String>?, origins: Array<String>?): ForeignFetchOptions {
    val o = js("({})")

    o["scopes"] = scopes
    o["origins"] = origins

    return o
}

public external open class FetchEvent(type: String, eventInitDict: FetchEventInit) : ExtendableEvent {
    open val request: Request
    open val clientId: String?
    open val isReload: Boolean
    fun respondWith(r: Promise<Response>): Unit
}

public external interface FetchEventInit : ExtendableEventInit {
    var request: Request?
        get() = definedExternally
        set(value) = definedExternally
    var clientId: String? /* = null */
        get() = definedExternally
        set(value) = definedExternally
    var isReload: Boolean? /* = false */
        get() = definedExternally
        set(value) = definedExternally
}

public inline fun FetchEventInit(request: Request?, clientId: String? = null, isReload: Boolean? = false, bubbles: Boolean? = false, cancelable: Boolean? = false, composed: Boolean? = false): FetchEventInit {
    val o = js("({})")

    o["request"] = request
    o["clientId"] = clientId
    o["isReload"] = isReload
    o["bubbles"] = bubbles
    o["cancelable"] = cancelable
    o["composed"] = composed

    return o
}

public external open class ForeignFetchEvent(type: String, eventInitDict: ForeignFetchEventInit) : ExtendableEvent {
    open val request: Request
    open val origin: String
    fun respondWith(r: Promise<ForeignFetchResponse>): Unit
}

public external interface ForeignFetchEventInit : ExtendableEventInit {
    var request: Request?
        get() = definedExternally
        set(value) = definedExternally
    var origin: String? /* = "null" */
        get() = definedExternally
        set(value) = definedExternally
}

public inline fun ForeignFetchEventInit(request: Request?, origin: String? = "null", bubbles: Boolean? = false, cancelable: Boolean? = false, composed: Boolean? = false): ForeignFetchEventInit {
    val o = js("({})")

    o["request"] = request
    o["origin"] = origin
    o["bubbles"] = bubbles
    o["cancelable"] = cancelable
    o["composed"] = composed

    return o
}

public external interface ForeignFetchResponse {
    var response: Response?
        get() = definedExternally
        set(value) = definedExternally
    var origin: String?
        get() = definedExternally
        set(value) = definedExternally
    var headers: Array<String>?
        get() = definedExternally
        set(value) = definedExternally
}

public inline fun ForeignFetchResponse(response: Response?, origin: String? = null, headers: Array<String>? = null): ForeignFetchResponse {
    val o = js("({})")

    o["response"] = response
    o["origin"] = origin
    o["headers"] = headers

    return o
}

public external open class ExtendableMessageEvent(type: String, eventInitDict: ExtendableMessageEventInit = definedExternally) : ExtendableEvent {
    open val data: Any?
    open val origin: String
    open val lastEventId: String
    open val source: UnionClientOrMessagePortOrServiceWorker?
    open val ports: dynamic
}

public external interface ExtendableMessageEventInit : ExtendableEventInit {
    var data: Any?
        get() = definedExternally
        set(value) = definedExternally
    var origin: String?
        get() = definedExternally
        set(value) = definedExternally
    var lastEventId: String?
        get() = definedExternally
        set(value) = definedExternally
    var source: UnionClientOrMessagePortOrServiceWorker?
        get() = definedExternally
        set(value) = definedExternally
    var ports: Array<MessagePort>?
        get() = definedExternally
        set(value) = definedExternally
}

public inline fun ExtendableMessageEventInit(data: Any? = null, origin: String? = null, lastEventId: String? = null, source: UnionClientOrMessagePortOrServiceWorker? = null, ports: Array<MessagePort>? = null, bubbles: Boolean? = false, cancelable: Boolean? = false, composed: Boolean? = false): ExtendableMessageEventInit {
    val o = js("({})")

    o["data"] = data
    o["origin"] = origin
    o["lastEventId"] = lastEventId
    o["source"] = source
    o["ports"] = ports
    o["bubbles"] = bubbles
    o["cancelable"] = cancelable
    o["composed"] = composed

    return o
}

public external abstract class Cache {
    fun match(request: dynamic, options: CacheQueryOptions = definedExternally): Promise<dynamic>
    fun matchAll(request: dynamic = definedExternally, options: CacheQueryOptions = definedExternally): Promise<dynamic>
    fun add(request: dynamic): Promise<dynamic>
    fun addAll(requests: Array<dynamic>): Promise<dynamic>
    fun put(request: dynamic, response: Response): Promise<dynamic>
    fun delete(request: dynamic, options: CacheQueryOptions = definedExternally): Promise<Boolean>
    fun keys(request: dynamic = definedExternally, options: CacheQueryOptions = definedExternally): Promise<dynamic>
}

public external interface CacheQueryOptions {
    var ignoreSearch: Boolean? /* = false */
        get() = definedExternally
        set(value) = definedExternally
    var ignoreMethod: Boolean? /* = false */
        get() = definedExternally
        set(value) = definedExternally
    var ignoreVary: Boolean? /* = false */
        get() = definedExternally
        set(value) = definedExternally
    var cacheName: String?
        get() = definedExternally
        set(value) = definedExternally
}

public inline fun CacheQueryOptions(ignoreSearch: Boolean? = false, ignoreMethod: Boolean? = false, ignoreVary: Boolean? = false, cacheName: String? = null): CacheQueryOptions {
    val o = js("({})")

    o["ignoreSearch"] = ignoreSearch
    o["ignoreMethod"] = ignoreMethod
    o["ignoreVary"] = ignoreVary
    o["cacheName"] = cacheName

    return o
}

public external interface CacheBatchOperation {
    var type: String?
        get() = definedExternally
        set(value) = definedExternally
    var request: Request?
        get() = definedExternally
        set(value) = definedExternally
    var response: Response?
        get() = definedExternally
        set(value) = definedExternally
    var options: CacheQueryOptions?
        get() = definedExternally
        set(value) = definedExternally
}

public inline fun CacheBatchOperation(type: String? = null, request: Request? = null, response: Response? = null, options: CacheQueryOptions? = null): CacheBatchOperation {
    val o = js("({})")

    o["type"] = type
    o["request"] = request
    o["response"] = response
    o["options"] = options

    return o
}

public external abstract class CacheStorage {
    fun match(request: dynamic, options: CacheQueryOptions = definedExternally): Promise<dynamic>
    fun has(cacheName: String): Promise<Boolean>
    fun open(cacheName: String): Promise<Cache>
    fun delete(cacheName: String): Promise<Boolean>
    fun keys(): Promise<dynamic>
}

public external open class FunctionalEvent : ExtendableEvent {
}

public external @marker interface UnionClientOrMessagePortOrServiceWorker {
}

