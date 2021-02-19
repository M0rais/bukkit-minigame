package pt.diogo.github.dao.impl

import pt.diogo.github.dao.DaoProvider
import pt.diogo.github.model.User

class UserDao : DaoProvider<User> {

    override val cache: MutableMap<String, User>
        get() = mutableMapOf()

    override fun addByType(t: User) {
        cache[t.playerName] = t
    }

    override fun findByID(id: String): User? {
        return cache[id]
    }

    override fun removeByID(id: String) {
        cache.remove(id)
    }
}