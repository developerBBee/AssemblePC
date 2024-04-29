package bbee.developer.jp.assemble_pc.components.widgets

import androidx.compose.runtime.Composable
import bbee.developer.jp.assemble_pc.util.Const
import bbee.developer.jp.assemble_pc.util.mediumSize
import com.varabyte.kobweb.compose.css.Cursor
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.attrsModifier
import com.varabyte.kobweb.compose.ui.modifiers.classNames
import com.varabyte.kobweb.compose.ui.modifiers.cursor
import com.varabyte.kobweb.compose.ui.modifiers.fontFamily
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.fontWeight
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.modifiers.onClick
import com.varabyte.kobweb.compose.ui.modifiers.width
import com.varabyte.kobweb.silk.components.icons.fa.FaShareFromSquare
import com.varabyte.kobweb.silk.components.icons.fa.FaThumbsUp
import com.varabyte.kobweb.silk.components.icons.fa.IconStyle
import com.varabyte.kobweb.silk.components.navigation.Link
import com.varabyte.kobweb.silk.components.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.components.text.SpanText
import org.jetbrains.compose.web.css.px

@Composable
fun ImpressionIcons(
    breakpoint: Breakpoint
) {
    Row(
        modifier = Modifier.width(240.px),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier
                .cursor(Cursor.Pointer)
                .onClick { },
            verticalAlignment = Alignment.CenterVertically
        ) {
            FaThumbsUp(style = IconStyle.FILLED)

            SpanText(
                modifier = Modifier
                    .margin(2.px)
                    .fontFamily(Const.FONT_FAMILY)
                    .fontSize(breakpoint.mediumSize())
                    .fontWeight(FontWeight.Bold),
                text = "100"
            )
        }

        Row(
            modifier = Modifier
                .cursor(Cursor.Pointer)
                .onClick { },
            verticalAlignment = Alignment.CenterVertically
        ) {
            FaShareFromSquare(style = IconStyle.FILLED)

            SpanText(
                modifier = Modifier
                    .margin(2.px)
                    .fontFamily(Const.FONT_FAMILY)
                    .fontSize(breakpoint.mediumSize())
                    .fontWeight(FontWeight.Bold),
                text = "100"
            )
        }

        Link(
            path = "https://twitter.com/intent/tweet?ref_src=twsrc%5Etfw",
            modifier = Modifier
                .classNames("twitter-share-button")
                .attrsModifier {
                    attr("data-show-count", "false")
                }
        ) {}
    }
}