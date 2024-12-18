package jp.developer.bbee.assemblepc.pages.admin

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import jp.developer.bbee.assemblepc.firebase.auth
import jp.developer.bbee.assemblepc.util.signInAnonymous
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.compose.css.TextAlign
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxSize
import com.varabyte.kobweb.compose.ui.modifiers.textAlign
import com.varabyte.kobweb.silk.components.text.SpanText
import dev.gitlive.firebase.auth.FirebaseUser
import kotlinx.coroutines.launch

@Page
@Composable
fun AdminPage() {
    val scope = rememberCoroutineScope()

    val user: FirebaseUser? by auth.authStateChanged
        .collectAsState(initial = null, scope.coroutineContext)

    LaunchedEffect(Unit) {
        scope.launch {
            signInAnonymous()
        }
    }

    user?.let {
        Box(modifier = Modifier.fillMaxSize()) {
            SpanText(modifier = Modifier.align(Alignment.Center), text = "Welcome, ${it.uid}!")
        }
    } ?: run {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                SpanText(
                    modifier = Modifier.textAlign(TextAlign.Center),
                    text = "Welcome, first visitor!"
                )
                SpanText(
                    modifier = Modifier.textAlign(TextAlign.Center),
                    text = "Please wait for a minute."
                )
            }
        }
    }
}