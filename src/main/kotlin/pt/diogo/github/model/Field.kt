package pt.diogo.github.model

data class Cuboid(val minX: Double, val maxX: Double, val minZ: Double, val maxZ: Double, val y: Double)

data class Field(val id: String, val display: String, val cuboid: Cuboid, val prizes: Prize)

data class Prize(val id: String, val commands: List<String>, val money: Double, val extraWin: Boolean)