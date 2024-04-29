package bbee.developer.jp.assemble_pc.util

import bbee.developer.jp.assemble_pc.models.Theme
import com.varabyte.kobweb.compose.css.CSSTextShadow
import org.jetbrains.compose.web.css.px

object Const {
    const val FONT_FAMILY = "Roboto"
}

object Res {
    object Image {
        const val LOGO = "/assemble-pc-logo.png"
        const val ITEM = "/item001.jpeg"
    }
}

val dropTextShadow = CSSTextShadow(2.px, 2.px, 2.px, Theme.TRANSLUCENT.rgb)