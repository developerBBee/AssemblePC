package bbee.developer.jp.assemble_pc.pages.building.parts

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import bbee.developer.jp.assemble_pc.components.layouts.BuildingNavLayout
import bbee.developer.jp.assemble_pc.components.layouts.CommonLayout
import bbee.developer.jp.assemble_pc.models.Item
import bbee.developer.jp.assemble_pc.models.ItemCategory
import bbee.developer.jp.assemble_pc.pages.building.BuildingContents
import bbee.developer.jp.assemble_pc.util.IsUserLoggedIn
import bbee.developer.jp.assemble_pc.util.collectOnUidChanged
import bbee.developer.jp.assemble_pc.util.getItems
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.silk.theme.breakpoint.rememberBreakpoint
import kotlinx.coroutines.launch

@Page
@Composable
fun CPUPage() {
    IsUserLoggedIn {
        CPULayout()
    }
}

@Composable
fun CPULayout() {
    val scope = rememberCoroutineScope()
    val breakpoint = rememberBreakpoint()
    val items = remember { mutableStateListOf<Item>() }

    LaunchedEffect(Unit) {
        scope.launch {
            collectOnUidChanged {
                console.log("call getItems()")
                getItems(0, ItemCategory.CPU)
                    .let { newItems ->
                        items.clear()
                        items.addAll(newItems)
                    }
            }
        }
    }

    CommonLayout(breakpoint = breakpoint) {
        BuildingNavLayout(breakpoint = breakpoint,) {
            BuildingContents(breakpoint = breakpoint, items = items)
        }
    }
}
