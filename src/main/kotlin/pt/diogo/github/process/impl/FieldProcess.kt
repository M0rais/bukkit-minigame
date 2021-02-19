package pt.diogo.github.process.impl

import pt.diogo.github.BukkitMiniGame
import pt.diogo.github.dao.DaoProvider
import pt.diogo.github.model.Cuboid
import pt.diogo.github.model.Field
import pt.diogo.github.process.ProcessProvider

class FieldProcess(private val plugin: BukkitMiniGame) : ProcessProvider<Field> {
    override val dao: DaoProvider<Field>
        get() = plugin.fieldDao
    private val prizeDao = plugin.prizeDao

    private val config = plugin.fieldConfiguration

    override fun load() {
        for (key in config.getConfigurationSection("fields")!!.getKeys(false)) {
            val path = "fields.$key"

            dao.addByType(
                Field(
                    id = config.getString("$path.id")!!,
                    display = config.getString("$path.id")!!,
                    cuboid = config.getString("$path.id")!!.toCuboid(),
                    prize = prizeDao.findByID("$path.prize")!!
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
        config.set("$path.cuboid", t.cuboid.toStr())
        config.set("$path.prize", t.prize.id)

        config.save()
        config.reload()
    }

    private fun Cuboid.toStr(): String {
        return "${this.minX}:${this.maxX}:${this.minZ}:${this.maxZ}:${this.y}:${this.world}"
    }

    private fun String.toCuboid(): Cuboid {
        val (minX, maxX, minZ, maxZ, y) = this.split(":")
        return Cuboid(
            minX.toDouble(),
            maxX.toDouble(),
            minZ.toDouble(),
            maxZ.toDouble(),
            y.toDouble(),
            this.split(":")[5]
        )
    }

}
