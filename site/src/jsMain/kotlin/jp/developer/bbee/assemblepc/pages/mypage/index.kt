package jp.developer.bbee.assemblepc.pages.mypage

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import jp.developer.bbee.assemblepc.components.layouts.CommonMyPageLayout
import jp.developer.bbee.assemblepc.components.layouts.TabMenuLayout
import jp.developer.bbee.assemblepc.components.widgets.ProfileEdit
import jp.developer.bbee.assemblepc.components.widgets.SearchBar
import jp.developer.bbee.assemblepc.models.MyTabMenu
import jp.developer.bbee.assemblepc.models.Profile
import jp.developer.bbee.assemblepc.util.IsUserLoggedIn
import jp.developer.bbee.assemblepc.util.getMyProfile
import jp.developer.bbee.assemblepc.util.largeSize
import jp.developer.bbee.assemblepc.util.updateMyProfile
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxSize
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.height
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.theme.breakpoint.rememberBreakpoint
import kotlinx.coroutines.launch
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px

@Page
@Composable
fun MyPage() {
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
                MyPageContents(
                    breakpoint = breakpoint,
                    userName = it.userName ?: "(NO NAME)",
                    onClick = { showNameEditPopup = true }
                )
            }
        }
    }
}

@Composable
fun MyPageContents(
    breakpoint: Breakpoint,
    userName: String,
    onClick: () -> Unit = {}
) {
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
                name = userName,
                fontSize = breakpoint.largeSize(),
                onClick = onClick
            )
            SearchBar(breakpoint = breakpoint)
        }

        TabMenuLayout(breakpoint = breakpoint, tabList = MyTabMenu.toTabInfoList()) {}
    }
}