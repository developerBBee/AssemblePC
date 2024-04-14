package bbee.developer.jp.assemble_pc.common.util

import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

private val TIMEZONE = TimeZone.currentSystemDefault()

val currentDateTime get() = Clock.System.now().toLocalDateTime(TIMEZONE)