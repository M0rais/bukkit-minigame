package pt.diogo.github.database.user

import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction


class UserService {

    //TODO: Everything

    suspend fun findUserById(id: String): User? = newSuspendedTransaction {
        User.find { Users.playerName eq id }.singleOrNull()
    }

    suspend fun saveUser(user: User) = newSuspendedTransaction {
        if (findUserById(user.playerName) != null) {

        }
    }

    suspend fun updatePlayer() = newSuspendedTransaction {

    }

}