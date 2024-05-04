package bbee.developer.jp.assemble_pc.models

import kotlinx.serialization.Serializable

@Serializable
data class Price(
    val value: Int,
) {
    override fun toString(): String {
        return "¥ $value"
    }
}