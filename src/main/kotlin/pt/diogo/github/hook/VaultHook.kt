package pt.diogo.github.hook

import net.milkbowl.vault.economy.Economy
import org.bukkit.Bukkit


class VaultHook {

    val economy: Economy

    init {
        economy = Bukkit.getServer().servicesManager.getRegistration(Economy::class.java)!!.provider
    }

}