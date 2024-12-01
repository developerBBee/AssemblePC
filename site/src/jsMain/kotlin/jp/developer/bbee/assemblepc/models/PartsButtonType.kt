package jp.developer.bbee.assemblepc.models

import org.jetbrains.compose.web.css.CSSColorValue

enum class PartsButtonType(val text: String, val bgColor: CSSColorValue) {
    REGISTRATION(text = "登録", bgColor = Theme.BLUE.rgb),
    ADDITION(text = "追加", bgColor = Theme.GREEN.rgb),
    DELETION(text = "削除", bgColor = Theme.RED.rgb),
}