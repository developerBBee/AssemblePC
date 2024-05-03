package bbee.developer.jp.assemble_pc.models

enum class Parts(val displayName: String) {
    CASE("PCケース"),
    MOTHER_BOARD("マザーボード"),
    PSU("電源"),
    CPU("CPU"),
    COOLER("CPUクーラー"),
    MEMORY("メモリ"),
    SSD("SSD"),
    HDD("HDD"),
    VIDEO_CARD("グラフィックボード"),
    OS("OS"),
    DISPLAY("ディスプレイ"),
    KEYBOARD("キーボード"),
    MOUSE("マウス"),
    DVD_DRIVE("DVDドライブ"),
    BD_DRIVE("BDドライブ"),
    SOUND_CARD("サウンドカード"),
    SPEAKER("スピーカー"),
    FAN_CONTROLLER("ファンコントローラー"),
    FAN("ファン"),
}