package pt.diogo.github.process.impl

import pt.diogo.github.BukkitMiniGame
import pt.diogo.github.dao.DaoProvider
import pt.diogo.github.model.Prize
import pt.diogo.github.process.ProcessProvider

class PrizeProcess(private val plugin: BukkitMiniGame) : ProcessProvider<Prize> {

    override val dao: DaoProvider<Prize> = plugin.prizeDao
    private val config = plugin.prizeConfiguration

    override fun load() {

        for (key in config.getConfigurationSection("prizes")!!.getKeys(false)) {

            val path = "prizes.$key"

            dao.addByType(
                Prize(
                    id = config.getString("$path.id")!!,
                    chance = config.getDouble("$path.chance"),
                    money = config.getDouble("$path.money"),
                    extraWin = config.getBoolean("$path.extra-win"),
                    commands = config.getStringList("$path.commands")
                )
            )
        }

    }

    override fun delete(id: String) {
        dao.removeByID(id)
        config.set("prizes.$id", null)
        config.save()
        config.reload()
    }

    override fun save(t: Prize) {
        dao.addByType(t)

        val path = "prizes.${t.id}"

        config.set("$path.id", t.id)
        config.set("$path.chance", t.chance)
        config.set("$path.money", t.money)
        config.set("$path.extra-win", t.extraWin)
        config.set("$path.commands", t.commands)

        config.save()
        config.reload()
    }


}