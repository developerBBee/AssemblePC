package bbee.developer.jp.assemble_pc.models

import bbee.developer.jp.assemble_pc.navigation.Screen
import bbee.developer.jp.assemble_pc.util.StringRes

enum class BuildingTabMenu(val text: String, val route: String) {
    ASSEMBLY(StringRes.assembly, Screen.AssemblyPage.route),
    SELECTION(StringRes.selection, Screen.SelectionPage.route),
    ;

    companion object {
        fun toTabInfoList(): List<TabInfo> =
            entries.map { TabInfo(title = it.text, route = it.route) }
    }
}