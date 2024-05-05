package bbee.developer.jp.assemble_pc.util

import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.datetime.toJavaLocalDateTime
import kotlinx.datetime.toLocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.time.Duration.Companion.days

private val TIMEZONE = TimeZone.currentSystemDefault()
private val DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd")

val currentDateTime get() = Clock.System.now().toLocalDateTime(TIMEZONE)
val currentDateString: String get() = currentDateTime.toJavaLocalDateTime().format(DATE_FORMATTER)

fun LocalDateTime.plusDays(days: Int): LocalDateTime {
    return this.toInstant(TIMEZONE).plus(days.days).toLocalDateTime(TIMEZONE)
}

fun LocalDateTime.set4Hour(): LocalDateTime {
    return LocalDateTime(this.year, this.month, this.dayOfMonth, 4, 0)
}

fun LocalDateTime.minus(other: LocalDateTime): Long {
    return (this.toInstant(TIMEZONE) - other.toInstant(TIMEZONE)).inWholeMilliseconds
}