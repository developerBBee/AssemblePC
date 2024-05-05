package bbee.developer.jp.assemble_pc.util

import bbee.developer.jp.assemble_pc.models.ItemCategory
import io.ktor.http.Url

fun Regex.first(word: String): String? = find(word)?.groupValues?.getOrNull(1)

fun String.toUrl(): Url = Url(this)

fun Url.append(path: String): Url = Url(this.toString() + path)

// TODO
fun getDetail(word: String, category: ItemCategory): String {
    return when (category) {
        ItemCategory.CASE -> getCaseDetail(word)
        ItemCategory.MOTHER_BOARD -> ""
        ItemCategory.PSU -> ""
        ItemCategory.CPU -> ""
        ItemCategory.COOLER -> ""
        ItemCategory.MEMORY -> ""
        ItemCategory.SSD -> ""
        ItemCategory.HDD -> ""
        ItemCategory.VIDEO_CARD -> ""
        ItemCategory.OS -> ""
        ItemCategory.DISPLAY -> ""
        ItemCategory.KEYBOARD -> ""
        ItemCategory.MOUSE -> ""
        ItemCategory.DVD_DRIVE -> ""
        ItemCategory.BD_DRIVE -> ""
        ItemCategory.SOUND_CARD -> ""
        ItemCategory.SPEAKER -> ""
        ItemCategory.FAN_CONTROLLER -> ""
        ItemCategory.FAN -> ""
    }
}

private fun getCaseDetail(word: String): String {
    return ""
}