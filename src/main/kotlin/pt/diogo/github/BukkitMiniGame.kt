package pt.diogo.github

import org.bukkit.plugin.java.JavaPlugin
import pt.diogo.github.config.Configuration
import pt.diogo.github.dao.impl.*
import pt.diogo.github.process.impl.FieldProcess
import pt.diogo.github.process.impl.PrizeProcess

class BukkitMiniGame : JavaPlugin() {

    val prizeDao = PrizeDao()
    val fieldDao = FieldDao()
    val userDao = UserDao()

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

}