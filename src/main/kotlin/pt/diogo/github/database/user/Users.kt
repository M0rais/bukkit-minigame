package pt.diogo.github.database.user

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable

class User(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<User>(Users)

    var playerName by Users.playerName
    var wins by Users.wins
    var maxLevel by Users.maxLevel
    var games by Users.games
}

object Users : IntIdTable() {
    val playerName = varchar("playerName", 30)
    val wins = double("wins")
    val maxLevel = integer("maxLevel")
    val games = double("games")

    override val primaryKey = PrimaryKey(playerName)
}