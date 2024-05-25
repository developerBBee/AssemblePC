package bbee.developer.jp.assemble_pc.components.widgets

import androidx.compose.runtime.Composable
import bbee.developer.jp.assemble_pc.models.Assembly
import bbee.developer.jp.assemble_pc.models.Theme
import bbee.developer.jp.assemble_pc.util.Const
import bbee.developer.jp.assemble_pc.util.hugeSize
import bbee.developer.jp.assemble_pc.util.mediumSize
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.borderRadius
import com.varabyte.kobweb.compose.ui.modifiers.boxShadow
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.fontFamily
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.fontWeight
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.silk.components.icons.fa.FaTurnDown
import com.varabyte.kobweb.silk.components.icons.fa.FaTurnUp
import com.varabyte.kobweb.silk.components.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.components.text.SpanText
import org.jetbrains.compose.web.css.px

@Composable
fun AssemblyInfo(
    modifier: Modifier = Modifier,
    breakpoint: Breakpoint,
    assembly: Assembly
) {
    Column(
        modifier = modifier
            .padding(8.px)
            .backgroundColor(Colors.White)
            .borderRadius(8.px)
            .boxShadow(blurRadius = 4.px, color = Theme.LIGHT_GRAY.rgb),
        verticalArrangement = Arrangement.Center
    ) {
        SpanText(
            modifier = Modifier
                .margin(topBottom = 4.px)
                .color(Colors.Black)
                .fontFamily(Const.FONT_FAMILY)
                .fontWeight(FontWeight.SemiBold)
                .fontSize(breakpoint.mediumSize()),
            text = assembly.publishedDate
        )

        Column(modifier = Modifier.margin(topBottom = 4.px)) {
            val totalPriceAtNow = assembly.assemblyDetails
                .map { it.item.price }
                .reduce { acc, price -> acc + price }
            val totalPriceAtRegistration = assembly.assemblyDetails
                .map { it.priceAtRegistered }
                .reduce { acc, price -> acc + price }

            SpanText(
                modifier = Modifier
                    .color(Colors.Black)
                    .fontFamily(Const.FONT_FAMILY)
                    .fontWeight(FontWeight.SemiBold)
                    .fontSize(breakpoint.mediumSize()),
                text = "合計金額："
            )

            Row {
                SpanText(
                    modifier = Modifier
                        .margin(leftRight = 4.px)
                        .color(Colors.Black)
                        .fontFamily(Const.FONT_FAMILY)
                        .fontSize(breakpoint.hugeSize())
                        .fontWeight(FontWeight.Bold),
                    text = totalPriceAtNow.yen()
                )

                if (totalPriceAtNow > totalPriceAtRegistration) {
                    FaTurnUp(modifier = Modifier.color(Theme.GREEN.rgb))
                } else if (totalPriceAtNow < totalPriceAtRegistration) {
                    FaTurnDown(modifier = Modifier.color(Theme.RED.rgb))
                }
            }

            SpanText(
                modifier = Modifier
                    .margin(leftRight = 4.px)
                    .color(Colors.Black)
                    .fontFamily(Const.FONT_FAMILY)
                    .fontWeight(FontWeight.SemiBold)
                    .fontSize(breakpoint.mediumSize()),
                text = "${totalPriceAtRegistration.yen()}（作成時）"
            )
        }

        Column(
            modifier = Modifier
                .weight(1)
                .margin(topBottom = 2.px),
        ) {
            SpanText(
                modifier = Modifier
                    .color(Colors.Black)
                    .fontFamily(Const.FONT_FAMILY)
                    .fontWeight(FontWeight.SemiBold)
                    .fontSize(breakpoint.mediumSize()),
                text = "作成者コメント："
            )
            SpanText(
                modifier = Modifier
                    .padding(leftRight = 8.px)
                    .color(Colors.Black)
                    .fontFamily(Const.FONT_FAMILY)
                    .fontSize(breakpoint.mediumSize()),
                text = assembly.ownerComment
            )
        }
    }
}