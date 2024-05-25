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
import bbee.developer.jp.assemble_pc.models.Assembly
import bbee.developer.jp.assemble_pc.models.AssemblyDetail
import bbee.developer.jp.assemble_pc.models.AssemblyForPost
import bbee.developer.jp.assemble_pc.models.Item
import bbee.developer.jp.assemble_pc.models.ItemCategory
import bbee.developer.jp.assemble_pc.util.IsUserLoggedIn
import bbee.developer.jp.assemble_pc.util.addAssemblyDetail
import bbee.developer.jp.assemble_pc.util.collectOnUidChanged
import bbee.developer.jp.assemble_pc.util.getCurrentAssembly
import bbee.developer.jp.assemble_pc.util.getItems
import bbee.developer.jp.assemble_pc.util.totalAmount
import bbee.developer.jp.assemble_pc.util.updateAssembly
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.core.rememberPageContext
import com.varabyte.kobweb.silk.components.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.theme.breakpoint.rememberBreakpoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Page(routeOverride = "/building/parts/{category}")
@Composable
fun PartsPage() {
    val scope = rememberCoroutineScope()
    val breakpoint = rememberBreakpoint()

    var currentAssembly: Assembly? by remember { mutableStateOf(null) }

    var showAssemblyNamePopup by remember { mutableStateOf(false) }

    IsUserLoggedIn {
        CommonLayout(breakpoint = breakpoint) {
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
                                            println("[SUCCESS]addAssemblyDetail $newAssembly")
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
