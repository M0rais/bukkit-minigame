package pt.diogo.github.model

import org.bukkit.util.Vector

data class Cuboid(val min: Vector, val max: Vector, val world: String)

data class Square(val yellowSquare: Cuboid, val greenSquare: Cuboid, val blueSquare: Cuboid, val redSquare: Cuboid)

data class Field(val id: String, val display: String, val square: Square, val prize: Prize)

data class Prize(val id: String, val chance: Double, val commands: List<String>, val money: Double, val extraWin: Boolean)