package entertainer.entertainments.wordgenerator.handlers;

import org.bukkit.block.Block;

public class Letter {

    private Block[][] blocks;
    private String letter;

    public Letter(Block[][] blocks, String letter) {
        this.blocks = blocks;
        this.letter = letter;
    }

    public Block[][] getBlocks() {
        return blocks;
    }

    public void setBlocks(Block[][] blocks) {
        this.blocks = blocks;
    }

    public String getLetter() {
        return letter;
    }

    public void setLetter(String letter) {
        this.letter = letter;
    }
}
