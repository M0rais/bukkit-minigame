package pt.diogo.github.dao.impl

import pt.diogo.github.dao.DaoProvider
import pt.diogo.github.model.Field


class FieldDao : DaoProvider<Field> {

    override val cache: MutableMap<String, Field>
        get() = mutableMapOf()

    override fun addByType(t: Field) {
        cache[t.id] = t
    }

    override fun findByID(id: String): Field? {
        return cache[id]
    }

    override fun removeByID(id: String) {
        cache.remove(id)
    }

}