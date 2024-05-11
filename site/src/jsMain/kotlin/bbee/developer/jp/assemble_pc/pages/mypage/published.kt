package bbee.developer.jp.assemble_pc.pages.mypage

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import bbee.developer.jp.assemble_pc.components.layouts.TabMenuLayout
import bbee.developer.jp.assemble_pc.components.sections.Advertisement
import bbee.developer.jp.assemble_pc.components.sections.Header
import bbee.developer.jp.assemble_pc.components.widgets.AssemblyCard
import bbee.developer.jp.assemble_pc.components.widgets.Profile
import bbee.developer.jp.assemble_pc.components.widgets.SearchBar
import bbee.developer.jp.assemble_pc.firebase.auth
import bbee.developer.jp.assemble_pc.models.MyTabMenu
import bbee.developer.jp.assemble_pc.util.largeSize
import bbee.developer.jp.assemble_pc.util.signInAnonymous
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
import dev.gitlive.firebase.auth.FirebaseUser
import kotlinx.coroutines.launch
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px

@Page
@Composable
fun PublishedPage() {
    val scope = rememberCoroutineScope()
    val breakpoint = rememberBreakpoint()

    val user: FirebaseUser? by auth.authStateChanged
        .collectAsState(initial = null, scope.coroutineContext)

    LaunchedEffect(Unit) {
        scope.launch {
            signInAnonymous()
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        Header(breakpoint)

        PublishedContents(breakpoint)
    }
}

@Composable
fun PublishedContents(
    breakpoint: Breakpoint
) {
    Row(
        modifier = Modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.SpaceAround,
    ) {
        if (breakpoint >= Breakpoint.XL) Advertisement(modifier = Modifier.margin(right = 16.px))

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

            TabMenuLayout(breakpoint = breakpoint, tabList = MyTabMenu.toTabInfoList()) {
                Column(modifier = Modifier.fillMaxWidth()) {
//                        MyTabMenu.CREATING -> {
//                            SpanText(text = "作成中のアセンブリ")
//                        }
//                        MyTabMenu.PUBLISHED -> {
                            AssemblyCard(breakpoint = breakpoint)
                            AssemblyCard(breakpoint = breakpoint)
                            AssemblyCard(breakpoint = breakpoint)
                            AssemblyCard(breakpoint = breakpoint)
//                        }
//                        MyTabMenu.FAVORITE -> {
//                            SpanText(text = "お気に入りのアセンブリ")
//                        }
//                    }
                }
            }
        }

        if (breakpoint >= Breakpoint.LG) Advertisement(modifier = Modifier.margin(left = 16.px))
    }
}
