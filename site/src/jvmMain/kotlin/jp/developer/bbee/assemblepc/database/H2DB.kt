package jp.developer.bbee.assemblepc.database

import jp.developer.bbee.assemblepc.database.entity.Assemblies
import jp.developer.bbee.assemblepc.database.entity.AssemblyDetails
import jp.developer.bbee.assemblepc.database.entity.CREATE_FAV_ASSEM_VIEW
import jp.developer.bbee.assemblepc.database.entity.CREATE_REF_ASSEM_VIEW
import jp.developer.bbee.assemblepc.database.entity.FavoriteAssemblies
import jp.developer.bbee.assemblepc.database.entity.FavoriteAssembliesView
import jp.developer.bbee.assemblepc.database.entity.FavoriteItems
import jp.developer.bbee.assemblepc.database.entity.FavoriteMakers
import jp.developer.bbee.assemblepc.database.entity.Items
import jp.developer.bbee.assemblepc.database.entity.TABLES
import jp.developer.bbee.assemblepc.database.entity.Users
import jp.developer.bbee.assemblepc.models.Assembly
import jp.developer.bbee.assemblepc.models.AssemblyDetail
import jp.developer.bbee.assemblepc.models.AssemblyId
import jp.developer.bbee.assemblepc.models.DESC_JSON
import jp.developer.bbee.assemblepc.models.DetailId
import jp.developer.bbee.assemblepc.models.Item
import jp.developer.bbee.assemblepc.models.ItemCategory
import jp.developer.bbee.assemblepc.models.ItemCategoryId
import jp.developer.bbee.assemblepc.models.ItemId
import jp.developer.bbee.assemblepc.models.MakerId
import jp.developer.bbee.assemblepc.models.Price
import jp.developer.bbee.assemblepc.models.Profile
import jp.developer.bbee.assemblepc.task.data.repository.LocalRepository
import jp.developer.bbee.assemblepc.util.currentDateTime
import jp.developer.bbee.assemblepc.util.localRepository
import jp.developer.bbee.assemblepc.util.logger
import jp.developer.bbee.assemblepc.util.toDateString
import jp.developer.bbee.assemblepc.util.toDateTimeString
import com.varabyte.kobweb.api.data.add
import com.varabyte.kobweb.api.init.InitApi
import com.varabyte.kobweb.api.init.InitApiContext
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.JoinType
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.SortOrder
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.SqlExpressionBuilder.inList
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.alias
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.batchInsert
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update

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
        exec(CREATE_FAV_ASSEM_VIEW)
        exec(CREATE_REF_ASSEM_VIEW)
    }
}

class H2DB(private val context: InitApiContext) : H2Repository, LocalRepository {
    private val database = Database.connect(
        url = "jdbc:h2:~/pcassem_db2/kakakudb", // Test db file
//        url = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;", // TODO: In memory DB for debug
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

    override suspend fun preRegisterUidUpdate(uid: String): Boolean {
        val now = currentDateTime

        return transaction(database) {
            context.logger.debug("preRegisterUidUpdate start update")

            Users.update(where = { Users.userId eq uid }) {
                it[updateEnabled] = true
                it[updatedAt] = now
            }
            true
        }
    }

    override suspend fun updateUserId(oldUid: String, newUid: String): Boolean {
        val now = currentDateTime

        return transaction(database) {
            context.logger.debug("check newUid already exist")
            Users.selectAll()
                .where { Users.userId eq newUid }
                .firstOrNull()
                ?.also {
                    context.logger.debug("New user already exist, delete old user")
                    Users.deleteWhere { userId eq oldUid }
                    Assemblies.deleteWhere { ownerUserId eq oldUid }
                    AssemblyDetails.deleteWhere { ownerUserId eq oldUid }
                    FavoriteItems.deleteWhere { ownerUserId eq oldUid }
                    FavoriteMakers.deleteWhere { ownerUserId eq oldUid }
                    FavoriteAssemblies.deleteWhere { ownerUserId eq oldUid }
                    return@transaction true
                }

            context.logger.debug("updateUserId start update")
            Users.update(where = { (Users.updateEnabled eq true) and (Users.userId eq oldUid) }) {
                it[userId] = newUid
                it[updateEnabled] = false
                it[updatedAt] = now
            }.let { result ->
                if (result == 1) {
                    context.logger.debug("updateUserId finish update")
                    Assemblies.update(where = { (Assemblies.ownerUserId eq oldUid) }) {
                        it[ownerUserId] = newUid
                        it[updatedAt] = now
                    }
                    AssemblyDetails.update(where = { (AssemblyDetails.ownerUserId eq oldUid) }) {
                        it[ownerUserId] = newUid
                        it[updatedAt] = now
                    }
                    FavoriteItems.update(where = { (FavoriteItems.ownerUserId eq oldUid) }) {
                        it[ownerUserId] = newUid
                        it[updatedAt] = now
                    }
                    FavoriteMakers.update(where = { (FavoriteMakers.ownerUserId eq oldUid) }) {
                        it[ownerUserId] = newUid
                        it[updatedAt] = now
                    }
                    FavoriteAssemblies.update(where = { (FavoriteAssemblies.ownerUserId eq oldUid) }) {
                        it[ownerUserId] = newUid
                        it[updatedAt] = now
                    }
                    true
                } else {
                    false
                }
            }
        }
    }
    override suspend fun getUserProfile(uid: String): Profile {
        return transaction(database) {
            context.logger.debug("getUserProfile() start select")
            Users.selectAll()
                .where { Users.userId eq uid }
                .first()
                .let {
                    Profile(
                        userName = it[Users.userName],
                        userEmail = it[Users.userEmail]
                    )
                }
        }
    }

    override suspend fun updateUserProfile(uid: String, profile: Profile): Boolean {
        val now = currentDateTime

        return transaction(database) {
            context.logger.debug("updateUserProfile() start update")
            Users.update(where = { Users.userId eq uid}) {
                it[userName] = profile.userName
                it[userEmail] = profile.userEmail
                it[createdAt] = now
                it[updatedAt] = now
            }
            context.logger.debug("updateUserProfile() finish update")
            true
        }
    }

    override suspend fun getCurrentAssembly(uid: String): Assembly {
        return transaction(database) {
            context.logger.debug("getCurrentAssembly() start select")
            Assemblies
                .join(
                    otherTable = Users,
                    joinType = JoinType.INNER,
                    onColumn = Assemblies.ownerUserId,
                    otherColumn = Users.userId,
                )
                .selectAll()
                .where { (Assemblies.ownerUserId eq uid) and (Assemblies.published eq false) }
                .orderBy(column = Assemblies.updatedAt, order = SortOrder.DESC)
                .limit(n = 1, offset = 0L)
                .map { assemblyRow ->
                    val assemblyId = assemblyRow[Assemblies.assemblyId]

                    val assemblyDetails = AssemblyDetails
                        .join(
                            otherTable = Items,
                            joinType = JoinType.INNER,
                            onColumn = AssemblyDetails.itemId,
                            otherColumn = Items.itemId,
                        )
                        .selectAll()
                        .where { AssemblyDetails.assemblyId eq assemblyId }
                        .map { row ->
                            AssemblyDetail(
                                detailId = DetailId(id = row[AssemblyDetails.detailId]),
                                item = Item(
                                    itemId = ItemId(row[Items.itemId]),
                                    itemCategoryId = ItemCategoryId(row[Items.itemCategoryId]),
                                    makerId = MakerId(row[Items.makerId]),
                                    itemName = row[Items.itemName],
                                    linkUrl = row[Items.linkUrl],
                                    imageUrl = row[Items.imageUrl],
                                    description = DESC_JSON.decodeFromString(row[Items.description]),
                                    price = Price(row[Items.price]),
                                    rank = row[Items.rank],
                                    flag1 = row[Items.flag1],
                                    flag2 = row[Items.flag2],
                                    releaseDate = row[Items.releaseDate],
                                    outdated = row[Items.outdated],
                                    lastUpdate = row[Items.updatedAt].toDateString()
                                ),
                                priceAtRegistered = Price(
                                    value = row[AssemblyDetails.priceAtRegistered]
                                ),
                            )
                        }

                    Assembly(
                        assemblyId = AssemblyId(id = assemblyRow[Assemblies.assemblyId]),
                        ownerUserId = assemblyRow[Assemblies.ownerUserId],
                        assemblyName = assemblyRow[Assemblies.assemblyName],
                        assemblyUrl = assemblyRow[Assemblies.assemblyUrl],
                        ownerName = assemblyRow[Users.userName],
                        ownerComment = assemblyRow[Assemblies.ownerComment],
                        referenceAssemblyId = assemblyRow[Assemblies.referenceAssemblyId]
                            ?.let { AssemblyId(id = it) },
                        published = assemblyRow[Assemblies.published],
                        publishedDate = assemblyRow[Assemblies.updatedAt].toDateTimeString(),
                        assemblyDetails = assemblyDetails,
                        favoriteCount = 0,
                    )
                }
                .first()
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
                it[published] = assembly.published
                it[createdAt] = now
                it[updatedAt] = now
            }.resultedValues?.firstOrNull()?.get(Assemblies.assemblyId)?.also { assemblyId ->
                AssemblyDetails.batchInsert(assembly.assemblyDetails) { detail ->
                    this[AssemblyDetails.ownerUserId] = uid
                    this[AssemblyDetails.assemblyId] = assemblyId
                    this[AssemblyDetails.itemId] = detail.item.itemId!!.id
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
                it[published] = assembly.published
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
                it[itemId] = detail.item.itemId!!.id
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
                it[itemId] = detail.item.itemId!!.id
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

    override suspend fun getItems(category: ItemCategory, skip: Long, limit: Int): List<Item> {
        return transaction(database) {
            context.logger.debug("getItems() start select")
            Items.selectAll()
                .where(Items.itemCategoryId eq category.ordinal)
                .orderBy(Items.rank)
                .limit(n = limit, offset = skip)
                .map {
                    Item(
                        itemId = ItemId(id = it[Items.itemId]),
                        itemCategoryId = ItemCategoryId(id = it[Items.itemCategoryId]),
                        makerId = MakerId(id = it[Items.makerId]),
                        itemName = it[Items.itemName],
                        linkUrl = it[Items.linkUrl],
                        imageUrl = it[Items.imageUrl],
                        description = DESC_JSON.decodeFromString(it[Items.description]),
                        price = Price(value = it[Items.price]),
                        rank = it[Items.rank],
                        releaseDate = it[Items.releaseDate],
                        outdated = it[Items.outdated],
                        lastUpdate = it[Items.updatedAt].toDateString(),
                    )
                        .also { logger.debug(it.toString()) }
                }
                .also {
                    context.logger.debug("getItems() finish select")
                }
        }
    }

    override suspend fun getAssemblies(
        uid: String,
        skip: Long,
        own: Boolean,
        published: Boolean,
        favoriteOnly: Boolean
    ): List<Assembly> {
        return transaction(database) {
            val sub = Assemblies
                .selectAll()
                .orderBy(column = Assemblies.updatedAt, order = SortOrder.DESC)
                .limit(n = 20, offset = skip)
                .alias("AssembliesSubQuery")

            sub
                .join(
                    otherTable = Users,
                    joinType = JoinType.INNER,
                    onColumn = sub[Assemblies.ownerUserId],
                    otherColumn = Users.userId,
                )
                .join(
                    otherTable = AssemblyDetails,
                    joinType = JoinType.INNER,
                    onColumn = sub[Assemblies.assemblyId],
                    otherColumn = AssemblyDetails.assemblyId
                )
                .join(
                    otherTable = Items,
                    joinType = JoinType.INNER,
                    onColumn = AssemblyDetails.itemId,
                    otherColumn = Items.itemId,
                )
                .join(
                    otherTable = FavoriteAssembliesView,
                    joinType = JoinType.LEFT,
                    onColumn = sub[Assemblies.assemblyId],
                    otherColumn = FavoriteAssembliesView.assemblyId
                )
                .join(
                    otherTable = FavoriteAssemblies,
                    joinType = JoinType.LEFT,
                    onColumn = sub[Assemblies.assemblyId],
                    otherColumn = FavoriteAssemblies.assemblyId
                )
                .selectAll()
                .where {
                    (sub[Assemblies.published] eq published) and
                            if (own) {
                                (sub[Assemblies.ownerUserId] eq uid)
                            } else {
                                (sub[Assemblies.ownerUserId] neq uid)
                            } and
                            if (favoriteOnly) {
                                (FavoriteAssemblies.ownerUserId eq uid)
                            } else {
                                (sub[Assemblies.assemblyId] eq sub[Assemblies.assemblyId]) // Always true
                            }
                }
                .orderBy(column = sub[Assemblies.updatedAt], order = SortOrder.DESC)
                .groupBy { it[sub[Assemblies.assemblyId]] }
                .map { (_, details) ->
                    val assemblyResult = details.first()
                    Assembly(
                        assemblyId = AssemblyId(id = assemblyResult[sub[Assemblies.assemblyId]]),
                        ownerUserId = assemblyResult[sub[Assemblies.ownerUserId]],
                        assemblyName = assemblyResult[sub[Assemblies.assemblyName]],
                        assemblyUrl = assemblyResult[sub[Assemblies.assemblyUrl]],
                        ownerName = assemblyResult[Users.userName],
                        ownerComment = assemblyResult[sub[Assemblies.ownerComment]],
                        referenceAssemblyId = assemblyResult[sub[Assemblies.referenceAssemblyId]]
                            ?.let { AssemblyId(id = it) },
                        published = assemblyResult[sub[Assemblies.published]],
                        publishedDate = assemblyResult[sub[Assemblies.updatedAt]].toDateTimeString(),
                        assemblyDetails = details.map {
                            AssemblyDetail(
                                detailId = DetailId(id = it[AssemblyDetails.detailId]),
                                item = Item(
                                    itemId = ItemId(it[Items.itemId]),
                                    itemCategoryId = ItemCategoryId(it[Items.itemCategoryId]),
                                    makerId = MakerId(it[Items.makerId]),
                                    itemName = it[Items.itemName],
                                    linkUrl = it[Items.linkUrl],
                                    imageUrl = it[Items.imageUrl],
                                    description = DESC_JSON.decodeFromString(it[Items.description]),
                                    price = Price(it[Items.price]),
                                    rank = it[Items.rank],
                                    flag1 = it[Items.flag1],
                                    flag2 = it[Items.flag2],
                                    releaseDate = it[Items.releaseDate],
                                    outdated = it[Items.outdated],
                                    lastUpdate = it[Items.updatedAt].toDateString(),
                                ),
                                priceAtRegistered = Price(value = it[AssemblyDetails.priceAtRegistered]),
                            )
                        },
                        favoriteCount = assemblyResult
                            .getOrNull(FavoriteAssembliesView.favoriteCount)?.toInt() ?: 0,
                    )
                }
        }
    }

    override suspend fun saveItem(item: Item) {
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
            updateItem(item = item, itemId = itemId, now = now)
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
                it[description] = Json.encodeToString(item.description)
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

    private fun updateItem(item: Item, itemId: Int, now: LocalDateTime): Boolean {
        return transaction(database) {
            context.logger.debug("updateItem() start update")
            Items.update(where = { Items.itemId eq itemId } ) {
                it[itemCategoryId] = item.itemCategoryId.id
                it[makerId] = item.makerId.id
                it[itemName] = item.itemName
                it[linkUrl] = item.linkUrl
                it[imageUrl] = item.imageUrl
                it[description] = Json.encodeToString(item.description)
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


    override suspend fun addFavoriteAssembly(uid: String, aid: AssemblyId): Boolean {
        return transaction(database) {
            context.logger.debug("addFavoriteAssembly() start check duplication : $uid#${aid.id}")
            FavoriteAssemblies
                .selectAll()
                .where {
                    (FavoriteAssemblies.ownerUserId eq uid) and
                            (FavoriteAssemblies.assemblyId eq aid.id)
                }
                .firstOrNull()
                ?.also {
                    context.logger.debug("addFavoriteAssembly() already exist : $uid#${aid.id}")
                    return@transaction false
                }

            context.logger.debug("addFavoriteAssembly() start insert : $uid#${aid.id}")
            FavoriteAssemblies.insert {
                val now = currentDateTime
                it[ownerUserId] = uid
                it[assemblyId] = aid.id
                it[createdAt] = now
                it[updatedAt] = now
            }
            context.logger.debug("addItem() finish insert")
            true
        }
    }

    override suspend fun removeFavoriteAssembly(uid: String, aid: AssemblyId): Boolean {
        return transaction(database) {
            context.logger.debug("removeFavoriteAssembly() start delete : $uid#${aid.id}")
            FavoriteAssemblies.deleteWhere { (ownerUserId eq uid) and (assemblyId eq aid.id) }
                .also {
                    if (it == 0) {
                        context.logger.debug("removeFavoriteAssembly() not found : $uid#${aid.id}")
                        return@transaction false
                    }
                }
            context.logger.debug("removeFavoriteAssembly() finish delete")
            true
        }
    }

    override suspend fun getMyFavoriteAssemblyIdList(uid: String): List<AssemblyId> {
        return transaction(database) {
            context.logger.debug("getMyFavoriteAssemblyIds() start select")
            FavoriteAssemblies
                .selectAll()
                .where { FavoriteAssemblies.ownerUserId eq uid }
                .map { AssemblyId(id = it[FavoriteAssemblies.assemblyId]) }
        }
    }
}