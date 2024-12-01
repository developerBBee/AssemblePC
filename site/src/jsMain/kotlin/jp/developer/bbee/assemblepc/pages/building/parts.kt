package jp.developer.bbee.assemblepc.pages.building

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import jp.developer.bbee.assemblepc.components.layouts.BuildingNavLayout
import jp.developer.bbee.assemblepc.components.layouts.CommonBuildingLayout
import jp.developer.bbee.assemblepc.components.widgets.EditPopup
import jp.developer.bbee.assemblepc.models.Assembly
import jp.developer.bbee.assemblepc.models.AssemblyDetail
import jp.developer.bbee.assemblepc.models.AssemblyForPost
import jp.developer.bbee.assemblepc.models.Item
import jp.developer.bbee.assemblepc.models.ItemCategory
import jp.developer.bbee.assemblepc.util.IsUserLoggedIn
import jp.developer.bbee.assemblepc.util.addAssemblyDetail
import jp.developer.bbee.assemblepc.util.collectOnUidChanged
import jp.developer.bbee.assemblepc.util.createAssembly
import jp.developer.bbee.assemblepc.util.getCurrentAssembly
import jp.developer.bbee.assemblepc.util.getItems
import jp.developer.bbee.assemblepc.util.runIf
import jp.developer.bbee.assemblepc.util.totalAmount
import jp.developer.bbee.assemblepc.util.updateAssembly
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.core.rememberPageContext
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.theme.breakpoint.rememberBreakpoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Page(routeOverride = "/building/parts/{category}")
@Composable
fun PartsPage() {
    val scope = rememberCoroutineScope()
    val breakpoint = rememberBreakpoint()

    var currentAssembly: Assembly? by remember { mutableStateOf(null) }

    var showNewCreatingPopup by remember { mutableStateOf(false) }
    var showAssemblyNamePopup by remember { mutableStateOf(false) }

    IsUserLoggedIn { user ->
        CommonBuildingLayout(
            breakpoint = breakpoint,
            isAnonymous = user.isAnonymous,
            showNewCreatingPopup = showNewCreatingPopup,
            showAssemblyNamePopup = showAssemblyNamePopup,
            currentAssembly = currentAssembly,
            onDismiss = {
                showNewCreatingPopup = false
                showAssemblyNamePopup = false
            },
            onNewCreatingPositiveClick = { name ->
                scope.launch {
                    createAssembly(assemblyName = name, referenceAssemblyId = null)
                        ?.let { newAssembly ->
                            currentAssembly = newAssembly
                        }
                    showNewCreatingPopup = false
                }
            },
            onNameEditPositiveClick = { newName ->
                currentAssembly?.also { assembly ->
                    scope.launch {
                        assembly.copy(assemblyName = newName).also { newAssembly ->
                            if (updateAssembly(newAssembly)) {
                                currentAssembly = newAssembly
                            }
                            showAssemblyNamePopup = false
                        }
                    }
                }
            },
        ) {
            LaunchedEffect(Unit) {
                getCurrentAssembly()?.also {
                    currentAssembly = it
                }
            }

            currentAssembly?.also { assembly ->
                println("ref $assembly")
                BuildingNavLayout(
                    breakpoint = breakpoint,
                    assemblyName = assembly.assemblyName,
                    totalAmount = assembly.totalAmount(),
                    newAssemblyVisible = assembly.assemblyDetails.isNotEmpty(),
                    onNewAssemblyClick = { showNewCreatingPopup = true },
                    onAssemblyNameClick =  { showAssemblyNamePopup = true }
                ) {
                    PartsLayout(
                        scope = scope,
                        breakpoint = breakpoint,
                        details = assembly.assemblyDetails,
                        onClickRegisterButton = { item ->
                            scope.launch {
                                AssemblyDetail(
                                    item = item,
                                    priceAtRegistered = item.price
                                ).also { postDetail ->
                                    AssemblyForPost(
                                        assemblyId = assembly.assemblyId,
                                        ownerUserId = assembly.ownerUserId,
                                        assemblyDetail = postDetail
                                    )
                                        .let { postAssembly ->
                                            addAssemblyDetail(postAssembly)
                                        }
                                        ?.also { newAssembly ->
                                            currentAssembly = newAssembly
                                        }
                                }
                            }
                        }
                    )
                }
            }
        }

        if (showAssemblyNamePopup) {
            currentAssembly?.let { assembly ->
                EditPopup(
                    breakpoint = breakpoint,
                    title = "構成名の更新",
                    initText = assembly.assemblyName,
                    buttonText = "OK",
                    onDismiss = { showAssemblyNamePopup = false },
                    onPositiveClick = { newName ->
                        scope.launch {
                            val newAssembly = assembly.copy(assemblyName = newName)
                            runIf(updateAssembly(newAssembly)) {
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
fun PartsLayout(
    scope: CoroutineScope,
    breakpoint: Breakpoint,
    details: List<AssemblyDetail>,
    onClickRegisterButton: (Item) -> Unit,
) {
    val context = rememberPageContext()

    val itemCategory = ItemCategory.fromSlug(context.route.slug)
    val items = remember { mutableStateListOf<Item>() }

    LaunchedEffect(key1 = itemCategory) {
        itemCategory?.also { category ->
            scope.launch {
                collectOnUidChanged {
                    println("call getItems() by category=${category.name}")
                    getItems(0, category)
                        .let { newItems ->
                            items.clear()
                            items.addAll(newItems)
                        }
                }
            }
        } ?: items.clear()
    }

    BuildingContents(
        breakpoint = breakpoint,
        items = items,
        details = details,
        onClick = { itemId ->
            items.find { it.itemId == itemId }?.also { item ->
                onClickRegisterButton(item)
            }
        }
    )
}
