package bbee.developer.jp.assemble_pc.util

import bbee.developer.jp.assemble_pc.models.Content
import bbee.developer.jp.assemble_pc.navigation.Screen
import com.varabyte.kobweb.core.PageContext

fun Content.matchesRoute(context: PageContext): Boolean =
    if (route == Screen.TopPage.route) {
        route == context.route.path
    } else {
        context.route.path.startsWith(route)
    }