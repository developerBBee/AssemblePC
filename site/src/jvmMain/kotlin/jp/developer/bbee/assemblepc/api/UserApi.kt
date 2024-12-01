package jp.developer.bbee.assemblepc.api

import jp.developer.bbee.assemblepc.api.util.deleteUser
import jp.developer.bbee.assemblepc.api.util.getBody
import jp.developer.bbee.assemblepc.api.util.getUid
import jp.developer.bbee.assemblepc.api.util.onFailureCommonResponse
import jp.developer.bbee.assemblepc.api.util.setBody
import jp.developer.bbee.assemblepc.database.H2DB
import jp.developer.bbee.assemblepc.models.Profile
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

@Api(routeOverride = "pre_register_uid_update")
suspend fun preRegisterUidUpdate(context: ApiContext) {
    context.runCatching {
        getUid().also { uid ->
            data.getValue<H2DB>()
                .preRegisterUidUpdate(uid = uid)
                .also { result -> res.setBody(result) }
        }
    }
}

@Api(routeOverride = "update_user_id")
suspend fun updateUserId(context: ApiContext) {
    context.runCatching {
        getUid().also { uid ->
            req.getBody<String>()?.also { anonymousUid ->
                data.getValue<H2DB>()
                    .updateUserId(oldUid = anonymousUid, newUid = uid)
                    .also { result ->
                        if (result) {
                            logger.debug("deleteUser: $anonymousUid")
                            deleteUser(anonymousUid)
                        }
                        res.setBody(result)
                    }
            }
        }
    }.onFailureCommonResponse(context = context, functionName = "updateUserId")
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