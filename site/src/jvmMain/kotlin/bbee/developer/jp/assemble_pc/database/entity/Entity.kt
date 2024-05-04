package bbee.developer.jp.assemble_pc.database.entity

import kotlinx.datetime.LocalDateTime
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.kotlin.datetime.datetime

val TABLES = arrayOf(
    Samples,
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

object Samples : Table() {
    val uuid: Column<String> = varchar("userId", 36)
    val name: Column<String> = varchar("userName", 10)

    override val primaryKey = PrimaryKey(firstColumn = uuid, name = "PK_Samples_uuid")
}

object Users : Table() {
    val userId: Column<String> = varchar("userId", 36)
    val userName: Column<String?> = varchar("userName", 100).nullable()
    val userEmail: Column<String?> = varchar("userEmail", 100).nullable()
    val password: Column<String?> = varchar("password", 255).nullable()
    val createdAt: Column<LocalDateTime> = datetime("createdAt")
    val updatedAt: Column<LocalDateTime> = datetime("updatedAt")

    override val primaryKey = PrimaryKey(firstColumn = userId, name = "PK_Users_userId")
}

object Items : Table() {
    val itemId: Column<Int> = integer("itemId").autoIncrement()
    val itemCategoryId: Column<Int> = integer("itemCategoryId")
    val makerId: Column<Int> = integer("makerId")
    val itemName: Column<String> = varchar("itemName", 255)
    val linkUrl: Column<String> = varchar("linkUrl", 255)
    val imageUrl: Column<String> = varchar("imageUrl", 255)
    val desc: Column<String> = varchar("desc", 4095)
    val price: Column<Int> = integer("price")
    val rank: Column<Int> = integer("rank")
    val flag1: Column<Int> = integer("flag1")
    val flag2: Column<Int> = integer("flag2")
    val releaseDate: Column<String> = varchar("releaseDate", 8)
    val outdated: Column<Boolean> = bool("outdated")
    val createdAt: Column<LocalDateTime> = datetime("createdAt")
    val updatedAt: Column<LocalDateTime> = datetime("updatedAt")

    override val primaryKey = PrimaryKey(firstColumn = itemId, name = "PK_Items_itemId")
}

object ItemCategories : Table() {
    val itemCategoryId: Column<Int> = integer("itemCategoryId").autoIncrement()
    val categoryName: Column<String> = varchar("categoryName", 20)
    val isSingleChoice: Column<Boolean> = bool("isSingleChoice")
    val createdAt: Column<LocalDateTime> = datetime("createdAt")
    val updatedAt: Column<LocalDateTime> = datetime("updatedAt")

    override val primaryKey =
        PrimaryKey(firstColumn = itemCategoryId, name = "PK_ItemCategories_itemCategoryId")
}

object Makers : Table() {
    val makerId: Column<Int> = integer("makerId").autoIncrement()
    val makerName: Column<String> = varchar("makerName", 50)
    val makerFullName: Column<String> = varchar("makerFullName", 100)
    val country: Column<String> = varchar("country", 50)
    val makerUrl: Column<String> = varchar("makerUrl", 255)
    val logoUrl: Column<String> = varchar("logoUrl", 255)
    val createdAt: Column<LocalDateTime> = datetime("createdAt")
    val updatedAt: Column<LocalDateTime> = datetime("updatedAt")

    override val primaryKey = PrimaryKey(firstColumn = makerId, name = "PK_Makers_makerId")
}

object Assemblies : Table() {
    val assemblyId: Column<Int> = integer("assemblyId").autoIncrement()
    val ownerUserId: Column<String> = varchar("ownerUserId", 36)
    val assemblyName: Column<String> = varchar("assemblyName", 100)
    val assemblyUrl: Column<String> = varchar("assemblyUrl", 255)
    val ownerComment: Column<String> = varchar("makerUrl", 511)
    val referenceAssemblyId: Column<Int?> = integer("referenceAssemblyId").nullable()
    val published: Column<Boolean> = bool("published")
    val createdAt: Column<LocalDateTime> = datetime("createdAt")
    val updatedAt: Column<LocalDateTime> = datetime("updatedAt")

    override val primaryKey =
        PrimaryKey(firstColumn = assemblyId, name = "PK_Assemblies_assemblyId")
}

object AssemblyDetails : Table() {
    val detailId: Column<Int> = integer("detailId").autoIncrement()
    val ownerUserId: Column<String> = varchar("ownerUserId", 36)
    val assemblyId: Column<Int> = integer("assemblyId")
    val itemId: Column<Int> = integer("itemId")
    val priceAtRegistered: Column<Int> = integer("priceAtRegistered")
    val createdAt: Column<LocalDateTime> = datetime("createdAt")
    val updatedAt: Column<LocalDateTime> = datetime("updatedAt")

    override val primaryKey =
        PrimaryKey(firstColumn = detailId, name = "PK_AssemblyDetails_detailId")
}

object FavoriteItems : Table() {
    val id: Column<Int> = integer("id").autoIncrement()
    val ownerUserId: Column<String> = varchar("ownerUserId", 36)
    val itemId: Column<Int> = integer("itemId")
    val createdAt: Column<LocalDateTime> = datetime("createdAt")
    val updatedAt: Column<LocalDateTime> = datetime("updatedAt")

    override val primaryKey = PrimaryKey(firstColumn = id, name = "PK_FavoriteItems_id")
}

object FavoriteMakers : Table() {
    val id: Column<Int> = integer("id").autoIncrement()
    val ownerUserId: Column<String> = varchar("ownerUserId", 36)
    val makerId: Column<Int> = integer("makerId")
    val createdAt: Column<LocalDateTime> = datetime("createdAt")
    val updatedAt: Column<LocalDateTime> = datetime("updatedAt")

    override val primaryKey = PrimaryKey(firstColumn = id, name = "PK_FavoriteMakers_id")
}

object FavoriteAssemblies : Table() {
    val id: Column<Int> = integer("id").autoIncrement()
    val ownerUserId: Column<String> = varchar("ownerUserId", 36)
    val assemblyId: Column<Int> = integer("assemblyId")
    val createdAt: Column<LocalDateTime> = datetime("createdAt")
    val updatedAt: Column<LocalDateTime> = datetime("updatedAt")

    override val primaryKey = PrimaryKey(firstColumn = id, name = "PK_FavoriteAssemblies_id")
}