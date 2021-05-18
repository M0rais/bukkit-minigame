package pt.diogo.github.dao

interface DaoProvider<T> {

    val cache: MutableMap<String, T>

    fun addByType(t: T)

    fun findByID(id: String): T?

    fun removeByID(id: String)

}