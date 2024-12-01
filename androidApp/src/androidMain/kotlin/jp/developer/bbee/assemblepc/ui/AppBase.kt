package jp.developer.bbee.assemblepc.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import jp.developer.bbee.assemblepc.component.BottomNavigationBar
import jp.developer.bbee.assemblepc.navigation.AppNavHost

@Composable
fun AppBase(modifier: Modifier = Modifier) {
    val appState = rememberAppState()

    Scaffold(
        bottomBar = { BottomNavigationBar(appState = appState) }
    ) { paddingValues ->
        AppNavHost(
            modifier = modifier.padding(paddingValues),
            appState = appState,
        )
    }
}
