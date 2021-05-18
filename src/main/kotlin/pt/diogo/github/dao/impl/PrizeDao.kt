package pt.diogo.github.dao.impl

import pt.diogo.github.dao.DaoProvider
import pt.diogo.github.model.Prize
import java.util.*

class PrizeDao : DaoProvider<Prize> {

    override val cache: MutableMap<String, Prize> = mutableMapOf()

    override fun addByType(t: Prize) {
        cache[t.id] = t
    }

    override fun findByID(id: String): Prize? = cache[id]

    override fun removeByID(id: String) {
        cache.remove(id)
    }

    fun getRandomPrize(): Prize {
        val randomCollection = RandomCollection<Prize>()

        cache.values.forEach {
            randomCollection.add(it.chance, it)
        }

        return randomCollection.next()
    }

}

class RandomCollection<E> {

    private val map: NavigableMap<Double, E> = TreeMap()
    private val random = Random()
    private var total = 0.0

    fun add(weight: Double, result: E): RandomCollection<E> {
        if (weight <= 0) return this

        total += weight
        map[total] = result
        return this
    }

    fun next(): E {
        val value = random.nextDouble() * total
        return map.higherEntry(value).value
    }

}