package jp.developer.bbee.assemblepc.pages.mypage

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import jp.developer.bbee.assemblepc.components.layouts.CommonMyPageLayout
import jp.developer.bbee.assemblepc.components.layouts.TabMenuLayout
import jp.developer.bbee.assemblepc.components.widgets.AssemblyCard
import jp.developer.bbee.assemblepc.components.widgets.ProfileEdit
import jp.developer.bbee.assemblepc.components.widgets.SearchBar
import jp.developer.bbee.assemblepc.models.Assembly
import jp.developer.bbee.assemblepc.models.AssemblyId
import jp.developer.bbee.assemblepc.models.MyTabMenu
import jp.developer.bbee.assemblepc.models.Profile
import jp.developer.bbee.assemblepc.util.Const
import jp.developer.bbee.assemblepc.util.IsUserLoggedIn
import jp.developer.bbee.assemblepc.util.addFavoriteAssembly
import jp.developer.bbee.assemblepc.util.favoriteUpdate
import jp.developer.bbee.assemblepc.util.getMyFavoriteAssemblies
import jp.developer.bbee.assemblepc.util.getMyProfile
import jp.developer.bbee.assemblepc.util.hugeSize
import jp.developer.bbee.assemblepc.util.largeSize
import jp.developer.bbee.assemblepc.util.removeFavoriteAssembly
import jp.developer.bbee.assemblepc.util.runIf
import jp.developer.bbee.assemblepc.util.updateMyProfile
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
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint
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

    IsUserLoggedIn { user ->
        LaunchedEffect(Unit) {
            scope.launch {
                myProfile = getMyProfile() ?: Profile()
            }
        }

        CommonMyPageLayout(
            breakpoint = breakpoint,
            isAnonymous = user.isAnonymous,
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
    }
}