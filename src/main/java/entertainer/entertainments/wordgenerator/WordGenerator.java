package entertainer.entertainments.wordgenerator;

import entertainer.entertainments.wordgenerator.handlers.Letter;
import org.bukkit.Location;
import org.bukkit.block.Block;

import java.util.HashMap;

public class WordGenerator {

    private final HashMap<String, Letter> letters = new HashMap<>();

    public WordGenerator() {

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
<<<<<<< Updated upstream
                        if (letter.getBlocks()[x][y] == null)continue;
                        blockPlaceLoc.getBlock().setType(letter.getBlocks()[x][y].getType());
=======
                        if (letter.getBlocks()[x][y] == null) {
                            blockPlaceLoc.getBlock().setType(Material.AIR);
                            continue;
                        }
                        blockPlaceLoc.getBlock().setType(letter.getBlocks()[x][y].getMaterial());
>>>>>>> Stashed changes
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
        //Start forming
        String letterList = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789:*,?/#<>+-=!|.";

        //Letters
        int letterLength = 26;
        Location letterLocation = location.clone().add(-2, 0, 0);
        for (int z = 0; z < letterLength; z++){
            String letter = letterList.substring(z, z+1);
            Block[][] blocks = new Block[3][3];
            for (int y = 0; y < 3; y++){
                for (int x = 0; x < 3; x++){
                    Location blockLoc = letterLocation.clone().add(x, y, -z*2);
                    blocks[x][y] = blockLoc.getBlock();
                }
            }
            letters.put(letter, new Letter(blocks, letter));
        }

        //Start forming numbers
        Location numberLocation = location.clone().add(-8, 0,  0);
        int numberLength = 10;
        for (int z = 0; z < numberLength; z++){
            String number = letterList.substring(26 + z, 26 + z + 1);
            Block[][] blocks = new Block[3][3];
            for (int y = 0; y < 3; y++){
                for (int x = 0; x < 3; x++){
                    Location blockLoc = numberLocation.clone().add(x, y, -z*2);
                    blocks[x][y] = blockLoc.getBlock();
                }
            }
            letters.put(number, new Letter(blocks, number));
        }

        //Start forming symbols
        Location symbolLocation = location.clone().add(-14, 0,  0);
        int symbolLength = 14;
        for (int z = 0; z < symbolLength; z++){
            String symbol = letterList.substring(letterLength + numberLength + z, letterLength + numberLength + z + 1);
            Block[][] blocks = new Block[3][3];
            for (int y = 0; y < 3; y++){
                for (int x = 0; x < 3; x++){
                    Location blockLoc = symbolLocation.clone().add(x, y, -z*2);
                    blocks[x][y] = blockLoc.getBlock();
                }
            }
            letters.put(symbol, new Letter(blocks, symbol));
        }
    }
}
