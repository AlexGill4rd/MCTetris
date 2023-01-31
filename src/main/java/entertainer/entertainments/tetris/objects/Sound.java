package entertainer.entertainments.tetris.objects;

import entertainer.entertainments.Entertainments;
import entertainer.entertainments.tetris.enums.TetrisDirection;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class Sound {
    private Player player;
    private String path;
    private float volume;
    private boolean enabled = false;

    private final Entertainments plugin = Entertainments.getPlugin(Entertainments.class);

    public Sound(Player player, String path, float volume){
        this.player = player;
        this.path = path;
        this.volume = volume;
    }

    public BukkitTask blockLoopTask = null;
    /**
     * @param musicLength Time in milliseconds of the music length
     */
    public void setLoop(int musicLength){
        int ticks = (musicLength/1000)*20;

        blockLoopTask = new BukkitRunnable() {
            @Override
            public void run() {
                if (enabled)
                    play();
            }
        }.runTaskTimer(plugin, ticks, ticks);
    }

    public Player getPlayer() {
        return player;
    }

    public String getPath() {
        return path;
    }
    public void setPath(String path) {
        this.path = path;
    }

    public float getVolume() {
        return volume;
    }
    public void setVolume(float volume) {
        this.volume = volume;
    }

    public void play(){
        enabled = true;
        player.playSound(player.getLocation(), path, volume, 1);
    }
    public void stop(){
        if (blockLoopTask != null)
            blockLoopTask.cancel();
        enabled = false;
        player.stopSound(path);
    }
}
