package bbee.developer.jp.assemble_pc.models

import bbee.developer.jp.assemble_pc.util.KakakuUrl
import io.ktor.http.Url

actual enum class ItemCategory(val url: Url) {
    CASE(KakakuUrl.CASE),
    MOTHER_BOARD(KakakuUrl.MOTHER_BOARD),
    PSU(KakakuUrl.PSU),
    CPU(KakakuUrl.CPU),
    COOLER(KakakuUrl.COOLER),
    MEMORY(KakakuUrl.MEMORY),
    SSD(KakakuUrl.SSD),
    HDD(KakakuUrl.HDD),
    VIDEO_CARD(KakakuUrl.VIDEO_CARD),
    OS(KakakuUrl.OS),
    DISPLAY(KakakuUrl.DISPLAY),
    KEYBOARD(KakakuUrl.KEYBOARD),
    MOUSE(KakakuUrl.MOUSE),
    DVD_DRIVE(KakakuUrl.DVD_DRIVE),
    BD_DRIVE(KakakuUrl.BD_DRIVE),
    SOUND_CARD(KakakuUrl.SOUND_CARD),
    SPEAKER(KakakuUrl.SPEAKER),
    FAN_CONTROLLER(KakakuUrl.FAN_CONTROLLER),
    FAN(KakakuUrl.FAN),
    ;

    fun toId() = ItemCategoryId(this.ordinal)

    companion object {
        fun from(id: ItemCategoryId) = entries.first { id.id == it.ordinal }
    }
}