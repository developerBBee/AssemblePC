package bbee.developer.jp.assemble_pc.database

import bbee.developer.jp.assemble_pc.database.entity.Assemblies
import bbee.developer.jp.assemble_pc.database.entity.AssemblyDetails
import bbee.developer.jp.assemble_pc.database.entity.Items
import bbee.developer.jp.assemble_pc.database.entity.Samples
import bbee.developer.jp.assemble_pc.database.entity.TABLES
import bbee.developer.jp.assemble_pc.database.entity.Users
import bbee.developer.jp.assemble_pc.models.Assembly
import bbee.developer.jp.assemble_pc.models.AssemblyDetail
import bbee.developer.jp.assemble_pc.models.AssemblyId
import bbee.developer.jp.assemble_pc.models.DetailId
import bbee.developer.jp.assemble_pc.models.Item
import bbee.developer.jp.assemble_pc.models.ItemCategory
import bbee.developer.jp.assemble_pc.models.ItemCategoryId
import bbee.developer.jp.assemble_pc.models.ItemId
import bbee.developer.jp.assemble_pc.models.MakerId
import bbee.developer.jp.assemble_pc.models.Price
import bbee.developer.jp.assemble_pc.task.data.repository.LocalRepository
import bbee.developer.jp.assemble_pc.util.currentDateTime
import bbee.developer.jp.assemble_pc.util.localRepository
import com.varabyte.kobweb.api.data.add
import com.varabyte.kobweb.api.init.InitApi
import com.varabyte.kobweb.api.init.InitApiContext
import kotlinx.datetime.LocalDateTime
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.JoinType
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.SqlExpressionBuilder.inList
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.batchInsert
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update
import java.util.UUID

@InitApi
fun initDatabase(context: InitApiContext) {
    context.logger.debug("initDatabase() called")
    H2DB(context).also { db ->
        localRepository = db
        context.data.add(db)
    }

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

class H2DB(private val context: InitApiContext) : H2Repository, LocalRepository {
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

    override suspend fun addAssembly(uid: String, assembly: Assembly): Boolean {
        val now = currentDateTime

        return transaction(database) {
            context.logger.debug("addAssembly() start insert")
            Assemblies.insert {
                it[ownerUserId] = uid
                it[assemblyName] = assembly.assemblyName
                it[assemblyUrl] = assembly.assemblyUrl
                it[ownerComment] = assembly.ownerComment
                assembly.referenceAssemblyId?.also { ref -> it[referenceAssemblyId] = ref.id }
                it[createdAt] = now
                it[updatedAt] = now
            }.resultedValues?.firstOrNull()?.get(Assemblies.assemblyId)?.also { assemblyId ->
                AssemblyDetails.batchInsert(assembly.assemblyDetails) { detail ->
                    this[AssemblyDetails.ownerUserId] = uid
                    this[AssemblyDetails.assemblyId] = assemblyId
                    this[AssemblyDetails.itemId] = detail.itemId.id
                    this[AssemblyDetails.priceAtRegistered] = detail.priceAtRegistered.value
                    this[AssemblyDetails.createdAt] = now
                    this[AssemblyDetails.updatedAt] = now
                }
            }
            context.logger.debug("addAssembly() finish insert")
            true
        }
    }

    override suspend fun updateAssembly(assembly: Assembly): Boolean {
        val now = currentDateTime
        val assemblyId = assembly.assemblyId.id

        return transaction(database) {
            context.logger.debug("updateAssembly() start update")
            Assemblies.update(where = { Assemblies.assemblyId eq assemblyId }) {
                it[assemblyName] = assembly.assemblyName
                it[assemblyUrl] = assembly.assemblyUrl
                it[ownerComment] = assembly.ownerComment
                assembly.referenceAssemblyId?.also { ref -> it[referenceAssemblyId] = ref.id }
                it[updatedAt] = now
            }.let { result ->
                context.logger.debug("updateAssembly() finish update")
                result == 1
            }
        }
    }

    override suspend fun deleteAssembly(assemblyId: AssemblyId): Boolean {
        return transaction(database) {
            context.logger.debug("deleteAssembly() start delete")
            Assemblies.deleteWhere { Assemblies.assemblyId eq assemblyId.id }
                .let { result ->
                    context.logger.debug("deleteAssembly() finish delete")
                    result == 1
                }
        }
    }

    override suspend fun addAssemblyDetail(
        uid: String,
        assemblyId: AssemblyId,
        detail: AssemblyDetail
    ): Boolean {
        val now = currentDateTime

        return transaction(database) {
            context.logger.debug("addAssemblyDetail() start insert")
            AssemblyDetails.insert {
                it[ownerUserId] = uid
                it[this.assemblyId] = assemblyId.id
                it[itemId] = detail.itemId.id
                it[priceAtRegistered] = detail.priceAtRegistered.value
                it[createdAt] = now
                it[updatedAt] = now
            }
            context.logger.debug("addAssemblyDetail() finish insert")
            true
        }
    }

    override suspend fun updateAssemblyDetail(
        detailId: DetailId,
        detail: AssemblyDetail
    ): Boolean {
        val now = currentDateTime

        return transaction(database) {
            context.logger.debug("updateAssemblyDetail() start update")
            AssemblyDetails.update(where = { AssemblyDetails.detailId eq detailId.id }) {
                it[itemId] = detail.itemId.id
                it[priceAtRegistered] = detail.priceAtRegistered.value
                it[updatedAt] = now
            }.let { result ->
                context.logger.debug("updateAssemblyDetail() finish update")
                result == 1
            }
        }
    }

    override suspend fun deleteAssemblyDetails(detailIds: List<DetailId>): Boolean {
        return transaction(database) {
            context.logger.debug("deleteAssemblyDetails() start delete")
            AssemblyDetails.deleteWhere { detailId inList detailIds.map { it.id } }
                .let { result ->
                    context.logger.debug("deleteAssemblyDetails() finish delete")
                    result == detailIds.size
                }
        }
    }

    override suspend fun getItems(category: ItemCategory, skip: Long): List<Item> {
        return transaction(database) {
            context.logger.debug("getItems() start select")
            Items.selectAll()
                .where(Items.itemCategoryId eq category.ordinal)
                .orderBy(Items.rank)
                .limit(n = 20, offset = skip)
                .map {
                    Item(
                        itemId = ItemId(id = it[Items.itemId]),
                        itemCategoryId = ItemCategoryId(id = it[Items.itemCategoryId]),
                        makerId = MakerId(id = it[Items.makerId]),
                        itemName = it[Items.itemName],
                        linkUrl = it[Items.linkUrl],
                        imageUrl = it[Items.imageUrl],
                        description = it[Items.description],
                        price = Price(value = it[Items.price]),
                        rank = it[Items.rank],
                        releaseDate = it[Items.releaseDate],
                        outdated = it[Items.outdated],
                    )
                }
                .also {
                    context.logger.debug("getItems() finish select")
                }
        }
    }

    override suspend fun getAssemblies(skip: Long): List<Assembly> {
        return transaction(database) {
            Assemblies.join(
                otherTable = AssemblyDetails,
                joinType = JoinType.INNER,
                onColumn = Assemblies.assemblyId,
                otherColumn = AssemblyDetails.assemblyId
            ).selectAll()
                .where { Assemblies.published eq true }
                .orderBy(Assemblies.updatedAt)
                .limit(n = 20, offset = skip)
                .groupBy { Assemblies.assemblyId }
                .map { (_, details) ->
                    val assemblyResult = details.first()
                    Assembly(
                        assemblyId = AssemblyId(id = assemblyResult[Assemblies.assemblyId]),
                        ownerUserId = assemblyResult[Assemblies.ownerUserId],
                        assemblyName = assemblyResult[Assemblies.assemblyName],
                        assemblyUrl = assemblyResult[Assemblies.assemblyUrl],
                        ownerComment = assemblyResult[Assemblies.ownerComment],
                        referenceAssemblyId = assemblyResult[Assemblies.referenceAssemblyId]
                            ?.let { AssemblyId(id = it) },
                        published = assemblyResult[Assemblies.published],
                        assemblyDetails = details.map {
                            AssemblyDetail(
                                detailId = DetailId(id = it[AssemblyDetails.detailId]),
                                itemId = ItemId(id = it[AssemblyDetails.itemId]),
                                priceAtRegistered = Price(value = it[AssemblyDetails.priceAtRegistered]),
                            )
                        }
                    )
                }
        }
    }

    override fun saveItem(item: Item) {
        val now = currentDateTime

        val itemId = transaction(database) {
            Items.select(Items.itemId)
                .where { Items.linkUrl eq item.linkUrl }
                .toList()
                .ifEmpty { return@transaction null }
                .first()[Items.itemId]
        }

        if (itemId == null) {
            addItem(item = item, now = now)
        } else {
            updateItem(item = item, now = now)
        }
    }

    private fun addItem(item: Item, now: LocalDateTime): Boolean {
        return transaction(database) {
            context.logger.debug("addItem() start insert : $item")
            Items.insert {
                it[itemCategoryId] = item.itemCategoryId.id
                it[makerId] = item.makerId.id
                it[itemName] = item.itemName
                it[linkUrl] = item.linkUrl
                it[imageUrl] = item.imageUrl
                it[description] = item.description
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

    private fun updateItem(item: Item, now: LocalDateTime): Boolean {
        return transaction(database) {
            context.logger.debug("updateItem() start update")
            Items.update(where = { Items.itemId eq 1 } ) {
                it[itemName] = item.itemName
                it[imageUrl] = item.imageUrl
                it[description] = item.description
                it[price] = item.price.value
                it[rank] = item.rank
                it[flag1] = item.flag1
                it[flag2] = item.flag2
                it[releaseDate] = item.releaseDate
                it[outdated] = item.outdated
                it[updatedAt] = now
            }
            context.logger.debug("updateItem() finish update")
            true
        }
    }
}