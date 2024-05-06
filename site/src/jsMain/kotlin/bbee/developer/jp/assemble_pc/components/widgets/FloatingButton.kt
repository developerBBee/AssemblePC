package bbee.developer.jp.assemble_pc.components.widgets

import androidx.compose.runtime.Composable
import bbee.developer.jp.assemble_pc.models.Theme
import bbee.developer.jp.assemble_pc.util.Const
import bbee.developer.jp.assemble_pc.util.hugeSize
import com.varabyte.kobweb.compose.css.Cursor
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.css.TextAlign
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.borderRadius
import com.varabyte.kobweb.compose.ui.modifiers.boxShadow
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.cursor
import com.varabyte.kobweb.compose.ui.modifiers.fontFamily
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.fontWeight
import com.varabyte.kobweb.compose.ui.modifiers.height
import com.varabyte.kobweb.compose.ui.modifiers.onClick
import com.varabyte.kobweb.compose.ui.modifiers.textAlign
import com.varabyte.kobweb.compose.ui.modifiers.width
import com.varabyte.kobweb.silk.components.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.components.text.SpanText
import org.jetbrains.compose.web.css.CSSColorValue
import org.jetbrains.compose.web.css.px

@Composable
fun FloatingButton(
    breakpoint: Breakpoint,
    text: String,
    color: CSSColorValue = Colors.White,
    backgroundColor: CSSColorValue = Theme.BLUE.rgb,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .width(if (breakpoint >= Breakpoint.MD) 64.px else 48.px)
            .height(if (breakpoint >= Breakpoint.MD) 32.px else 24.px)
            .backgroundColor(backgroundColor)
            .borderRadius(8.px)
            .boxShadow(offsetX = 1.px, offsetY = 1.px, blurRadius = 2.px, color = Theme.TRANSLUCENT.rgb)
            .cursor(Cursor.Pointer)
            .onClick { onClick() },
        contentAlignment = Alignment.Center
    ) {
        SpanText(
            modifier = Modifier
                .color(color)
                .textAlign(TextAlign.Center)
                .fontFamily(Const.FONT_FAMILY)
                .fontSize(breakpoint.hugeSize())
                .fontWeight(FontWeight.Bold),
            text = text
        )
    }
}