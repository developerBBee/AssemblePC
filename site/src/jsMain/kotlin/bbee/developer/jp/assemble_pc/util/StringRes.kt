package bbee.developer.jp.assemble_pc.util

import bbee.developer.jp.assemble_pc.util.LanguageContext.currentLanguage

enum class Lang {
    JA,
    EN,
}

object LanguageContext {
    var currentLanguage: Lang = Lang.JA
}

object StringRes {
    val appTitle get() = StringMap.APP_TITLE[currentLanguage] ?: Ja.APP_TITLE
    val login get() = StringMap.LOGIN[currentLanguage] ?: Ja.LOGIN
    val top get() = StringMap.TOP[currentLanguage] ?: Ja.TOP
    val myPage get() = StringMap.MY_PAGE[currentLanguage] ?: Ja.MY_PAGE
    val building get() = StringMap.BUILDING[currentLanguage] ?: Ja.BUILDING

    private object StringMap {
        val APP_TITLE = mapOf(Lang.JA to Ja.APP_TITLE, Lang.EN to En.APP_TITLE)
        val LOGIN = mapOf(Lang.JA to Ja.LOGIN, Lang.EN to En.LOGIN)
        val TOP = mapOf(Lang.JA to Ja.TOP, Lang.EN to En.TOP)
        val MY_PAGE = mapOf(Lang.JA to Ja.MY_PAGE, Lang.EN to En.MY_PAGE)
        val BUILDING = mapOf(Lang.JA to Ja.BUILDING, Lang.EN to En.BUILDING)
    }

    private object Ja {
        const val APP_TITLE = "自作PC構築支援"
        const val LOGIN = "ログイン"
        const val TOP = "トップ"
        const val MY_PAGE = "マイページ"
        const val BUILDING = "PC構築"
    }

    private object En {
        const val APP_TITLE = "PC Building Support"
        const val LOGIN = "Login"
        const val TOP = "Top"
        const val MY_PAGE = "My Page"
        const val BUILDING = "Build PC"
    }
}
