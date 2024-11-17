package bbee.developer.jp.assemble_pc.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import bbee.developer.jp.assemble_pc.firebase.auth
import bbee.developer.jp.assemble_pc.models.Theme
import com.varabyte.kobweb.compose.css.Cursor
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.css.Overflow
import com.varabyte.kobweb.compose.css.TextOverflow
import com.varabyte.kobweb.compose.css.Visibility
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.border
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.cursor
import com.varabyte.kobweb.compose.ui.modifiers.fontWeight
import com.varabyte.kobweb.compose.ui.modifiers.onClick
import com.varabyte.kobweb.compose.ui.modifiers.outline
import com.varabyte.kobweb.compose.ui.modifiers.overflow
import com.varabyte.kobweb.compose.ui.modifiers.textOverflow
import com.varabyte.kobweb.compose.ui.modifiers.visibility
import com.varabyte.kobweb.compose.ui.styleModifier
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint
import dev.gitlive.firebase.auth.externals.User
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import org.jetbrains.compose.web.css.CSSColorValue
import org.jetbrains.compose.web.css.CSSSizeValue
import org.jetbrains.compose.web.css.CSSUnit
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.px

@Composable
fun IsUserLoggedIn(content: @Composable (User) -> Unit) {
    val scope = rememberCoroutineScope()
    val user by auth.authStateChanged
        .map { it?.js }
        .collectAsState(initial = null, context = scope.coroutineContext)
    var uid by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        scope.launch {
            signInAnonymous()
        }
    }

    user?.also { loginUser ->
        uid?.also { bufferedUid ->
            if (!(loginUser.isAnonymous) && bufferedUid != loginUser.uid) {
                scope.launch {
                    updateUserId(uid = bufferedUid)
                }
            }
        }

        uid = loginUser.uid
        content(loginUser)
    }
}

fun Modifier.visibleIf(visible: Boolean): Modifier = this
    .visibility(if (visible) Visibility.Visible else Visibility.Hidden)

fun Modifier.noBorder(): Modifier = this
    .border(
        width = 0.px,
        style = LineStyle.None,
        color = Colors.Transparent
    )
    .outline(
        width = 0.px,
        style = LineStyle.None,
        color = Colors.Transparent
    )

fun Modifier.maxLines(maxLines: Int): Modifier = this
    .textOverflow(TextOverflow.Ellipsis)
    .overflow(Overflow.Hidden)
    .styleModifier {
        property("display", "-webkit-box")
        property("-webkit-line-clamp", "$maxLines")
        property("line-clamp", "$maxLines")
        property("-webkit-box-orient", "vertical")
    }

fun Modifier.enabledButton(
    enabled: Boolean,
    backgroundColor: CSSColorValue,
    onClick: () -> Unit
): Modifier = this
    .cursor(if (enabled) Cursor.Pointer else Cursor.Auto)
    .backgroundColor(if (enabled) backgroundColor else Theme.LIGHT_GRAY.rgb)
    .onClick { if (enabled) { onClick() } }

fun Modifier.enabledButtonText(
    enabled: Boolean,
    textColor: CSSColorValue,
): Modifier = this
    .color(if (enabled) textColor else Theme.LIGHT_GRAY.rgb)
    .fontWeight(if (enabled) FontWeight.Bold else FontWeight.Normal)

fun Breakpoint.smallSize(): CSSSizeValue<CSSUnit.px> = when (this) {
    Breakpoint.ZERO -> 9.px
    Breakpoint.SM -> 9.px
    Breakpoint.MD -> 9.px
    Breakpoint.LG -> 10.px
    Breakpoint.XL -> 10.px
}

fun Breakpoint.mediumSize(): CSSSizeValue<CSSUnit.px> = when (this) {
    Breakpoint.ZERO -> 10.px
    Breakpoint.SM -> 10.px
    Breakpoint.MD -> 11.px
    Breakpoint.LG -> 12.px
    Breakpoint.XL -> 12.px
}

fun Breakpoint.largeSize(): CSSSizeValue<CSSUnit.px> = when (this) {
    Breakpoint.ZERO -> 11.px
    Breakpoint.SM -> 11.px
    Breakpoint.MD -> 13.px
    Breakpoint.LG -> 14.px
    Breakpoint.XL -> 14.px
}

fun Breakpoint.hugeSize(): CSSSizeValue<CSSUnit.px> = when (this) {
    Breakpoint.ZERO -> 11.px
    Breakpoint.SM -> 12.px
    Breakpoint.MD -> 14.px
    Breakpoint.LG -> 16.px
    Breakpoint.XL -> 16.px
}
