// WITH_RUNTIME
// WITH_COROUTINES
// TREAT_AS_ONE_FILE
import kotlin.coroutines.*
import kotlin.coroutines.intrinsics.*
suspend fun suspendHere() = ""

fun builder(c: suspend () -> Unit) {
    c.startCoroutine(EmptyContinuation)
}

fun box(): String {

    for (i in 1..3) {
        builder {
            if (suspendHere() != "OK") throw java.lang.RuntimeException("fail 1")
        }
    }

    return "OK"
}

// 2 GETSTATIC kotlin/Unit.INSTANCE
// 1 GETSTATIC EmptyContinuation.Companion
// 3 GETSTATIC kotlin\/coroutines\/EmptyCoroutineContext.INSTANCE
// 6 GETSTATIC
