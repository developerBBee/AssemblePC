package jp.developer.bbee.assemblepc.util

import jp.developer.bbee.assemblepc.firebase.auth
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.filterNotNull

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

suspend fun collectOnUidChanged(block: suspend () -> Unit) {
    auth.authStateChanged
        .filterNotNull()
        .distinctUntilChangedBy { it.uid }
        .collect {
            block()
        }
}