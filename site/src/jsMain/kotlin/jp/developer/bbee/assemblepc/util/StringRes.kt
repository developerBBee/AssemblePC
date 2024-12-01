package jp.developer.bbee.assemblepc.util

import jp.developer.bbee.assemblepc.util.LanguageContext.currentLanguage

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
    val logout get() = StringMap.LOGOUT[currentLanguage] ?: Ja.LOGOUT
    val top get() = StringMap.TOP[currentLanguage] ?: Ja.TOP
    val myPage get() = StringMap.MY_PAGE[currentLanguage] ?: Ja.MY_PAGE
    val building get() = StringMap.BUILDING[currentLanguage] ?: Ja.BUILDING
    val creating get() = StringMap.CREATING[currentLanguage] ?: Ja.CREATING
    val published get() = StringMap.PUBLISHED[currentLanguage] ?: Ja.PUBLISHED
    val favorite get() = StringMap.FAVORITE[currentLanguage] ?: Ja.FAVORITE
    val assembly get() = StringMap.ASSEMBLY[currentLanguage] ?: Ja.ASSEMBLY
    val selection get() = StringMap.SELECTION[currentLanguage] ?: Ja.SELECTION

    private object StringMap {
        val APP_TITLE = mapOf(Lang.JA to Ja.APP_TITLE, Lang.EN to En.APP_TITLE)
        val LOGIN = mapOf(Lang.JA to Ja.LOGIN, Lang.EN to En.LOGIN)
        val LOGOUT = mapOf(Lang.JA to Ja.LOGOUT, Lang.EN to En.LOGOUT)
        val TOP = mapOf(Lang.JA to Ja.TOP, Lang.EN to En.TOP)
        val MY_PAGE = mapOf(Lang.JA to Ja.MY_PAGE, Lang.EN to En.MY_PAGE)
        val BUILDING = mapOf(Lang.JA to Ja.BUILDING, Lang.EN to En.BUILDING)
        val CREATING = mapOf(Lang.JA to Ja.CREATING, Lang.EN to En.CREATING)
        val PUBLISHED = mapOf(Lang.JA to Ja.PUBLISHED, Lang.EN to En.PUBLISHED)
        val FAVORITE = mapOf(Lang.JA to Ja.FAVORITE, Lang.EN to En.FAVORITE)
        val ASSEMBLY = mapOf(Lang.JA to Ja.ASSEMBLY, Lang.EN to En.ASSEMBLY)
        val SELECTION = mapOf(Lang.JA to Ja.SELECTION, Lang.EN to En.SELECTION)
    }

    private object Ja {
        const val APP_TITLE = "自作PC構築支援"
        const val LOGIN = "ログイン"
        const val LOGOUT = "ログアウト"
        const val TOP = "トップ"
        const val MY_PAGE = "マイページ"
        const val BUILDING = "PC構築"
        const val CREATING = "作成中"
        const val PUBLISHED = "公開済み"
        const val FAVORITE = "お気に入り"
        const val ASSEMBLY = "構成"
        const val SELECTION = "パーツ選択"
    }

    private object En {
        const val APP_TITLE = "PC Building Support"
        const val LOGIN = "Login"
        const val LOGOUT = "Logout"
        const val TOP = "Top"
        const val MY_PAGE = "My Page"
        const val BUILDING = "Build PC"
        const val CREATING = "Creating"
        const val PUBLISHED = "Published"
        const val FAVORITE = "Favorite"
        const val ASSEMBLY = "Assembly"
        const val SELECTION = "Selection"
    }
}
