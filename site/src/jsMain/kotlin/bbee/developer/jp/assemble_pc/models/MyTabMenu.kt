package bbee.developer.jp.assemble_pc.models

import bbee.developer.jp.assemble_pc.navigation.Screen
import bbee.developer.jp.assemble_pc.util.StringRes

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