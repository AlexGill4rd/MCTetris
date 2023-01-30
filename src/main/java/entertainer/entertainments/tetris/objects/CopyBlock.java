package entertainer.entertainments.tetris.objects;

import org.bukkit.Material;
import org.bukkit.block.data.BlockData;

public class CopyBlock {

    private int x;
    private int y;
    private int z;
    private Material material;
    private BlockData blockData;

    public CopyBlock(int x, int y, int z, Material material, BlockData blockData) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.material = material;
        this.blockData = blockData;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getZ() {
        return z;
    }

    public void setZ(int z) {
        this.z = z;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public BlockData getBlockData() {
        return blockData;
    }

    public void setBlockData(BlockData blockData) {
        this.blockData = blockData;
    }
}
