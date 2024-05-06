package bbee.developer.jp.assemble_pc.api

import bbee.developer.jp.assemble_pc.api.util.getUid
import bbee.developer.jp.assemble_pc.api.util.onFailureCommonResponse
import bbee.developer.jp.assemble_pc.api.util.setBody
import bbee.developer.jp.assemble_pc.database.H2DB
import bbee.developer.jp.assemble_pc.models.ItemCategory
import com.varabyte.kobweb.api.Api
import com.varabyte.kobweb.api.ApiContext
import com.varabyte.kobweb.api.data.getValue

@Api(routeOverride = "get_items")
suspend fun getItems(context: ApiContext) {
    context.runCatching {
        getUid() // user authentication

        val category = req.params["category"]?.let { name ->
            ItemCategory.entries.first { it.name == name }
        } ?: throw IllegalArgumentException("Invalid ItemCategory specification")
        val skip = req.params["skip"]?.toLong() ?: 0L

        context.data.getValue<H2DB>().getItems(category = category, skip = skip)
            .also { items ->
                context.res.setBody(items)
            }
    }.onFailureCommonResponse(context = context, functionName = "getItems")
}