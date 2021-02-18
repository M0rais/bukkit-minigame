package pt.diogo.github.dao.impl

import pt.diogo.github.dao.DaoProvider
import pt.diogo.github.model.Prize

class PrizeDao : DaoProvider<Prize> {

    private val prizes = mutableMapOf<String, Prize>()

    override fun addByType(t: Prize) {
        prizes[t.id] = t
    }

    override fun findByID(id: String): Prize? {
        return prizes[id]
    }

    override fun getMap(): MutableMap<String, Prize> {
        return prizes
    }

    override fun removeByID(id: String) {
        prizes.remove(id)
        TODO("Set id to null at prizes.yml")
    }


}