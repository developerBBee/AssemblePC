package bbee.developer.jp.assemble_pc.database

import bbee.developer.jp.assemble_pc.models.Assembly
import bbee.developer.jp.assemble_pc.models.AssemblyDetail
import bbee.developer.jp.assemble_pc.models.AssemblyId
import bbee.developer.jp.assemble_pc.models.DetailId
import bbee.developer.jp.assemble_pc.models.Item
import bbee.developer.jp.assemble_pc.models.ItemCategory

interface H2Repository {
    suspend fun addUserAnonymous(uid: String): Boolean
    suspend fun getCurrentAssembly(uid: String): Assembly
    suspend fun addAssembly(uid: String, assembly: Assembly): Boolean
    suspend fun updateAssembly(assembly: Assembly): Boolean
    suspend fun deleteAssembly(assemblyId: AssemblyId): Boolean
    suspend fun addAssemblyDetail(uid: String, assemblyId: AssemblyId, detail: AssemblyDetail): Boolean
    suspend fun updateAssemblyDetail(detailId: DetailId, detail: AssemblyDetail): Boolean
    suspend fun deleteAssemblyDetails(detailIds: List<DetailId>): Boolean
    suspend fun getItems(category: ItemCategory, skip: Long): List<Item>
    suspend fun getAssemblies(uid: String, skip: Long, own: Boolean): List<Assembly>
}