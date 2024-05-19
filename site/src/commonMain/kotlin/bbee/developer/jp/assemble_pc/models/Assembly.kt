package bbee.developer.jp.assemble_pc.models

import kotlinx.serialization.Serializable

@Serializable
data class Assembly(
    val assemblyId: AssemblyId,
    val ownerUserId: String,
    val assemblyName: String,
    val assemblyUrl: String,
    val ownerComment: String,
    val referenceAssemblyId: AssemblyId?,
    val published: Boolean,
    val assemblyDetails: List<AssemblyDetail>,
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