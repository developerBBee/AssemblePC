package bbee.developer.jp.assemble_pc.models

import kotlinx.serialization.Serializable

@Serializable
data class Price(
    val value: Int,
) {
    operator fun plus(other: Price): Price {
        return Price(value + other.value)
    }

    operator fun plus(other: Int): Price {
        return Price(value + other)
    }

    operator fun minus(other: Price): Price {
        return Price(value - other.value)
    }

    operator fun minus(other: Int): Price {
        return Price(value - other)
    }

    operator fun times(other: Int): Price {
        return Price(value * other)
    }

    override fun equals(other: Any?): Boolean {
        if (other is Price) {
            return other.value == this.value
        }
        return false
    }

    fun yen(): String {
        return "¥ $value"
    }

    override fun hashCode(): Int {
        return value
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