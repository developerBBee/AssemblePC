package bbee.developer.jp.assemble_pc.models

import org.jetbrains.compose.web.css.CSSColorValue
import org.jetbrains.compose.web.css.rgb
import org.jetbrains.compose.web.css.rgba

enum class Theme(
    val hex: String,
    val rgb: CSSColorValue,
) {
    LIGHT_GRAY(
        hex = "#C4C4C4",
        rgb = rgb(196, 196, 196)
    ),
    DARK_GRAY(
        hex = "#545454",
        rgb = rgb(84, 84, 84)
    ),
    RED(
        hex = "#FF2020",
        rgb = rgb(255, 32, 32)
    ),
    GREEN(
        hex = "#00C000",
        rgb = rgb(0, 192, 0)
    ),
    BLUE(
        hex = "#0075FF",
        rgb = rgb(0, 117, 255)
    ),
    YELLOW(
        hex = "#FFFF00",
        rgb = rgb(255, 255, 0)
    ),
    PURPLE(
        hex = "#ECB5FF",
        rgb = rgb(236, 181, 255)
    ),
    TRANSLUCENT(
        hex = "#000000",
        rgb = rgba(0, 0, 0, 0.5)
    ),
}