package pt.diogo.github.process.impl

import org.bukkit.util.Vector
import pt.diogo.github.BukkitMiniGame
import pt.diogo.github.dao.DaoProvider
import pt.diogo.github.model.Cuboid
import pt.diogo.github.model.Field
import pt.diogo.github.model.Square
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
                    display = config.getString("$path.display")!!,
                    square = config.getString("$path.square")!!.toSquare(),
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
        config.set("$path.square", t.square.toStr())
        config.set("$path.prize", t.prize.id)

        config.save()
        config.reload()
    }

    private fun Square.toStr(): String {
        return "${this.yellowSquare.toStr()};${this.greenSquare.toStr()};${this.blueSquare.toStr()};${this.redSquare.toStr()}"
    }

    private fun String.toSquare(): Square {
        val (yellowSquare, greenSquare, blueSquare, redSquare) = this.split(";")

        return Square(
            yellowSquare = yellowSquare.toCuboid(),
            greenSquare = greenSquare.toCuboid(),
            blueSquare = blueSquare.toCuboid(),
            redSquare = redSquare.toCuboid()
        )
    }

    private fun Vector.toStr(): String {
        return "${this.blockX}:${this.blockY}:${this.blockZ}"
    }

    private fun String.toVector(): Vector {
        val (x, y, z) = this.split(":")
        return Vector(x.toDouble(), y.toDouble(), z.toDouble())
    }

    private fun Cuboid.toStr(): String {
        return "${this.min.toStr()}:${this.max.toStr()}:${this.world}"
    }

    private fun String.toCuboid(): Cuboid {
        val (min, max, world) = this.split(":")
        return Cuboid(
            min.toVector(),
            max.toVector(),
            world
        )
    }

}
