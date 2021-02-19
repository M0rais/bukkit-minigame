package pt.diogo.github.config

import org.bukkit.configuration.file.YamlConfiguration
import pt.diogo.github.BukkitMiniGame
import java.io.File
import java.io.IOException

class Configuration(plugin: BukkitMiniGame, name: String) : YamlConfiguration() {

    private val file: File = File(plugin.dataFolder, name)

    init {
        if (!file.exists()) plugin.saveResource(name, false)
        try {
            this.load(file)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    fun reload() {
        try {
            this.load(file)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    fun save() {
        try {
            this.save(file)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

}