package bbee.developer.jp.assemble_pc.components.layouts

import androidx.compose.runtime.Composable
import bbee.developer.jp.assemble_pc.models.BuildingTabMenu
import bbee.developer.jp.assemble_pc.models.Price
import bbee.developer.jp.assemble_pc.util.Const
import bbee.developer.jp.assemble_pc.util.hugeSize
import bbee.developer.jp.assemble_pc.util.largeSize
import bbee.developer.jp.assemble_pc.util.maxLines
import com.varabyte.kobweb.compose.css.Cursor
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.cursor
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxSize
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.fontFamily
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.fontWeight
import com.varabyte.kobweb.compose.ui.modifiers.height
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.modifiers.onClick
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.width
import com.varabyte.kobweb.silk.components.icons.fa.FaFile
import com.varabyte.kobweb.silk.components.icons.fa.FaPenToSquare
import com.varabyte.kobweb.silk.components.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.components.text.SpanText
import org.jetbrains.compose.web.css.CSSSizeValue
import org.jetbrains.compose.web.css.CSSUnit
import org.jetbrains.compose.web.css.px

@Composable
fun BuildingNavLayout(
    breakpoint: Breakpoint,
    assemblyName: String,
    totalAmount: Price,
    onAssemblyNameClick: () -> Unit,
    content: @Composable () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.px),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        CreateNew(fontSize = breakpoint.largeSize())

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(40.px)
                .padding(if (breakpoint >= Breakpoint.MD) 24.px else 4.px),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            AssemblyName(
                assemblyName = assemblyName,
                fontSize = breakpoint.largeSize(),
                onClick = onAssemblyNameClick
            )

            TotalAmount(breakpoint = breakpoint, totalAmount = totalAmount)
        }

        TabMenuLayout(
            breakpoint = breakpoint,
            tabList = BuildingTabMenu.toTabInfoList(),
        ) {
            content()
        }
    }
}

@Composable
fun CreateNew(fontSize: CSSSizeValue<CSSUnit.px>) {
    Row(
        modifier = Modifier
            .height(32.px)
            .margin(8.px)
            .cursor(Cursor.Pointer),
        verticalAlignment = Alignment.CenterVertically
    ) {
        FaFile(
            modifier = Modifier
                .margin(right = 8.px)
                .cursor(Cursor.Pointer)
        )
        SpanText(
            modifier = Modifier
                .fontSize(fontSize)
                .fontFamily(Const.FONT_FAMILY)
                .fontWeight(FontWeight.Bold)
                .maxLines(1),
            text = "新規作成",
        )
    }
}

@Composable
fun AssemblyName(
    assemblyName: String,
    fontSize: CSSSizeValue<CSSUnit.px>,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .margin(8.px)
            .cursor(Cursor.Pointer)
            .onClick { onClick() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        FaPenToSquare(
            modifier = Modifier
                .margin(right = 8.px)
                .cursor(Cursor.Pointer)
        )
        SpanText(
            modifier = Modifier
                .fontSize(fontSize)
                .fontFamily(Const.FONT_FAMILY)
                .fontWeight(FontWeight.Bold)
                .maxLines(2),
            text = assemblyName.ifEmpty { "(NO NAME)" },
        )
    }
}

@Composable
fun TotalAmount(
    breakpoint: Breakpoint,
    totalAmount: Price,
) {
    if (breakpoint >= Breakpoint.SM) {
        Row(
            modifier = Modifier
                .width(200.px)
                .padding(4.px),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
        ) {
            TotalAmountContents(
                totalAmount,
                titleFontSize = breakpoint.hugeSize(),
                valueFontSize = breakpoint.hugeSize(),
            )
        }
    } else {
        Column(
            modifier = Modifier
                .width(200.px)
                .padding(4.px),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            TotalAmountContents(
                totalAmount,
                titleFontSize = breakpoint.largeSize(),
                valueFontSize = breakpoint.hugeSize(),
            )
        }
    }
}

@Composable
fun TotalAmountContents(
    totalAmount: Price,
    titleFontSize: CSSSizeValue<CSSUnit.px>,
    valueFontSize: CSSSizeValue<CSSUnit.px>
) {
    SpanText(
        modifier = Modifier
            .padding(right = 4.px)
            .fontSize(titleFontSize)
            .fontFamily(Const.FONT_FAMILY)
            .fontWeight(FontWeight.Bold)
            .maxLines(1),
        text = "合計金額",
    )

    SpanText(
        modifier = Modifier
            .fontSize(valueFontSize)
            .fontFamily(Const.FONT_FAMILY)
            .fontWeight(FontWeight.Bold)
            .maxLines(1),
        text = totalAmount.yen(),
    )
}