package jp.developer.bbee.assemblepc.pages

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import jp.developer.bbee.assemblepc.components.layouts.CommonLayout
import jp.developer.bbee.assemblepc.components.widgets.AssemblyCard
import jp.developer.bbee.assemblepc.models.Assembly
import jp.developer.bbee.assemblepc.models.AssemblyId
import jp.developer.bbee.assemblepc.util.IsUserLoggedIn
import jp.developer.bbee.assemblepc.util.addFavoriteAssembly
import jp.developer.bbee.assemblepc.util.favoriteUpdate
import jp.developer.bbee.assemblepc.util.getMyFavoriteAssemblyIdList
import jp.developer.bbee.assemblepc.util.getPublishedAssemblies
import jp.developer.bbee.assemblepc.util.removeFavoriteAssembly
import jp.developer.bbee.assemblepc.util.runIf
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxSize
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.theme.breakpoint.rememberBreakpoint
import kotlinx.coroutines.launch
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px

@Page
@Composable
fun TopPage() {
    val breakpoint = rememberBreakpoint()

    IsUserLoggedIn { user ->
        CommonLayout(breakpoint = breakpoint, isAnonymous = user.isAnonymous) {
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
        // TODO pagination or paging
        assemblies.forEach { assembly ->
            AssemblyCard(
                breakpoint = breakpoint,
                assembly = assembly,
                isMyFavorite = assembly.assemblyId in myFavoriteList,
                onFavoriteClick = { isFavorite ->
                    scope.launch {
                        val aid = assembly.assemblyId
                        if (isFavorite) {
                            runIf(removeFavoriteAssembly(aid)) {
                                myFavoriteList.remove(aid)
                                assemblies.favoriteUpdate(assemblyId = aid, addCount = -1)
                            }
                        } else {
                            runIf(addFavoriteAssembly(aid)) {
                                myFavoriteList.add(aid)
                                assemblies.favoriteUpdate(assemblyId = aid, addCount = 1)
                            }
                        }
                    }
                }
            )
        }
    }
}