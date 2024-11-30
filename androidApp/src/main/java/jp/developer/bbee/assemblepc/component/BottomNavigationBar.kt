package jp.developer.bbee.assemblepc.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.automirrored.outlined.List
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Build
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.navOptions
import jp.developer.bbee.assemblepc.navigation.AppDestination
import jp.developer.bbee.assemblepc.ui.AppState

@Composable
fun BottomNavigationBar(
    modifier: Modifier = Modifier,
    appState: AppState
) {
    val routes = AppDestination.Routes
    val items = listOf("マイページ", "構成", "フィード")
    val unSelectedIcons = listOf(
        Icons.Outlined.Person,
        Icons.Outlined.Build,
        Icons.AutoMirrored.Outlined.List,
    )
    val selectedIcons = listOf(
        Icons.Filled.Person,
        Icons.Filled.Build,
        Icons.AutoMirrored.Filled.List,
    )

    var selectedItem by rememberSaveable { mutableIntStateOf(0) }

    NavigationBar(
        modifier = modifier,
    ) {
        items.forEachIndexed { index, item ->
            val icons = if (selectedItem == index) selectedIcons else unSelectedIcons
            NavigationBarItem(
                selected = selectedItem == index,
                onClick = {
                    selectedItem = index
                    appState.navigateTo(destination = routes[index])
                },
                label = { Text(text = item) },
                icon = { Icon(imageVector = icons[index], contentDescription = item) }
            )
        }
    }
}