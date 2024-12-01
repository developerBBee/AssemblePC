package jp.developer.bbee.assemblepc.util

import kotlinx.serialization.json.Json

fun getApiHeader(token: String) = mapOf("Authorization" to "Bearer $token")

inline fun <reified T> String.parseData(): T {
    return Json.decodeFromString(this)
}