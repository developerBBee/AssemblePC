package bbee.developer.jp.assemble_pc.util

import bbee.developer.jp.assemble_pc.models.ItemCategory
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

    @Test
    fun getMotherBoardDetailTest() {
        val expect = "ASRock\nSocketAM4\nMicroATX\nDIMM DDR4"
        val testString1 = """
            <th class="itemviewColor03b textL">CPUソケット</th><td>SocketAM4</td>
            <th class="itemviewColor03b textL">フォームファクタ</th><td class="lineNone"><a href="https://kakaku.com/pc/motherboard/itemlist.aspx?pdf_Spec101=6">MicroATX</a></td>
            <th class="itemviewColor03b textL">詳細メモリタイプ</th><td class="lineNone"><a href="javascript:void(0);" onclick="changeLocation('pdf_Spec114=1');return false;">DIMM DDR4</a></td>
            <span class="digestMakerName">ASRock</span>
            """.trimIndent()
        val actual1 = getDetail(testString1, ItemCategory.MOTHER_BOARD).desc()
        assertEquals(expect, actual1)

        val testString2 = """
            <th class="itemviewColor03b textL">CPUソケット</th><td>SocketAM4</td>
            <th class="itemviewColor03b textL">フォームファクタ</th><td>MicroATX</td>
            <th class="itemviewColor03b textL">詳細メモリタイプ</th><td>DIMM DDR4</td>
            <span class="digestMakerName">ASRock</span>
            """.trimIndent()
        val actual2 = getDetail(testString2, ItemCategory.MOTHER_BOARD).desc()
        assertEquals(expect, actual2)
    }

    @Test
    fun getPSUDetailTest() {
        val expect = "FSP\nフォームファクタ：SFX<br>規格：ATX12V V3.0\n850 W\nGOLD"
        val testString = """
            <th class="itemviewColor03b textL">対応規格</th><td>フォームファクタ：SFX<br>規格：ATX12V V3.0</td>
            <th class="itemviewColor03b textL">電源容量</th><td class="lineNone"><a href="https://kakaku.com/pc/power-supply/itemlist.aspx?pdf_Spec301=850">850 W</a></td>
            <th class="itemviewColor03b textL">80PLUS認証&nbsp;<img src="https://img1.kakaku.k-img.com/images/balloonhelp/explain_icn.gif" alt="" width="30" height="15" onClick="showHelp(this, 'Balloon-Spec101')" class="helpBT explainIcn"></th><td class="lineNone"><a href="https://kakaku.com/pc/power-supply/itemlist.aspx?pdf_Spec101=1">GOLD</a></td>
            <span class="digestMakerName">FSP</span>
            """.trimIndent()
        val actual = getDetail(testString, ItemCategory.PSU).desc()
        assertEquals(expect, actual)
    }

    @Test
    fun getBDDriveDetailTest() {
        val expectNoUltraHD = "バッファロー\n外付け\nUSB3.2 Gen1(USB3.0)"
        val testStringNoUltraHD = """
            <th class="itemviewColor03b textL">設置方式</th><td class="lineNone"><a href="https://kakaku.com/pc/blu-ray-drive/itemlist.aspx?pdf_Spec101=2">外付け</a></td>
            <th class="itemviewColor03b textL">接続インターフェース</th><td>USB3.2 Gen1(USB3.0)</td>
            <th class="itemviewColor03b textL">Ultra HD Blu-ray&nbsp;<img src="https://img1.kakaku.k-img.com/images/balloonhelp/explain_icn.gif" alt="" width="30" height="15" onClick="showHelp(this, 'Balloon-Spec028')" class="helpBT explainIcn"></th><td>　</td>
            <th class="itemviewColor03b textL">BD-ROM</th><td class="lineNone"><a href="https://kakaku.com/pc/blu-ray-drive/itemlist.aspx?pdf_Spec011=1">○</a></td>
            <span class="digestMakerName">バッファロー</span>
            """.trimIndent()
        val actualNoUltraHD = getDetail(testStringNoUltraHD, ItemCategory.BD_DRIVE).desc()
        assertEquals(expectNoUltraHD, actualNoUltraHD)

        val expectHasUltraHD = "パイオニア\n外付け\nUSB3.2 Gen1(USB3.0)\nUltra HD Blu-ray"
        val testStringHasUltraHD = """
            <th class="itemviewColor03b textL">設置方式</th><td class="lineNone"><a href="https://kakaku.com/pc/blu-ray-drive/itemlist.aspx?pdf_Spec101=2">外付け</a></td>
            <th class="itemviewColor03b textL">接続インターフェース</th><td>USB3.2 Gen1(USB3.0)</td>
            <th class="itemviewColor03b textL">Ultra HD Blu-ray&nbsp;<img src="https://img1.kakaku.k-img.com/images/balloonhelp/explain_icn.gif" alt="" width="30" height="15" onClick="showHelp(this, 'Balloon-Spec028')" class="helpBT explainIcn"></th><td class="lineNone"><a href="https://kakaku.com/pc/blu-ray-drive/itemlist.aspx?pdf_Spec028=1">○</a></td>
            <th class="itemviewColor03b textL">BD-ROM</th><td class="lineNone"><a href="https://kakaku.com/pc/blu-ray-drive/itemlist.aspx?pdf_Spec011=1">○</a></td>
            <span class="digestMakerName">パイオニア</span>
            """.trimIndent()
        val actualHasUltraHD = getDetail(testStringHasUltraHD, ItemCategory.BD_DRIVE).desc()
        assertEquals(expectHasUltraHD, actualHasUltraHD)
    }

    @Test
    fun getFanDetailTest() {
        val expect = "DEEPCOOL\n120 mm角\n56.5 CFM\n1500 rpm\n27 dB\n4pin\nLEDライティング対応\n1 個"
        val testString = """
            <th class="itemviewColor03b textL">ファンサイズ</th><td class="lineNone"><a href="javascript:void(0);" onclick="changeLocation('pdf_Spec308=120');return false;">120 mm角</a></td>
            <th class="itemviewColor03b textL">最大風量</th><td class="lineNone"><a href="javascript:void(0);" onclick="changeLocation('pdf_Spec302=56.5');return false;">56.5 CFM</a></td>
            <th class="itemviewColor03b textL">最大ノイズレベル</th><td class="lineNone"><a href="javascript:void(0);" onclick="changeLocation('pdf_Spec301=27');return false;">27 dB</a></td>
            <th class="itemviewColor03b textL">最大回転数</th><td class="lineNone"><a href="javascript:void(0);" onclick="changeLocation('pdf_Spec303=1500');return false;">1500 rpm</a></td>
            <th class="itemviewColor03b textL">PWM</th><td class="lineNone"><a href="https://kakaku.com/pc/case-fan/itemlist.aspx?pdf_Spec002=1">○</a></td>
            <th class="itemviewColor03b textL">コネクタ</th><td class="lineNone"><a href="javascript:void(0);" onclick="changeLocation('pdf_Spec102=2');return false;">4pin</a></td>
            <th class="itemviewColor03b textL">LEDライティング対応</th><td class="lineNone"><a href="https://kakaku.com/pc/case-fan/itemlist.aspx?pdf_Spec003=1">○</a></td>
            <th class="itemviewColor03b textL">ファンコン</th><td>　</td>
            <th class="itemviewColor03b textL">個数</th><td class="lineNone"><a href="javascript:void(0);" onclick="changeLocation('pdf_Spec201=1');return false;">1 個</a></td>
            <th class="itemviewColor03b textL">幅x高さx厚さ</th><td>120x120x25 mm</td>
            <span class="digestMakerName">DEEPCOOL</span>
            """.trimIndent()
        val actual = getDetail(testString, ItemCategory.FAN).desc()
        assertEquals(expect, actual)
    }
}