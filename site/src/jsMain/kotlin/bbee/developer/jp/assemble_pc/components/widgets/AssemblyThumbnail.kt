package bbee.developer.jp.assemble_pc.components.widgets

import androidx.compose.runtime.Composable
import bbee.developer.jp.assemble_pc.models.Theme
import bbee.developer.jp.assemble_pc.util.Const
import bbee.developer.jp.assemble_pc.util.Res
import bbee.developer.jp.assemble_pc.util.largeSize
import bbee.developer.jp.assemble_pc.util.maxLines
import bbee.developer.jp.assemble_pc.util.smallSize
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.css.ObjectFit
import com.varabyte.kobweb.compose.css.Overflow
import com.varabyte.kobweb.compose.css.TextOverflow
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.borderRadius
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.fontFamily
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.fontWeight
import com.varabyte.kobweb.compose.ui.modifiers.minSize
import com.varabyte.kobweb.compose.ui.modifiers.objectFit
import com.varabyte.kobweb.compose.ui.modifiers.overflow
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.size
import com.varabyte.kobweb.compose.ui.modifiers.textOverflow
import com.varabyte.kobweb.silk.components.graphics.Image
import com.varabyte.kobweb.silk.components.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.components.text.SpanText
import org.jetbrains.compose.web.css.px

@Composable
fun AssemblyThumbnail(
    breakpoint: Breakpoint
) {
    val imageSize = if (breakpoint >= Breakpoint.MD) 104 else 80

    Row(modifier = Modifier.padding(4.px)) {
        Box(modifier = Modifier.minSize(imageSize.px).color(Colors.Gray)) {
            Image(
                modifier = Modifier.size(imageSize.px).objectFit(ObjectFit.Contain),
                src = Res.Image.ITEM
            )

            CategoryTag(breakpoint = breakpoint)
        }

        Column {
            SpanText(
                modifier = Modifier
                    .fillMaxWidth()
                    .color(Colors.Black)
                    .fontFamily(Const.FONT_FAMILY)
                    .fontSize(breakpoint.largeSize())
                    .textOverflow(TextOverflow.Ellipsis)
                    .overflow(Overflow.Hidden)
                    .maxLines(2),
                text = "ABCD-12345-67890ABCD-12345-67890ABCD-12345-67890ABCD-12345-67890ABCD-12345-67890ABCD-12345-67890"
            )

            Column(
                modifier = Modifier.weight(1),
                verticalArrangement = Arrangement.Center
            ) {
                SpanText(
                    modifier = Modifier
                        .color(Colors.Black)
                        .fontFamily(Const.FONT_FAMILY)
                        .fontSize(breakpoint.largeSize())
                        .fontWeight(FontWeight.Bold),
                    text = "¥10,000"
                )

                SpanText(
                    modifier = Modifier
                        .color(Colors.Black)
                        .fontFamily(Const.FONT_FAMILY)
                        .fontSize(breakpoint.smallSize()),
                    text = "¥10,000（作成時）"
                )
            }
        }
    }
}

@Composable
fun CategoryTag(
    breakpoint: Breakpoint
) {
    Box(
        modifier = Modifier
            .backgroundColor(Theme.PURPLE.rgb)
            .borderRadius(8.px)
            .padding(leftRight = 4.px)
    ) {
        SpanText(
            modifier = Modifier
                .color(Colors.Black)
                .fontFamily(Const.FONT_FAMILY)
                .fontSize(breakpoint.smallSize()),
            text = "PCケース"
        )
    }
}
