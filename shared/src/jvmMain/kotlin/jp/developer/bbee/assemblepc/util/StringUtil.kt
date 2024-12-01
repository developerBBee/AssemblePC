package jp.developer.bbee.assemblepc.util

import jp.developer.bbee.assemblepc.constant.LINE_BREAK
import jp.developer.bbee.assemblepc.models.Description
import jp.developer.bbee.assemblepc.models.ItemCategory
import io.ktor.http.Url

fun Regex.first(word: String): String? = find(word)?.groupValues?.getOrNull(1)

fun String.toUrl(): Url = Url(this)

fun Url.append(path: String): Url = Url(this.toString() + path)

fun String.replaceHtmlEntity(): String {
    var str = this
    HtmlEntityMap.forEach { (key, value) ->
        str = str.replace(key, value)
    }
    return str
}

fun getDetail(word: String, category: ItemCategory): Description {
    return when (category) {
        ItemCategory.CASE -> getCaseDetail(word)
        ItemCategory.MOTHER_BOARD -> getMotherBoardDetail(word)
        ItemCategory.PSU -> getPSUDetail(word)
        ItemCategory.CPU -> getCPUDetail(word)
        ItemCategory.COOLER -> getCoolerDetail(word)
        ItemCategory.MEMORY -> getMemoryDetail(word)
        ItemCategory.SSD -> getSSDDetail(word)
        ItemCategory.HDD -> getHDDDetail(word)
        ItemCategory.VIDEO_CARD -> getVideoCardDetail(word)
        ItemCategory.OS -> getOSDetail(word)
        ItemCategory.DISPLAY -> getDisplayDetail(word)
        ItemCategory.KEYBOARD -> getKeyboardDetail(word)
        ItemCategory.MOUSE -> getMouseDetail(word)
        ItemCategory.DVD_DRIVE -> getDVDDriveDetail(word)
        ItemCategory.BD_DRIVE -> getBDDriveDetail(word)
        ItemCategory.SOUND_CARD -> getSoundCardDetail(word)
        ItemCategory.SPEAKER -> getSpeakerDetail(word)
        ItemCategory.FAN_CONTROLLER -> getFanControllerDetail(word)
        ItemCategory.FAN -> getFanDetail(word)
    }
}

// COMMON
private const val HAS_SPEC = "○"
private const val LEFT_TAG = """<th class="itemviewColor03b textL">"""
private const val RIGHT_TAG = """(.*?)?(<img.*?>)?</th><td(.*?>)(<a.*?>)?(.*?)(</a>)?</td>"""
private val MAKER_REGEX = Regex("""<span class="digestMakerName">(.*?)</span>""")

// CASE
private val CASE_POWER_SPECIFICATION_REGEX = Regex("${LEFT_TAG}電源規格${RIGHT_TAG}")
private val CASE_MOTHER_BOARDS_REGEX = Regex("${LEFT_TAG}対応マザーボード${RIGHT_TAG}")
private val CASE_SIZE_REGEX = Regex("${LEFT_TAG}幅x高さx奥行${RIGHT_TAG}")

// MOTHER_BOARD
private val MOTHER_BOARD_SOCKET_REGEX = Regex("${LEFT_TAG}CPUソケット${RIGHT_TAG}")
private val MOTHER_BOARD_TYPE_REGEX = Regex("${LEFT_TAG}フォームファクタ${RIGHT_TAG}")
private val MOTHER_BOARD_MEMORY_TYPE_REGEX = Regex("${LEFT_TAG}詳細メモリタイプ${RIGHT_TAG}")

// PSU
private val PSU_POWER_SPECIFICATION_REGEX = Regex("${LEFT_TAG}対応規格${RIGHT_TAG}")
private val PSU_POWER_CAPACITY_REGEX = Regex("${LEFT_TAG}電源容量${RIGHT_TAG}")
private val PSU_80_PLUS_CERTIFIED_REGEX = Regex("${LEFT_TAG}80PLUS認証${RIGHT_TAG}")

// CPU
private val CPU_PROCESSOR_REGEX = Regex("${LEFT_TAG}プロセッサ名${RIGHT_TAG}")
private val CPU_SOCKET_REGEX = Regex("${LEFT_TAG}ソケット形状${RIGHT_TAG}")
private val CPU_CORE_REGEX = Regex("${LEFT_TAG}コア数${RIGHT_TAG}")
private val CPU_BASE_CLOCK_REGEX = Regex("${LEFT_TAG}クロック周波数${RIGHT_TAG}")
private val CPU_MAX_CLOCK_REGEX = Regex("${LEFT_TAG}最大動作クロック周波数${RIGHT_TAG}")
private val CPU_THREAD_REGEX = Regex("${LEFT_TAG}スレッド数${RIGHT_TAG}")

// COOLER
private val COOLER_SOCKET_LGA_REGEX = Regex("${LEFT_TAG}Intel対応ソケット${RIGHT_TAG}")
private val COOLER_SOCKET_AM_REGEX = Regex("${LEFT_TAG}AMD対応ソケット${RIGHT_TAG}")
private val COOLER_TYPE_REGEX = Regex("${LEFT_TAG}タイプ${RIGHT_TAG}")
private val COOLER_ROTATION_SPEED_REGEX = Regex("${LEFT_TAG}最大ファン回転数${RIGHT_TAG}")
private val COOLER_AIR_FLOW_REGEX = Regex("${LEFT_TAG}最大ファン風量${RIGHT_TAG}")
private val COOLER_SIZE_REGEX = Regex("${LEFT_TAG}幅x高さx奥行${RIGHT_TAG}")

// MEMORY
private val MEMORY_CAPACITY_REGEX = Regex("${LEFT_TAG}メモリ容量${RIGHT_TAG}")
private val MEMORY_SET_QUANTITY_REGEX = Regex("${LEFT_TAG}枚数${RIGHT_TAG}")
private val MEMORY_INTERFACE_REGEX = Regex("${LEFT_TAG}メモリインターフェイス${RIGHT_TAG}")
private val MEMORY_STANDARD_REGEX = Regex("${LEFT_TAG}メモリ規格${RIGHT_TAG}")
private val MEMORY_SPEED_REGEX = Regex("${LEFT_TAG}データ転送速度${RIGHT_TAG}")

// SSD
private val SSD_CAPACITY_REGEX = Regex("${LEFT_TAG}容量${RIGHT_TAG}")
private val SSD_STANDARD_REGEX = Regex("${LEFT_TAG}規格サイズ${RIGHT_TAG}")
private val SSD_INTERFACE_REGEX = Regex("${LEFT_TAG}インターフェイス${RIGHT_TAG}")
private val SSD_READ_SPEED_REGEX = Regex("${LEFT_TAG}読込速度${RIGHT_TAG}")
private val SSD_WRITE_SPEED_REGEX = Regex("${LEFT_TAG}書込速度${RIGHT_TAG}")

// HDD
private val HDD_CAPACITY_REGEX = Regex("${LEFT_TAG}容量${RIGHT_TAG}")
private val HDD_INTERFACE_REGEX = Regex("${LEFT_TAG}インターフェイス${RIGHT_TAG}")
private val HDD_ROTATION_SPEED_REGEX = Regex("${LEFT_TAG}回転数${RIGHT_TAG}")

// VIDEO_CARD
private val VIDEO_CARD_CHIP_REGEX = Regex("${LEFT_TAG}搭載チップ${RIGHT_TAG}")
private val VIDEO_CARD_MEMORY_REGEX = Regex("${LEFT_TAG}メモリ${RIGHT_TAG}")
private val VIDEO_CARD_INTERFACE_REGEX = Regex("${LEFT_TAG}バスインターフェイス${RIGHT_TAG}")
private val VIDEO_CARD_CONNECTORS_REGEX = Regex("${LEFT_TAG}モニタ端子${RIGHT_TAG}")
private val VIDEO_CARD_POWER_REGEX = Regex("${LEFT_TAG}消費電力${RIGHT_TAG}")
private val VIDEO_CARD_POWER_CONNECTOR_REGEX = Regex("${LEFT_TAG}補助電源${RIGHT_TAG}")
private val VIDEO_CARD_SIZE_REGEX = Regex("${LEFT_TAG}本体(幅x高さx奥行)${RIGHT_TAG}")

// OS
private val OS_SERIES_REGEX = Regex("""<li class="seriesLabel">(<a.*?>)?(.*?)(</a>)?</li>""")

// DISPLAY
private val DISPLAY_SIZE_REGEX = Regex("${LEFT_TAG}モニタサイズ${RIGHT_TAG}")
private val DISPLAY_ASPECT_REGEX = Regex("${LEFT_TAG}アスペクト比${RIGHT_TAG}")
private val DISPLAY_SURFACE_REGEX = Regex("${LEFT_TAG}表面処理${RIGHT_TAG}")
private val DISPLAY_PANEL_REGEX = Regex("${LEFT_TAG}パネル種類${RIGHT_TAG}")
private val DISPLAY_RESOLUTION_REGEX = Regex("${LEFT_TAG}解像度${RIGHT_TAG}")
private val DISPLAY_RESPONSE_REGEX = Regex("${LEFT_TAG}応答速度${RIGHT_TAG}")
private val DISPLAY_REFRESH_REGEX = Regex("${LEFT_TAG}リフレッシュレート${RIGHT_TAG}")
private val DISPLAY_CONNECTION_REGEX = Regex("${LEFT_TAG}入力端子${RIGHT_TAG}")

// KEYBOARD
private val KEYBOARD_CONNECTION_REGEX = Regex("${LEFT_TAG}ケーブル<${RIGHT_TAG}")
private val KEYBOARD_CABLE_LENGTH_REGEX = Regex("${LEFT_TAG}ケーブル長${RIGHT_TAG}")
private val KEYBOARD_KEY_LAYOUT_REGEX = Regex("${LEFT_TAG}キーレイアウト${RIGHT_TAG}")
private val KEYBOARD_KEY_SWITCH_REGEX = Regex("${LEFT_TAG}キースイッチ${RIGHT_TAG}")
private val KEYBOARD_KEY_AXIS_REGEX = Regex("${LEFT_TAG}軸の種類${RIGHT_TAG}")
private val KEYBOARD_POWER_REGEX = Regex("${LEFT_TAG}電源${RIGHT_TAG}")

// MOUSE
private val MOUSE_TYPE_REGEX = Regex("${LEFT_TAG}タイプ${RIGHT_TAG}")
private val MOUSE_CONNECTION_REGEX = Regex("${LEFT_TAG}ケーブル${RIGHT_TAG}")
private val MOUSE_BUTTON_REGEX = Regex("${LEFT_TAG}ボタン数${RIGHT_TAG}")
private val MOUSE_RESOLUTION_REGEX = Regex("${LEFT_TAG}解像度${RIGHT_TAG}")
private val MOUSE_WEIGHT_REGEX = Regex("${LEFT_TAG}重さ${RIGHT_TAG}")

// DVD_DRIVE
private val DVD_DRIVE_INSTALLATION_REGEX = Regex("${LEFT_TAG}設置方式${RIGHT_TAG}")
private val DVD_DRIVE_CONNECTION_REGEX = Regex("${LEFT_TAG}接続インターフェース${RIGHT_TAG}")
private val DVD_DRIVE_MEDIA_REGEX = Regex("${LEFT_TAG}対応メディア${RIGHT_TAG}")

// BD_DRIVE
private val BD_DRIVE_INSTALLATION_REGEX = Regex("${LEFT_TAG}設置方式${RIGHT_TAG}")
private val BD_DRIVE_CONNECTION_REGEX = Regex("${LEFT_TAG}接続インターフェース${RIGHT_TAG}")
private val BD_DRIVE_ULTRA_HD_REGEX = Regex("${LEFT_TAG}(Ultra HD Blu-ray)${RIGHT_TAG}")

// SOUND_CARD
private val SOUND_CARD_INSTALLATION_REGEX = Regex("${LEFT_TAG}タイプ${RIGHT_TAG}")
private val SOUND_CARD_CONNECTION_REGEX = Regex("${LEFT_TAG}インターフェース${RIGHT_TAG}")
private val SOUND_CARD_SURROUND_REGEX = Regex("${LEFT_TAG}サラウンド機能${RIGHT_TAG}")

// SPEAKER
private val SPEAKER_TYPE_REGEX = Regex("${LEFT_TAG}タイプ${RIGHT_TAG}")
private val SPEAKER_SUPPLY_REGEX = Regex("${LEFT_TAG}電源${RIGHT_TAG}")

// FAN_CONTROLLER
private val FUN_CONTROLLER_NUMBER_REGEX = Regex("${LEFT_TAG}ファンコントローラ数${RIGHT_TAG}")

// FAN
private val FAN_SIZE_REGEX = Regex("${LEFT_TAG}ファンサイズ${RIGHT_TAG}")
private val FAN_FLOW_REGEX = Regex("${LEFT_TAG}最大風量${RIGHT_TAG}")
private val FAN_ROTATION_REGEX = Regex("${LEFT_TAG}最大回転数${RIGHT_TAG}")
private val FAN_NOISE_REGEX = Regex("${LEFT_TAG}最大ノイズレベル${RIGHT_TAG}")
private val FAN_PIN_REGEX = Regex("${LEFT_TAG}コネクタ${RIGHT_TAG}")
private val FAN_LED_REGEX = Regex("${LEFT_TAG}(LEDライティング対応)${RIGHT_TAG}")
private val FAN_QUANTITY_REGEX = Regex("${LEFT_TAG}個数${RIGHT_TAG}")

private fun getCaseDetail(word: String) = Description.CaseDescription(
    maker = MAKER_REGEX.first(word) ?: "",
    powerSpecification = CASE_POWER_SPECIFICATION_REGEX.getGroupValue(word = word),
    motherboards = CASE_MOTHER_BOARDS_REGEX.getGroupValue(word = word),
    size = CASE_SIZE_REGEX.getGroupValue(word = word),
)

private fun getMotherBoardDetail(word: String) = Description.MotherBoardDescription(
    maker = MAKER_REGEX.first(word) ?: "",
    socket = MOTHER_BOARD_SOCKET_REGEX.getGroupValue(word = word),
    motherboardType = MOTHER_BOARD_TYPE_REGEX.getGroupValue(word = word),
    memoryType = MOTHER_BOARD_MEMORY_TYPE_REGEX.getGroupValue(word = word),
)

private fun getPSUDetail(word: String) = Description.PSUDescription(
    maker = MAKER_REGEX.first(word) ?: "",
    powerSpecification = PSU_POWER_SPECIFICATION_REGEX.getGroupValue(word = word),
    powerCapacity = PSU_POWER_CAPACITY_REGEX.getGroupValue(word = word),
    power80PlusCertified = PSU_80_PLUS_CERTIFIED_REGEX.getGroupValue(word = word),
)

private fun getCPUDetail(word: String) = Description.CPUDescription(
    maker = MAKER_REGEX.first(word) ?: "",
    processor = CPU_PROCESSOR_REGEX.getGroupValue(word = word),
    socket = CPU_SOCKET_REGEX.getGroupValue(word = word),
    core = CPU_CORE_REGEX.getGroupValue(word = word),
    baseClock = CPU_BASE_CLOCK_REGEX.getGroupValue(word = word),
    maxClock = CPU_MAX_CLOCK_REGEX.getGroupValue(word = word),
    thread = CPU_THREAD_REGEX.getGroupValue(word = word),
)

private fun getCoolerDetail(word: String) = Description.CoolerDescription(
    maker = MAKER_REGEX.first(word) ?: "",
    socketLGA = COOLER_SOCKET_LGA_REGEX.getGroupValue(word = word),
    socketAM = COOLER_SOCKET_AM_REGEX.getGroupValue(word = word),
    coolerType = COOLER_TYPE_REGEX.getGroupValue(word = word),
    rotationSpeed = COOLER_ROTATION_SPEED_REGEX.getGroupValue(word = word),
    airFlow = COOLER_AIR_FLOW_REGEX.getGroupValue(word = word),
    size = COOLER_SIZE_REGEX.getGroupValue(word = word),
)

private fun getMemoryDetail(word: String) = Description.MemoryDescription(
    maker = MAKER_REGEX.first(word) ?: "",
    memoryCapacity = MEMORY_CAPACITY_REGEX.getGroupValue(word = word),
    setQuantity = MEMORY_SET_QUANTITY_REGEX.getGroupValue(word = word),
    memoryInterface = MEMORY_INTERFACE_REGEX.getGroupValue(word = word),
    memoryStandard = MEMORY_STANDARD_REGEX.getGroupValue(word = word),
    memorySpeed = MEMORY_SPEED_REGEX.getGroupValue(word = word),
)

private fun getSSDDetail(word: String) = Description.SSDDescription(
    maker = MAKER_REGEX.first(word) ?: "",
    storageCapacity = SSD_CAPACITY_REGEX.getGroupValue(word = word),
    ssdStandard = SSD_STANDARD_REGEX.getGroupValue(word = word),
    ssdInterface = SSD_INTERFACE_REGEX.getGroupValue(word = word),
    readSpeed = SSD_READ_SPEED_REGEX.getGroupValue(word = word),
    writeSpeed = SSD_WRITE_SPEED_REGEX.getGroupValue(word = word),
)

private fun getHDDDetail(word: String) = Description.HDDDescription(
    maker = MAKER_REGEX.first(word) ?: "",
    storageCapacity = HDD_CAPACITY_REGEX.getGroupValue(word = word),
    hddInterface = HDD_INTERFACE_REGEX.getGroupValue(word = word),
    rotationSpeed = HDD_ROTATION_SPEED_REGEX.getGroupValue(word = word),
)

private fun getVideoCardDetail(word: String) = Description.VideoCardDescription(
    maker = MAKER_REGEX.first(word) ?: "",
    graphicChip = VIDEO_CARD_CHIP_REGEX.getGroupValue(word = word),
    graphicMemory = VIDEO_CARD_MEMORY_REGEX.getGroupValue(word = word),
    busInterface = VIDEO_CARD_INTERFACE_REGEX.getGroupValue(word = word),
    monitorConnectors = VIDEO_CARD_CONNECTORS_REGEX.getGroupValue(word = word),
    powerConsumption = VIDEO_CARD_POWER_REGEX.getGroupValue(word = word),
    powerConnector = VIDEO_CARD_POWER_CONNECTOR_REGEX.getGroupValue(word = word),
    size = VIDEO_CARD_SIZE_REGEX.getGroupValue(word = word),
)

private fun getOSDetail(word: String) = Description.OSDescription(
    vendor = MAKER_REGEX.first(word) ?: "",
    series = OS_SERIES_REGEX.getGroupValue(word = word, index = 2),
)

private fun getDisplayDetail(word: String) = Description.DisplayDescription(
    maker = MAKER_REGEX.first(word) ?: "",
    size = DISPLAY_SIZE_REGEX.getGroupValue(word = word),
    aspectRatio = DISPLAY_ASPECT_REGEX.getGroupValue(word = word),
    surfaceTreatment = DISPLAY_SURFACE_REGEX.getGroupValue(word = word),
    panelType = DISPLAY_PANEL_REGEX.getGroupValue(word = word),
    resolution = DISPLAY_RESOLUTION_REGEX.getGroupValue(word = word),
    responseTime = DISPLAY_RESPONSE_REGEX.getGroupValue(word = word),
    refreshRate = DISPLAY_REFRESH_REGEX.getGroupValue(word = word),
    connections = DISPLAY_CONNECTION_REGEX.getGroupValue(word = word),
)

private fun getKeyboardDetail(word: String) = Description.KeyboardDescription(
    maker = MAKER_REGEX.first(word) ?: "",
    connections = KEYBOARD_CONNECTION_REGEX.getGroupValue(word = word),
    cableLength = KEYBOARD_CABLE_LENGTH_REGEX.getGroupValue(word = word),
    keyLayout = KEYBOARD_KEY_LAYOUT_REGEX.getGroupValue(word = word),
    keySwitch = KEYBOARD_KEY_SWITCH_REGEX.getGroupValue(word = word),
    keyAxis = KEYBOARD_KEY_AXIS_REGEX.getGroupValue(word = word),
    power = KEYBOARD_POWER_REGEX.getGroupValue(word = word),
)

private fun getMouseDetail(word: String) = Description.MouseDescription(
    maker = MAKER_REGEX.first(word) ?: "",
    mouseType = MOUSE_TYPE_REGEX.getGroupValue(word = word),
    connections = MOUSE_CONNECTION_REGEX.getGroupValue(word = word),
    buttons = MOUSE_BUTTON_REGEX.getGroupValue(word = word),
    resolution = MOUSE_RESOLUTION_REGEX.getGroupValue(word = word),
    weight = MOUSE_WEIGHT_REGEX.getGroupValue(word = word),
)

private fun getDVDDriveDetail(word: String) = Description.DVDDriveDescription(
    maker = MAKER_REGEX.first(word) ?: "",
    installation = DVD_DRIVE_INSTALLATION_REGEX.getGroupValue(word = word),
    connections = DVD_DRIVE_CONNECTION_REGEX.getGroupValue(word = word),
    dvdMediaType = DVD_DRIVE_MEDIA_REGEX.getGroupValue(word = word),
)

private fun getBDDriveDetail(word: String) = Description.BDDriveDescription(
    maker = MAKER_REGEX.first(word) ?: "",
    installation = BD_DRIVE_INSTALLATION_REGEX.getGroupValue(word = word),
    connections = BD_DRIVE_CONNECTION_REGEX.getGroupValue(word = word),
    ultraHD = BD_DRIVE_ULTRA_HD_REGEX.getGroupHasSpec(word = word),
)

private fun getSoundCardDetail(word: String) = Description.SoundCardDescription(
    maker = MAKER_REGEX.first(word) ?: "",
    installation = SOUND_CARD_INSTALLATION_REGEX.getGroupValue(word = word),
    connections = SOUND_CARD_CONNECTION_REGEX.getGroupValue(word = word),
    surround = SOUND_CARD_SURROUND_REGEX.getGroupValue(word = word),
)

private fun getSpeakerDetail(word: String) = Description.SpeakerDescription(
    maker = MAKER_REGEX.first(word) ?: "",
    speakerType = SPEAKER_TYPE_REGEX.getGroupValue(word = word),
    powerSupply = SPEAKER_SUPPLY_REGEX.getGroupValue(word = word),
)

private fun getFanControllerDetail(word: String) = Description.FanControllerDescription(
    maker = MAKER_REGEX.first(word) ?: "",
    numberOfControls = FUN_CONTROLLER_NUMBER_REGEX.getGroupValue(word = word),
)

private fun getFanDetail(word: String) = Description.FanDescription(
    maker = MAKER_REGEX.first(word) ?: "",
    size = FAN_SIZE_REGEX.getGroupValue(word = word),
    airFlow = FAN_FLOW_REGEX.getGroupValue(word = word),
    rotationSpeed = FAN_ROTATION_REGEX.getGroupValue(word = word),
    noise = FAN_NOISE_REGEX.getGroupValue(word = word),
    connectorPin = FAN_PIN_REGEX.getGroupValue(word = word),
    led = FAN_LED_REGEX.getGroupHasSpec(word = word),
    setQuantity = FAN_QUANTITY_REGEX.getGroupValue(word = word),
)

private fun Regex.getGroupValue(word: String, index: Int = 5): String =
    (find(word)?.groupValues?.getOrNull(index = index) ?: "")

private fun Regex.getGroupHasSpec(word: String, index: Int = 6): String {
    return find(word)?.groupValues?.let { groupValues ->
        if (groupValues[index] == HAS_SPEC) {
            groupValues[1]
        } else {
            ""
        }
    } ?: ""
}

private fun String.replaceBreakLine(newValue: String = " | "): String =
    replace(LINE_BREAK, newValue)