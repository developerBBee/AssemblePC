package bbee.developer.jp.assemble_pc.components.widgets

import androidx.compose.runtime.Composable
import bbee.developer.jp.assemble_pc.models.Item
import bbee.developer.jp.assemble_pc.models.ItemCategory
import bbee.developer.jp.assemble_pc.models.ItemId
import bbee.developer.jp.assemble_pc.models.PartsButtonType
import bbee.developer.jp.assemble_pc.models.Theme
import bbee.developer.jp.assemble_pc.util.Const
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
import com.varabyte.kobweb.compose.ui.modifiers.height
import com.varabyte.kobweb.compose.ui.modifiers.objectFit
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.size
import com.varabyte.kobweb.compose.ui.modifiers.textDecorationLine
import com.varabyte.kobweb.compose.ui.modifiers.width
import com.varabyte.kobweb.compose.ui.thenIf
import com.varabyte.kobweb.silk.components.graphics.Image
import com.varabyte.kobweb.silk.components.navigation.Link
import com.varabyte.kobweb.silk.components.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.components.text.SpanText
import org.jetbrains.compose.web.css.FlexWrap
import org.jetbrains.compose.web.css.px

@Composable
fun PartsCard(
    breakpoint: Breakpoint,
    item: Item,
    itemCategory: ItemCategory? = null,
    hasItemCount: Int = 0,
    buttonType: PartsButtonType,
    onClick: (ItemId) -> Unit
) {
    Box(modifier = Modifier.fillMaxWidth()) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(if (breakpoint >= Breakpoint.MD) 16.px else 4.px)
                .thenIf(
                    condition = itemCategory != null,
                    other = Modifier.padding(
                        top = if (breakpoint >= Breakpoint.MD) 16.px else 12.px,
                        bottom = if (breakpoint >= Breakpoint.MD) 16.px else 4.px,
                        leftRight = if (breakpoint >= Breakpoint.MD) 16.px else 4.px
                    )
                ),
        ) {
            PartsCardContent(
                breakpoint = breakpoint,
                item = item,
                hasItemCount = hasItemCount,
                buttonType = buttonType,
                onClick = onClick
            )
        }

        itemCategory?.let {
            CategoryTag(
                breakpoint = breakpoint,
                itemCategory = it,
                fontSize = breakpoint.largeSize()
            )
        }
    }
}

@Composable
fun PartsCardContent(
    breakpoint: Breakpoint,
    item: Item,
    hasItemCount: Int,
    buttonType: PartsButtonType,
    onClick: (ItemId) -> Unit,
) {
    val imageSize = if (breakpoint >= Breakpoint.MD) 104 else 64

    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(if (breakpoint >= Breakpoint.MD) 16.px else 8.px)
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
            path = item.linkUrl,
            text = item.itemName,
        )

        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                modifier = Modifier.size(imageSize.px).objectFit(ObjectFit.Contain),
                src = item.imageUrl
            )

            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                item.description.split("\n")
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
                modifier = Modifier
                    .width(if (breakpoint >= Breakpoint.MD) 160.px else 120.px),
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.Center
            ) {
                Box(
                    modifier = Modifier.height(32.px),
                    contentAlignment = Alignment.Center
                ) {
                    if (hasItemCount > 0) {
                        SpanText(
                            modifier = Modifier
                                .padding(topBottom = 8.px)
                                .color(Theme.DARK_GRAY.rgb)
                                .fontFamily(Const.FONT_FAMILY)
                                .fontSize(breakpoint.mediumSize())
                                .fontWeight(FontWeight.Bold)
                                .maxLines(1),
                            text = "${hasItemCount} 個登録済"
                        )
                    }
                }

                SpanText(
                    modifier = Modifier
                        .padding(topBottom = 8.px)
                        .color(Colors.Black)
                        .fontFamily(Const.FONT_FAMILY)
                        .fontSize(breakpoint.hugeSize())
                        .fontWeight(FontWeight.Bold)
                        .maxLines(1),
                    text = item.price.yen()
                )

                FloatingButton(
                    breakpoint = breakpoint,
                    text = buttonType.text,
                    backgroundColor = buttonType.bgColor,
                    onClick = { item.itemId?.let { onClick(it) } }
                )
            }
        }
    }
}