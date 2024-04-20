package bbee.developer.jp.assemble_pc.database

import bbee.developer.jp.assemble_pc.model.Assembly
import bbee.developer.jp.assemble_pc.model.Item

interface H2Repository {
    suspend fun addUserAnonymous(): Boolean
    suspend fun addItem(item: Item): Boolean
    suspend fun addAssembly(userId: String, assembly: Assembly): Boolean
}