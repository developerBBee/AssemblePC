package bbee.developer.jp.assemble_pc.components.sections

import androidx.compose.runtime.Composable
import bbee.developer.jp.assemble_pc.models.Theme
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxHeight
import com.varabyte.kobweb.compose.ui.modifiers.minWidth
import com.varabyte.kobweb.compose.ui.modifiers.width
import org.jetbrains.compose.web.css.px

@Composable
fun Advertisement(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .width(200.px)
            .fillMaxHeight() // TODO
            .minWidth(200.px)
            .backgroundColor(Theme.TRANSLUCENT.rgb)
    ) {

    }
}