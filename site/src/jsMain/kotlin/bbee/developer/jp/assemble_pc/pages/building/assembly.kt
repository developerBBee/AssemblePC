package bbee.developer.jp.assemble_pc.pages.building

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import bbee.developer.jp.assemble_pc.components.layouts.BuildingNavLayout
import bbee.developer.jp.assemble_pc.components.layouts.CommonLayout
import bbee.developer.jp.assemble_pc.components.widgets.FloatingButton
import bbee.developer.jp.assemble_pc.components.widgets.PartsCard
import bbee.developer.jp.assemble_pc.models.Item
import bbee.developer.jp.assemble_pc.models.ItemCategory
import bbee.developer.jp.assemble_pc.models.PartsButtonType
import bbee.developer.jp.assemble_pc.models.Theme
import bbee.developer.jp.assemble_pc.util.Const
import bbee.developer.jp.assemble_pc.util.IsUserLoggedIn
import bbee.developer.jp.assemble_pc.util.hugeSize
import com.varabyte.kobweb.compose.css.PointerEvents
import com.varabyte.kobweb.compose.css.Resize
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.fontFamily
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.height
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.modifiers.maxHeight
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.pointerEvents
import com.varabyte.kobweb.compose.ui.modifiers.position
import com.varabyte.kobweb.compose.ui.modifiers.resize
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.silk.components.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.theme.breakpoint.rememberBreakpoint
import org.jetbrains.compose.web.css.Position
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.css.vh
import org.jetbrains.compose.web.dom.TextArea

@Page
@Composable
fun AssemblyPage() {
    val breakpoint = rememberBreakpoint()
    val items = remember { mutableStateListOf<Item>() }

    IsUserLoggedIn {
        CommonLayout(breakpoint = breakpoint) {
            BuildingNavLayout(breakpoint = breakpoint) {
                AssemblyContents(breakpoint = breakpoint, items = items)
            }
        }

        StickyActionBar(breakpoint = breakpoint)
    }
}

@Composable
fun AssemblyContents(
    breakpoint: Breakpoint,
    items: List<Item>,
) {
    var comment: String by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 48.px),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        TextArea(
            value = comment,
            attrs = Modifier
                .fillMaxWidth(90.percent)
                .height(120.px)
                .maxHeight(120.px)
                .margin(topBottom = 8.px)
                .padding(16.px)
                .fontFamily(Const.FONT_FAMILY)
                .fontSize(breakpoint.hugeSize())
                .resize(Resize.None)
                .toAttrs {
                    attr("placeholder", "構成に関するコメント")
                    onInput { comment = it.value }
                }
        )

        items.forEach { item ->
            PartsCard(
                breakpoint = breakpoint,
                item = item,
                itemCategory = ItemCategory.from(id = item.itemCategoryId),
                buttonType = PartsButtonType.DELETION,
                onClick = {}
            )
        }
    }
}


@Composable
fun StickyActionBar(breakpoint: Breakpoint) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.vh)
            .pointerEvents(PointerEvents.None),
        contentAlignment = Alignment.BottomCenter
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .pointerEvents(PointerEvents.Auto)
                .padding(2.px)
                .position(Position.Fixed)
                .backgroundColor(Theme.TRANSLUCENT.rgb),
            contentAlignment = Alignment.BottomCenter
        ) {
            FloatingButton(
                breakpoint = breakpoint,
                text = "投稿",
                color = Colors.Black,
                backgroundColor = Theme.YELLOW.rgb
            ) {}
        }
    }
}