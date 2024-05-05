package bbee.developer.jp.assemble_pc.util

import kotlin.test.Test
import kotlin.test.assertEquals

class RegexTest {
    @Test
    fun rankingUrlRegexTest() {
        val expect = "https://kakaku.com/item/J0000026025/"
        val testString = """<a href="$expect" class="rkgBoxLink">"""

        val actual = KakakuRegex.RANKING_URL.find(testString)?.let { result ->
            result.groupValues[1]
        }

        assertEquals(expect, actual)
    }
}