package entertainer.entertainments.tetris.objects;

import entertainer.entertainments.Entertainments;
import entertainer.entertainments.configuration.Configs;
import entertainer.entertainments.tetris.enums.TetrisDirection;
import entertainer.entertainments.tetris.events.TetrisBlockCollideEvent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;

import static entertainer.entertainments.configuration.Configs.customConfigFile2;

public class TetrisBlock {

    private int row;
    private Location startPosition;
    private HashMap<Integer, CopyBlock[][][]> variants = new HashMap<>();
    private int currentVariant = -1;
    private Location currentLocation;
    private Material material;
    private TetrisBoard tetrisBoard;

    private Entertainments plugin = Entertainments.getPlugin(Entertainments.class);

    public TetrisBlock(Location startPosition, int row) {
        this.row = row;
        if (Configs.getCustomConfig2().contains("blocks." + row))
            loadVariants();
        else if (startPosition != null){
            this.startPosition = startPosition;
            initialiseVariants();
        }
    }

    public void setCurrentVariant(int currentVariant) {
        this.currentVariant = currentVariant;
    }
    public void setCurrentLocation(Location currentLocation) {
        this.currentLocation = currentLocation;
    }
    private void loadVariants(){
        if (Configs.getCustomConfig2().contains("blocks." + row)){
            for (String variantNumber : Configs.getCustomConfig2().getConfigurationSection("blocks." + row).getKeys(false)){
                CopyBlock[][][] blocks = new CopyBlock[12][12][3];
                for (String blockID : Configs.getCustomConfig2().getConfigurationSection("blocks." + row + "." + variantNumber).getKeys(false)){
                    if (Configs.getCustomConfig2().getString("blocks." + row + "." + variantNumber + "." + blockID + ".c") != null){
                        String[] coords = Configs.getCustomConfig2().getString("blocks." + row + "." + variantNumber + "." + blockID + ".c").split(",");
                        int x = Integer.parseInt(coords[0]);
                        int y = Integer.parseInt(coords[1]);
                        int z = Integer.parseInt(coords[2]);
                        System.out.println(x + " | " + y + " | " + z);

                        Material material = Material.valueOf(Configs.getCustomConfig2().getString("blocks." + row + "." + variantNumber + "." + blockID + ".m"));
                        BlockData blockData = Bukkit.getServer().createBlockData(Objects.requireNonNull(Configs.getCustomConfig2().getString("blocks." + row + "." + variantNumber + "." + blockID + ".b")));
                        CopyBlock copyBlock = new CopyBlock(x, y, z, material, blockData);
                        blocks[x][y][z] = copyBlock;
                    }
                }
                variants.put(Integer.parseInt(variantNumber), blocks);
            }
        }
    }
    private void saveVariants(HashMap<Integer, CopyBlock[][][]> variantList){
        for (Integer variantID : variantList.keySet()){
            int blockCounter = 0;
            for (int y = 0; y < 12; y++) {
                for (int x = 0; x < 12; x++) {
                    for (int z = 0; z < 3; z++) {
                        CopyBlock copyBlock = variantList.get(variantID)[x][y][z];
                        if (copyBlock.getMaterial() == Material.AIR)continue;

                        Configs.getCustomConfig2().set("blocks." + row + "." + variantID + "." + blockCounter + ".c", x + "," + y + "," + z);

                        Configs.getCustomConfig2().set("blocks." + row + "." + variantID + "." + blockCounter + ".m", copyBlock.getMaterial().name());
                        Configs.getCustomConfig2().set("blocks." + row + "." + variantID + "." + blockCounter + ".b", copyBlock.getBlockData().getAsString());

                        blockCounter++;
                        saveTetrisConfig();
                    }
                }
            }
        }
        tetrisBoard.getHost().sendMessage("§6Tetris pallet " + row + " saved to config!");
    }
    private void saveTetrisConfig(){
        try {
            Configs.getCustomConfig2().save(customConfigFile2);
        } catch (IOException ignored) {}
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

            CopyBlock[][][] blocks = new CopyBlock[12][12][3];

            for (int y = leftCorner.getBlockY(); y <= rightCorner.getBlockY(); y++) {
                for (int x = leftCorner.getBlockX(); x <= rightCorner.getBlockX(); x++) {
                    for (int z = leftCorner.getBlockZ(); z >= rightCorner.getBlockZ(); z--) {
                        if (leftCorner.getWorld().getBlockAt(x, y, z).getType() != Material.AIR)
                            material = leftCorner.getWorld().getBlockAt(x, y, z).getType();
                        Block block = leftCorner.getWorld().getBlockAt(x, y, z);
                        CopyBlock copyBlock = new CopyBlock(x, y, z, block.getType(), block.getBlockData());
                        blocks[x - leftCorner.getBlockX()][y - leftCorner.getBlockY()][Math.abs(z) + leftCorner.getBlockZ()] = copyBlock;
                    }
                }
            }
            variants.put(i, blocks);
        }
        saveVariants(variants);
    }
    public void place(){
        if (currentVariant == -1)return;

        //Get all blocks of a Tetris piece variant
        CopyBlock[][][] blocks = variants.get(currentVariant);
        for(int x = 0; x < getWidth(); x++) {
            for(int y = 0; y < getHeight(); y++) {
                for(int z = 0; z < 3; z++) {
                    //Skip block overwriting with AIR
                    if (blocks[x][y][z] == null || blocks[x][y][z].getMaterial() == Material.AIR)continue;
                    //Set block to corresponding block in blocks 3D array
                    Block block = currentLocation.clone().add(x, y, z).getBlock();
                    block.setType(blocks[x][y][z].getMaterial());
                    block.setBlockData(blocks[x][y][z].getBlockData());
                }
            }
        }
    }
    public Integer getWidth(){
        CopyBlock[][][] blocks = variants.get(currentVariant);
        int highestNumber = 0;

        for (int y = 0; y < 12; y++){
            int midNum = 0;
            for (int x = 11; x >= 0; x--){
                if (blocks[x][y][0] != null && blocks[x][y][0].getMaterial() != Material.AIR){
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
        CopyBlock[][][] blocks = variants.get(currentVariant);

        int highestNumber = 0;
        for (int x = 0; x < 12; x++){
            int midNum = 0;
            for (int y = 11; y >= 0; y--){
                if (blocks[x][y][0] != null && blocks[x][y][0].getMaterial() != Material.AIR){
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
                CopyBlock[][][] blocks = variants.get(currentVariant);
                for(int x = 0; x < getWidth(); x++) {
                    for(int z = 0; z < 3; z++) {
                        Location newLoc1 = null;
                        for(int y = 0; y < getHeight(); y++) {
                            Location midLoc = currentLocation.clone().add(x, y, z);
                            if (blocks[x][y][z] != null && midLoc.getBlock().getType() != Material.AIR && blocks[x][y][z].getMaterial() == midLoc.getBlock().getType()){
                                newLoc1 = midLoc;
                                break;
                            }
                        }
                        if (newLoc1 != null){
                            Location newLoc2 = newLoc1.clone().add(0, -1, 0);

                            if (newLoc1.getBlock().getType() != Material.AIR && newLoc2.getBlock().getType() != Material.AIR){
                                return false;
                            }
                        }else{
                            return false;
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
        CopyBlock[][][] blocks = variants.get(currentVariant);
        for(int x = 0; x < getWidth(); x++) {
            for(int y = 0; y < getHeight(); y++) {
                for(int z = 0; z < 3; z++) {
                    Block block = currentLocation.clone().add(x, y, z).getBlock();
                    if (blocks[x][y][z] != null && block.getType() == blocks[x][y][z].getMaterial())
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
