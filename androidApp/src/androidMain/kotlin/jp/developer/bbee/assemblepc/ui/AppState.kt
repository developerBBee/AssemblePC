package jp.developer.bbee.assemblepc.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import jp.developer.bbee.assemblepc.navigation.AppDestination
import kotlinx.coroutines.CoroutineScope

@Composable
fun rememberAppState(
    navController: NavHostController = rememberNavController(),
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
): AppState {
    return remember {
        AppState(
            navController = navController,
            coroutineScope = coroutineScope,
        )
    }
}


@Stable
class AppState(
    val navController: NavHostController,
    coroutineScope: CoroutineScope,
) {
    private val currentDestination: NavDestination?
        @Composable get() = navController
            .currentBackStackEntryAsState().value?.destination

    val currentAppDestination: AppDestination?
        @Composable get() {
            return AppDestination.Routes.firstOrNull { route ->
                currentDestination?.hasRoute(route = route::class) == true
            }
        }

    fun navigateTo(destination: AppDestination) {
        val navOptions = navOptions {
            // Pop up to the start destination of the graph to
            // avoid building up a large stack of destinations
            // on the back stack as users select items
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            // Avoid multiple copies of the same destination when
            // reselecting the same item
            launchSingleTop = true
            // Restore state when reselecting a previously selected item
            restoreState = true
        }

        navController.navigate(route = destination, navOptions = navOptions)
    }
}