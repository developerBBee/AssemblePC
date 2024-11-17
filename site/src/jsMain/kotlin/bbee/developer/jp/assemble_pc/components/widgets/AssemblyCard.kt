package bbee.developer.jp.assemble_pc.components.widgets

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import bbee.developer.jp.assemble_pc.models.Assembly
import bbee.developer.jp.assemble_pc.models.Theme
import bbee.developer.jp.assemble_pc.util.Const
import bbee.developer.jp.assemble_pc.util.largeSize
import bbee.developer.jp.assemble_pc.util.maxLines
import com.varabyte.kobweb.compose.css.Cursor
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.css.TextAlign
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.foundation.layout.Spacer
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.borderRadius
import com.varabyte.kobweb.compose.ui.modifiers.boxShadow
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.cursor
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxHeight
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.fontFamily
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.fontWeight
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.modifiers.minWidth
import com.varabyte.kobweb.compose.ui.modifiers.onClick
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.textAlign
import com.varabyte.kobweb.compose.ui.modifiers.width
import com.varabyte.kobweb.silk.components.layout.HorizontalDivider
import com.varabyte.kobweb.silk.components.layout.SimpleGrid
import com.varabyte.kobweb.silk.components.layout.numColumns
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.components.text.SpanText
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px

@Composable
fun AssemblyCard(
    breakpoint: Breakpoint,
    assembly: Assembly,
    isMyFavorite: Boolean = false,
    ownerName: String = assembly.ownerName ?: "(NO NAME)",
    onFavoriteClick: (Boolean) -> Unit = {},
    onReferenceClick: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.px),
        contentAlignment = Alignment.Center
    ) {
        AssemblyCardContent(
            breakpoint = breakpoint,
            assembly = assembly,
            isMyFavorite = isMyFavorite,
            ownerName = ownerName,
            onFavoriteClick = onFavoriteClick,
            onReferenceClick = onReferenceClick
        )
    }
}

@Composable
fun AssemblyCardContent(
    breakpoint: Breakpoint,
    assembly: Assembly,
    isMyFavorite: Boolean,
    ownerName: String,
    onFavoriteClick: (Boolean) -> Unit,
    onReferenceClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .backgroundColor(Colors.White)
            .borderRadius(8.px)
            .boxShadow(blurRadius = 4.px, color = Theme.TRANSLUCENT.rgb),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        AssemblyHeader(
            breakpoint = breakpoint,
            assemblyName = assembly.assemblyName,
            ownerName = ownerName,
        )

        HorizontalDivider(modifier = Modifier
            .width(95.percent)
            .margin(topBottom = 2.px)
            .align(Alignment.CenterHorizontally)
        )

        AssemblyMain(
            breakpoint = breakpoint,
            assembly = assembly
        )

        HorizontalDivider(modifier = Modifier
            .width(95.percent)
            .margin(topBottom = 2.px)
            .align(Alignment.CenterHorizontally)
        )

        AssemblyFooter(
            breakpoint = breakpoint,
            isMyFavorite = isMyFavorite,
            favoriteCount = assembly.favoriteCount,
            referenceCount = 0, // TODO assembly.referenceCount,
            onFavoriteClick = onFavoriteClick,
            onReferenceClick = onReferenceClick
        )
    }
}

@Composable
fun AssemblyHeader(
    breakpoint: Breakpoint,
    assemblyName: String,
    ownerName: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(95.percent)
            .padding(topBottom = 8.px),
        horizontalArrangement = Arrangement.Center
    ) {
        SpanText(
            modifier = Modifier
                .fontFamily(Const.FONT_FAMILY)
                .fontWeight(FontWeight.Bold)
                .fontSize(breakpoint.largeSize())
                .maxLines(1),
            text = assemblyName
        )

        Spacer()

        SpanText(
            modifier = Modifier
                .minWidth(80.px)
                .fontFamily(Const.FONT_FAMILY)
                .fontWeight(FontWeight.Bold)
                .fontSize(breakpoint.largeSize())
                .maxLines(1),
            text = ownerName
        )
    }
}

@Composable
fun AssemblyMain(
    breakpoint: Breakpoint,
    assembly: Assembly
) {
    var showAll by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth(95.percent)
            .padding(topBottom = 8.px),
        horizontalArrangement = Arrangement.Center,
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(50.percent),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val showNumber = if (breakpoint >= Breakpoint.LG) 4 else 2
            val detailsSize = assembly.assemblyDetails.size

            SimpleGrid(numColumns = numColumns(base = 1, lg = 2)) {
                assembly.assemblyDetails
                    .sortedBy { it.item.itemCategoryId.id }
                    .take(if (showAll) detailsSize else showNumber)
                    .forEach { detail ->
                        AssemblyThumbnail(breakpoint = breakpoint, detail = detail)
                    }
            }

            if (showNumber < detailsSize) {
                SpanText(
                    modifier = Modifier
                        .color(Theme.BLUE.rgb)
                        .textAlign(TextAlign.Center)
                        .fontFamily(Const.FONT_FAMILY)
                        .fontSize(breakpoint.largeSize())
                        .fontWeight(FontWeight.Medium)
                        .cursor(Cursor.Pointer)
                        .onClick { showAll = !showAll },
                    text = if (showAll) "表示を省略" else "すべて表示"
                )
            }
        }

        AssemblyInfo(
            modifier = Modifier
                .fillMaxWidth(50.percent)
                .margin(2.px)
                .fillMaxHeight(),
            breakpoint = breakpoint,
            assembly = assembly
        )
    }
}

@Composable
fun AssemblyFooter(
    breakpoint: Breakpoint,
    isMyFavorite: Boolean,
    favoriteCount: Int,
    referenceCount: Int,
    onFavoriteClick: (Boolean) -> Unit,
    onReferenceClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(95.percent)
            .padding(topBottom = 8.px),
        horizontalArrangement = Arrangement.Center
    ) {
        ImpressionIcons(
            breakpoint = breakpoint,
            isMyFavorite = isMyFavorite,
            favoriteCount = favoriteCount,
            referenceCount = referenceCount,
            onFavoriteClick = onFavoriteClick,
            onReferenceClick = onReferenceClick,
        )
    }
}