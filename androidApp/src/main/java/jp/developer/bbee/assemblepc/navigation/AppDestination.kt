package jp.developer.bbee.assemblepc.navigation

import kotlinx.serialization.Serializable

@Serializable data object TopRoute

sealed interface AppDestination {
    @Serializable
    data object MyPageRoute : AppDestination

    @Serializable
    data object AssemblyRoute : AppDestination

    @Serializable
    data object FeedRoute : AppDestination

    companion object {
        val Routes: List<AppDestination> = listOf(
            MyPageRoute,
            AssemblyRoute,
            FeedRoute,
        )
    }
}
