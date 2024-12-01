package jp.developer.bbee.assemblepc.models

import kotlinx.serialization.Serializable

@Serializable
data class Profile(
    val userName: String? = null,
    val userEmail: String? = null,
)