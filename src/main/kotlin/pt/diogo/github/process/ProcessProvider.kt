package pt.diogo.github.process

import pt.diogo.github.dao.DaoProvider

interface ProcessProvider<T> {

    val dao: DaoProvider<T>

    fun load()

    fun delete(id: String)

    fun save(t: T)

}