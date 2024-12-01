package jp.developer.bbee.assemblepc.models

import jp.developer.bbee.assemblepc.navigation.Screen
import jp.developer.bbee.assemblepc.util.StringRes

enum class MyTabMenu(val text: String, val route: String) {
    CREATING(StringRes.creating, Screen.CreatingPage.route),
    PUBLISHED(StringRes.published, Screen.PublishedPage.route),
    FAVORITE(StringRes.favorite, Screen.FavoritePage.route),
    ;

    companion object {
        fun toTabInfoList(): List<TabInfo> =
            entries.map { TabInfo(title = it.text, route = it.route) }
    }
}