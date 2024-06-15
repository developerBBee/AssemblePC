package bbee.developer.jp.assemble_pc.database.entity

import kotlinx.datetime.LocalDateTime
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.kotlin.datetime.datetime

val TABLES = arrayOf(
    Users,
    Items,
    ItemCategories,
    Makers,
    Assemblies,
    AssemblyDetails,
    FavoriteItems,
    FavoriteMakers,
    FavoriteAssemblies
)
const val CREATE_FAV_ASSEM_VIEW: String = """CREATE VIEW IF NOT EXISTS FAVORITE_ASSEMBLIES_VIEW AS (SELECT ASSEMBLY_ID, COUNT(ASSEMBLY_ID) AS FAVORITE_COUNT FROM FAVORITEASSEMBLIES GROUP BY ASSEMBLY_ID);"""
const val CREATE_REF_ASSEM_VIEW: String = """CREATE VIEW IF NOT EXISTS REFERENCE_ASSEMBLIES_VIEW AS (SELECT REFERENCE_ASSEMBLY_ID AS ASSEMBLY_ID, COUNT(REFERENCE_ASSEMBLY_ID) AS REFERENCE_COUNT FROM ASSEMBLIES WHERE REFERENCE_ASSEMBLY_ID IS NOT NULL GROUP BY REFERENCE_ASSEMBLY_ID);"""

object Users : Table() {
    val userId: Column<String> = varchar("USER_ID", 36)
    val userName: Column<String?> = varchar("USER_NAME", 100).nullable()
    val userEmail: Column<String?> = varchar("USER_EMAIL", 100).nullable()
    val password: Column<String?> = varchar("PASSWORD", 255).nullable()
    val updateEnabled: Column<Boolean> = bool("UPDATE_ENABLED").default(false)
    val createdAt: Column<LocalDateTime> = datetime("CREATED_AT")
    val updatedAt: Column<LocalDateTime> = datetime("UPDATED_AT")

    override val primaryKey = PrimaryKey(firstColumn = userId, name = "PK_USERS_USER_ID")
}

object Items : Table() {
    val itemId: Column<Int> = integer("ITEM_ID").autoIncrement()
    val itemCategoryId: Column<Int> = integer("ITEM_CATEGORY_ID")
    val makerId: Column<Int> = integer("MAKER_ID")
    val itemName: Column<String> = varchar("ITEM_NAME", 255)
    val linkUrl: Column<String> = varchar("LINK_URL", 255)
    val imageUrl: Column<String> = varchar("IMAGE_URL", 255)
    val description: Column<String> = varchar("DESCRIPTION", 4095)
    val price: Column<Int> = integer("PRICE")
    val rank: Column<Int> = integer("RANK")
    val flag1: Column<Int> = integer("FLAG1")
    val flag2: Column<Int> = integer("FLAG2")
    val releaseDate: Column<String> = varchar("RELEASE_DATE", 8)
    val outdated: Column<Boolean> = bool("OUTDATED")
    val createdAt: Column<LocalDateTime> = datetime("CREATED_AT")
    val updatedAt: Column<LocalDateTime> = datetime("UPDATED_AT")

    override val primaryKey = PrimaryKey(firstColumn = itemId, name = "PK_ITEMS_ITEM_ID")
}

object ItemCategories : Table() {
    val itemCategoryId: Column<Int> = integer("ITEM_CATEGORY_ID").autoIncrement()
    val categoryName: Column<String> = varchar("CATEGORY_NAME", 20)
    val isSingleChoice: Column<Boolean> = bool("IS_SINGLE_CHOICE")
    val createdAt: Column<LocalDateTime> = datetime("CREATED_AT")
    val updatedAt: Column<LocalDateTime> = datetime("UPDATED_AT")

    override val primaryKey =
        PrimaryKey(firstColumn = itemCategoryId, name = "PK_ITEM_CATEGORIES_ITEM_CATEGORY_ID")
}

object Makers : Table() {
    val makerId: Column<Int> = integer("MAKER_ID").autoIncrement()
    val makerName: Column<String> = varchar("MAKER_NAME", 50)
    val makerFullName: Column<String> = varchar("MAKER_FULL_NAME", 100)
    val country: Column<String> = varchar("COUNTRY", 50)
    val makerUrl: Column<String> = varchar("MAKER_URL", 255)
    val logoUrl: Column<String> = varchar("LOGO_URL", 255)
    val createdAt: Column<LocalDateTime> = datetime("CREATED_AT")
    val updatedAt: Column<LocalDateTime> = datetime("UPDATED_AT")

    override val primaryKey = PrimaryKey(firstColumn = makerId, name = "PK_MAKERS_MAKER_ID")
}

object Assemblies : Table() {
    val assemblyId: Column<Int> = integer("ASSEMBLY_ID").autoIncrement()
    val ownerUserId: Column<String> = varchar("OWNER_USER_ID", 36)
    val assemblyName: Column<String> = varchar("ASSEMBLY_NAME", 100)
    val assemblyUrl: Column<String> = varchar("ASSEMBLY_URL", 255)
    val ownerComment: Column<String> = varchar("OWNER_COMMENT", 511)
    val referenceAssemblyId: Column<Int?> = integer("REFERENCE_ASSEMBLY_ID").nullable()
    val published: Column<Boolean> = bool("PUBLISHED")
    val createdAt: Column<LocalDateTime> = datetime("CREATED_AT")
    val updatedAt: Column<LocalDateTime> = datetime("UPDATED_AT")

    override val primaryKey =
        PrimaryKey(firstColumn = assemblyId, name = "PK_ASSEMBLIES_ASSEMBLY_ID")
}

object AssemblyDetails : Table() {
    val detailId: Column<Int> = integer("DETAIL_ID").autoIncrement()
    val ownerUserId: Column<String> = varchar("OWNER_USER_ID", 36)
    val assemblyId: Column<Int> = integer("ASSEMBLY_ID")
    val itemId: Column<Int> = integer("ITEM_ID")
    val priceAtRegistered: Column<Int> = integer("PRICE_AT_REGISTERED")
    val createdAt: Column<LocalDateTime> = datetime("CREATED_AT")
    val updatedAt: Column<LocalDateTime> = datetime("UPDATED_AT")

    override val primaryKey =
        PrimaryKey(firstColumn = detailId, name = "PK_ASSEMBLY_DETAILS_DETAIL_ID")
}

object FavoriteItems : Table() {
    val id: Column<Int> = integer("ID").autoIncrement()
    val ownerUserId: Column<String> = varchar("OWNER_USER_ID", 36)
    val itemId: Column<Int> = integer("ITEM_ID")
    val createdAt: Column<LocalDateTime> = datetime("CREATED_AT")
    val updatedAt: Column<LocalDateTime> = datetime("UPDATED_AT")

    override val primaryKey = PrimaryKey(firstColumn = id, name = "PK_FAVORITE_ITEMS_ID")
}

object FavoriteMakers : Table() {
    val id: Column<Int> = integer("ID").autoIncrement()
    val ownerUserId: Column<String> = varchar("OWNER_USER_ID", 36)
    val makerId: Column<Int> = integer("MAKER_ID")
    val createdAt: Column<LocalDateTime> = datetime("CREATED_AT")
    val updatedAt: Column<LocalDateTime> = datetime("UPDATED_AT")

    override val primaryKey = PrimaryKey(firstColumn = id, name = "PK_FAVORITE_MAKERS_ID")
}

object FavoriteAssemblies : Table() {
    val id: Column<Int> = integer("ID").autoIncrement()
    val ownerUserId: Column<String> = varchar("OWNER_USER_ID", 36)
    val assemblyId: Column<Int> = integer("ASSEMBLY_ID")
    val createdAt: Column<LocalDateTime> = datetime("CREATED_AT")
    val updatedAt: Column<LocalDateTime> = datetime("UPDATED_AT")

    override val primaryKey = PrimaryKey(firstColumn = id, name = "PK_FAVORITE_ASSEMBLIES_ID")
}

object FavoriteAssembliesView : Table("FAVORITE_ASSEMBLIES_VIEW") {
    val assemblyId: Column<Int> = integer("ASSEMBLY_ID")
    val favoriteCount: Column<Long> = long("FAVORITE_COUNT")
}

object ReferenceAssembliesView : Table("REFERENCE_ASSEMBLIES_VIEW") {
    val assemblyId: Column<Int> = integer("ASSEMBLY_ID")
    val referenceCount: Column<Long> = long("REFERENCE_COUNT")
}
