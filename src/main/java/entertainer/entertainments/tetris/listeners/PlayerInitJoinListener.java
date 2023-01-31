package entertainer.entertainments.tetris.listeners;

import entertainer.entertainments.tetris.objects.TetrisPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import static entertainer.entertainments.Entertainments.tetrisPlayers;

public class PlayerInitJoinListener implements Listener {


    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e){

        Player player = e.getPlayer();

        TetrisPlayer tetrisPlayer = new TetrisPlayer(player);

        tetrisPlayers.put(player.getUniqueId(), tetrisPlayer);
    }
}
