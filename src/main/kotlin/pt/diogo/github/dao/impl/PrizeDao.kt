package pt.diogo.github.dao.impl

import pt.diogo.github.dao.DaoProvider
import pt.diogo.github.model.Prize

class PrizeDao : DaoProvider<Prize> {

    override val cache: MutableMap<String, Prize>
        get() = mutableMapOf()

    override fun addByType(t: Prize) {
        cache[t.id] = t
    }

    override fun findByID(id: String): Prize? {
        return cache[id]
    }

    override fun removeByID(id: String) {
        cache.remove(id)
    }


}