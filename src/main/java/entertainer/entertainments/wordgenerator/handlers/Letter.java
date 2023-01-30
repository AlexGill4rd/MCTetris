package entertainer.entertainments.wordgenerator.handlers;

import entertainer.entertainments.tetris.objects.CopyBlock;

public class Letter {

    private CopyBlock[][] blocks;
    private String letter;

    public Letter(CopyBlock[][] blocks, String letter) {
        this.blocks = blocks;
        this.letter = letter;
    }

    public CopyBlock[][] getBlocks() {
        return blocks;
    }

    public void setBlocks(CopyBlock[][] blocks) {
        this.blocks = blocks;
    }

    public String getLetter() {
        return letter;
    }

    public void setLetter(String letter) {
        this.letter = letter;
    }
}
