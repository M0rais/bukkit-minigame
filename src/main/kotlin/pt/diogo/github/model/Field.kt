package pt.diogo.github.model

data class Cuboid(val minX: Double, val maxX: Double, val minZ: Double, val maxZ: Double, val y: Double, val world: String)

data class Field(val id: String, val display: String, val cuboid: Cuboid, val prize: Prize)

data class Prize(val id: String, val chance: Double, val commands: List<String>, val money: Double, val extraWin: Boolean)