package jp.developer.bbee.assemblepc.models

import jp.developer.bbee.assemblepc.navigation.Screen
import jp.developer.bbee.assemblepc.util.StringRes

enum class BuildingTabMenu(val text: String, val route: String) {
    ASSEMBLY(StringRes.assembly, Screen.AssemblyPage.route),
    SELECTION(StringRes.selection, Screen.SelectionPage.route),
    ;

    companion object {
        fun toTabInfoList(): List<TabInfo> =
            entries.map { TabInfo(title = it.text, route = it.route) }
    }
}