package entertainer.entertainments.wordgenerator;

import entertainer.entertainments.configuration.Configs;
import entertainer.entertainments.tetris.objects.CopyBlock;
import entertainer.entertainments.wordgenerator.handlers.Letter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.data.BlockData;

import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;

import static entertainer.entertainments.configuration.Configs.customConfigFile4;

public class WordGenerator {

    private final HashMap<String, Letter> letters = new HashMap<>();
    public boolean isConfigured = false;
    public String letterList = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789:*,?/#<>+-=!|.";

    public WordGenerator() {
        if (Configs.getCustomConfig4().contains("letters"))
            loadLetters();
    }
    private void loadLetters(){
        isConfigured = true;

        char[] splittedLetters = letterList.toCharArray();
        for (char letter : splittedLetters){
            if (Configs.getCustomConfig4().contains("letters." + letter)){
                CopyBlock[][] copyBlock = new CopyBlock[3][3];

                for (String blockId : Configs.getCustomConfig4().getConfigurationSection("letters." + letter).getKeys(false)){
                    if (Configs.getCustomConfig4().getString("letters." + letter + "." + blockId + ".c") != null){
                        String[] coords = Configs.getCustomConfig4().getString("letters." + letter + "." + blockId + ".c").split(",");
                        int x = Integer.parseInt(coords[0]);
                        int y = Integer.parseInt(coords[1]);

                        Material material = Material.valueOf(Configs.getCustomConfig4().getString("letters." + letter + "." + blockId + ".m"));
                        BlockData blockData = Bukkit.getServer().createBlockData(Objects.requireNonNull(Configs.getCustomConfig4().getString("letters." + letter + "." + blockId + ".b")));

                        copyBlock[x][y] = new CopyBlock(x, y, material, blockData);
                    }
                }
                Letter newLetter = new Letter(copyBlock, String.valueOf(letter));
                letters.put(String.valueOf(letter), newLetter);
            }
        }
    }
    public void saveLetters(){
        for (Letter letter : letters.values()){
            int blockId = 0;
            for (int y = 0; y < 3; y++) {
                for (int x = 0; x < 3; x++) {
                    CopyBlock copyBlock = letters.get(letter.getLetter()).getBlocks()[x][y];
                    if (copyBlock.getMaterial() == Material.AIR)continue;

                    Configs.getCustomConfig4().set("letters." + letter.getLetter() + "." + blockId + ".c", x + "," + y);
                    Configs.getCustomConfig4().set("letters." + letter.getLetter() + "." + blockId + ".m", copyBlock.getMaterial().name());
                    Configs.getCustomConfig4().set("letters." + letter.getLetter() + "." + blockId + ".b", copyBlock.getBlockData().getAsString());

                    blockId++;
                    saveLetterConfig();
                }
            }
        }

        System.out.println("§6Tetris letters saved to config!");
    }
    private void saveLetterConfig(){
        try {
            Configs.getCustomConfig4().save(customConfigFile4);
        } catch (IOException ignored) {}
    }
    public void writeWord(Location location, String word){
        word = word.trim();
        char[] splitted = word.toCharArray();

        int wordCount = 0;
        for (char s : splitted){
            if (!String.valueOf(s).equals(" ")){
                Letter letter = letters.get(String.valueOf(s));
                for (int x = 0; x < 3; x++){
                    for (int y = 0; y < 3; y++){
                        Location blockPlaceLoc = location.clone().add(x + ((wordCount*3) + wordCount), y, 0);
                        if (letter.getBlocks()[x][y] == null)continue;
                        blockPlaceLoc.getBlock().setType(letter.getBlocks()[x][y].getMaterial());
                        blockPlaceLoc.getBlock().setBlockData(letter.getBlocks()[x][y].getBlockData());
                    }
                }
                wordCount++;
            }else{
                location.add(2, 0, 0);
            }
        }
    }
    public void initialiseWords(Location location){
        isConfigured = true;
        //Start forming

        //Letters
        int letterLength = 26;
        Location letterLocation = location.clone().add(-2, 0, 0);
        for (int z = 0; z < letterLength; z++){
            String letter = letterList.substring(z, z+1);
            CopyBlock[][] blocks = new CopyBlock[3][3];
            for (int y = 0; y < 3; y++){
                for (int x = 0; x < 3; x++){
                    Location blockLoc = letterLocation.clone().add(x, y, -z*2);
                    blocks[x][y] = new CopyBlock(x, y, blockLoc.getBlock().getType(), blockLoc.getBlock().getBlockData());
                }
            }
            letters.put(letter, new Letter(blocks, letter));
        }

        //Start forming numbers
        Location numberLocation = location.clone().add(-8, 0,  0);
        int numberLength = 10;
        for (int z = 0; z < numberLength; z++){
            String number = letterList.substring(26 + z, 26 + z + 1);
            CopyBlock[][] blocks = new CopyBlock[3][3];
            for (int y = 0; y < 3; y++){
                for (int x = 0; x < 3; x++){
                    Location blockLoc = numberLocation.clone().add(x, y, -z*2);
                    blocks[x][y] = new CopyBlock(x, y, blockLoc.getBlock().getType(), blockLoc.getBlock().getBlockData());
                }
            }
            letters.put(number, new Letter(blocks, number));
        }

        //Start forming symbols
        Location symbolLocation = location.clone().add(-14, 0,  0);
        int symbolLength = 14;
        for (int z = 0; z < symbolLength; z++){
            String symbol = letterList.substring(letterLength + numberLength + z, letterLength + numberLength + z + 1);
            CopyBlock[][] blocks = new CopyBlock[3][3];
            for (int y = 0; y < 3; y++){
                for (int x = 0; x < 3; x++){
                    Location blockLoc = symbolLocation.clone().add(x, y, -z*2);
                    blocks[x][y] = new CopyBlock(x, y, blockLoc.getBlock().getType(), blockLoc.getBlock().getBlockData());
                }
            }
            letters.put(symbol, new Letter(blocks, symbol));
        }
        saveLetters();
    }
}
