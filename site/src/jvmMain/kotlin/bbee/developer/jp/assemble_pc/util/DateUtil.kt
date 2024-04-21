package bbee.developer.jp.assemble_pc.util

import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Duration.Companion.days

private val TIMEZONE = TimeZone.currentSystemDefault()

val currentDateTime get() = Clock.System.now().toLocalDateTime(TIMEZONE)

fun LocalDateTime.plusDays(days: Int): LocalDateTime {
    return this.toInstant(TIMEZONE).plus(days.days).toLocalDateTime(TIMEZONE)
}

fun LocalDateTime.set4Hour(): LocalDateTime {
    return LocalDateTime(this.year, this.month, this.dayOfMonth, 4, 0)
}

fun LocalDateTime.minus(other: LocalDateTime): Long {
    return (this.toInstant(TIMEZONE) - other.toInstant(TIMEZONE)).inWholeMilliseconds
}