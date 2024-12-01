package jp.developer.bbee.assemblepc.components.widgets

import androidx.compose.runtime.Composable
import jp.developer.bbee.assemblepc.models.ItemCategory
import jp.developer.bbee.assemblepc.models.Theme
import jp.developer.bbee.assemblepc.util.Const
import jp.developer.bbee.assemblepc.util.maxLines
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.borderRadius
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.fontFamily
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.minWidth
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.components.text.SpanText
import org.jetbrains.compose.web.css.CSSSizeValue
import org.jetbrains.compose.web.css.CSSUnit
import org.jetbrains.compose.web.css.px

@Composable
fun CategoryTag(
    breakpoint: Breakpoint,
    itemCategory: ItemCategory,
    fontSize: CSSSizeValue<CSSUnit.px>,
) {
    Box(
        modifier = Modifier
            .minWidth(if(breakpoint >= Breakpoint.MD) 40.px else 32.px)
            .backgroundColor(Theme.PURPLE.rgb)
            .borderRadius(if(breakpoint >= Breakpoint.MD) 8.px else 4.px)
            .padding(
                topBottom = if(breakpoint >= Breakpoint.MD) 2.px else 1.px,
                leftRight = if(breakpoint >= Breakpoint.MD) 4.px else 2.px
            ),
        contentAlignment = Alignment.Center
    ) {
        SpanText(
            modifier = Modifier
                .color(Colors.Black)
                .fontFamily(Const.FONT_FAMILY)
                .fontSize(fontSize)
                .maxLines(1),
            text = itemCategory.displayName
        )
    }
}
