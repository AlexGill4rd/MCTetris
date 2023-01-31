package entertainer.entertainments.tetris.listeners;

import entertainer.entertainments.tetris.objects.TetrisPlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import static entertainer.entertainments.Entertainments.tetrisPlayers;

public class PlayerQuitDataSaveListener implements Listener {

    @EventHandler
    public void onQuit(PlayerQuitEvent e){

        TetrisPlayer tetrisPlayer = tetrisPlayers.get(e.getPlayer().getUniqueId());
        if (tetrisPlayer != null)
            tetrisPlayer.savePlayerData();

    }
}
