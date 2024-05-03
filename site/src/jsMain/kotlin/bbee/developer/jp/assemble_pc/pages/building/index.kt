package bbee.developer.jp.assemble_pc.pages.building

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import bbee.developer.jp.assemble_pc.components.sections.Advertisement
import bbee.developer.jp.assemble_pc.components.sections.Header
import bbee.developer.jp.assemble_pc.components.widgets.PartsCard
import bbee.developer.jp.assemble_pc.components.widgets.TabMenuButton
import bbee.developer.jp.assemble_pc.firebase.auth
import bbee.developer.jp.assemble_pc.models.BuildingTabMenu
import bbee.developer.jp.assemble_pc.models.Parts
import bbee.developer.jp.assemble_pc.models.Theme
import bbee.developer.jp.assemble_pc.pages.mypage.SearchBar
import bbee.developer.jp.assemble_pc.util.Const
import bbee.developer.jp.assemble_pc.util.hugeSize
import bbee.developer.jp.assemble_pc.util.largeSize
import bbee.developer.jp.assemble_pc.util.maxLines
import bbee.developer.jp.assemble_pc.util.signInAnonymous
import com.varabyte.kobweb.compose.css.Cursor
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.borderRadius
import com.varabyte.kobweb.compose.ui.modifiers.borderTop
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.cursor
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxSize
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.fontFamily
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.fontWeight
import com.varabyte.kobweb.compose.ui.modifiers.height
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.width
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.silk.components.icons.fa.FaPenToSquare
import com.varabyte.kobweb.silk.components.layout.HorizontalDivider
import com.varabyte.kobweb.silk.components.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.theme.breakpoint.rememberBreakpoint
import dev.gitlive.firebase.auth.FirebaseUser
import kotlinx.coroutines.launch
import org.jetbrains.compose.web.css.CSSSizeValue
import org.jetbrains.compose.web.css.CSSUnit
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Option
import org.jetbrains.compose.web.dom.Select

@Page
@Composable
fun BuildingPage() {
    val scope = rememberCoroutineScope()
    val breakpoint = rememberBreakpoint()

    val user: FirebaseUser? by auth.authStateChanged
        .collectAsState(initial = null, scope.coroutineContext)

    LaunchedEffect(Unit) {
        scope.launch {
            signInAnonymous()
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        Header(breakpoint)

        BuildingContents(breakpoint)
    }
}

@Composable
fun BuildingContents(
    breakpoint: Breakpoint,
) {
    Row(
        modifier = Modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.SpaceAround,
    ) {
        if (breakpoint >= Breakpoint.XL) Advertisement(modifier = Modifier.margin(right = 16.px))

        Column(
            modifier = Modifier
                .fillMaxSize(95.percent)
                .margin(8.px),
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
                AssemblyName(fontSize = breakpoint.largeSize())
                TotalAmount(fontSize = breakpoint.hugeSize())
            }

            TabMenuLayout(breakpoint = breakpoint)
        }

        if (breakpoint >= Breakpoint.LG) Advertisement(modifier = Modifier.margin(left = 16.px))
    }
}

@Composable
fun CreateNew(fontSize: CSSSizeValue<CSSUnit.px>) {
    Row(
        modifier = Modifier
            .margin(8.px)
            .cursor(Cursor.Pointer),
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
                .maxLines(1),
            text = "新規作成",
        )
    }
}

@Composable
fun AssemblyName(fontSize: CSSSizeValue<CSSUnit.px>) {
    Row(
        modifier = Modifier
            .margin(8.px)
            .cursor(Cursor.Pointer),
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
            text = "コスパ最強コスパ最強コスパ最強コスパ最強",
        )
    }
}

@Composable
fun TotalAmount(fontSize: CSSSizeValue<CSSUnit.px>) {
    Row(
        modifier = Modifier
            .width(200.px)
            .margin(4.px)
            .cursor(Cursor.Pointer),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End
    ) {
        SpanText(
            modifier = Modifier
                .margin(right = 4.px)
                .fontSize(fontSize)
                .fontFamily(Const.FONT_FAMILY)
                .fontWeight(FontWeight.Bold)
                .maxLines(1),
            text = "合計金額",
        )
        SpanText(
            modifier = Modifier
                .fontSize(fontSize)
                .fontFamily(Const.FONT_FAMILY)
                .fontWeight(FontWeight.Bold)
                .maxLines(1),
            text = "¥ 1,234,567",
        )
    }
}

@Composable
fun TabMenuLayout(
    breakpoint: Breakpoint,
    selectedMenu: BuildingTabMenu = BuildingTabMenu.SELECTION
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(
                if (breakpoint > Breakpoint.SM) 50.percent else 75.percent
            ),
            horizontalArrangement = Arrangement.SpaceAround,
        ) {
            TabMenuButton(
                text = "構成",
                fontSize = breakpoint.largeSize(),
                selected = selectedMenu == BuildingTabMenu.ASSEMBLY
            )
            TabMenuButton(
                text = "パーツ選択",
                fontSize = breakpoint.largeSize(),
                selected = selectedMenu == BuildingTabMenu.SELECTION
            )
        }

        HorizontalDivider(
            modifier = Modifier
                .fillMaxWidth()
                .margin(top = 0.px, bottom = 8.px)
                .borderTop(width = 1.px, style = LineStyle.Solid, color = Theme.LIGHT_GRAY.rgb)
        )

        Column(modifier = Modifier.fillMaxWidth()) {
            when (selectedMenu) {
                BuildingTabMenu.ASSEMBLY -> {
                    SpanText(text = "構成画面")
                }
                BuildingTabMenu.SELECTION -> {
                    Row(modifier = Modifier.fillMaxWidth()) {
                        if (breakpoint >= Breakpoint.MD) {
                            Box(modifier = Modifier.padding(16.px)) {
                                PartsMenu(fontSize = breakpoint.largeSize())
                            }
                        }

                        Box(
                            modifier = Modifier
                                .weight(1)
                                .padding(if(breakpoint >= Breakpoint.MD) 16.px else 8.px)
                        ) {
                            PartsListLayout(breakpoint = breakpoint)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun PartsMenu(
    fontSize: CSSSizeValue<CSSUnit.px>,
    selectedParts: Parts = Parts.MOTHER_BOARD
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.px)
            .backgroundColor(Theme.LIGHT_GRAY.rgb)
            .borderRadius(8.px),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Parts.entries.forEach { parts ->
            SpanText(
                modifier = Modifier
                    .margin(topBottom = 8.px)
                    .color(if (selectedParts == parts) Theme.BLUE.rgb else Theme.DARK_GRAY.rgb)
                    .fontSize(fontSize)
                    .fontFamily(Const.FONT_FAMILY)
                    .fontWeight(FontWeight.Bold)
                    .cursor(Cursor.Pointer)
                    .maxLines(1),
                text = parts.displayName
            )
        }
    }
}

@Composable
fun PartsListLayout(
    breakpoint: Breakpoint
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(if(breakpoint >= Breakpoint.MD) 16.px else 8.px)
            .backgroundColor(Theme.LIGHT_GRAY.rgb)
            .borderRadius(8.px),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        FilterAndSearch(breakpoint = breakpoint)

        Box (
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    bottom = 16.px,
                    leftRight = 4.px
                )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .backgroundColor(Colors.White)
                    .padding(if(breakpoint >= Breakpoint.MD) 16.px else 8.px)
                    .borderRadius(8.px),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                PartsCard(breakpoint = breakpoint)
                PartsCard(breakpoint = breakpoint)
                PartsCard(breakpoint = breakpoint)
                PartsCard(breakpoint = breakpoint)
            }
        }
    }
}

@Composable
fun FilterAndSearch(
    breakpoint: Breakpoint
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                leftRight = if (breakpoint >= Breakpoint.MD) 24.px else 4.px,
                topBottom = 8.px
            ),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Select {
            Option(
                value = "1",
                content = { SpanText(text = "価格が安い順") }
            )
            Option(
                value = "2",
                content = { SpanText(text = "価格が高い順") }
            )
        }

        SearchBar(breakpoint = breakpoint)
    }
}