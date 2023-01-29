package entertainer.entertainments.tetris.objects;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TetrisBlock {

    private int row;
    private Location startPosition;
    private HashMap<Integer, Block[][][]> variants = new HashMap<Integer, Block[][][]>();
    private int currentVariant = -1;
    private Location currentLocation;

    public TetrisBlock(Location startPosition, int row) {
        this.startPosition = startPosition;
        this.row = row;
        initialiseVariants();
    }

    public void setCurrentVariant(int currentVariant) {
        this.currentVariant = currentVariant;
    }
    public void setCurrentLocation(Location currentLocation) {
        this.currentLocation = currentLocation;
    }

    public void initialiseVariants(){
        for (int i = 0; i < 4; i++){
            int adder = 12 * (i + 1) - 1;
            if (i == 3)
                adder+=12;
            else if (i == 2)
                adder+=8;
            else if (i == 1)
                adder+=4;

            int adder2 = 12 * i;
            if (i == 3)
                adder2+=12;
            else if (i == 2)
                adder2+=8;
            else if (i == 1)
                adder2+=4;

            Location leftCorner = new Location(startPosition.getWorld(), startPosition.getBlockX() - adder, startPosition.getBlockY(), startPosition.getBlockZ() - (row * 6));
            Location rightCorner = new Location(startPosition.getWorld(), startPosition.getBlockX() - adder2, startPosition.getBlockY() + 11, startPosition.getBlockZ() - 2  - (row * 6));

            Block[][][] blocks = new Block[12][12][3];

            for (int y = leftCorner.getBlockY(); y <= rightCorner.getBlockY(); y++) {
                for (int x = leftCorner.getBlockX(); x <= rightCorner.getBlockX(); x++) {
                    for (int z = leftCorner.getBlockZ(); z >= rightCorner.getBlockZ(); z--) {
                        blocks[x - leftCorner.getBlockX()][y - leftCorner.getBlockY()][Math.abs(z) + leftCorner.getBlockZ()] = leftCorner.getWorld().getBlockAt(x, y, z);
                    }
                }
            }
            variants.put(i, blocks);
        }
    }
    public void place(){
        if (currentVariant == -1)return;
        Block[][][] blocks = variants.get(currentVariant);
        for(int x = 0; x < 12; x++) {
            for(int y = 0; y < 12; y++) {
                for(int z = 0; z < 3; z++) {
                    if (blocks[x][y][z] == null)continue;
                    Block block = currentLocation.clone().add(x, y, z).getBlock();
                    block.setType(blocks[x][y][z].getType());
                    block.setBlockData(blocks[x][y][z].getBlockData());
                }
            }
        }
    }
    public void place(Location location, int variantIndex){
        Block[][][] blocks = variants.get(variantIndex);
        for(int x = 0; x < 12; x++) {
            for(int y = 0; y < 12; y++) {
                for(int z = 0; z < 3; z++) {
                    if (blocks[x][y][z] == null)continue;
                    Block block = location.clone().add(x, y, z).getBlock();
                    block.setType(blocks[x][y][z].getType());
                    block.setBlockData(blocks[x][y][z].getBlockData());
                }
            }
        }
    }
    public boolean canMove(){
        Block[][][] blocks = variants.get(currentVariant);
        Block leftCorner = blocks[0][0][0];
        Block rightCorner = blocks[11][0][2];

        for (int x = leftCorner.getX(); x <= rightCorner.getX(); x++) {
            for (int z = leftCorner.getZ(); z >= rightCorner.getZ(); z--) {
                Location newLoc1 = new Location(currentLocation.getWorld(), x, leftCorner.getY(), z);
                Location newLoc2 = new Location(currentLocation.getWorld(), x, leftCorner.getY() - 1, z);
                if (newLoc1.getBlock().getType() != Material.AIR && newLoc2.getBlock().getType() != Material.AIR)return false;
            }
        }
        return true;
    }
    private List<Block> getBlocks(){
        Block[][][] blocks = variants.get(currentVariant);
        ArrayList<Block> blockList = new ArrayList<>();
        for(int x = 0; x < 12; x++) {
            for(int y = 0; y < 12; y++) {
                for(int z = 0; z < 3; z++) {
                    if (blocks[x][y][z] == null || blocks[x][y][z].getType() == Material.AIR)continue;
                    blockList.add(blocks[x][y][z]);
                }
            }
        }
        return blockList;
    }
    public boolean move(int amount) {
        if (canMove()){
            for (Block block : getBlocks()){
                Location newLoc = block.getLocation().add(0, amount, 0);
                newLoc.getBlock().setType(block.getType());
                newLoc.getBlock().setBlockData(block.getBlockData());
                block.setType(Material.AIR);
            }
            return true;
        }else return false;
    }
}
