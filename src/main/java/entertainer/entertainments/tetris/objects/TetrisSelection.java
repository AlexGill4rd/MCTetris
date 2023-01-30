package entertainer.entertainments.tetris.objects;

import org.bukkit.Location;

public class TetrisSelection {

    private Location leftCorner;
    private Location rightCorner;

    public TetrisSelection() {
    }

    public Location getLeftCorner() {
        return leftCorner;
    }

    public void setLeftCorner(Location leftCorner) {
        this.leftCorner = leftCorner;
    }

    public Location getRightCorner() {
        return rightCorner;
    }

    public void setRightCorner(Location rightCorner) {
        this.rightCorner = rightCorner;
    }
}
