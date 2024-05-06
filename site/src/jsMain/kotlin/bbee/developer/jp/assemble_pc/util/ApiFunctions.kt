package bbee.developer.jp.assemble_pc.util

import bbee.developer.jp.assemble_pc.firebase.auth
import bbee.developer.jp.assemble_pc.models.Item
import bbee.developer.jp.assemble_pc.models.ItemCategory
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

suspend fun getItems(skip: Int, category: ItemCategory): List<Item> {
    return runCatching {
        auth.currentUser?.getIdToken(false)
            ?.let {
                window.api.tryGet(
                    apiPath = "get_items?skip=$skip&category=${category.name}",
                    headers = getApiHeader(it)
                )?.decodeToString()?.parseData<List<Item>>()
                    ?: throw IllegalStateException(message = "getItems() result is null")
            } ?: throw IllegalStateException(message = "getItems() user or token is null")
    }.getOrElse { e ->
        println(e.message)
        emptyList()
    }
}