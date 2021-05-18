package pt.diogo.github.database.user

import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

class UserService {

    suspend fun findUserById(id: String): User? = newSuspendedTransaction {
        User.find { Users.playerName eq id }.singleOrNull()
    }

    suspend fun saveUser(id: String, win: Double, level: Int) = newSuspendedTransaction {
        val user = findUserById(id) ?: return@newSuspendedTransaction Unit.apply {
            User.new {
                playerName = id
                wins = win
                maxLevel = level
                games = 1.0
            }
        }

        user.wins += win
        if (level > user.maxLevel) user.maxLevel = level
        user.games += 1
    }

}