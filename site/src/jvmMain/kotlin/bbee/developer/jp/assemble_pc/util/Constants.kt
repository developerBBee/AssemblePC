package bbee.developer.jp.assemble_pc.util

private const val KAKAKU_BASE_URL = "https://kakaku.com"

object KakakuUrl {
    val CASE = "$KAKAKU_BASE_URL/pc/pc-case/ranking_0580/".toUrl()
    val MOTHER_BOARD = "$KAKAKU_BASE_URL/pc/motherboard/ranking_0540/".toUrl()
    val PSU = "$KAKAKU_BASE_URL/pc/power-supply/ranking_0590/".toUrl()
    val CPU = "$KAKAKU_BASE_URL/pc/cpu/ranking_0510/".toUrl()
    val COOLER = "$KAKAKU_BASE_URL/pc/cpu-cooler/ranking_0512/".toUrl()
    val MEMORY = "$KAKAKU_BASE_URL/pc/pc-memory/ranking_0520/".toUrl()
    val SSD = "$KAKAKU_BASE_URL/pc/ssd/ranking_0537/".toUrl()
    val HDD = "$KAKAKU_BASE_URL/pc/hdd-35inch/ranking_0530/".toUrl()
    val VIDEO_CARD = "$KAKAKU_BASE_URL/pc/videocard/ranking_0550/".toUrl()
    val OS = "$KAKAKU_BASE_URL/pc/os-soft/ranking_0310/".toUrl()
    val DISPLAY = "$KAKAKU_BASE_URL/pc/lcd-monitor/ranking_0085/".toUrl()
    val KEYBOARD = "$KAKAKU_BASE_URL/pc/keyboard/ranking_0150/".toUrl()
    val MOUSE = "$KAKAKU_BASE_URL/pc/mouse/ranking_0160/".toUrl()
    val DVD_DRIVE = "$KAKAKU_BASE_URL/pc/dvd-drive/ranking_0125/".toUrl()
    val BD_DRIVE = "$KAKAKU_BASE_URL/pc/blu-ray-drive/ranking_0126/".toUrl()
    val SOUND_CARD = "$KAKAKU_BASE_URL/pc/sound-card/ranking_0560/".toUrl()
    val SPEAKER = "$KAKAKU_BASE_URL/pc/pc-speaker/ranking_0170/".toUrl()
    val FAN_CONTROLLER = "$KAKAKU_BASE_URL/pc/fan-controller/ranking_0582/".toUrl()
    val FAN = "$KAKAKU_BASE_URL/pc/case-fan/ranking_0581/".toUrl()
}

object KakakuRegex {
    val RANKING_URL = Regex("""<a href="($KAKAKU_BASE_URL.*)" class="rkgBoxLink">""")
    val MAKER_NAME = Regex("""<span class="digestMakerName">(.*)</span>""")
    val ITEM_NAME = Regex("""<h2 itemprop="name">(.*)</h2>""")
    val IMAGE_URL = Regex("""<img itemprop="image" src="(.*)"""")
    val PRICE_TEXT = Regex("""<span class="price">(.*)</span>""")
    val RANK_TEXT = Regex("""<li class="ranking">.*<span class="num">(\d+)</span>位.*</li>""")
}

val HtmlEntityMap = mapOf(
    "&amp;" to "&",
    "&quot;" to "\"",
    "&lt;" to "<",
    "&gt;" to ">",
    "&nbsp;" to " ",
    "&yen;" to "¥",
    "&copy;" to "©",
    "&#215;" to "×",
)