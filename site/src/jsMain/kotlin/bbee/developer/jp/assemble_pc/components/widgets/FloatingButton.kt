package bbee.developer.jp.assemble_pc.components.widgets

import androidx.compose.runtime.Composable
import bbee.developer.jp.assemble_pc.models.Theme
import bbee.developer.jp.assemble_pc.util.Const
import bbee.developer.jp.assemble_pc.util.enabledButton
import bbee.developer.jp.assemble_pc.util.hugeSize
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.css.TextAlign
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.borderRadius
import com.varabyte.kobweb.compose.ui.modifiers.boxShadow
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.fontFamily
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.fontWeight
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.textAlign
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.components.text.SpanText
import org.jetbrains.compose.web.css.CSSColorValue
import org.jetbrains.compose.web.css.px

@Composable
fun FloatingButton(
    modifier: Modifier = Modifier,
    breakpoint: Breakpoint,
    text: String,
    enabled: Boolean = true,
    color: CSSColorValue = Colors.White,
    backgroundColor: CSSColorValue = Theme.BLUE.rgb,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .padding(
                topBottom = if (breakpoint >= Breakpoint.MD) 8.px else 4.px,
                leftRight = if (breakpoint >= Breakpoint.MD) 16.px else 8.px
            )
            .borderRadius(8.px)
            .boxShadow(offsetX = 1.px, offsetY = 1.px, blurRadius = 2.px, color = Theme.TRANSLUCENT.rgb)
            .enabledButton(enabled = enabled, backgroundColor = backgroundColor, onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        SpanText(
            modifier = Modifier
                .color(if (enabled) color else Theme.DARK_GRAY.rgb)
                .textAlign(TextAlign.Center)
                .fontFamily(Const.FONT_FAMILY)
                .fontSize(breakpoint.hugeSize())
                .fontWeight(if (enabled) FontWeight.Bold else FontWeight.Normal),
            text = text
        )
    }
}