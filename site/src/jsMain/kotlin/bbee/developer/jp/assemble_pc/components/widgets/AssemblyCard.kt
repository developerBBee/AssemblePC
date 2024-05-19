package bbee.developer.jp.assemble_pc.components.widgets

import androidx.compose.runtime.Composable
import bbee.developer.jp.assemble_pc.models.Theme
import bbee.developer.jp.assemble_pc.util.Const
import bbee.developer.jp.assemble_pc.util.largeSize
import bbee.developer.jp.assemble_pc.util.maxLines
import com.varabyte.kobweb.compose.css.Cursor
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.css.TextAlign
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.foundation.layout.Spacer
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.borderRadius
import com.varabyte.kobweb.compose.ui.modifiers.boxShadow
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.cursor
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxHeight
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.fontFamily
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.fontWeight
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.modifiers.minWidth
import com.varabyte.kobweb.compose.ui.modifiers.onClick
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.textAlign
import com.varabyte.kobweb.compose.ui.modifiers.width
import com.varabyte.kobweb.silk.components.layout.HorizontalDivider
import com.varabyte.kobweb.silk.components.layout.SimpleGrid
import com.varabyte.kobweb.silk.components.layout.numColumns
import com.varabyte.kobweb.silk.components.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.components.text.SpanText
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px

@Composable
fun AssemblyCard(
    breakpoint: Breakpoint
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.px),
        contentAlignment = Alignment.Center
    ) {
        AssemblyCardContent(breakpoint = breakpoint)
    }
}

@Composable
fun AssemblyCardContent(
    breakpoint: Breakpoint
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .backgroundColor(Colors.White)
            .borderRadius(8.px)
            .boxShadow(blurRadius = 4.px, color = Theme.TRANSLUCENT.rgb),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        AssemblyHeader(breakpoint = breakpoint)

        HorizontalDivider(modifier = Modifier
            .width(95.percent)
            .margin(topBottom = 2.px)
            .align(Alignment.CenterHorizontally)
        )

        AssemblyMain(breakpoint = breakpoint)

        HorizontalDivider(modifier = Modifier
            .width(95.percent)
            .margin(topBottom = 2.px)
            .align(Alignment.CenterHorizontally)
        )

        AssemblyFooter(breakpoint = breakpoint)
    }
}

@Composable
fun AssemblyHeader(
    breakpoint: Breakpoint
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(95.percent)
            .padding(topBottom = 8.px),
        horizontalArrangement = Arrangement.Center
    ) {
        SpanText(
            modifier = Modifier
                .fontFamily(Const.FONT_FAMILY)
                .fontWeight(FontWeight.Bold)
                .fontSize(breakpoint.largeSize())
                .maxLines(1),
            text = "コスパ最強コスパ最強コスパ最強コスパ最強コスパ最強コスパ最強コスパ最強コスパ最強コスパ最強コスパ最強")

        Spacer()

        SpanText(
            modifier = Modifier
                .minWidth(80.px)
                .fontFamily(Const.FONT_FAMILY)
                .fontWeight(FontWeight.Bold)
                .fontSize(breakpoint.largeSize())
                .maxLines(1),
            text = "作成者：山田XYZ")
    }
}

@Composable
fun AssemblyMain(
    breakpoint: Breakpoint,
    onShowMore: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(95.percent)
            .padding(topBottom = 8.px),
        horizontalArrangement = Arrangement.Center,
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(50.percent),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SimpleGrid(numColumns = numColumns(base = 1, lg = 2)) {
                AssemblyThumbnail(breakpoint = breakpoint)
                AssemblyThumbnail(breakpoint = breakpoint)
                if (breakpoint >= Breakpoint.LG) {
                    AssemblyThumbnail(breakpoint = breakpoint)
                    AssemblyThumbnail(breakpoint = breakpoint)
                }
            }

            SpanText(
                modifier = Modifier
                    .color(Theme.BLUE.rgb)
                    .textAlign(TextAlign.Center)
                    .fontFamily(Const.FONT_FAMILY)
                    .fontSize(breakpoint.largeSize())
                    .fontWeight(FontWeight.Medium)
                    .cursor(Cursor.Pointer)
                    .onClick { onShowMore() },
                text = "すべて表示"
            )
        }

        AssemblyInfo(
            modifier = Modifier
                .fillMaxWidth(50.percent)
                .margin(2.px)
                .fillMaxHeight(),
            breakpoint = breakpoint
        )
    }
}

@Composable
fun AssemblyFooter(
    breakpoint: Breakpoint
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(95.percent)
            .padding(topBottom = 8.px),
        horizontalArrangement = Arrangement.Center
    ) {
        ImpressionIcons(breakpoint = breakpoint)
    }
}