package bbee.developer.jp.assemble_pc.models

import kotlinx.serialization.Serializable

@Serializable
data class Profile(
    val userName: String? = null,
    val userEmail: String? = null,
)