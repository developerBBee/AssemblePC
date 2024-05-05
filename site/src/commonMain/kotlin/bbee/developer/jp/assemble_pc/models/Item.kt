package bbee.developer.jp.assemble_pc.models

import kotlinx.serialization.Serializable

@Serializable
data class Item(
    val itemCategoryId: ItemCategoryId,
    val makerId: MakerId,
    val itemName: String,
    val linkUrl: String,
    val imageUrl: String,
    val description: String,
    val price: Price,
    val rank: Int,
    val flag1: Int = 0,
    val flag2: Int = 0,
    val releaseDate: String,
    val outdated: Boolean,
) {
    override fun toString(): String {
        return "itemCategoryId: $itemCategoryId, makerId: $makerId, itemName: $itemName" +
                ", linkUrl: $linkUrl, imageUrl: $imageUrl, description: $description" +
                ", price: $price, rank: $rank, releaseDate: $releaseDate, outdated: $outdated"
    }
}

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

    override fun toString(): String {
        return "$id"
    }
}

@Serializable
data class MakerId(
    val id: Int
) {
    override operator fun equals(other: Any?): Boolean {
        return when (other is MakerId) {
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