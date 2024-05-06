package bbee.developer.jp.assemble_pc.pages.building.parts

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import bbee.developer.jp.assemble_pc.components.layouts.CommonLayout
import bbee.developer.jp.assemble_pc.firebase.auth
import bbee.developer.jp.assemble_pc.models.BuildingTabMenu
import bbee.developer.jp.assemble_pc.models.Item
import bbee.developer.jp.assemble_pc.models.ItemCategory
import bbee.developer.jp.assemble_pc.pages.building.BuildingContents
import bbee.developer.jp.assemble_pc.util.collectOnUidChanged
import bbee.developer.jp.assemble_pc.util.getItems
import bbee.developer.jp.assemble_pc.util.signInAnonymous
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.silk.theme.breakpoint.rememberBreakpoint
import dev.gitlive.firebase.auth.FirebaseUser
import kotlinx.coroutines.launch

@Page
@Composable
fun CPUPage() {
    val scope = rememberCoroutineScope()
    val breakpoint = rememberBreakpoint()

    val user: FirebaseUser? by auth.authStateChanged
        .collectAsState(initial = null, scope.coroutineContext)

    val items = remember { mutableStateListOf<Item>() }

    LaunchedEffect(Unit) {
        scope.launch {
            signInAnonymous()
        }

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

    CommonLayout(
        breakpoint = breakpoint,
        selectedMenu = BuildingTabMenu.SELECTION
    ) {
        BuildingContents(breakpoint = breakpoint, items = items)
    }
}
