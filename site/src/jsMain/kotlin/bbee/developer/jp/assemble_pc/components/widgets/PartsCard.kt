package bbee.developer.jp.assemble_pc.components.widgets

import androidx.compose.runtime.Composable
import bbee.developer.jp.assemble_pc.models.Theme
import bbee.developer.jp.assemble_pc.util.Const
import bbee.developer.jp.assemble_pc.util.Res
import bbee.developer.jp.assemble_pc.util.hugeSize
import bbee.developer.jp.assemble_pc.util.largeSize
import bbee.developer.jp.assemble_pc.util.maxLines
import bbee.developer.jp.assemble_pc.util.mediumSize
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.css.ObjectFit
import com.varabyte.kobweb.compose.css.TextDecorationLine
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.borderRadius
import com.varabyte.kobweb.compose.ui.modifiers.boxShadow
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxSize
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.flexWrap
import com.varabyte.kobweb.compose.ui.modifiers.fontFamily
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.fontWeight
import com.varabyte.kobweb.compose.ui.modifiers.objectFit
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.size
import com.varabyte.kobweb.compose.ui.modifiers.textDecorationLine
import com.varabyte.kobweb.compose.ui.modifiers.width
import com.varabyte.kobweb.silk.components.graphics.Image
import com.varabyte.kobweb.silk.components.navigation.Link
import com.varabyte.kobweb.silk.components.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.components.text.SpanText
import org.jetbrains.compose.web.css.FlexWrap
import org.jetbrains.compose.web.css.px

@Composable
fun PartsCard(
    breakpoint: Breakpoint
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(if(breakpoint >= Breakpoint.MD) 16.px else 4.px),
        contentAlignment = Alignment.Center
    ) {
        PartsCardContent(breakpoint = breakpoint)
    }
}

@Composable
fun PartsCardContent(
    breakpoint: Breakpoint
) {
    val imageSize = if (breakpoint >= Breakpoint.MD) 104 else 64

    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(8.px)
            .backgroundColor(Colors.White)
            .borderRadius(8.px)
            .boxShadow(blurRadius = 4.px, color = Theme.TRANSLUCENT.rgb),
        verticalArrangement = Arrangement.Center
    ) {
        Link(
            modifier = Modifier
                .fontFamily(Const.FONT_FAMILY)
                .fontSize(breakpoint.largeSize())
                .fontWeight(FontWeight.Bold)
                .textDecorationLine(TextDecorationLine.Underline),
            path = "https://google.com",
            text = "CC560 ドスパラWeb限定モデル"
        )

        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                modifier = Modifier.size(imageSize.px).objectFit(ObjectFit.Contain),
                src = Res.Image.ITEM
            )

            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                val description = "DEEPCOOL\n電源規格：ATX PS2\nATX/MicroATX/Mini-ITX/Mini-ITX/Mini-ITX/Mini-ITX/Mini-ITX/Mini-ITX/Mini-ITX/Mini-ITX\n210x477x416 mm"
                description.split("\n")
                    .filter { it.isNotBlank() }
                    .forEach {
                        SpanText(
                            modifier = Modifier
                                .flexWrap(FlexWrap.Wrap)
                                .color(Colors.Black)
                                .fontFamily(Const.FONT_FAMILY)
                                .fontSize(breakpoint.mediumSize())
                                .fontWeight(FontWeight.Bold),
                            text = it
                        )
                    }
            }

            Column(
                modifier = Modifier.width(200.px),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                SpanText(
                    modifier = Modifier
                        .padding(topBottom = 8.px)
                        .color(Colors.Black)
                        .fontFamily(Const.FONT_FAMILY)
                        .fontSize(breakpoint.hugeSize())
                        .fontWeight(FontWeight.Bold)
                        .maxLines(1),
                    text = "¥ 1,234,567"
                )

                FloatingButton(
                    breakpoint = breakpoint,
                    text = "登録"
                )
            }
        }
    }
}