package bbee.developer.jp.assemble_pc.database

import bbee.developer.jp.assemble_pc.database.entity.Samples
import bbee.developer.jp.assemble_pc.database.entity.TABLES
import com.varabyte.kobweb.api.data.add
import com.varabyte.kobweb.api.init.InitApi
import com.varabyte.kobweb.api.init.InitApiContext
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
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

class H2DB(private val context: InitApiContext) {
    val database = Database.connect(
        url = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;",
        driver = "org.h2.Driver",
        user = "root",
        password = "",
    )
}
