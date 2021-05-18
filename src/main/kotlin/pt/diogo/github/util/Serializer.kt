package pt.diogo.github

import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.util.Vector
import pt.diogo.github.model.Cuboid


fun Map<String, Cuboid>.toStr(): String {
    var s = ""

    var i = 0
    this.forEach { (t, u) ->
        s += "$t:${u.toStr()}"
        if (i != this.size) s += ";"
        i++
    }

    return s
}

fun String.toMap(): Map<String, Cuboid> {
    val split = this.split(";")

    val map = mutableMapOf<String, Cuboid>()

    for (i in 0..4) {
        val (color, cuboid) = split[i].split(":")
        map[color] = cuboid.toCuboid()
    }

    return map
}

fun Location.toStr(): String {
    return "${this.world.name}:${this.toVector().toStr()}"
}

fun String.toLocation(): Location {
    val (world, vector) = this.split(":")

    val v = vector.toVector()

    return Location(Bukkit.getWorld(world), v.x, v.y, v.z)
}

fun Vector.toStr(): String {
    return "${this.blockX}:${this.blockY}:${this.blockZ}"
}

fun String.toVector(): Vector {
    val (x, y, z) = this.split(":")
    return Vector(x.toDouble(), y.toDouble(), z.toDouble())
}

fun Cuboid.toStr(): String {
    return "${this.min.toStr()}:${this.max.toStr()}:${this.world}"
}

fun String.toCuboid(): Cuboid {
    val (min, max, world) = this.split(":")
    return Cuboid(
        min.toVector(),
        max.toVector(),
        world
    )
}