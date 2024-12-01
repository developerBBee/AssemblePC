package jp.developer.bbee.assemblepc.pages.account

import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import jp.developer.bbee.assemblepc.components.layouts.CommonLayout
import jp.developer.bbee.assemblepc.components.widgets.FloatingButton
import jp.developer.bbee.assemblepc.firebase.auth
import jp.developer.bbee.assemblepc.util.IsUserLoggedIn
import jp.developer.bbee.assemblepc.util.Res
import jp.developer.bbee.assemblepc.util.preRegisterUidUpdate
import jp.developer.bbee.assemblepc.util.runIf
import jp.developer.bbee.assemblepc.util.visibleIf
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.onClick
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.silk.components.graphics.Image
import com.varabyte.kobweb.silk.theme.breakpoint.rememberBreakpoint
import dev.gitlive.firebase.auth.externals.GoogleAuthProvider
import dev.gitlive.firebase.auth.externals.signInWithPopup
import dev.gitlive.firebase.auth.externals.signOut
import kotlinx.coroutines.launch
import org.jetbrains.compose.web.css.px

@Page
@Composable
fun AccountPage() {
    val scope = rememberCoroutineScope()
    val breakpoint = rememberBreakpoint()
    val authProvider = GoogleAuthProvider()

    IsUserLoggedIn { user ->
        CommonLayout(
            modifier = Modifier.fillMaxWidth().padding(24.px),
            contentAlignment = Alignment.TopCenter,
            breakpoint = breakpoint,
            isAnonymous = user.isAnonymous
        ) {
            Image(
                modifier = Modifier.visibleIf(user.isAnonymous)
                    .onClick {
                        scope.launch {
                            runIf(preRegisterUidUpdate()) {
                                signInWithPopup(
                                    auth = auth.js,
                                    provider = authProvider
                                )
                            }
                        }
                    },
                src = Res.Image.SIGN_IN,
            )

            FloatingButton(
                modifier = Modifier.visibleIf(!user.isAnonymous),
                breakpoint = breakpoint,
                text = "sign out",
            ) {
                scope.launch {
                    runIf(!user.isAnonymous) {
                        signOut(auth = auth.js.apply { languageCode = "ja" })
                    }
                }
            }
        }
    }
}