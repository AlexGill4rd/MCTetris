package entertainer.entertainments.tetris.objects;

import entertainer.entertainments.tetris.enums.TetrisDirection;
import entertainer.entertainments.tetris.events.TetrisBlockCollideEvent;
import entertainer.entertainments.tetris.events.TetrisGameEndEvent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.HashMap;

import static entertainer.entertainments.tetris.listeners.TetrisZoneSeletionListener.tetrisBoard;

public class TetrisBlock {

    private int row;
    private Location startPosition;
    private HashMap<Integer, Block[][][]> variants = new HashMap<Integer, Block[][][]>();
    private int currentVariant = -1;
    private Location currentLocation;
    private Material material;

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
                        if (leftCorner.getWorld().getBlockAt(x, y, z).getType() != Material.AIR)
                            material = leftCorner.getWorld().getBlockAt(x, y, z).getType();
                        blocks[x - leftCorner.getBlockX()][y - leftCorner.getBlockY()][Math.abs(z) + leftCorner.getBlockZ()] = leftCorner.getWorld().getBlockAt(x, y, z);
                    }
                }
            }
            variants.put(i, blocks);
        }
    }
    public void place(){
        if (currentVariant == -1)return;

        //Get all blocks of a Tetris piece variant
        Block[][][] blocks = variants.get(currentVariant);
        for(int x = 0; x < getWidth(); x++) {
            for(int y = 0; y < getHeight(); y++) {
                for(int z = 0; z < 3; z++) {
                    //Skip block overwriting with AIR
                    if (blocks[x][y][z] == null || blocks[x][y][z].getType() == Material.AIR)continue;
                    //Set block to corresponding block in blocks 3D array
                    Block block = currentLocation.clone().add(x, y, z).getBlock();
                    block.setType(blocks[x][y][z].getType());
                    block.setBlockData(blocks[x][y][z].getBlockData());
                }
            }
        }
    }
    public Integer getWidth(){
        Block[][][] blocks = variants.get(currentVariant);
        int highestNumber = 0;

        for (int y = 0; y < 12; y++){
            int midNum = 0;
            for (int x = 11; x >= 0; x--){
                if (blocks[x][y][0].getType() != Material.AIR){
                    midNum = x;
                    break;
                }

            }
            if (midNum > highestNumber)
                highestNumber = midNum;
        }
        return highestNumber + 1;
    }
    public Integer getHeight(){
        Block[][][] blocks = variants.get(currentVariant);

        int highestNumber = 0;
        for (int x = 0; x < 12; x++){
            int midNum = 0;
            for (int y = 11; y >= 0; y--){
                if (blocks[x][y][0].getType() != Material.AIR){
                    midNum = y;
                    break;
                }
            }
            if (midNum > highestNumber)
                highestNumber = midNum;
        }
        return highestNumber + 1;
    }
    public boolean canMove(TetrisDirection tetrisDirection){
        switch (tetrisDirection){
            case DOWN:
                for(int x = 0; x < getWidth(); x++) {
                    for(int y = 0; y < getHeight(); y++) {
                        for(int z = 0; z < 3; z++) {
                            Location newLoc1 = currentLocation.clone().add(x, 0, z);
                            Location newLoc2 = currentLocation.clone().add(x, -1, z);

                            if (newLoc1.getBlock().getType() != Material.AIR && newLoc2.getBlock().getType() != Material.AIR){
                                return false;
                            }
                        }
                    }

                }
                break;
            case RIGHT:
                for(int y = 0; y < getHeight(); y++) {
                    for(int z = 0; z < 3; z++) {
                        int width = getWidth() - 1;
                        Location newLoc1 = currentLocation.clone().add(width, y, z);
                        Location newLoc2 = currentLocation.clone().add(width + 1, y, z);

                        if (newLoc1.getBlock().getType() != Material.AIR && newLoc2.getBlock().getType() != Material.AIR){
                            return false;
                        }
                    }
                }
                break;
            case LEFT:
                for(int z = 0; z < 3; z++) {
                    for(int y = 0; y < getHeight(); y++) {
                        Location newLoc1 = currentLocation.clone().add(0, y, z);
                        Location newLoc2 = currentLocation.clone().add(-1, y, z);

                        if (newLoc1.getBlock().getType() != Material.AIR && newLoc2.getBlock().getType() != Material.AIR){
                            return false;
                        }
                    }
                }
                break;
        }

        return true;
    }
    public void removeTetrisBlock(){
        Block[][][] blocks = variants.get(currentVariant);
        for(int x = 0; x < getWidth(); x++) {
            for(int y = 0; y < getHeight(); y++) {
                for(int z = 0; z < 3; z++) {
                    Block block = currentLocation.clone().add(x, y, z).getBlock();
                    if (block.getType() == material && blocks[x][y][z].getType() != Material.AIR)
                        block.setType(Material.AIR);
                }
            }
        }
    }
    public void move(TetrisDirection tetrisDirection, int amount) {
        if (canMove(tetrisDirection) && tetrisBoard.isStarted()){
            removeTetrisBlock();
            switch (tetrisDirection){
                case LEFT:
                case RIGHT:
                    currentLocation.add(amount, 0,0);
                    break;
                case DOWN:
                    currentLocation.add(0, amount, 0);
                    break;
            }
            this.place();
        }else {
            if (tetrisDirection == TetrisDirection.DOWN){
                if (currentLocation.getBlockY() + getHeight() - 1 >= tetrisBoard.getRightTopCorner().getBlockY()){
                    for (Player player : tetrisBoard.getPlayers())
                        player.sendTitle("§4You lost!", "§7Tetris above maximum height!", 20, 40, 20);
                    tetrisBoard.getHost().sendTitle("§4You lost!", "§7Tetris above maximum height!", 20, 40, 20);
                    removeTetrisBlock();
                    tetrisBoard.stop();
                }else{
                    TetrisBlockCollideEvent event = new TetrisBlockCollideEvent(this);
                    Bukkit.getPluginManager().callEvent(event);
                }
            }
        }
    }
    public void rotateRight(){
        this.removeTetrisBlock();
        setCurrentVariant(currentVariant+1 > 3 ? 0 : currentVariant+1);
        this.place();
    }
    public void rotateLeft(){
        this.removeTetrisBlock();
        setCurrentVariant(currentVariant-1 < 0 ? 3 : currentVariant-1);
        this.place();
    }
}
