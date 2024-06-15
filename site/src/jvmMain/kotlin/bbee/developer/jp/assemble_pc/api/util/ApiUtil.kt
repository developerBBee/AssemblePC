package bbee.developer.jp.assemble_pc.api.util

import com.google.firebase.auth.FirebaseAuth
import com.varabyte.kobweb.api.ApiContext
import com.varabyte.kobweb.api.http.Request
import com.varabyte.kobweb.api.http.Response
import com.varabyte.kobweb.api.http.setBodyText
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

inline fun <reified T> Request.getBody(): T? {
    return body?.decodeToString()?.let { Json.decodeFromString(it) }
}

inline fun <reified T> Response.setBody(data: T) {
    setBodyText(Json.encodeToString(data))
}

inline fun <reified T> Result<T>.onFailureCommonResponse(
    context: ApiContext,
    functionName: String
): Result<T> = onFailure { e ->
    context.logger.error("$functionName API EXCEPTIN: $e")
    context.res.setBody(e.message ?: "$functionName API error")
}

fun ApiContext.getUid(): String = req.headers["Authorization"]
    ?.first()
    ?.removePrefix("Bearer ")
    ?.let { token ->
        FirebaseAuth.getInstance().verifyIdToken(token).uid
    }
    ?: throw IllegalArgumentException("The result of parsing the authentication in the request header was null.")

fun deleteUser(uid: String) {
    FirebaseAuth.getInstance().deleteUser(uid)
}