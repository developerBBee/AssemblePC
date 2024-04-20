package bbee.developer.jp.assemble_pc.util

import bbee.developer.jp.assemble_pc.firebase.auth
import com.varabyte.kobweb.browser.api
import kotlinx.browser.window

suspend fun createAnonymousUser(): Boolean {
    return runCatching {
        auth.currentUser?.getIdToken(false)
            ?.let {
                window.api.tryPost(
                    apiPath = "create_user_anonymous",
                    headers = getApiHeader(it)
                )?.decodeToString()?.parseData<Boolean>()
                    ?: throw IllegalStateException(message = "createAnonymousUser() result is null")
            } ?: throw IllegalStateException(message = "createAnonymousUser() user or token is null")
    }.getOrElse { e ->
        println(e.message)
        false
    }
}