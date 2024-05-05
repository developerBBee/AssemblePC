package bbee.developer.jp.assemble_pc.models

import kotlinx.serialization.Serializable

@Serializable
data class ItemDetail(
    val itemId: ItemId,
    val itemCategoryId: ItemCategoryId,
    val makerId: MakerId,
    val itemName: String,
    val linkUrl: String,
    val imageUrl: String,
    val desc: String,
    val price: Price,
    val rank: Int,
    val releaseDate: String,
)

@Serializable
data class ItemCategoryId(
    val id: Int,
) {
    override operator fun equals(other: Any?): Boolean {
        return when (other is ItemCategoryId) {
            true -> (id == other.id)
            false -> false
        }
    }

    override fun hashCode(): Int {
        return id
    }

    override fun toString(): String {
        return "$id"
    }
}