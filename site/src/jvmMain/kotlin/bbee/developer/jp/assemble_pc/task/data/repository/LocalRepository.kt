package bbee.developer.jp.assemble_pc.task.data.repository

import bbee.developer.jp.assemble_pc.models.Item

interface LocalRepository {
    fun saveItem(item: Item)
}