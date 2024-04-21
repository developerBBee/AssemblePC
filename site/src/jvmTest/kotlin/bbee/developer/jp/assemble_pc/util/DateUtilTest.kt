package bbee.developer.jp.assemble_pc.util

import kotlinx.datetime.LocalDateTime
import kotlin.math.floor
import kotlin.test.Test
import kotlin.test.assertEquals

class DateUtilTest {
    private val testDateTime = LocalDateTime(
        year = 2000,
        monthNumber = 12,
        dayOfMonth = 31,
        hour = 23,
        minute = 59,
        second = 59,
        nanosecond = 123456
    )

    @Test
    fun plusDaysTest() {
        val actualDateTime = testDateTime.plusDays(1)
        val expectDateTime = LocalDateTime(
            year = 2001,
            monthNumber = 1,
            dayOfMonth = 1,
            hour = 23,
            minute = 59,
            second = 59,
            nanosecond = 123456
        )
        assertEquals(expectDateTime, actualDateTime)
    }

    @Test
    fun set4HourTest() {
        val actualDateTime = testDateTime.set4Hour()
        val expectDateTime = LocalDateTime(
            year = 2000,
            monthNumber = 12,
            dayOfMonth = 31,
            hour = 4,
            minute = 0
        )
        assertEquals(expectDateTime, actualDateTime)
    }


    @Test
    fun minusTest() {
        val minusDateTime = LocalDateTime(
            year = 2000,
            monthNumber = 12,
            dayOfMonth = 29,
            hour = 20,
            minute = 50,
            second = 30,
            nanosecond = 654321
        )

        // millisecond以下は切り捨てられる（負数の切り捨てが発生すると-1秒される）
        val nanoDiffLong = floor((123456 - 654321).toDouble() / 1000.0 / 1000.0).toLong()

        val expect = ((((31-29) * 24 + (23-20)) * 60 + (59-50)) * 60 + (59-30)) * 1000 + nanoDiffLong

        val actual = testDateTime.minus(minusDateTime)
        assertEquals(expect, actual)
    }
}