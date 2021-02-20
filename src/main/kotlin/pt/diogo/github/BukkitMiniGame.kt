package pt.diogo.github

import org.bukkit.plugin.java.JavaPlugin
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import pt.diogo.github.config.Configuration
import pt.diogo.github.dao.impl.*
import pt.diogo.github.database.Hikari
import pt.diogo.github.database.user.Users
import pt.diogo.github.process.impl.FieldProcess
import pt.diogo.github.process.impl.PrizeProcess

class BukkitMiniGame : JavaPlugin() {

    val prizeDao = PrizeDao()
    val fieldDao = FieldDao()

    val prizeConfiguration = Configuration(this, "prizes.yml")
    val fieldConfiguration = Configuration(this, "fields.yml")

    lateinit var prizeProcess: PrizeProcess
    lateinit var fieldProcess: FieldProcess

    override fun onEnable() {
        saveDefaultConfig()
        loadProcess()
    }

    private fun loadProcess() {
        prizeProcess = PrizeProcess(this)
        fieldProcess = FieldProcess(this)
    }

    private fun loadDatabase() {
        val hikari = Hikari(
            config.getString("mysql.host")!!,
            config.getString("mysql.port")!!,
            config.getString("mysql.database")!!,
            config.getString("mysql.user")!!,
            config.getString("mysql.password")!!
        )

        Database.connect(hikari.source)

        transaction {
            SchemaUtils.create(Users)
        }

    }

}