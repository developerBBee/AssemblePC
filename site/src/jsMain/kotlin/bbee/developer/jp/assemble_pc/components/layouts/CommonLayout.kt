package bbee.developer.jp.assemble_pc.components.layouts

import androidx.compose.runtime.Composable
import bbee.developer.jp.assemble_pc.components.sections.Advertisement
import bbee.developer.jp.assemble_pc.components.sections.Header
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxSize
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.silk.components.style.breakpoint.Breakpoint
import org.jetbrains.compose.web.css.px

@Composable
fun CommonLayout(
    breakpoint: Breakpoint,
    content: @Composable () -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        Header(breakpoint)

        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceAround,
        ) {
            if (breakpoint >= Breakpoint.XL) Advertisement(modifier = Modifier.margin(right = 16.px))

            content()

            if (breakpoint >= Breakpoint.LG) Advertisement(modifier = Modifier.margin(left = 16.px))
        }
    }
}