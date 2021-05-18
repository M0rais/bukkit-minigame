package pt.diogo.github.manager

import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.scheduler.BukkitTask
import pt.diogo.github.BukkitMiniGame

class GamerManager(private val plugin: BukkitMiniGame, private val gameManager: GameManager) {

    private val prizeDao = plugin.prizeDao
    private val economy = plugin.vaultHook.economy
    private val userService = plugin.userService
    private val startTask = mutableMapOf<Game, BukkitTask>()

    private fun getGameByPlayer(player: Player): Game? = gameManager.games.values.find {
        it.players.contains(player)
    }

    fun getPlayersByGame(game: Game) : MutableList<Player> = game.players

    fun joinGame(player: Player, id: String) {
        val games = gameManager.games
        val game = games[id] ?: return Unit.apply {
            player.sendMessage("§cGame not found, available games:")
            player.sendMessage("§7${games.keys}")
        }

        game.players.add(player)
        player.teleport(game.field.spawnLocation)
        player.sendMessage("You joined the game!")


        if (game.players.size == 2) {
            startTask[game] = Bukkit.getScheduler().runTaskLaterAsynchronously(plugin, Runnable {
                gameManager.startGame(game)
            }, 20 * 30)
        }
    }

    fun quitGame(player: Player) {
        val game = getGameByPlayer(player) ?: return
        game.players.remove(player)
        player.teleport(game.field.exitLocation)
        cancelGame(game)
    }

    private fun cancelGame(game: Game) {
        if (!game.open && game.players.size > 2) return
        startTask[game]!!.cancel()
        startTask.remove(game)
        for (p in game.players) {
            p.sendMessage("§cGame start was cancelled.")
        }
    }

    suspend fun lose(player: Player) {
        val game = getGameByPlayer(player) ?: return
        game.players.remove(player)
        player.spigot().respawn()
        player.teleport(game.field.exitLocation)
        userService.saveUser(player.name, 0.0, game.level)
        givePrize(game)
    }

    private suspend fun givePrize(game: Game) {

        if (game.players.size != 1) return

        val player = game.players[0]

        val prize = prizeDao.getRandomPrize()

        val playerName = player.name

        prize.commands.forEach {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), it.replace("{player}", playerName))
        }

        economy.depositPlayer(player, prize.money)
        userService.saveUser(playerName, if (prize.extraWin) 2.0 else 1.0, game.level)
    }

}