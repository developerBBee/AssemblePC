package bbee.developer.jp.assemble_pc.task.data.repository

import bbee.developer.jp.assemble_pc.models.Item
import bbee.developer.jp.assemble_pc.models.ItemCategory

interface LocalRepository {
    suspend fun saveItem(item: Item)
    suspend fun getItems(category: ItemCategory, skip: Long, limit: Int): List<Item>
}