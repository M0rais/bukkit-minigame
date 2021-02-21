package pt.diogo.github.hook

import com.boydti.fawe.util.EditSessionBuilder
import com.sk89q.worldedit.MaxChangedBlocksException
import com.sk89q.worldedit.bukkit.BukkitAdapter
import com.sk89q.worldedit.bukkit.WorldEditPlugin
import com.sk89q.worldedit.function.pattern.Pattern
import com.sk89q.worldedit.function.pattern.RandomPattern
import com.sk89q.worldedit.regions.CuboidRegion
import com.sk89q.worldedit.regions.Region
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.entity.Player
import pt.diogo.github.model.Cuboid

class WorldEditHook {

    private val we: WorldEditPlugin = Bukkit.getServer().pluginManager.getPlugin("WorldEdit") as WorldEditPlugin

    fun getRegionByPlayer(player: Player) : Region {
        val bPlayer = BukkitAdapter.adapt(player)
        return we.worldEdit.sessionManager.get(bPlayer).getRegionSelector(bPlayer.world).region
    }

    fun setBlocksByCuboid(cuboid: Cuboid, material: Material) {
        val minVector = cuboid.min
        val maxVector = cuboid.max
        val world = Bukkit.getWorld(cuboid.world)

        val min = BukkitAdapter.adapt(Location(world, minVector.x, minVector.y, minVector.z))
        val max = BukkitAdapter.adapt(Location(world, maxVector.x, maxVector.y, maxVector.z))

        val session = EditSessionBuilder(BukkitAdapter.adapt(world)).fastmode(true).build()
        val region = CuboidRegion(min.toBlockPoint(), max.toBlockPoint()) as Region

        val randomPattern = RandomPattern()
        randomPattern.add(BukkitAdapter.adapt(material.createBlockData()) as Pattern, 1.0)

        try {
            session.setBlocks(region, randomPattern as Pattern)
            session.flushQueue()
        } catch (e: MaxChangedBlocksException) {
            e.printStackTrace()
        }
    }

}

