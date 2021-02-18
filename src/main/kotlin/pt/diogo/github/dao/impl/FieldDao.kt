package pt.diogo.github.dao.impl

import pt.diogo.github.dao.DaoProvider
import pt.diogo.github.model.Field


class FieldDao : DaoProvider<Field> {

    private val fields = mutableMapOf<String, Field>()

    override fun addByType(t: Field) {
        fields[t.id] = t
        save(t)
    }

    override fun findByID(id: String): Field? {
        return fields[id]
    }

    override fun getMap(): MutableMap<String, Field> {
        return fields
    }

    override fun removeByID(id: String) {
        fields.remove(id)
        TODO("Set id to null at fields.yml")
    }

    private fun save(field: Field) {
        TODO("Save inside fields.yml")
    }

}