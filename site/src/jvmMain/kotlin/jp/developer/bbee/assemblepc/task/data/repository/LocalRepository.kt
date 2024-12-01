package jp.developer.bbee.assemblepc.task.data.repository

import jp.developer.bbee.assemblepc.models.Item
import jp.developer.bbee.assemblepc.models.ItemCategory

interface LocalRepository {
    suspend fun saveItem(item: Item)
    suspend fun getItems(category: ItemCategory, skip: Long, limit: Int): List<Item>
}