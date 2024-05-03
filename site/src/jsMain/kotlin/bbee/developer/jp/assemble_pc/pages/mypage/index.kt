package bbee.developer.jp.assemble_pc.pages.mypage

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import bbee.developer.jp.assemble_pc.components.sections.Advertisement
import bbee.developer.jp.assemble_pc.components.sections.Header
import bbee.developer.jp.assemble_pc.components.widgets.AssemblyCard
import bbee.developer.jp.assemble_pc.firebase.auth
import bbee.developer.jp.assemble_pc.models.TabMenu
import bbee.developer.jp.assemble_pc.models.Theme
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
import com.varabyte.kobweb.compose.ui.modifiers.border
import com.varabyte.kobweb.compose.ui.modifiers.borderBottom
import com.varabyte.kobweb.compose.ui.modifiers.borderRadius
import com.varabyte.kobweb.compose.ui.modifiers.borderTop
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.cursor
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxHeight
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxSize
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.fontFamily
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.fontWeight
import com.varabyte.kobweb.compose.ui.modifiers.height
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.modifiers.minWidth
import com.varabyte.kobweb.compose.ui.modifiers.onClick
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.width
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.silk.components.forms.Input
import com.varabyte.kobweb.silk.components.icons.fa.FaPenToSquare
import com.varabyte.kobweb.silk.components.layout.HorizontalDivider
import com.varabyte.kobweb.silk.components.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.theme.breakpoint.rememberBreakpoint
import dev.gitlive.firebase.auth.FirebaseUser
import kotlinx.coroutines.launch
import org.jetbrains.compose.web.attributes.InputType
import org.jetbrains.compose.web.css.CSSSizeValue
import org.jetbrains.compose.web.css.CSSUnit
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px

@Page
@Composable
fun MyPage() {
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

        MyPageContents(breakpoint)
    }
}

@Composable
fun MyPageContents(
    breakpoint: Breakpoint
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
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.px)
                    .padding(24.px),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Profile(fontSize = breakpoint.largeSize())
                SearchBar(breakpoint = breakpoint)
            }

            TabMenuLayout(breakpoint = breakpoint)
        }

        if (breakpoint >= Breakpoint.LG) Advertisement(modifier = Modifier.margin(left = 16.px))
    }
}

@Composable
fun Profile(fontSize: CSSSizeValue<CSSUnit.px>) {
    Row(
        modifier = Modifier.fillMaxHeight(),
        verticalAlignment = Alignment.CenterVertically,
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
            text = "ユーザー名ユーザー名ユーザー名ユーザー名ユーザー名",
        )
    }
}

@Composable
fun SearchBar(
    breakpoint: Breakpoint
) {
    var searchText by remember { mutableStateOf("") }

    Input(
        modifier = Modifier
            .width(if (breakpoint > Breakpoint.SM) 300.px else 200.px)
            .fontSize(breakpoint.largeSize())
            .fontFamily(Const.FONT_FAMILY)
            .maxLines(1),
        type = InputType.Search,
        placeholder = "検索",
        value = searchText,
        onValueChanged = { searchText = it }
    )
}

@Composable
fun TabMenuLayout(
    breakpoint: Breakpoint,
    selectedTabMenu: TabMenu = TabMenu.PUBLISHED
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
                text = "作成中",
                fontSize = breakpoint.largeSize(),
                selected = selectedTabMenu == TabMenu.CREATING
            )
            TabMenuButton(
                text = "公開済み",
                fontSize = breakpoint.largeSize(),
                selected = selectedTabMenu == TabMenu.PUBLISHED
            )
            TabMenuButton(
                text = "お気に入り",
                fontSize = breakpoint.largeSize(),
                selected = selectedTabMenu == TabMenu.FAVORITE
            )
        }

        HorizontalDivider(
            modifier = Modifier
                .fillMaxWidth()
                .margin(top = 0.px, bottom = 8.px)
                .borderTop(width = 1.px, style = LineStyle.Solid, color = Theme.LIGHT_GRAY.rgb)
        )

        Column(modifier = Modifier.fillMaxWidth()) {
            when (selectedTabMenu) {
                TabMenu.CREATING -> {
                    SpanText(text = "作成中のアセンブリ")
                }
                TabMenu.PUBLISHED -> {
                    AssemblyCard(breakpoint = breakpoint)
                    AssemblyCard(breakpoint = breakpoint)
                    AssemblyCard(breakpoint = breakpoint)
                    AssemblyCard(breakpoint = breakpoint)
                }
                TabMenu.FAVORITE -> {
                    SpanText(text = "お気に入りのアセンブリ")
                }
            }
        }
    }
}

@Composable
fun TabMenuButton(
    text: String,
    fontSize: CSSSizeValue<CSSUnit.px>,
    selected: Boolean,
    onClick: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .width(30.percent)
            .minWidth(100.px)
            .padding(8.px)
            .borderRadius(topLeft = 8.px, topRight = 8.px)
            .border(width = 1.px, style = LineStyle.Solid, color = Theme.LIGHT_GRAY.rgb)
            .borderBottom(0.px)
            .backgroundColor(if (selected) Colors.White else Theme.LIGHT_GRAY.rgb)
            .cursor(Cursor.Pointer)
            .onClick { onClick() },
        contentAlignment = Alignment.Center
    ) {
        SpanText(
            modifier = Modifier
                .color(if (selected) Colors.Black else Theme.DARK_GRAY.rgb)
                .fontSize(fontSize)
                .fontFamily(Const.FONT_FAMILY)
                .fontWeight(if (selected) FontWeight.Bold else FontWeight.Normal)
                .maxLines(1),
            text = text
        )
    }
}