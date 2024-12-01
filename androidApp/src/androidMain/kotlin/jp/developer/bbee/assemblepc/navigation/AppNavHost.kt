package jp.developer.bbee.assemblepc.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import jp.developer.bbee.assemblepc.screen.assembly.AssemblyScreen
import jp.developer.bbee.assemblepc.screen.feed.FeedScreen
import jp.developer.bbee.assemblepc.screen.mypage.MyPageScreen
import jp.developer.bbee.assemblepc.ui.AppState

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    appState: AppState,
) {
    NavHost(
        navController = appState.navController,
        startDestination = TopRoute,
        modifier = modifier,
    ) {
        navigation<TopRoute>(startDestination = AppDestination.MyPageRoute) {
            composable<AppDestination.MyPageRoute> {
                MyPageScreen()
            }
            composable<AppDestination.FeedRoute> {
                FeedScreen()
            }
            composable<AppDestination.AssemblyRoute> {
                AssemblyScreen()
            }
        }
    }
}