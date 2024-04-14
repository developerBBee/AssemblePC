package bbee.developer.jp.assemble_pc.api

import bbee.developer.jp.assemble_pc.api.util.getBody
import bbee.developer.jp.assemble_pc.api.util.setBody
import bbee.developer.jp.assemble_pc.database.H2DB
import bbee.developer.jp.assemble_pc.model.Assembly
import com.varabyte.kobweb.api.Api
import com.varabyte.kobweb.api.ApiContext
import com.varabyte.kobweb.api.data.getValue

@Api(routeOverride = "add_item")
suspend fun addAssembly(context: ApiContext) {
    context.runCatching {
        req.cookies["session"]?.also { session ->
            req.getBody<Assembly>()?.also { assembly ->
                data.getValue<H2DB>().addAssembly(session, assembly).also { result ->
                    res.setBody(result)
                }
            }
        }
    }.onFailure { e ->
        context.logger.error("addAssembly API EXCEPTION: $e")
        context.res.setBody(e.message ?: "addAssembly API error")
    }
}