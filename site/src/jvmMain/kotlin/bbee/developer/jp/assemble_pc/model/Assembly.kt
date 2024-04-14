package bbee.developer.jp.assemble_pc.model

import kotlinx.serialization.Serializable

@Serializable
data class Assembly(
    val assemblyId: AssemblyId,
    val assemblyName: String,
    val assemblyUrl: String,
    val ownerComment: String,
    val referenceAssemblyId: AssemblyId,
    val details: List<Detail>,
)

@Serializable
data class AssemblyId(
    val id: Int,
)

@Serializable
data class Detail(
    val itemId: ItemId,
    val priceAtRegistered: Int,
)