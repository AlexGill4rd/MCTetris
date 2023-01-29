package entertainer.entertainments.tetris.events;

import entertainer.entertainments.tetris.objects.TetrisBoard;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class TetrisGameEndEvent extends Event {

    private TetrisBoard tetrisBoard;
    private static final HandlerList HANDLERS_LIST = new HandlerList();

    public TetrisGameEndEvent(TetrisBoard tetrisBoard){
        this.tetrisBoard = tetrisBoard;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS_LIST;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS_LIST;
    }

    public TetrisBoard getTetrisBoard() {
        return tetrisBoard;
    }
    public void setTetrisBoard(TetrisBoard tetrisBoard) {
        this.tetrisBoard = tetrisBoard;
    }
}
