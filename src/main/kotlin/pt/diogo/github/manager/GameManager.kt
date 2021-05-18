package pt.diogo.github.manager

import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.scheduler.BukkitTask
import pt.diogo.github.BukkitMiniGame
import pt.diogo.github.model.Field
import kotlin.random.Random

data class Game(
    val id: String,
    val field: Field,
    var task: BukkitTask? = null,
    var level: Int = 10,
    val players: MutableList<Player> = mutableListOf(),
    var open: Boolean = true
)

class GameManager(private val plugin: BukkitMiniGame) {

    val worldEditHook = plugin.worldEditHook
    val gamerManager = GamerManager(plugin, this)
    val games = mutableMapOf<String, Game>()

    fun createGame(field: Field) {
        val id = field.id
        games[id] = Game(id, field)
        Bukkit.broadcastMessage("§a${field.id} is now open!")
    }

    private fun shuffleColor(game: Game) {
        if (Random.nextInt(0, 100) <= 20) {
            game.field.squares =
                game.field.squares.map { it.key to it.value }.shuffled().toMap()
            game.players.forEach {
                it.sendMessage("§aCOLOR SHUFFLE UHU")
            }
        }
    }

    private fun getRandomColor(game: Game): String = game.field.squares.keys.random()

    private fun getColor(color: String): String = when (color) {
        "yellow" -> "§eRUN TO YELLOW"
        "blue" -> "§bRUN TO BLUE"
        "red" -> "§cRUN TO RED"
        else -> "§aRUN TO GREEN"
    }

    fun startGame(game: Game) {

        var time = game.level
        var wait = 3
        var color = getRandomColor(game)




        game.task = Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, Runnable {

            if (time != 0) {
            }

            if (wait == 0) {

            }


        }, 20, 20)

    }

    private fun removeFloor(game: Game, color: String) {
        game.players.forEach {
            it.sendTitle("§aFLOOR REMOVED", "§7CYA NERDS")
        }
        setAir(game, color)
    }

    private fun setAir(game: Game, color: String) {
        game.field.squares.forEach {
            if (!it.key.equals(color)) worldEditHook.setBlocksByCuboid(it.value, Material.AIR)
        }
    }

    private fun setWool(game: Game) {
        game.field.squares.forEach {
            worldEditHook.setBlocksByCuboid(it.value, Material.getMaterial("${it.key.toUpperCase()}_WOOL")!!)
        }
    }

    private fun setFloor(game: Game) {
        shuffleColor(game)
        setWool(game)
    }

}