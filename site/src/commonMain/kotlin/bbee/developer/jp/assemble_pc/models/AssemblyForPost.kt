package bbee.developer.jp.assemble_pc.models

import kotlinx.serialization.Serializable

@Serializable
data class AssemblyForPost(
    val assemblyId: AssemblyId,
    val ownerUserId: String,
    val assemblyDetail: AssemblyDetail,
)
