package jp.developer.bbee.assemblepc.util

import jp.developer.bbee.assemblepc.firebase.auth
import jp.developer.bbee.assemblepc.models.Assembly
import jp.developer.bbee.assemblepc.models.AssemblyForPost
import jp.developer.bbee.assemblepc.models.AssemblyId
import jp.developer.bbee.assemblepc.models.Item
import jp.developer.bbee.assemblepc.models.ItemCategory
import jp.developer.bbee.assemblepc.models.Profile
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

suspend fun preRegisterUidUpdate(): Boolean {
    return runCatching {
        auth.currentUser?.getIdToken(false)
            ?.let { token ->
                window.api.tryPost(
                    apiPath = "pre_register_uid_update",
                    headers = getApiHeader(token)
                )?.decodeToString()?.parseData<Boolean>()
                    ?: throw IllegalStateException(message = "preRegisterUidUpdate() result is null")
            } ?: throw IllegalStateException(message = "preRegisterUidUpdate() user or token is null")
    }.getOrElse { e ->
        println(e.message)
        false
    }
}

suspend fun updateUserId(uid: String): Boolean {
    return runCatching {
        auth.currentUser?.getIdToken(false)
            ?.let { token ->
                window.api.tryPost(
                    apiPath = "update_user_id",
                    headers = getApiHeader(token),
                    body = Json.encodeToString(uid).encodeToByteArray()
                )?.decodeToString()?.parseData<Boolean>()
                    ?: throw IllegalStateException(message = "updateUserId() result is null")
            } ?: throw IllegalStateException(message = "updateUserId() user or token is null")
    }.getOrElse { e ->
        println(e.message)
        false
    }
}

suspend fun getMyProfile(): Profile? {
    return runCatching {
        auth.currentUser?.getIdToken(false)
            ?.let { token ->
                window.api.tryGet(
                    apiPath = "get_my_profile",
                    headers = getApiHeader(token)
                )?.decodeToString()?.parseData<Profile>()
                    ?: throw IllegalStateException(message = "getMyProfile() result is null")
            } ?: throw IllegalStateException(message = "getMyProfile() user or token is null")
    }.getOrElse { e ->
        println(e.message)
        null
    }
}

suspend fun updateMyProfile(profile: Profile): Boolean {
    return runCatching {
        auth.currentUser?.getIdToken(false)
            ?.let { token ->
                window.api.tryPost(
                    apiPath = "update_my_profile",
                    headers = getApiHeader(token),
                    body = Json.encodeToString(profile).encodeToByteArray()
                )?.decodeToString()?.parseData<Boolean>()
                    ?: throw IllegalStateException(message = "updateMyProfile() result is null")
            } ?: throw IllegalStateException(message = "updateMyProfile() user or token is null")
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

suspend fun getMyUnpublishedAssemblies(skip: Int): List<Assembly> {
    return runCatching {
        auth.currentUser?.getIdToken(false)
            ?.let { token ->
                window.api.tryGet(
                    apiPath = "get_my_unpublished_assemblies?skip=$skip",
                    headers = getApiHeader(token),
                )?.decodeToString()?.parseData<List<Assembly>>()
                    ?: throw IllegalStateException(message = "getMyUnpublishedAssemblies() result is null")
            } ?: throw IllegalStateException(message = "getMyUnpublishedAssemblies() user or token is null")
    }.getOrElse { e ->
        println(e.message)
        emptyList()
    }
}

suspend fun getPublishedAssemblies(skip: Int): List<Assembly> {
    return runCatching {
        auth.currentUser?.getIdToken(false)
            ?.let { token ->
                window.api.tryGet(
                    apiPath = "get_published_assemblies?skip=$skip",
                    headers = getApiHeader(token),
                )?.decodeToString()?.parseData<List<Assembly>>()
                    ?: throw IllegalStateException(message = "getPublishedAssemblies() result is null")
            } ?: throw IllegalStateException(message = "getPublishedAssemblies() user or token is null")
    }.getOrElse { e ->
        println(e.message)
        emptyList()
    }
}

suspend fun getMyFavoriteAssemblyIdList(): List<AssemblyId> {
    return runCatching {
        auth.currentUser?.getIdToken(false)
            ?.let { token ->
                window.api.tryGet(
                    apiPath = "get_my_favorite_assembly_id_list",
                    headers = getApiHeader(token),
                )?.decodeToString()?.parseData<List<AssemblyId>>()
                    ?: throw IllegalStateException(message = "getMyFavoriteAssemblyIdList() result is null")
            } ?: throw IllegalStateException(message = "getMyFavoriteAssemblyIdList() user or token is null")
    }.getOrElse { e ->
        println(e.message)
        emptyList()
    }
}

suspend fun addFavoriteAssembly(assemblyId: AssemblyId): Boolean {
    return runCatching {
        auth.currentUser?.getIdToken(false)
            ?.let { token ->
                window.api.tryGet(
                    apiPath = "add_favorite_assembly?assemblyId=${assemblyId.id}",
                    headers = getApiHeader(token),
                )?.decodeToString()?.parseData<Boolean>()
                    ?: throw IllegalStateException(message = "addFavoriteAssembly() result is null")
            } ?: throw IllegalStateException(message = "addFavoriteAssembly() user or token is null")
    }.getOrElse { e ->
        println(e.message)
        false
    }
}

suspend fun removeFavoriteAssembly(assemblyId: AssemblyId): Boolean {
    return runCatching {
        auth.currentUser?.getIdToken(false)
            ?.let { token ->
                window.api.tryGet(
                    apiPath = "remove_favorite_assembly?assemblyId=${assemblyId.id}",
                    headers = getApiHeader(token),
                )?.decodeToString()?.parseData<Boolean>()
                    ?: throw IllegalStateException(message = "removeFavoriteAssembly() result is null")
            } ?: throw IllegalStateException(message = "removeFavoriteAssembly() user or token is null")
    }.getOrElse { e ->
        println(e.message)
        false
    }
}

suspend fun getMyFavoriteAssemblies(skip: Int): List<Assembly> {
    return runCatching {
        auth.currentUser?.getIdToken(false)
            ?.let { token ->
                window.api.tryGet(
                    apiPath = "get_my_favorite_assemblies?skip=$skip",
                    headers = getApiHeader(token),
                )?.decodeToString()?.parseData<List<Assembly>>()
                    ?: throw IllegalStateException(message = "getMyFavoriteAssemblies() result is null")
            } ?: throw IllegalStateException(message = "getMyFavoriteAssemblies() user or token is null")
    }.getOrElse { e ->
        println(e.message)
        emptyList()
    }
}