package bbee.developer.jp.assemble_pc.pages.mypage

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import bbee.developer.jp.assemble_pc.components.layouts.CommonLayout
import bbee.developer.jp.assemble_pc.components.layouts.TabMenuLayout
import bbee.developer.jp.assemble_pc.components.widgets.AssemblyCard
import bbee.developer.jp.assemble_pc.components.widgets.Profile
import bbee.developer.jp.assemble_pc.components.widgets.SearchBar
import bbee.developer.jp.assemble_pc.models.Assembly
import bbee.developer.jp.assemble_pc.models.MyTabMenu
import bbee.developer.jp.assemble_pc.util.Const
import bbee.developer.jp.assemble_pc.util.IsUserLoggedIn
import bbee.developer.jp.assemble_pc.util.getMyPublishedAssemblies
import bbee.developer.jp.assemble_pc.util.hugeSize
import bbee.developer.jp.assemble_pc.util.largeSize
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
fun PublishedPage() {
    val breakpoint = rememberBreakpoint()

    IsUserLoggedIn {
        CommonLayout(breakpoint = breakpoint) {
            PublishedContents(breakpoint)
        }
    }
}

@Composable
fun PublishedContents(
    breakpoint: Breakpoint
) {
    val scope = rememberCoroutineScope()
    val assemblies: MutableList<Assembly> = remember { mutableStateListOf() }
    LaunchedEffect(Unit) {
        scope.launch {
            assemblies.addAll(getMyPublishedAssemblies(skip = 0))
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
            Profile(fontSize = breakpoint.largeSize())
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
                        text = "公開済みの構成はありません"
                    )
                }
                assemblies.forEach { assembly ->
                    AssemblyCard(
                        breakpoint = breakpoint,
                        assembly = assembly
                    )
                }
            }
        }
    }
}
