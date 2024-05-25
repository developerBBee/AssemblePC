package bbee.developer.jp.assemble_pc.pages.mypage

import androidx.compose.runtime.Composable
import bbee.developer.jp.assemble_pc.components.layouts.CommonLayout
import bbee.developer.jp.assemble_pc.components.layouts.TabMenuLayout
import bbee.developer.jp.assemble_pc.components.widgets.Profile
import bbee.developer.jp.assemble_pc.components.widgets.SearchBar
import bbee.developer.jp.assemble_pc.models.MyTabMenu
import bbee.developer.jp.assemble_pc.util.IsUserLoggedIn
import bbee.developer.jp.assemble_pc.util.largeSize
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
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px

@Page
@Composable
fun MyPage() {
    val breakpoint = rememberBreakpoint()

    IsUserLoggedIn {
        CommonLayout(breakpoint = breakpoint) {
            MyPageContents(breakpoint)
        }
    }
}

@Composable
fun MyPageContents(
    breakpoint: Breakpoint
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
            Profile(fontSize = breakpoint.largeSize())
            SearchBar(breakpoint = breakpoint)
        }

        TabMenuLayout(breakpoint = breakpoint, tabList = MyTabMenu.toTabInfoList()) {}
    }
}