package jp.developer.bbee.assemblepc.models

import kotlinx.serialization.Serializable

@Serializable
data class Assembly(
    val assemblyId: AssemblyId,
    val ownerUserId: String,
    val assemblyName: String,
    val assemblyUrl: String,
    val ownerName: String?,
    val ownerComment: String,
    val referenceAssemblyId: AssemblyId?,
    val published: Boolean,
    val publishedDate: String,
    val assemblyDetails: List<AssemblyDetail>,
    val favoriteCount: Int,
)

@Serializable
data class AssemblyDetail(
    val detailId: DetailId? = null,
    val item: Item,
    val priceAtRegistered: Price,
)

@Serializable
data class AssemblyId(
    val id: Int,
) {
    override operator fun equals(other: Any?): Boolean {
        return when (other is AssemblyId) {
            true -> (id == other.id)
            false -> false
        }
    }

    override fun hashCode(): Int {
        return id
    }
}

@Serializable
data class DetailId(
    val id: Int,
) {
    override operator fun equals(other: Any?): Boolean {
        return when (other is DetailId) {
            true -> (id == other.id)
            false -> false
        }
    }

    override fun hashCode(): Int {
        return id
    }
}