package pt.diogo.github.dao.impl

import pt.diogo.github.dao.DaoProvider
import pt.diogo.github.model.User

class UserDao : DaoProvider<User> {

    private val users = mutableMapOf<String, User>()

    override fun addByType(t: User) {
        users[t.playerName] = t
    }

    override fun findByID(id: String): User? {
        return users[id]
    }

    override fun getMap(): MutableMap<String, User> {
        return users
    }

    override fun removeByID(id: String) {
        users.remove(id)
        TODO("Remove from database")
    }
}