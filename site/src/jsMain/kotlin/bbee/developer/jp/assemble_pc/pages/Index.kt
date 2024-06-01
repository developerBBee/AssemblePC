package bbee.developer.jp.assemble_pc.pages

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import bbee.developer.jp.assemble_pc.components.layouts.CommonLayout
import bbee.developer.jp.assemble_pc.components.widgets.AssemblyCard
import bbee.developer.jp.assemble_pc.models.Assembly
import bbee.developer.jp.assemble_pc.models.AssemblyId
import bbee.developer.jp.assemble_pc.util.IsUserLoggedIn
import bbee.developer.jp.assemble_pc.util.addFavoriteAssembly
import bbee.developer.jp.assemble_pc.util.favoriteUpdate
import bbee.developer.jp.assemble_pc.util.getMyFavoriteAssemblyIdList
import bbee.developer.jp.assemble_pc.util.getPublishedAssemblies
import bbee.developer.jp.assemble_pc.util.removeFavoriteAssembly
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxSize
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.silk.components.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.theme.breakpoint.rememberBreakpoint
import kotlinx.coroutines.launch
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px

@Page
@Composable
fun TopPage() {
    val breakpoint = rememberBreakpoint()

    IsUserLoggedIn {
        CommonLayout(breakpoint) {
            TopContents(breakpoint)
        }
    }
}

@Composable
fun TopContents(breakpoint: Breakpoint) {
    val scope = rememberCoroutineScope()

    val myFavoriteList: MutableList<AssemblyId> = remember { mutableStateListOf() }
    val assemblies: MutableList<Assembly> = remember { mutableStateListOf() }

    LaunchedEffect(Unit) {
        scope.launch {
            myFavoriteList.addAll(getMyFavoriteAssemblyIdList())
            assemblies.addAll(getPublishedAssemblies(skip = 0))
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize(95.percent)
            .margin(8.px),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        assemblies.forEach { assembly ->
            AssemblyCard(
                breakpoint = breakpoint,
                assembly = assembly,
                isMyFavorite = assembly.assemblyId in myFavoriteList,
                onFavoriteClick = { isFavorite ->
                    scope.launch {
                        val aid = assembly.assemblyId
                        if (isFavorite) {
                            removeFavoriteAssembly(aid).also { isSuccess ->
                                if (isSuccess) {
                                    myFavoriteList.remove(aid)
                                    assemblies.favoriteUpdate(assemblyId = aid, addCount = -1)
                                }
                            }
                        } else {
                            addFavoriteAssembly(aid).also { isSuccess ->
                                if (isSuccess) {
                                    myFavoriteList.add(aid)
                                    assemblies.favoriteUpdate(assemblyId = aid, addCount = 1)
                                }
                            }
                        }
                    }
                }
            )
        }
    }
}