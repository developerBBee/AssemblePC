package bbee.developer.jp.assemble_pc.database

import bbee.developer.jp.assemble_pc.database.entity.Assemblies
import bbee.developer.jp.assemble_pc.database.entity.AssemblyDetails
import bbee.developer.jp.assemble_pc.database.entity.Items
import bbee.developer.jp.assemble_pc.database.entity.Samples
import bbee.developer.jp.assemble_pc.database.entity.TABLES
import bbee.developer.jp.assemble_pc.database.entity.Users
import bbee.developer.jp.assemble_pc.model.Assembly
import bbee.developer.jp.assemble_pc.model.Item
import bbee.developer.jp.assemble_pc.common.util.currentDateTime
import com.varabyte.kobweb.api.data.add
import com.varabyte.kobweb.api.init.InitApi
import com.varabyte.kobweb.api.init.InitApiContext
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.batchInsert
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.UUID

@InitApi
fun initDatabase(context: InitApiContext) {
    context.logger.debug("initDatabase() called")
    context.data.add(H2DB(context))

    transaction {
        addLogger(StdOutSqlLogger) // TODO: Where are logs stored?

        // Create table if not exists
        SchemaUtils.create(*TABLES)

        // Insert sample data
        val testUuid = UUID.randomUUID().toString()
        Samples.insert {
            it[uuid] = testUuid
            it[name] = "Test User"
        }

        // Output SELECT result to log
        Samples
            .select(Samples.uuid, Samples.name)
            .where { Samples.uuid eq testUuid }
            .forEach {
                context.logger.debug("Hello ${it[Samples.name]}")
                context.logger.debug("Your UUID = ${it[Samples.uuid]}")
            }
    }
}

class H2DB(private val context: InitApiContext) : H2Repository {
    private val database = Database.connect(
        url = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;", // TODO: In memory DB for debug
        driver = "org.h2.Driver",
        user = "root",
        password = "",
    )

    override suspend fun addUserAnonymous(uid: String): Boolean {
        val now = currentDateTime

        return transaction(database) {
            context.logger.debug("addUserAnonymous() start insert")
            Users.insert {
                it[userId] = uid
                it[createdAt] = now
                it[updatedAt] = now
            }
            context.logger.debug("addUserAnonymous() finish insert")
            true
        }
    }

    override suspend fun addItem(item: Item): Boolean {
        val now = currentDateTime

        return transaction(database) {
            context.logger.debug("addItem() start insert")
            Items.insert {
                it[itemCategoryId] = item.itemCategoryId
                it[makerId] = item.makerId
                it[itemName] = item.itemName
                it[linkUrl] = item.linkUrl
                it[imageUrl] = item.imageUrl
                it[desc] = item.description
                it[price] = item.price.value
                it[rank] = item.rank
                it[flag1] = item.flag1
                it[flag2] = item.flag2
                it[releaseDate] = item.releaseDate
                it[outdated] = item.outdated
                it[createdAt] = now
                it[updatedAt] = now
            }
            context.logger.debug("addItem() finish insert")
            true
        }
    }

    override suspend fun addAssembly(uid: String, assembly: Assembly): Boolean {
        val now = currentDateTime

        return transaction(database) {
            context.logger.debug("addAssembly() start insert")
            Assemblies.insert {
                it[ownerUserId] = uid
                it[assemblyName] = assembly.assemblyName
                it[assemblyUrl] = assembly.assemblyUrl
                it[ownerComment] = assembly.ownerComment
                it[referenceAssemblyId] = assembly.referenceAssemblyId.id
                it[createdAt] = now
                it[updatedAt] = now
            }.resultedValues?.firstOrNull()?.get(Assemblies.assemblyId)?.also { assemblyId ->
                AssemblyDetails.batchInsert(assembly.details) { detail ->
                    this[AssemblyDetails.assemblyId] = assemblyId
                    this[AssemblyDetails.itemId] = detail.itemId.id
                    this[AssemblyDetails.priceAtRegistered] = detail.priceAtRegistered
                    this[AssemblyDetails.createdAt] = now
                    this[AssemblyDetails.updatedAt] = now
                }
            }
            context.logger.debug("addAssembly() finish insert")
            true
        }
    }
}
