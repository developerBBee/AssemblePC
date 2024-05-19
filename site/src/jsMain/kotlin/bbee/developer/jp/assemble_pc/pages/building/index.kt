package bbee.developer.jp.assemble_pc.pages.building

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import bbee.developer.jp.assemble_pc.components.layouts.BuildingNavLayout
import bbee.developer.jp.assemble_pc.components.layouts.CommonLayout
import bbee.developer.jp.assemble_pc.components.widgets.EditPopup
import bbee.developer.jp.assemble_pc.components.widgets.PartsCard
import bbee.developer.jp.assemble_pc.components.widgets.SearchBar
import bbee.developer.jp.assemble_pc.models.Assembly
import bbee.developer.jp.assemble_pc.models.Item
import bbee.developer.jp.assemble_pc.models.ItemCategory
import bbee.developer.jp.assemble_pc.models.ItemId
import bbee.developer.jp.assemble_pc.models.PartsButtonType
import bbee.developer.jp.assemble_pc.models.Theme
import bbee.developer.jp.assemble_pc.util.Const
import bbee.developer.jp.assemble_pc.util.IsUserLoggedIn
import bbee.developer.jp.assemble_pc.util.getCurrentAssembly
import bbee.developer.jp.assemble_pc.util.largeSize
import bbee.developer.jp.assemble_pc.util.maxLines
import bbee.developer.jp.assemble_pc.util.totalAmount
import bbee.developer.jp.assemble_pc.util.updateAssembly
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
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.cursor
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.fontFamily
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.fontWeight
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.modifiers.minWidth
import com.varabyte.kobweb.compose.ui.modifiers.onClick
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.core.rememberPageContext
import com.varabyte.kobweb.silk.components.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.theme.breakpoint.rememberBreakpoint
import kotlinx.coroutines.launch
import org.jetbrains.compose.web.css.CSSSizeValue
import org.jetbrains.compose.web.css.CSSUnit
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Option
import org.jetbrains.compose.web.dom.Select

@Page
@Composable
fun BuildingPage() {
    val scope = rememberCoroutineScope()
    val breakpoint = rememberBreakpoint()
    val items = remember { mutableStateListOf<Item>() }

    var currentAssembly: Assembly? by remember { mutableStateOf(null) }

    var showAssemblyNamePopup by remember { mutableStateOf(false) }

    IsUserLoggedIn {
        CommonLayout(breakpoint = breakpoint) {
            LaunchedEffect(Unit) {
                getCurrentAssembly()?.also {
                    currentAssembly = it
                }
            }

            currentAssembly?.let { assembly ->
                BuildingNavLayout(
                    breakpoint = breakpoint,
                    assemblyName = assembly.assemblyName,
                    totalAmount = assembly.totalAmount(),
                    onAssemblyNameClick = {}
                ) {
                    BuildingContents(breakpoint = breakpoint, items = items)
                }
            }
        }

        if (showAssemblyNamePopup) {
            currentAssembly?.let { assembly ->
                EditPopup(
                    breakpoint = breakpoint,
                    title = "構成名の更新",
                    initText = assembly.assemblyName,
                    onDialogDismiss = { showAssemblyNamePopup = false },
                    onUpdateClick = { newName ->
                        scope.launch {
                            val newAssembly = assembly.copy(assemblyName = newName)
                            val isSuccess = updateAssembly(newAssembly)
                            if (isSuccess) {
                                currentAssembly = newAssembly
                            }
                            showAssemblyNamePopup = false
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun BuildingContents(
    breakpoint: Breakpoint,
    items: List<Item>,
    onClick: (ItemId) -> Unit = {}
) {
    Row(modifier = Modifier.fillMaxWidth()) {
        if (breakpoint >= Breakpoint.MD) {
            Box(
                modifier = Modifier
                    .minWidth(200.px)
                    .padding(leftRight = 4.px, topBottom = 16.px)
            ) {
                PartsMenu(fontSize = breakpoint.largeSize())
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(if (breakpoint >= Breakpoint.MD) 16.px else 8.px)
        ) {
            PartsListLayout(breakpoint = breakpoint, items = items, onClick = onClick)
        }
    }
}

@Composable
fun PartsMenu(
    fontSize: CSSSizeValue<CSSUnit.px>,
) {
    val context = rememberPageContext()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.px)
            .backgroundColor(Theme.LIGHT_GRAY.rgb)
            .borderRadius(8.px),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        ItemCategory.entries.forEach { parts ->
            val isSelected = context.route.path.contains(parts.route)

            SpanText(
                modifier = Modifier
                    .margin(topBottom = 8.px)
                    .color(if (isSelected) Theme.BLUE.rgb else Theme.DARK_GRAY.rgb)
                    .fontSize(fontSize)
                    .fontFamily(Const.FONT_FAMILY)
                    .fontWeight(FontWeight.Bold)
                    .cursor(Cursor.Pointer)
                    .maxLines(1)
                    .onClick { context.router.navigateTo(parts.route) },
                text = parts.displayName
            )
        }
    }
}

@Composable
fun PartsListLayout(
    breakpoint: Breakpoint,
    items: List<Item>? = null,
    onClick: (ItemId) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(if (breakpoint >= Breakpoint.MD) 16.px else 8.px)
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
                    .padding(if (breakpoint >= Breakpoint.MD) 16.px else 8.px)
                    .borderRadius(8.px),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                items?.forEach { item ->
                    PartsCard(
                        breakpoint = breakpoint,
                        item = item,
                        buttonType = PartsButtonType.REGISTRATION,
                        onClick = onClick
                    )
                }
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