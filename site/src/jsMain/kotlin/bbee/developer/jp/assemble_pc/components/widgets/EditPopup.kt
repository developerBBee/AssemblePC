package bbee.developer.jp.assemble_pc.components.widgets

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import bbee.developer.jp.assemble_pc.models.Theme
import bbee.developer.jp.assemble_pc.util.Const
import com.varabyte.kobweb.compose.css.TextAlign
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.borderRadius
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxSize
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.fontFamily
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.height
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.modifiers.onClick
import com.varabyte.kobweb.compose.ui.modifiers.onFocusOut
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.position
import com.varabyte.kobweb.compose.ui.modifiers.textAlign
import com.varabyte.kobweb.compose.ui.modifiers.width
import com.varabyte.kobweb.compose.ui.modifiers.zIndex
import com.varabyte.kobweb.silk.components.forms.Input
import com.varabyte.kobweb.silk.components.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.components.text.SpanText
import org.jetbrains.compose.web.attributes.InputType
import org.jetbrains.compose.web.css.Position
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.css.vh
import org.jetbrains.compose.web.css.vw

@Composable
fun EditPopup(
    breakpoint: Breakpoint,
    title: String? = null,
    initText: String = "",
    maxLength: Int = 30,
    onDialogDismiss: () -> Unit,
    onUpdateClick: (String) -> Unit = {}
) {
    Box(
        modifier = Modifier
            .width(100.vw)
            .height(100.vh)
            .position(Position.Fixed)
            .zIndex(19),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .backgroundColor(Theme.TRANSLUCENT.rgb)
                .onClick { onDialogDismiss() }
        )
        Box(
            modifier = Modifier
                .width(640.px)
                .padding(all = 32.px)
                .backgroundColor(Colors.White)
                .borderRadius(r = 4.px)
        ) {
            EditContent(
                breakpoint = breakpoint,
                title = title,
                initText = initText,
                maxLength = maxLength,
                onUpdateClick = onUpdateClick
            )
        }
    }
}

@Composable
fun EditContent(
    breakpoint: Breakpoint,
    title: String?,
    initText: String,
    maxLength: Int,
    onUpdateClick: (String) -> Unit
) {
    var inputText by remember { mutableStateOf(initText) }
    var nameUpdateEnabled by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(topBottom = 16.px),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (!title.isNullOrEmpty()) {
            SpanText(text = title)
        }
        Input(
            modifier = Modifier
                .fillMaxWidth()
                .margin(topBottom = 16.px)
                .textAlign(TextAlign.Center)
                .fontFamily(Const.FONT_FAMILY)
                .fontSize(16.px)
                .onFocusOut { inputText = inputText.take(maxLength) },
            type = InputType.Text,
            value = inputText,
            onValueChanged = { inputText = it },
            onCommit = { inputText = inputText.take(maxLength) },
        )
        FloatingButton(
            modifier = Modifier.padding(8.px),
            breakpoint = breakpoint,
            text = "OK",
            enabled = inputText != initText,
            onClick = { onUpdateClick(inputText) }
        )
    }
}