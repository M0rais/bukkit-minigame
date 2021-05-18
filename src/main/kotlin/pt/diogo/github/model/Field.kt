package pt.diogo.github.model

import org.bukkit.Location
import org.bukkit.util.Vector

data class Cuboid(
    val min: Vector,
    val max: Vector,
    val world: String
)

data class Field(
    val id: String,
    val display: String,
    var squares: Map<String, Cuboid>,
    val spawnLocation: Location,
    val exitLocation: Location
)

data class Prize(
    val id: String,
    val chance: Double,
    val commands: List<String>,
    val money: Double,
    val extraWin: Boolean
)