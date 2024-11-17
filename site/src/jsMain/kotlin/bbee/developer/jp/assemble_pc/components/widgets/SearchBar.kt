package bbee.developer.jp.assemble_pc.components.widgets

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import bbee.developer.jp.assemble_pc.models.Theme
import bbee.developer.jp.assemble_pc.util.Const
import bbee.developer.jp.assemble_pc.util.largeSize
import bbee.developer.jp.assemble_pc.util.maxLines
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.fontFamily
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.outlineColor
import com.varabyte.kobweb.compose.ui.modifiers.width
import com.varabyte.kobweb.silk.components.forms.Input
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint
import org.jetbrains.compose.web.attributes.InputType
import org.jetbrains.compose.web.css.CSSColorValue
import org.jetbrains.compose.web.css.px

@Composable
fun SearchBar(
    breakpoint: Breakpoint,
    color: CSSColorValue = Colors.White,
    outlineColor: CSSColorValue = Theme.LIGHT_GRAY.rgb,
) {
    var searchText by remember { mutableStateOf("") }

    Input(
        modifier = Modifier
            .width(if (breakpoint > Breakpoint.SM) 300.px else 200.px)
            .backgroundColor(color)
            .outlineColor(outlineColor)
            .fontSize(breakpoint.largeSize())
            .fontFamily(Const.FONT_FAMILY)
            .maxLines(1),
        type = InputType.Search,
        placeholder = "検索",
        value = searchText,
        onValueChange = { searchText = it }
    )
}