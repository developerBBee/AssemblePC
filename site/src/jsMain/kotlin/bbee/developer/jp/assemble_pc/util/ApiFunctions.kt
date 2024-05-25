package bbee.developer.jp.assemble_pc.util

import bbee.developer.jp.assemble_pc.firebase.auth
import bbee.developer.jp.assemble_pc.models.Assembly
import bbee.developer.jp.assemble_pc.models.AssemblyForPost
import bbee.developer.jp.assemble_pc.models.AssemblyId
import bbee.developer.jp.assemble_pc.models.Item
import bbee.developer.jp.assemble_pc.models.ItemCategory
import com.varabyte.kobweb.browser.api
import kotlinx.browser.window
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

suspend fun createAnonymousUser(): Boolean {
    return runCatching {
        auth.currentUser?.getIdToken(false)
            ?.let { token ->
                window.api.tryPost(
                    apiPath = "create_user_anonymous",
                    headers = getApiHeader(token)
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
            ?.let { token ->
                window.api.tryGet(
                    apiPath = "get_items?skip=$skip&category=${category.name}",
                    headers = getApiHeader(token)
                )?.decodeToString()?.parseData<List<Item>>()
                    ?: throw IllegalStateException(message = "getItems() result is null")
            } ?: throw IllegalStateException(message = "getItems() user or token is null")
    }.getOrElse { e ->
        println(e.message)
        emptyList()
    }
}

suspend fun getCurrentAssembly(): Assembly? {
    return runCatching {
        auth.currentUser?.getIdToken(false)
            ?.let { token ->
                window.api.tryGet(
                    apiPath = "get_current_assembly",
                    headers = getApiHeader(token)
                )?.decodeToString()?.parseData<Assembly>()
                    ?: throw IllegalStateException(message = "getCurrentAssembly() result is null")
            } ?: throw IllegalStateException(message = "getCurrentAssembly() user or token is null")
    }.getOrElse { e ->
        println(e.message)
        createAssembly(assemblyName = null, referenceAssemblyId = null)
    }
}

suspend fun createAssembly(assemblyName: String?, referenceAssemblyId: AssemblyId?): Assembly? {
    return runCatching {
        auth.currentUser?.getIdToken(false)
            ?.let { token ->
                val p1 = assemblyName ?: ""
                val p2 = referenceAssemblyId ?: ""

                window.api.tryGet(
                    apiPath = "create_assembly?assemblyName=$p1&referenceAssemblyId=$p2",
                    headers = getApiHeader(token)
                )?.decodeToString()?.parseData<Assembly>()
                    ?: throw IllegalStateException(message = "createAssembly() result is null")
            } ?: throw IllegalStateException(message = "createAssembly() user or token is null")
    }.getOrElse { e ->
        println(e.message)
        null
    }
}

suspend fun updateAssembly(assembly: Assembly): Boolean {
    return runCatching {
        auth.currentUser?.getIdToken(false)
            ?.let { token ->
                window.api.tryPost(
                    apiPath = "update_assembly",
                    headers = getApiHeader(token),
                    body = Json.encodeToString(assembly).encodeToByteArray()
                )?.decodeToString()?.parseData<Boolean>()
                    ?: throw IllegalStateException(message = "updateAssembly() result is null")
            } ?: throw IllegalStateException(message = "updateAssembly() user or token is null")
    }.getOrElse { e ->
        println(e.message)
        false
    }
}

suspend fun addAssemblyDetail(assembly: AssemblyForPost): Assembly? {
    return runCatching {
        auth.currentUser?.getIdToken(false)
            ?.let { token ->
                window.api.tryPost(
                    apiPath = "add_assembly_detail",
                    headers = getApiHeader(token),
                    body = Json.encodeToString(assembly).encodeToByteArray()
                )?.decodeToString()?.parseData<Assembly>()
                    ?: throw IllegalStateException(message = "addAssemblyDetail() result is null")
            } ?: throw IllegalStateException(message = "addAssemblyDetail() user or token is null")
    }.getOrElse { e ->
        println(e.message)
        null
    }
}

suspend fun deleteAssemblyDetail(assembly: AssemblyForPost): Boolean {
    return runCatching {
        auth.currentUser?.getIdToken(false)
            ?.let { token ->
                window.api.tryPost(
                    apiPath = "delete_assembly_detail",
                    headers = getApiHeader(token),
                    body = Json.encodeToString(assembly).encodeToByteArray()
                )?.decodeToString()?.parseData<Boolean>()
                    ?: throw IllegalStateException(message = "deleteAssemblyDetail() result is null")
            } ?: throw IllegalStateException(message = "deleteAssemblyDetail() user or token is null")
    }.getOrElse { e ->
        println(e.message)
        false
    }
}

suspend fun getMyPublishedAssemblies(skip: Int): List<Assembly> {
    return runCatching {
        auth.currentUser?.getIdToken(false)
            ?.let { token ->
                window.api.tryGet(
                    apiPath = "get_my_published_assemblies?skip=$skip",
                    headers = getApiHeader(token),
                )?.decodeToString()?.parseData<List<Assembly>>()
                    ?: throw IllegalStateException(message = "getMyPublishedAssemblies() result is null")
            } ?: throw IllegalStateException(message = "getMyPublishedAssemblies() user or token is null")
    }.getOrElse { e ->
        println(e.message)
        emptyList()
    }
}