package bbee.developer.jp.assemble_pc.util

import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.border
import com.varabyte.kobweb.compose.ui.modifiers.outline
import com.varabyte.kobweb.compose.ui.styleModifier
import com.varabyte.kobweb.silk.components.style.breakpoint.Breakpoint
import org.jetbrains.compose.web.css.CSSSizeValue
import org.jetbrains.compose.web.css.CSSUnit
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.px

fun Modifier.noBorder(): Modifier = this
    .border(
        width = 0.px,
        style = LineStyle.None,
        color = Colors.Transparent
    )
    .outline(
        width = 0.px,
        style = LineStyle.None,
        color = Colors.Transparent
    )

fun Modifier.maxLines(maxLines: Int): Modifier = this.styleModifier {
    property("display", "-webkit-box")
    property("-webkit-line-clamp", "$maxLines")
    property("line-clamp", "$maxLines")
    property("-webkit-box-orient", "vertical")
}

fun Breakpoint.smallSize(): CSSSizeValue<CSSUnit.px> = when (this) {
    Breakpoint.ZERO -> 9.px
    Breakpoint.SM -> 9.px
    Breakpoint.MD -> 9.px
    Breakpoint.LG -> 10.px
    Breakpoint.XL -> 10.px
}

fun Breakpoint.mediumSize(): CSSSizeValue<CSSUnit.px> = when (this) {
    Breakpoint.ZERO -> 10.px
    Breakpoint.SM -> 10.px
    Breakpoint.MD -> 11.px
    Breakpoint.LG -> 12.px
    Breakpoint.XL -> 12.px
}

fun Breakpoint.largeSize(): CSSSizeValue<CSSUnit.px> = when (this) {
    Breakpoint.ZERO -> 11.px
    Breakpoint.SM -> 11.px
    Breakpoint.MD -> 13.px
    Breakpoint.LG -> 14.px
    Breakpoint.XL -> 14.px
}

fun Breakpoint.hugeSize(): CSSSizeValue<CSSUnit.px> = when (this) {
    Breakpoint.ZERO -> 12.px
    Breakpoint.SM -> 12.px
    Breakpoint.MD -> 14.px
    Breakpoint.LG -> 16.px
    Breakpoint.XL -> 16.px
}
