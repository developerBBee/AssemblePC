package bbee.developer.jp.assemble_pc.util

import bbee.developer.jp.assemble_pc.firebase.auth
import kotlinx.coroutines.flow.filter

suspend fun signInAnonymous() {
    // If no user in local storage, run signInAnonymously.
    auth.authStateChanged
        .filter { it == null }
        .collect {
            println("signInAnonymously")
            auth.signInAnonymously()
            println("call createAnonymousUser()")

            createAnonymousUser()
                .takeIf { it }
                ?.run { println("createAnonymousUser() succeeded") }
        }
}