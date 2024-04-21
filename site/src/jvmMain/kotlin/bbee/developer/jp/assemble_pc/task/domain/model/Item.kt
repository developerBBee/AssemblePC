package bbee.developer.jp.assemble_pc.task.domain.model

import bbee.developer.jp.assemble_pc.util.URL

enum class Item(val url: String) {
    CASE(URL.CASE),
    MOTHER_BOARD(URL.MOTHER_BOARD),
    PSU(URL.PSU),
    CPU(URL.CPU),
    COOLER(URL.COOLER),
    MEMORY(URL.MEMORY),
    HDD(URL.HDD),
    SSD(URL.SSD),
    VIDEO_CARD(URL.VIDEO_CARD),
    OS(URL.OS),
    DISPLAY(URL.DISPLAY),
    KEYBOARD(URL.KEYBOARD),
    MOUSE(URL.MOUSE),
    DVD_DRIVE(URL.DVD_DRIVE),
    BD_DRIVE(URL.BD_DRIVE),
    SOUND_CARD(URL.SOUND_CARD),
    SPEAKER(URL.SPEAKER),
    FAN_CONTROLLER(URL.FAN_CONTROLLER),
    FAN(URL.FAN),
}