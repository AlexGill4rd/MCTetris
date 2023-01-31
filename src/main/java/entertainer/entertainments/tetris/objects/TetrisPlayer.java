package entertainer.entertainments.tetris.objects;

import org.bukkit.entity.Player;

public class TetrisPlayer {

    private Player player;
    private TetrisStats tetrisStats;

    public TetrisPlayer(Player player) {
        this.player = player;
        tetrisStats = new TetrisStats(player);
    }

    public TetrisStats getTetrisStats() {
        return tetrisStats;
    }

    public Player getPlayer() {
        return player;
    }
    public void setPlayer(Player player) {
        this.player = player;
    }
}
