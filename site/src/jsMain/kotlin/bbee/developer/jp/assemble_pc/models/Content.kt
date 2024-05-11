package bbee.developer.jp.assemble_pc.models

import bbee.developer.jp.assemble_pc.navigation.Screen
import bbee.developer.jp.assemble_pc.util.StringRes

enum class Content(val text: String, val route: String) {
    TOP(StringRes.top, Screen.TopPage.route),
    MY_PAGE(StringRes.myPage, Screen.MyPage.route),
    BUILDING(StringRes.building, Screen.BuildingPage.route),
}