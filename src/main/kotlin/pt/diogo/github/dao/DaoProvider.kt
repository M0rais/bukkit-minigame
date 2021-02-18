package pt.diogo.github.dao

interface DaoProvider<T> {

    fun addByType(t: T)

    fun findByID(id: String) : T?

    fun getMap() : MutableMap<String, T>

    fun removeByID(id: String)

}