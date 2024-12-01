package jp.developer.bbee.assemblepc.components.widgets

import androidx.compose.runtime.Composable
import jp.developer.bbee.assemblepc.util.Const
import jp.developer.bbee.assemblepc.util.maxLines
import com.varabyte.kobweb.compose.css.Cursor
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.cursor
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxHeight
import com.varabyte.kobweb.compose.ui.modifiers.fontFamily
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.fontWeight
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.modifiers.onClick
import com.varabyte.kobweb.silk.components.icons.fa.FaPenToSquare
import com.varabyte.kobweb.silk.components.text.SpanText
import org.jetbrains.compose.web.css.CSSSizeValue
import org.jetbrains.compose.web.css.CSSUnit
import org.jetbrains.compose.web.css.px

@Composable
fun ProfileEdit(
    name: String,
    fontSize: CSSSizeValue<CSSUnit.px>,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxHeight(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        FaPenToSquare(
            modifier = Modifier
                .margin(right = 8.px)
                .cursor(Cursor.Pointer)
                .onClick { onClick() }
        )
        SpanText(
            modifier = Modifier
                .fontSize(fontSize)
                .fontFamily(Const.FONT_FAMILY)
                .fontWeight(FontWeight.Bold)
                .maxLines(1),
            text = name
        )
    }
}