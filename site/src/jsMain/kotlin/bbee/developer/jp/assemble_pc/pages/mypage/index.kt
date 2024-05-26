package bbee.developer.jp.assemble_pc.pages.mypage

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import bbee.developer.jp.assemble_pc.components.layouts.CommonMyPageLayout
import bbee.developer.jp.assemble_pc.components.layouts.TabMenuLayout
import bbee.developer.jp.assemble_pc.components.widgets.ProfileEdit
import bbee.developer.jp.assemble_pc.components.widgets.SearchBar
import bbee.developer.jp.assemble_pc.models.MyTabMenu
import bbee.developer.jp.assemble_pc.models.Profile
import bbee.developer.jp.assemble_pc.util.IsUserLoggedIn
import bbee.developer.jp.assemble_pc.util.getMyProfile
import bbee.developer.jp.assemble_pc.util.largeSize
import bbee.developer.jp.assemble_pc.util.updateMyProfile
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
import com.varabyte.kobweb.silk.components.style.breakpoint.Breakpoint
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