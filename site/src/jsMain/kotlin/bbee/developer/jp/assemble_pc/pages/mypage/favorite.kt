package bbee.developer.jp.assemble_pc.pages.mypage

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import bbee.developer.jp.assemble_pc.components.layouts.CommonMyPageLayout
import bbee.developer.jp.assemble_pc.components.layouts.TabMenuLayout
import bbee.developer.jp.assemble_pc.components.widgets.AssemblyCard
import bbee.developer.jp.assemble_pc.components.widgets.ProfileEdit
import bbee.developer.jp.assemble_pc.components.widgets.SearchBar
import bbee.developer.jp.assemble_pc.models.Assembly
import bbee.developer.jp.assemble_pc.models.AssemblyId
import bbee.developer.jp.assemble_pc.models.MyTabMenu
import bbee.developer.jp.assemble_pc.models.Profile
import bbee.developer.jp.assemble_pc.util.Const
import bbee.developer.jp.assemble_pc.util.IsUserLoggedIn
import bbee.developer.jp.assemble_pc.util.addFavoriteAssembly
import bbee.developer.jp.assemble_pc.util.favoriteUpdate
import bbee.developer.jp.assemble_pc.util.getMyFavoriteAssemblies
import bbee.developer.jp.assemble_pc.util.getMyProfile
import bbee.developer.jp.assemble_pc.util.hugeSize
import bbee.developer.jp.assemble_pc.util.largeSize
import bbee.developer.jp.assemble_pc.util.removeFavoriteAssembly
import bbee.developer.jp.assemble_pc.util.updateMyProfile
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxSize
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.fontFamily
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.height
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.silk.components.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.theme.breakpoint.rememberBreakpoint
import kotlinx.coroutines.launch
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px

@Page
@Composable
fun FavoritePage() {
    val breakpoint = rememberBreakpoint()
    val scope = rememberCoroutineScope()

    var myProfile: Profile? by remember { mutableStateOf(null) }

    var showNameEditPopup by remember { mutableStateOf(false) }

    IsUserLoggedIn {
        LaunchedEffect(Unit) {
            scope.launch {
                myProfile = getMyProfile() ?: Profile()
            }
        }

        CommonMyPageLayout(
            breakpoint = breakpoint,
            showNameEditPopup = showNameEditPopup,
            userName = myProfile?.userName ?: "",
            onDismiss = { showNameEditPopup = false },
            onUserNamePositiveClick = { newName ->
                scope.launch {
                    val email = myProfile?.userEmail
                    if (updateMyProfile(Profile(userName = newName, userEmail = email))) {
                        myProfile = Profile(userName = newName, userEmail = email)
                    }
                    showNameEditPopup = false
                }
            }
        ) {
            myProfile?.also {
                FavoriteContents(
                    breakpoint = breakpoint,
                    userName = it.userName ?: "(NO NAME)",
                    onClick = { showNameEditPopup = true }
                )
            }
        }
    }
}

@Composable
fun FavoriteContents(
    breakpoint: Breakpoint,
    userName: String,
    onClick: () -> Unit
) {
    val scope = rememberCoroutineScope()
    val assemblies: MutableList<Assembly> = remember { mutableStateListOf() }
    val myFavoriteList: MutableList<AssemblyId> = remember { mutableStateListOf() }

    LaunchedEffect(Unit) {
        scope.launch {
            getMyFavoriteAssemblies(skip = 0)
                .also {
                    assemblies.addAll(it)
                    myFavoriteList.addAll(it.map { it.assemblyId })
                }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize(95.percent)
            .margin(8.px),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(40.px)
                .padding(24.px),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            ProfileEdit(
                fontSize = breakpoint.largeSize(),
                name = userName,
                onClick = onClick
            )
            SearchBar(breakpoint = breakpoint)
        }

        TabMenuLayout(breakpoint = breakpoint, tabList = MyTabMenu.toTabInfoList()) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                assemblies.ifEmpty {
                    SpanText(
                        modifier = Modifier
                            .padding(24.px)
                            .fontFamily(Const.FONT_FAMILY)
                            .fontSize(breakpoint.hugeSize()),
                        text = "お気に入りの構成はありません"
                    )
                }
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
    }
}