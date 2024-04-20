package bbee.developer.jp.assemble_pc.pages.admin

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxSize
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.silk.components.text.SpanText
import kotlinx.coroutines.launch

@Page
@Composable
fun DashboardScreen() {
    val scope = rememberCoroutineScope()

    val dashboardData = remember { mutableStateListOf<String>() }

    LaunchedEffect(Unit) {
        scope.launch {

        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Column {
            SpanText("作成した構成")
            dashboardData.forEach {

            }
        }
    }
}