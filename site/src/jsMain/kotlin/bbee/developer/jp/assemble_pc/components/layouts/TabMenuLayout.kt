package bbee.developer.jp.assemble_pc.components.layouts

import androidx.compose.runtime.Composable
import bbee.developer.jp.assemble_pc.components.widgets.TabMenuButton
import bbee.developer.jp.assemble_pc.models.TabInfo
import bbee.developer.jp.assemble_pc.models.Theme
import bbee.developer.jp.assemble_pc.util.largeSize
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.borderTop
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.core.rememberPageContext
import com.varabyte.kobweb.silk.components.layout.HorizontalDivider
import com.varabyte.kobweb.silk.components.style.breakpoint.Breakpoint
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px

@Composable
fun TabMenuLayout(
    breakpoint: Breakpoint,
    tabList: List<TabInfo>,
    content: @Composable () -> Unit
) {
    val context = rememberPageContext()

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(
                if (breakpoint > Breakpoint.SM) 50.percent else 90.percent
            ),
            horizontalArrangement = Arrangement.SpaceAround,
        ) {
            tabList.forEach { info ->
                TabMenuButton(
                    text = info.title,
                    fontSize = breakpoint.largeSize(),
                    selected = context.route.path.contains(info.route),
                    onClick = { context.router.navigateTo(info.route) }
                )
            }
        }

        HorizontalDivider(
            modifier = Modifier
                .fillMaxWidth()
                .margin(top = 0.px, bottom = 8.px)
                .borderTop(width = 1.px, style = LineStyle.Solid, color = Theme.LIGHT_GRAY.rgb)
        )

        content()
    }
}