package bbee.developer.jp.assemble_pc.navigation

sealed class Screen(val route: String) {
    data object TopPage : Screen("/")

    data object MyPage : Screen("/mypage/")
    data object CreatingPage : Screen("/mypage/creating")
    data object PublishedPage : Screen("/mypage/published")
    data object FavoritePage : Screen("/mypage/favorite")

    data object BuildingPage : Screen("/building/")
    data object AssemblyPage : Screen("/building/assembly")
    data object SelectionPage : Screen("/building/parts")
    data object CasePage : Screen("/building/parts/case")
    data object MotherBoardPage : Screen("/building/parts/motherboard")
    data object PSUPage : Screen("/building/parts/psu")
    data object CPUPage : Screen("/building/parts/cpu")
    data object CoolerPage : Screen("/building/parts/cooler")
    data object MemoryPage : Screen("/building/parts/memory")
    data object SSDPage : Screen("/building/parts/ssd")
    data object HDDPage : Screen("/building/parts/hdd")
    data object VideoCardPage : Screen("/building/parts/videocard")
    data object OSPage : Screen("/building/parts/os")
    data object DisplayPage : Screen("/building/parts/display")
    data object KeyboardPage : Screen("/building/parts/keyboard")
    data object MousePage : Screen("/building/parts/mouse")
    data object DVDPage : Screen("/building/parts/dvd")
    data object BDPage : Screen("/building/parts/bd")
    data object SoundCardPage : Screen("/building/parts/soundcard")
    data object SpeakerPage : Screen("/building/parts/speaker")
    data object FanControllerPage : Screen("/building/parts/fancontroller")
    data object FanPage : Screen("/building/parts/fan")

}