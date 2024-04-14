package bbee.developer.jp.assemble_pc.api

import bbee.developer.jp.assemble_pc.api.util.setBody
import bbee.developer.jp.assemble_pc.database.H2DB
import com.varabyte.kobweb.api.Api
import com.varabyte.kobweb.api.ApiContext
import com.varabyte.kobweb.api.data.getValue

@Api(routeOverride = "create_user_anonymous")
suspend fun createUserAnonymous(context: ApiContext) {
    context.runCatching {
        data.getValue<H2DB>()
            .addUserAnonymous()
            .also { result ->
                res.setBody(result)
            }
    }.onFailure { e ->
        context.logger.error("createUserAnonymous API EXCEPTION: $e")
        context.res.setBody(e.message ?: "createUserAnonymous API error")
    }
}