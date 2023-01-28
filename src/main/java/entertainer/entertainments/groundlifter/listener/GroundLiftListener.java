package entertainer.entertainments.groundlifter.listener;

import entertainer.entertainments.Entertainments;
import entertainer.entertainments.functions.Functions;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.FallingBlock;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

import static entertainer.entertainments.Entertainments.featureManager;

public class GroundLiftListener implements Listener {

    Entertainments plugin = Entertainments.getPlugin(Entertainments.class);

    private ArrayList<Location> location = new ArrayList<>();

    Location previous = null;

    int runnable = 0;
    @EventHandler
    public void onBowShoot(ProjectileLaunchEvent e){
        if (!featureManager.groundLifter)return;
        location = new ArrayList<>();

        runnable = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, () -> {
            if (e.getEntity().isOnGround())
                Bukkit.getScheduler().cancelTask(runnable);
            else{
                if (previous != null){
                    for (Location location2 : getLineBetweenPoints(previous, e.getLocation(), 5)){
                        //location2.getWorld().getBlockAt(location2).setType(Material.GOLD_BLOCK);
                        Location newLoc = getGroundLocation(location2);
                        FallingBlock fallingBlock = newLoc.getWorld().spawnFallingBlock(newLoc, newLoc.getBlock().getBlockData());
                        fallingBlock.setVelocity(new Vector(new Random().nextDouble(), 1, new Random().nextDouble()));
                        fallingBlock.setDropItem(false);
                        fallingBlock.setFallDistance(0);
                        newLoc.getBlock().setType(Material.AIR);
                    }
                }
                previous = e.getLocation();
            }
        }, 0, 1);

    }
    public Location getGroundLocation(Location location){
        for (int i = location.getBlockY(); i > 0; i--){
            Location locationNew = new Location(location.getWorld(), location.getBlockX(), i, location.getBlockZ());
            if (location.getWorld().getBlockAt(locationNew).getType() != Material.AIR){
                return locationNew;
            }
        }
        return location;
    }
    public static HashSet<Location> getLineBetweenPoints(Location point1, Location point2, int pointsInLine){
        double p1X = point1.getX();
        double p1Y = point1.getY();
        double p1Z = point1.getZ();
        double p2X = point2.getX();
        double p2Y = point2.getY();
        double p2Z = point2.getZ();

        double lineAveX = (p2X-p1X)/pointsInLine;
        double lineAveY = (p2Y-p1Y)/pointsInLine;
        double lineAveZ = (p2Z-p1Z)/pointsInLine;

        World world = point1.getWorld();
        HashSet<Location> line = new HashSet<>();
        for(int i = 0; i <= pointsInLine; i++){
            Location loc = new Location(world, p1X + lineAveX * i, p1Y + lineAveY * i, p1Z + lineAveZ * i);
            line.add(loc);
        }
        return line;
    }
}
