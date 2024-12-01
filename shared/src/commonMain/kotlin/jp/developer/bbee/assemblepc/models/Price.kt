package jp.developer.bbee.assemblepc.models

import kotlinx.serialization.Serializable

@Serializable
data class Price(
    val value: Int,
) : Comparable<Price> {
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

    override operator fun compareTo(other: Price): Int {
        return value.compareTo(other.value)
    }

    override fun equals(other: Any?): Boolean {
        if (other is Price) {
            return value == other.value
        }
        return false
    }

    override fun hashCode(): Int {
        return value
    }

    fun yen(): String {
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