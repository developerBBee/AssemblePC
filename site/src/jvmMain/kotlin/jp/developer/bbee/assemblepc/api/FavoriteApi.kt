package jp.developer.bbee.assemblepc.api

import jp.developer.bbee.assemblepc.api.util.getUid
import jp.developer.bbee.assemblepc.api.util.onFailureCommonResponse
import jp.developer.bbee.assemblepc.api.util.setBody
import jp.developer.bbee.assemblepc.database.H2DB
import jp.developer.bbee.assemblepc.models.AssemblyId
import com.varabyte.kobweb.api.Api
import com.varabyte.kobweb.api.ApiContext
import com.varabyte.kobweb.api.data.getValue

@Api(routeOverride = "add_favorite_assembly")
suspend fun addFavoriteAssembly(context: ApiContext) {
    context.runCatching {
        getUid().also { uid ->
            req.params["assemblyId"]?.toIntOrNull()?.also { reqId ->
                AssemblyId(reqId).also { assemblyId ->
                    data.getValue<H2DB>().addFavoriteAssembly(uid, assemblyId)
                        .also { isSuccess -> res.setBody(isSuccess) }
                }
            } ?: throw IllegalArgumentException("Invalid assemblyId specification")
        }
    }.onFailureCommonResponse(context = context, functionName = "addFavoriteAssembly")
}

@Api(routeOverride = "remove_favorite_assembly")
suspend fun removeFavoriteAssembly(context: ApiContext) {
    context.runCatching {
        getUid().also { uid ->
            req.params["assemblyId"]?.toIntOrNull()?.also { reqId ->
                AssemblyId(reqId).also { assemblyId ->
                    data.getValue<H2DB>().removeFavoriteAssembly(uid, assemblyId)
                        .also { isSuccess -> res.setBody(isSuccess) }
                }
            } ?: throw IllegalArgumentException("Invalid assemblyId specification")
        }
    }.onFailureCommonResponse(context = context, functionName = "removeFavoriteAssembly")
}
