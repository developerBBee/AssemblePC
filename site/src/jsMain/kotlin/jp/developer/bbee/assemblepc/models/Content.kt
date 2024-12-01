package jp.developer.bbee.assemblepc.models

import jp.developer.bbee.assemblepc.navigation.Screen
import jp.developer.bbee.assemblepc.util.StringRes

enum class Content(val text: String, val route: String) {
    TOP(StringRes.top, Screen.TopPage.route),
    MY_PAGE(StringRes.myPage, Screen.MyPage.route),
    BUILDING(StringRes.building, Screen.BuildingPage.route),
}