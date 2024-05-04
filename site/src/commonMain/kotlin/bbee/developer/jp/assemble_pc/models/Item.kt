package bbee.developer.jp.assemble_pc.models

import kotlinx.serialization.Serializable

@Serializable
data class Item(
    val itemCategoryId: ItemCategoryId,
    val makerId: Int,
    val itemName: String,
    val linkUrl: String,
    val imageUrl: String,
    val description: String,
    val price: Price,
    val rank: Int,
    val flag1: Int,
    val flag2: Int,
    val releaseDate: String,
    val outdated: Boolean,
)

@Serializable
data class ItemId(
    val id: Int,
) {
    override operator fun equals(other: Any?): Boolean {
        return when (other is ItemId) {
            true -> (id == other.id)
            false -> false
        }
    }

    override fun hashCode(): Int {
        return id
    }
}
