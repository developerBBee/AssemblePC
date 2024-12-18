package jp.developer.bbee.assemblepc.components.widgets

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import jp.developer.bbee.assemblepc.models.Theme
import jp.developer.bbee.assemblepc.util.Const
import jp.developer.bbee.assemblepc.util.largeSize
import com.varabyte.kobweb.compose.css.FontWeight
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
import com.varabyte.kobweb.compose.ui.modifiers.fontWeight
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
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint
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
    message: String? = null,
    initText: String = "",
    hintText: String = "",
    buttonText: String,
    maxLength: Int = 30,
    onDismiss: () -> Unit,
    onPositiveClick: (String) -> Unit = {}
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
                .onClick { onDismiss() }
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
                message = message,
                initText = initText,
                hintText = hintText,
                buttonText = buttonText,
                maxLength = maxLength,
                onPositiveClick = onPositiveClick
            )
        }
    }
}

@Composable
fun EditContent(
    breakpoint: Breakpoint,
    title: String?,
    message: String?,
    initText: String,
    hintText: String,
    buttonText: String,
    maxLength: Int,
    onPositiveClick: (String) -> Unit
) {
    var inputText by remember { mutableStateOf(initText) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(topBottom = 16.px),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (!title.isNullOrEmpty()) {
            SpanText(
                modifier = Modifier
                    .margin(topBottom = 16.px)
                    .fontFamily(Const.FONT_FAMILY)
                    .fontWeight(FontWeight.Bold),
                text = title
            )
        }

        message?.split("\n")?.forEach { messageEachLine ->
            SpanText(
                modifier = Modifier
                    .fontFamily(Const.FONT_FAMILY)
                    .fontSize(breakpoint.largeSize()),
                text = messageEachLine
            )
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
            placeholder = hintText,
            value = inputText,
            onValueChange = { inputText = it },
            onCommit = { inputText = inputText.take(maxLength) },
        )
        FloatingButton(
            modifier = Modifier,
            breakpoint = breakpoint,
            text = buttonText,
            enabled = inputText != initText,
            onClick = { onPositiveClick(inputText) }
        )
    }
}