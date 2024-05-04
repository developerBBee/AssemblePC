package bbee.developer.jp.assemble_pc.api

import bbee.developer.jp.assemble_pc.api.util.getBody
import bbee.developer.jp.assemble_pc.api.util.getUid
import bbee.developer.jp.assemble_pc.api.util.setBody
import bbee.developer.jp.assemble_pc.database.H2DB
import bbee.developer.jp.assemble_pc.models.Assembly
import com.varabyte.kobweb.api.Api
import com.varabyte.kobweb.api.ApiContext
import com.varabyte.kobweb.api.data.getValue

@Api(routeOverride = "add_assembly")
suspend fun addAssembly(context: ApiContext) {
    context.runCatching {
        getUid().also { uid ->
            req.getBody<Assembly>()?.also { assembly ->
                data.getValue<H2DB>().addAssembly(uid, assembly).also { result ->
                    res.setBody(result)
                }
            }
        }
    }.onFailure { e ->
        context.logger.error("addAssembly API EXCEPTION: $e")
        context.res.setBody(e.message ?: "addAssembly API error")
    }
}

@Api(routeOverride = "update_assembly")
suspend fun updateAssembly(context: ApiContext) {
    context.runCatching {
        getUid().also { uid ->
            req.getBody<Assembly>()?.also { assembly ->
                if (uid != assembly.ownerUserId) throw IllegalArgumentException("You are not owner")

                data.getValue<H2DB>().updateAssembly(assembly).also { result ->
                    res.setBody(result)
                }
            }
        }
    }.onFailure { e ->
        context.logger.error("updateAssembly API EXCEPTION: $e")
        context.res.setBody(e.message ?: "updateAssembly API error")
    }
}

@Api(routeOverride = "delete_assembly")
suspend fun deleteAssembly(context: ApiContext) {
    context.runCatching {
        getUid().also { uid ->
            req.getBody<Assembly>()?.also { assembly ->
                if (uid != assembly.ownerUserId) throw IllegalArgumentException("You are not owner")

                data.getValue<H2DB>().deleteAssembly(assembly.assemblyId).also { result ->
                    res.setBody(result)
                }
            }
        }
    }.onFailure { e ->
        context.logger.error("deleteAssembly API EXCEPTION: $e")
        context.res.setBody(e.message ?: "deleteAssembly API error")
    }
}

@Api(routeOverride = "add_assembly_detail")
suspend fun addAssemblyDetail(context: ApiContext) {
    context.runCatching {
        getUid().also { uid ->
            req.getBody<Assembly>()?.also { assembly ->
                if (uid != assembly.ownerUserId) throw IllegalArgumentException("You are not owner")

                data.getValue<H2DB>().addAssemblyDetail(
                    uid = uid,
                    assemblyId = assembly.assemblyId,
                    detail = assembly.assemblyDetails.first()
                ).also { result ->
                    res.setBody(result)
                }
            }
        }
    }.onFailure { e ->
        context.logger.error("addAssemblyDetail API EXCEPTION: $e")
        context.res.setBody(e.message ?: "addAssemblyDetail API error")
    }
}

@Api(routeOverride = "update_assembly_detail")
suspend fun updateAssemblyDetail(context: ApiContext) {
    context.runCatching {
        getUid().also { uid ->
            req.getBody<Assembly>()?.also { assembly ->
                if (uid != assembly.ownerUserId) throw IllegalArgumentException("You are not owner")
                val detail = assembly.assemblyDetails.first()

                data.getValue<H2DB>().updateAssemblyDetail(
                    detailId = detail.detailId,
                    detail = detail,
                ).also { result ->
                    res.setBody(result)
                }
            }
        }
    }.onFailure { e ->
        context.logger.error("updateAssemblyDetail API EXCEPTION: $e")
        context.res.setBody(e.message ?: "updateAssemblyDetail API error")
    }
}

@Api(routeOverride = "delete_assembly_details")
suspend fun deleteAssemblyDetails(context: ApiContext) {
    context.runCatching {
        getUid().also { uid ->
            req.getBody<Assembly>()?.also { assembly ->
                if (uid != assembly.ownerUserId) throw IllegalArgumentException("You are not owner")
                val detailIds = assembly.assemblyDetails.map { it.detailId }

                data.getValue<H2DB>().deleteAssemblyDetails(
                    detailIds = detailIds,
                ).also { result ->
                    res.setBody(result)
                }
            }
        }
    }.onFailure { e ->
        context.logger.error("updateAssemblyDetail API EXCEPTION: $e")
        context.res.setBody(e.message ?: "updateAssemblyDetail API error")
    }
}