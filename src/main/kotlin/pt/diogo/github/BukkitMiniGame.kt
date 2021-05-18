package pt.diogo.github

import org.bukkit.plugin.java.JavaPlugin
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import pt.diogo.github.config.Configuration
import pt.diogo.github.dao.impl.FieldDao
import pt.diogo.github.dao.impl.PrizeDao
import pt.diogo.github.database.Hikari
import pt.diogo.github.database.user.UserService
import pt.diogo.github.database.user.Users
import pt.diogo.github.hook.VaultHook
import pt.diogo.github.hook.WorldEditHook
import pt.diogo.github.process.impl.FieldProcess
import pt.diogo.github.process.impl.PrizeProcess

class BukkitMiniGame : JavaPlugin() {

    val prizeDao = PrizeDao()
    val fieldDao = FieldDao()

    val userService = UserService()

    val prizeConfiguration = Configuration(this, "prizes.yml")
    val fieldConfiguration = Configuration(this, "fields.yml")

    lateinit var prizeProcess: PrizeProcess
    lateinit var fieldProcess: FieldProcess
    lateinit var vaultHook: VaultHook
    lateinit var worldEditHook: WorldEditHook


    override fun onEnable() {
        loadHook()
        saveDefaultConfig()
        loadDatabase()
        loadProcess()
    }

    private fun loadHook() {
        vaultHook = VaultHook()
        worldEditHook = WorldEditHook()
    }

    private fun loadProcess() {
        prizeProcess = PrizeProcess(this)
        prizeProcess.load()
        fieldProcess = FieldProcess(this)
        fieldProcess.load()
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