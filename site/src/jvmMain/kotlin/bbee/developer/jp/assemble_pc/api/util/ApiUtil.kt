package bbee.developer.jp.assemble_pc.api.util

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
