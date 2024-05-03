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
import bbee.developer.jp.assemble_pc.components.widgets.TabMenuButton
import bbee.developer.jp.assemble_pc.firebase.auth
import bbee.developer.jp.assemble_pc.models.MyTabMenu
import bbee.developer.jp.assemble_pc.models.Theme
import bbee.developer.jp.assemble_pc.util.Const
import bbee.developer.jp.assemble_pc.util.largeSize
import bbee.developer.jp.assemble_pc.util.maxLines
import bbee.developer.jp.assemble_pc.util.signInAnonymous
import com.varabyte.kobweb.compose.css.Cursor
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.borderTop
import com.varabyte.kobweb.compose.ui.modifiers.cursor
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxHeight
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxSize
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.fontFamily
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.fontWeight
import com.varabyte.kobweb.compose.ui.modifiers.height
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.modifiers.outlineColor
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
import org.jetbrains.compose.web.css.CSSColorValue
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
    breakpoint: Breakpoint,
    color: CSSColorValue = Colors.White,
    outlineColor: CSSColorValue = Theme.LIGHT_GRAY.rgb,
) {
    var searchText by remember { mutableStateOf("") }

    Input(
        modifier = Modifier
            .width(if (breakpoint > Breakpoint.SM) 300.px else 200.px)
            .backgroundColor(color)
            .outlineColor(outlineColor)
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
    selectedMenu: MyTabMenu = MyTabMenu.PUBLISHED
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(
                if (breakpoint > Breakpoint.SM) 50.percent else 90.percent
            ),
            horizontalArrangement = Arrangement.SpaceAround,
        ) {
            TabMenuButton(
                text = "作成中",
                fontSize = breakpoint.largeSize(),
                selected = selectedMenu == MyTabMenu.CREATING
            )
            TabMenuButton(
                text = "公開済み",
                fontSize = breakpoint.largeSize(),
                selected = selectedMenu == MyTabMenu.PUBLISHED
            )
            TabMenuButton(
                text = "お気に入り",
                fontSize = breakpoint.largeSize(),
                selected = selectedMenu == MyTabMenu.FAVORITE
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
                MyTabMenu.CREATING -> {
                    SpanText(text = "作成中のアセンブリ")
                }
                MyTabMenu.PUBLISHED -> {
                    AssemblyCard(breakpoint = breakpoint)
                    AssemblyCard(breakpoint = breakpoint)
                    AssemblyCard(breakpoint = breakpoint)
                    AssemblyCard(breakpoint = breakpoint)
                }
                MyTabMenu.FAVORITE -> {
                    SpanText(text = "お気に入りのアセンブリ")
                }
            }
        }
    }
}