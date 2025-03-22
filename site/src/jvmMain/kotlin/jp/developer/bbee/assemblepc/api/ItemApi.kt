package jp.developer.bbee.assemblepc.api

import jp.developer.bbee.assemblepc.api.util.getUid
import jp.developer.bbee.assemblepc.api.util.onFailureCommonResponse
import jp.developer.bbee.assemblepc.api.util.setBody
import jp.developer.bbee.assemblepc.models.ItemCategory
import com.varabyte.kobweb.api.Api
import com.varabyte.kobweb.api.ApiContext
import com.varabyte.kobweb.api.data.getValue
import jp.developer.bbee.assemblepc.firebase.store.FirestoreRepository

@Api(routeOverride = "get_items")
suspend fun getItems(context: ApiContext) {
    context.runCatching {
        getUid() // user authentication

        val category = req.params["category"]?.let { name ->
            ItemCategory.entries.first { it.name == name }
        } ?: throw IllegalArgumentException("Invalid ItemCategory specification")
        val skip = req.params["skip"]?.toLong() ?: 0L

        data.getValue<FirestoreRepository>().getItems(
            category = category,
            skip = skip,
            limit = 20,
        )
            .also { items ->
                context.res.setBody(items)
            }
    }.onFailureCommonResponse(context = context, functionName = "getItems")
}