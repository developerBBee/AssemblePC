package bbee.developer.jp.assemble_pc.components.widgets

import androidx.compose.runtime.Composable
import bbee.developer.jp.assemble_pc.models.AssemblyDetail
import bbee.developer.jp.assemble_pc.models.ItemCategory
import bbee.developer.jp.assemble_pc.util.Const
import bbee.developer.jp.assemble_pc.util.largeSize
import bbee.developer.jp.assemble_pc.util.maxLines
import bbee.developer.jp.assemble_pc.util.smallSize
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.css.ObjectFit
import com.varabyte.kobweb.compose.css.WordBreak
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.fontFamily
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.fontWeight
import com.varabyte.kobweb.compose.ui.modifiers.minSize
import com.varabyte.kobweb.compose.ui.modifiers.objectFit
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.size
import com.varabyte.kobweb.compose.ui.modifiers.wordBreak
import com.varabyte.kobweb.silk.components.graphics.Image
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.components.text.SpanText
import org.jetbrains.compose.web.css.px

@Composable
fun AssemblyThumbnail(
    breakpoint: Breakpoint,
    detail: AssemblyDetail,
) {
    val imageSize = when (breakpoint) {
        Breakpoint.XL -> 104
        Breakpoint.MD, Breakpoint.SM -> 96
        Breakpoint.LG, Breakpoint.ZERO -> 80
    }

    Row(modifier = Modifier.padding(4.px)) {
        Box(modifier = Modifier.minSize(imageSize.px).color(Colors.Gray)) {
            Image(
                modifier = Modifier.size(imageSize.px).objectFit(ObjectFit.Contain),
                src = detail.item.imageUrl
            )

            CategoryTag(
                breakpoint = breakpoint,
                itemCategory = ItemCategory.from(detail.item.itemCategoryId),
                fontSize = breakpoint.smallSize(),
            )
        }

        Column {
            SpanText(
                modifier = Modifier
                    .color(Colors.Black)
                    .fontFamily(Const.FONT_FAMILY)
                    .fontSize(breakpoint.largeSize())
                    .wordBreak(WordBreak.BreakAll)
                    .maxLines(2),
                text = detail.item.itemName
            )

            Column(
                modifier = Modifier.weight(1),
                verticalArrangement = Arrangement.Center
            ) {
                SpanText(
                    modifier = Modifier
                        .color(Colors.Black)
                        .fontFamily(Const.FONT_FAMILY)
                        .fontSize(breakpoint.largeSize())
                        .fontWeight(FontWeight.Bold),
                    text = detail.item.price.yen()
                )

                SpanText(
                    modifier = Modifier
                        .color(Colors.Black)
                        .fontFamily(Const.FONT_FAMILY)
                        .fontSize(breakpoint.smallSize()),
                    text = "${detail.priceAtRegistered.yen()}（作成時）"
                )
            }
        }
    }
}