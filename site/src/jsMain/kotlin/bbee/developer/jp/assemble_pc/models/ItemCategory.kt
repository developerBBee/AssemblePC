package bbee.developer.jp.assemble_pc.models

import bbee.developer.jp.assemble_pc.navigation.Screen

actual enum class ItemCategory(val displayName: String, val route: String) {
    CASE("PCケース", Screen.CasePage.route),
    MOTHER_BOARD("マザーボード", Screen.MotherBoardPage.route),
    PSU("電源", Screen.PSUPage.route),
    CPU("CPU", Screen.CPUPage.route),
    COOLER("CPUクーラー", Screen.CoolerPage.route),
    MEMORY("メモリ", Screen.MemoryPage.route),
    SSD("SSD", Screen.SSDPage.route),
    HDD("HDD", Screen.HDDPage.route),
    VIDEO_CARD("グラフィックボード", Screen.VideoCardPage.route),
    OS("OS", Screen.OSPage.route),
    DISPLAY("ディスプレイ", Screen.DisplayPage.route),
    KEYBOARD("キーボード", Screen.KeyboardPage.route),
    MOUSE("マウス", Screen.MousePage.route),
    DVD_DRIVE("DVDドライブ", Screen.DVDPage.route),
    BD_DRIVE("BDドライブ", Screen.BDPage.route),
    SOUND_CARD("サウンドカード", Screen.SoundCardPage.route),
    SPEAKER("スピーカー", Screen.SpeakerPage.route),
    FAN_CONTROLLER("ファンコントローラー", Screen.FanControllerPage.route),
    FAN("ファン", Screen.FanPage.route),
    ;

    companion object {
        fun from(id: ItemCategoryId) = entries.first { it.ordinal == id.id }
    }
}