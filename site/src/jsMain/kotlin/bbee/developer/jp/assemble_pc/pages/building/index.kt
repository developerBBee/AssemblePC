package bbee.developer.jp.assemble_pc.pages.building

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import bbee.developer.jp.assemble_pc.components.layouts.CommonLayout
import bbee.developer.jp.assemble_pc.components.widgets.PartsCard
import bbee.developer.jp.assemble_pc.firebase.auth
import bbee.developer.jp.assemble_pc.models.BuildingTabMenu
import bbee.developer.jp.assemble_pc.models.Parts
import bbee.developer.jp.assemble_pc.models.PartsButtonType
import bbee.developer.jp.assemble_pc.models.Theme
import bbee.developer.jp.assemble_pc.pages.mypage.SearchBar
import bbee.developer.jp.assemble_pc.util.Const
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
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.cursor
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.fontFamily
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.fontWeight
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.silk.components.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.theme.breakpoint.rememberBreakpoint
import dev.gitlive.firebase.auth.FirebaseUser
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

    val user: FirebaseUser? by auth.authStateChanged
        .collectAsState(initial = null, scope.coroutineContext)

    LaunchedEffect(Unit) {
        scope.launch {
            signInAnonymous()
        }
    }

    CommonLayout(
        breakpoint = breakpoint,
        selectedMenu = BuildingTabMenu.SELECTION
    ) {
        BuildingContents(breakpoint)
    }
}

@Composable
fun BuildingContents(
    breakpoint: Breakpoint,
) {
    Row(modifier = Modifier.fillMaxWidth()) {
        if (breakpoint >= Breakpoint.MD) {
            Box(modifier = Modifier.padding(16.px)) {
                PartsMenu(fontSize = breakpoint.largeSize())
            }
        }

        Box(
            modifier = Modifier
                .weight(1)
                .padding(if (breakpoint >= Breakpoint.MD) 16.px else 8.px)
        ) {
            PartsListLayout(breakpoint = breakpoint)
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
                PartsCard(breakpoint = breakpoint, buttonType = PartsButtonType.REGISTRATION) {}
                PartsCard(breakpoint = breakpoint, buttonType = PartsButtonType.ADDITION) {}
                PartsCard(breakpoint = breakpoint, buttonType = PartsButtonType.REGISTRATION) {}
                PartsCard(breakpoint = breakpoint, buttonType = PartsButtonType.REGISTRATION) {}
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