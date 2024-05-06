package bbee.developer.jp.assemble_pc.util

import bbee.developer.jp.assemble_pc.models.Price
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

    @Test
    fun itemNameRegexTest() {
        val expect = "Ryzen 7 5700X BOX"
        val testString = """<h2 itemprop="name">$expect</h2>"""

        val actual = KakakuRegex.ITEM_NAME.find(testString)?.let { result ->
            result.groupValues[1]
        }

        assertEquals(expect, actual)
    }

    @Test
    fun itemPriceRegexTest() {
        val expect = Price.from("¥22,800")
        val testString = """<span class="priceTxt">&yen;22,800</span>""".replaceHtmlEntity()

        val actual = KakakuRegex.PRICE_TEXT.find(testString)?.let { result ->
            Price.from(result.groupValues[1])
        }

        assertEquals(expect, actual)
    }
}