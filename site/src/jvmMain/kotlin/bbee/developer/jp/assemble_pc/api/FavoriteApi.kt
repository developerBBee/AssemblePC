package bbee.developer.jp.assemble_pc.api

import bbee.developer.jp.assemble_pc.api.util.getUid
import bbee.developer.jp.assemble_pc.api.util.onFailureCommonResponse
import bbee.developer.jp.assemble_pc.api.util.setBody
import bbee.developer.jp.assemble_pc.database.H2DB
import bbee.developer.jp.assemble_pc.models.AssemblyId
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
