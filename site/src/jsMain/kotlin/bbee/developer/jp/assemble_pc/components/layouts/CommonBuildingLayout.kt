package bbee.developer.jp.assemble_pc.components.layouts

import androidx.compose.runtime.Composable
import bbee.developer.jp.assemble_pc.components.widgets.EditPopup
import bbee.developer.jp.assemble_pc.models.Assembly
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint

@Composable
fun CommonBuildingLayout(
    breakpoint: Breakpoint,
    isAnonymous: Boolean,
    showNewCreatingPopup: Boolean,
    showAssemblyNamePopup: Boolean,
    currentAssembly: Assembly?,
    onDismiss: () -> Unit,
    onNewCreatingPositiveClick: (String) -> Unit,
    onNameEditPositiveClick: (String) -> Unit,
    content: @Composable () -> Unit
) {
    CommonLayout(breakpoint = breakpoint, isAnonymous = isAnonymous) {
        content()
    }

    if (showNewCreatingPopup) {
        EditPopup(
            breakpoint = breakpoint,
            title = "新規構成の作成",
            message = "新規に作成するには、構成タイトルを記入して「新規作成」を押してください。\n現在作成中の構成はマイページから再開できます。",
            hintText = "構成タイトルを記入",
            buttonText = "新規作成",
            onDismiss = onDismiss,
            onPositiveClick = onNewCreatingPositiveClick
        )
    }

    if (showAssemblyNamePopup) {
        currentAssembly?.also { assembly ->
            EditPopup(
                breakpoint = breakpoint,
                title = "構成名の更新",
                initText = assembly.assemblyName,
                buttonText = "OK",
                onDismiss = onDismiss,
                onPositiveClick = onNameEditPositiveClick
            )
        }
    }
}