package bbee.developer.jp.assemble_pc.api

import bbee.developer.jp.assemble_pc.api.util.getBody
import bbee.developer.jp.assemble_pc.api.util.getUid
import bbee.developer.jp.assemble_pc.api.util.onFailureCommonResponse
import bbee.developer.jp.assemble_pc.api.util.setBody
import bbee.developer.jp.assemble_pc.database.H2DB
import bbee.developer.jp.assemble_pc.models.Profile
import com.varabyte.kobweb.api.Api
import com.varabyte.kobweb.api.ApiContext
import com.varabyte.kobweb.api.data.getValue

@Api(routeOverride = "create_user_anonymous")
suspend fun createUserAnonymous(context: ApiContext) {
    val uid = runCatching {
        context.getUid()
    }.getOrElse { e ->
        context.logger.error("createUserAnonymous API EXCEPTION: $e")
        context.res.setBody(e.message ?: "createUserAnonymous API error")
        return
    }

    context.runCatching {
        data.getValue<H2DB>()
            .addUserAnonymous(uid)
            .also { result ->
                res.setBody(result)
            }
    }.onFailureCommonResponse(context = context, functionName = "createUserAnonymous")
}

@Api(routeOverride = "get_my_profile")
suspend fun getMyProfile(context: ApiContext) {
    context.runCatching {
        getUid().also { uid ->
            data.getValue<H2DB>()
                .getUserProfile(uid = uid)
                .also { result -> res.setBody(result) }
        }
    }.onFailureCommonResponse(context = context, functionName = "getMyProfile")
}

@Api(routeOverride = "update_my_profile")
suspend fun updateMyProfile(context: ApiContext) {
    context.runCatching {
        getUid().also { uid ->
            req.getBody<Profile>()?.also { profile ->
                data.getValue<H2DB>()
                    .updateUserProfile(uid = uid, profile = profile)
                    .also { result -> res.setBody(result) }
            }
        }
    }.onFailureCommonResponse(context = context, functionName = "getMyProfile")
}