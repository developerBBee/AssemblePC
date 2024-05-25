package bbee.developer.jp.assemble_pc.pages.building

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import bbee.developer.jp.assemble_pc.components.layouts.BuildingNavLayout
import bbee.developer.jp.assemble_pc.components.layouts.CommonLayout
import bbee.developer.jp.assemble_pc.components.widgets.EditPopup
import bbee.developer.jp.assemble_pc.components.widgets.FloatingButton
import bbee.developer.jp.assemble_pc.components.widgets.PartsCard
import bbee.developer.jp.assemble_pc.models.Assembly
import bbee.developer.jp.assemble_pc.models.AssemblyDetail
import bbee.developer.jp.assemble_pc.models.AssemblyForPost
import bbee.developer.jp.assemble_pc.models.DetailId
import bbee.developer.jp.assemble_pc.models.ItemCategory
import bbee.developer.jp.assemble_pc.models.PartsButtonType
import bbee.developer.jp.assemble_pc.models.Theme
import bbee.developer.jp.assemble_pc.navigation.Screen
import bbee.developer.jp.assemble_pc.util.Const
import bbee.developer.jp.assemble_pc.util.IsUserLoggedIn
import bbee.developer.jp.assemble_pc.util.deleteAssemblyDetail
import bbee.developer.jp.assemble_pc.util.getCurrentAssembly
import bbee.developer.jp.assemble_pc.util.hugeSize
import bbee.developer.jp.assemble_pc.util.totalAmount
import bbee.developer.jp.assemble_pc.util.updateAssembly
import com.varabyte.kobweb.compose.css.PointerEvents
import com.varabyte.kobweb.compose.css.Resize
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.fontFamily
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.height
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.modifiers.maxHeight
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.pointerEvents
import com.varabyte.kobweb.compose.ui.modifiers.position
import com.varabyte.kobweb.compose.ui.modifiers.resize
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.core.rememberPageContext
import com.varabyte.kobweb.silk.components.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.theme.breakpoint.rememberBreakpoint
import kotlinx.coroutines.launch
import org.jetbrains.compose.web.css.Position
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.css.vh
import org.jetbrains.compose.web.dom.TextArea

@Page
@Composable
fun AssemblyPage() {
    val scope = rememberCoroutineScope()
    val breakpoint = rememberBreakpoint()
    val context = rememberPageContext()

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
                    onAssemblyNameClick = { showAssemblyNamePopup = true }
                ) {
                    assembly.assemblyDetails.let { details ->
                        AssemblyContents(
                            breakpoint = breakpoint,
                            details = details,
                            comment = assembly.ownerComment,
                            onCommentUpdateClick = { newComment ->
                                scope.launch {
                                    val newAssembly = assembly.copy(ownerComment = newComment)
                                    val isSuccess = updateAssembly(newAssembly)
                                    if (isSuccess) {
                                        currentAssembly = newAssembly
                                    }
                                }
                            },
                            onDeleteButtonClick = { detailId ->
                                // Delete button clicked
                                assembly.assemblyDetails.find { it.detailId == detailId }
                                    ?.let { assemblyDetail ->
                                        scope.launch {
                                            val postAssembly = AssemblyForPost(
                                                assemblyId = assembly.assemblyId,
                                                ownerUserId = assembly.ownerUserId,
                                                assemblyDetail = assemblyDetail
                                            )
                                            val isSuccess = deleteAssemblyDetail(postAssembly)
                                            if (isSuccess) {
                                                currentAssembly = assembly.copy(
                                                    assemblyDetails = assembly.assemblyDetails
                                                        .filter { it.detailId != detailId }
                                                )
                                            }
                                        }
                                    }
                            }
                        )
                    }
                }
            }
        }

        currentAssembly?.also { assembly ->
            if (assembly.assemblyDetails.isNotEmpty()) {
                StickyActionBar(
                    breakpoint = breakpoint,
                    onPublishClick = {
                        scope.launch {
                            if (updateAssembly(assembly = assembly.copy(published = true))) {
                                context.router.navigateTo(Screen.PublishedPage.route)
                            }
                        }
                    }
                )
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
fun AssemblyContents(
    breakpoint: Breakpoint,
    details: List<AssemblyDetail>,
    comment: String,
    onCommentUpdateClick: (String) -> Unit,
    onDeleteButtonClick: (DetailId) -> Unit,
) {
    var commentText: String by remember { mutableStateOf(comment) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 48.px),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        TextArea(
            value = commentText,
            attrs = Modifier
                .fillMaxWidth(90.percent)
                .height(120.px)
                .maxHeight(120.px)
                .margin(topBottom = 8.px)
                .padding(16.px)
                .fontFamily(Const.FONT_FAMILY)
                .fontSize(breakpoint.hugeSize())
                .resize(Resize.None)
                .toAttrs {
                    attr("placeholder", "構成に関するコメント")
                    onInput { commentText = it.value }
                }
        )
        FloatingButton(
            modifier = Modifier
                .align(Alignment.CenterHorizontally),
            breakpoint = breakpoint,
            text = "更新",
            enabled = commentText != comment,
            onClick = { onCommentUpdateClick(commentText) }
        )

        details
            .sortedBy { it.item.itemCategoryId.id }
            .forEach { detail ->
            PartsCard(
                breakpoint = breakpoint,
                item = detail.item,
                itemCategory = ItemCategory.from(id = detail.item.itemCategoryId),
                buttonType = PartsButtonType.DELETION,
                onClick = { onDeleteButtonClick(requireNotNull(detail.detailId)) }
            )
        }
    }
}

@Composable
fun StickyActionBar(
    breakpoint: Breakpoint,
    onPublishClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.vh)
            .pointerEvents(PointerEvents.None),
        contentAlignment = Alignment.BottomCenter
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .pointerEvents(PointerEvents.Auto)
                .padding(2.px)
                .position(Position.Fixed)
                .backgroundColor(Theme.TRANSLUCENT.rgb),
            contentAlignment = Alignment.BottomCenter
        ) {
            FloatingButton(
                breakpoint = breakpoint,
                text = "公開",
                color = Colors.Black,
                backgroundColor = Theme.YELLOW.rgb,
                onClick = onPublishClick
            )
        }
    }
}