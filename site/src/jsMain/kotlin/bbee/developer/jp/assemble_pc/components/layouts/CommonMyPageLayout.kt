package bbee.developer.jp.assemble_pc.components.layouts

import androidx.compose.runtime.Composable
import bbee.developer.jp.assemble_pc.components.widgets.EditPopup
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint

@Composable
fun CommonMyPageLayout(
    breakpoint: Breakpoint,
    isAnonymous: Boolean,
    showNameEditPopup: Boolean,
    userName: String,
    onDismiss: () -> Unit,
    onUserNamePositiveClick: (String) -> Unit,
    content: @Composable () -> Unit
) {
    CommonLayout(breakpoint = breakpoint, isAnonymous = isAnonymous) {
        content()
    }

    if (showNameEditPopup) {
        EditPopup(
            breakpoint = breakpoint,
            title = "ユーザー名変更",
            initText = userName,
            hintText = "新しいユーザー名を記入",
            buttonText = "OK",
            onDismiss = onDismiss,
            onPositiveClick = onUserNamePositiveClick
        )
    }
}