package bbee.developer.jp.assemble_pc.models

import kotlinx.serialization.Serializable

@Serializable
data class Price(
    val value: Int,
) {
    override fun toString(): String {
        return "¥ $value"
    }

    companion object {
        fun from(value: String): Price {
            return value
                .replace("¥", "")
                .replace(",", "")
                .trim()
                .toInt()
                .let { Price(it) }
        }
    }
}