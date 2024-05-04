package bbee.developer.jp.assemble_pc.components.widgets

import androidx.compose.runtime.Composable
import bbee.developer.jp.assemble_pc.models.ItemCategory
import bbee.developer.jp.assemble_pc.models.Theme
import bbee.developer.jp.assemble_pc.util.Const
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.borderRadius
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.fontFamily
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.silk.components.text.SpanText
import org.jetbrains.compose.web.css.CSSSizeValue
import org.jetbrains.compose.web.css.CSSUnit
import org.jetbrains.compose.web.css.px

@Composable
fun CategoryTag(
    itemCategory: ItemCategory,
    fontSize: CSSSizeValue<CSSUnit.px>,
) {
    Box(
        modifier = Modifier
            .backgroundColor(Theme.PURPLE.rgb)
            .borderRadius(8.px)
            .padding(topBottom = 1.px, leftRight = 8.px)
    ) {
        SpanText(
            modifier = Modifier
                .color(Colors.Black)
                .fontFamily(Const.FONT_FAMILY)
                .fontSize(fontSize),
            text = itemCategory.displayName
        )
    }
}
