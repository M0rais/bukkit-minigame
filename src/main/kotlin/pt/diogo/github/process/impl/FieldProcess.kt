package pt.diogo.github.process.impl

import pt.diogo.github.BukkitMiniGame
import pt.diogo.github.dao.DaoProvider
import pt.diogo.github.model.Field
import pt.diogo.github.process.ProcessProvider
import pt.diogo.github.toLocation
import pt.diogo.github.toMap
import pt.diogo.github.toStr

class FieldProcess(private val plugin: BukkitMiniGame) : ProcessProvider<Field> {
    override val dao: DaoProvider<Field> = plugin.fieldDao

    private val config = plugin.fieldConfiguration

    override fun load() {
        for (key in config.getConfigurationSection("fields")!!.getKeys(false)) {
            val path = "fields.$key"

            dao.addByType(
                Field(
                    id = config.getString("$path.id")!!,
                    display = config.getString("$path.display")!!,
                    squares = config.getString("$path.square")!!.toMap(),
                    exitLocation = config.getString("$path.exit")!!.toLocation(),
                    spawnLocation = config.getString("$path.spawn")!!.toLocation()
                )
            )

        }
    }

    override fun delete(id: String) {
        dao.removeByID(id)
        config.set("fields.$id", null)
        config.save()
        config.reload()
    }

    override fun save(t: Field) {
        val path = "fields.${t.id}"

        config.set("$path.id", t.id)
        config.set("$path.display", t.display)
        config.set("$path.square", t.squares.toStr())
        config.set("$path.exit", t.exitLocation.toStr())
        config.set("$path.spawn", t.spawnLocation.toStr())

        config.save()
        config.reload()
    }

}
