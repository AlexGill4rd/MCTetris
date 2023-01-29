package entertainer.entertainments.tetris.events;

import entertainer.entertainments.tetris.objects.TetrisBlock;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class TetrisBlockCollideEvent extends Event {

    private TetrisBlock tetrisBlock;
    private static final HandlerList HANDLERS_LIST = new HandlerList();

    public TetrisBlockCollideEvent(TetrisBlock tetrisBlock){
        this.tetrisBlock = tetrisBlock;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS_LIST;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS_LIST;
    }

    public TetrisBlock getTetrisBlock() {
        return tetrisBlock;
    }

    public void setTetrisBlock(TetrisBlock tetrisBlock) {
        this.tetrisBlock = tetrisBlock;
    }
}
