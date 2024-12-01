package jp.developer.bbee.assemblepc.components.widgets

import androidx.compose.runtime.Composable
import jp.developer.bbee.assemblepc.models.Theme
import jp.developer.bbee.assemblepc.util.Const
import jp.developer.bbee.assemblepc.util.mediumSize
import com.varabyte.kobweb.compose.css.Cursor
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.attrsModifier
import com.varabyte.kobweb.compose.ui.modifiers.classNames
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.cursor
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxHeight
import com.varabyte.kobweb.compose.ui.modifiers.fontFamily
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.fontWeight
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.modifiers.onClick
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.width
import com.varabyte.kobweb.silk.components.icons.fa.FaShareFromSquare
import com.varabyte.kobweb.silk.components.icons.fa.FaThumbsUp
import com.varabyte.kobweb.silk.components.icons.fa.IconSize
import com.varabyte.kobweb.silk.components.icons.fa.IconStyle
import com.varabyte.kobweb.silk.components.navigation.Link
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.components.text.SpanText
import org.jetbrains.compose.web.css.px

@Composable
fun ImpressionIcons(
    breakpoint: Breakpoint,
    isMyFavorite: Boolean,
    favoriteCount: Int,
    referenceCount: Int,
    onFavoriteClick: (Boolean) -> Unit,
    onReferenceClick: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxHeight().width(240.px),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier
                .fillMaxHeight()
                .cursor(Cursor.Pointer)
                .onClick { onFavoriteClick(isMyFavorite) },
            verticalAlignment = Alignment.CenterVertically
        ) {
            FaThumbsUp(
                modifier = Modifier
                    .padding(bottom = 2.px)
                    .color(if (isMyFavorite) Theme.RED.rgb else Theme.DARK_GRAY.rgb),
                style = IconStyle.FILLED,
                size = IconSize.LG
            )

            SpanText(
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .margin(left = 4.px)
                    .fontFamily(Const.FONT_FAMILY)
                    .fontSize(breakpoint.mediumSize())
                    .fontWeight(FontWeight.Bold)
                    .color(if (isMyFavorite) Theme.RED.rgb else Theme.DARK_GRAY.rgb),
                text = favoriteCount.toString()
            )
        }

        Row(
            modifier = Modifier
                .cursor(Cursor.Pointer)
                .onClick { onReferenceClick() },
            verticalAlignment = Alignment.CenterVertically
        ) {
            FaShareFromSquare(
                modifier = Modifier.color(Theme.DARK_GRAY.rgb),
                style = IconStyle.FILLED,
                size = IconSize.LG
            )

            SpanText(
                modifier = Modifier
                    .margin(left = 4.px)
                    .fontFamily(Const.FONT_FAMILY)
                    .fontSize(breakpoint.mediumSize())
                    .fontWeight(FontWeight.Bold)
                    .color(Theme.DARK_GRAY.rgb),
                text = referenceCount.toString()
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