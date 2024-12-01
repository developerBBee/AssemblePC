package jp.developer.bbee.assemblepc.components.sections

import androidx.compose.runtime.Composable
import jp.developer.bbee.assemblepc.models.Content
import jp.developer.bbee.assemblepc.models.Theme
import jp.developer.bbee.assemblepc.navigation.Screen
import jp.developer.bbee.assemblepc.util.Const
import jp.developer.bbee.assemblepc.util.Res
import jp.developer.bbee.assemblepc.util.StringRes
import jp.developer.bbee.assemblepc.util.dropTextShadow
import jp.developer.bbee.assemblepc.util.matchesRoute
import com.varabyte.kobweb.compose.css.Cursor
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.css.TextAlign
import com.varabyte.kobweb.compose.css.TextDecorationLine
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Box
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
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.fontFamily
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.fontWeight
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.modifiers.maxWidth
import com.varabyte.kobweb.compose.ui.modifiers.minSize
import com.varabyte.kobweb.compose.ui.modifiers.onClick
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.size
import com.varabyte.kobweb.compose.ui.modifiers.textAlign
import com.varabyte.kobweb.compose.ui.modifiers.textDecorationLine
import com.varabyte.kobweb.compose.ui.modifiers.textShadow
import com.varabyte.kobweb.compose.ui.thenIf
import com.varabyte.kobweb.core.PageContext
import com.varabyte.kobweb.core.rememberPageContext
import com.varabyte.kobweb.silk.components.graphics.Image
import com.varabyte.kobweb.silk.components.navigation.Link
import com.varabyte.kobweb.silk.components.navigation.UndecoratedLinkVariant
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint
import org.jetbrains.compose.web.css.px

@Composable
fun Header(
    breakpoint: Breakpoint,
    isAnonymous: Boolean,
) {
    val context = rememberPageContext()

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.px)
            .backgroundColor(Theme.BLUE.rgb),
        verticalAlignment = Alignment.CenterVertically
    ){
        Image(
            modifier = Modifier
                .margin(leftRight = 8.px)
                .size(40.px)
                .minSize(40.px)
                .borderRadius(r = 10.px)
                .boxShadow(blurRadius = 4.px, color = Theme.TRANSLUCENT.rgb)
                .cursor(Cursor.Pointer)
                .onClick { context.router.navigateTo(Screen.TopPage.route) },
            src = Res.Image.LOGO,
        )

        if (breakpoint >= Breakpoint.MD) {
            Link(
                modifier = Modifier
                    .margin(left = 8.px, right = 16.px)
                    .color(Colors.White)
                    .fontFamily(Const.FONT_FAMILY)
                    .fontSize(16.px)
                    .fontWeight(FontWeight.Bold)
                    .textAlign(TextAlign.Center)
                    .textShadow(dropTextShadow)
                    .onClick { context.router.navigateTo(Screen.TopPage.route) },
                path = "",
                text = StringRes.appTitle,
                variant = UndecoratedLinkVariant
            )
        }

        HeaderMenu(context = context)

        Spacer()

        Link(
            modifier = Modifier
                .margin(leftRight = 8.px)
                .color(Colors.White)
                .fontFamily(Const.FONT_FAMILY)
                .fontSize(12.px)
                .fontWeight(FontWeight.Bold)
                .textAlign(TextAlign.Center)
                .onClick { context.router.navigateTo(Screen.AccountPage.route) },
            path = "",
            text = if (isAnonymous) StringRes.login else StringRes.logout,
        )
    }
}

@Composable
fun HeaderMenu(
    context: PageContext,
) {
    Row(
        modifier = Modifier.maxWidth(300.px),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        Content.entries.forEach { content ->
            val isSelected = content.matchesRoute(context)

            Box(
                modifier = Modifier
                    .margin(leftRight = 8.px)
                    .weight(1),
                contentAlignment = Alignment.Center
            ) {
                Link(
                    modifier = Modifier
                        .color(if (isSelected) Theme.YELLOW.rgb else Colors.White)
                        .thenIf(
                            condition = isSelected,
                            other = Modifier.textDecorationLine(TextDecorationLine.Underline)
                        )
                        .fontFamily(Const.FONT_FAMILY)
                        .fontSize(12.px)
                        .fontWeight(FontWeight.Bold)
                        .textAlign(TextAlign.Center)
                        .onClick { context.router.navigateTo(content.route) },
                    path = "",
                    text = content.text,
                    variant = if (isSelected) null else UndecoratedLinkVariant
                )
            }
        }
    }
}
