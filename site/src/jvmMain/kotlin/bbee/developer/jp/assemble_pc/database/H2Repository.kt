package bbee.developer.jp.assemble_pc.database

import bbee.developer.jp.assemble_pc.models.Assembly
import bbee.developer.jp.assemble_pc.models.AssemblyDetail
import bbee.developer.jp.assemble_pc.models.AssemblyId
import bbee.developer.jp.assemble_pc.models.DetailId
import bbee.developer.jp.assemble_pc.models.ItemCategory
import bbee.developer.jp.assemble_pc.models.ItemDetail

interface H2Repository {
    suspend fun addUserAnonymous(uid: String): Boolean
    suspend fun addAssembly(uid: String, assembly: Assembly): Boolean
    suspend fun updateAssembly(assembly: Assembly): Boolean
    suspend fun deleteAssembly(assemblyId: AssemblyId): Boolean
    suspend fun addAssemblyDetail(uid: String, assemblyId: AssemblyId, detail: AssemblyDetail): Boolean
    suspend fun updateAssemblyDetail(detailId: DetailId, detail: AssemblyDetail): Boolean
    suspend fun deleteAssemblyDetails(detailIds: List<DetailId>): Boolean
    suspend fun getItems(skip: Long, category: ItemCategory): List<ItemDetail>
    suspend fun getAssemblies(skip: Long): List<Assembly>
}