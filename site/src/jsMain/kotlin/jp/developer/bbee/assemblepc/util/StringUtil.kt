package jp.developer.bbee.assemblepc.util

import jp.developer.bbee.assemblepc.models.Content
import jp.developer.bbee.assemblepc.navigation.Screen
import com.varabyte.kobweb.core.PageContext

fun Content.matchesRoute(context: PageContext): Boolean =
    if (route == Screen.TopPage.route) {
        route == context.route.path
    } else {
        context.route.path.startsWith(route)
    }