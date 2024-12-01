package jp.developer.bbee.assemblepc.util

inline fun <T, R> T.runIf(condition: Boolean, block: T.() -> R): R? {
    return if (condition) block() else null
}