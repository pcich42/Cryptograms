package Cryptogram;

import java.io.IOException;

public class LettersCryptogram extends Cryptogram {

    LettersCryptogram() throws IOException {
        super();
    }

    @Override
    public String[] getPossibleValues() {
        return "abcdefghijklmnopqrstuvwxyz".split("(?!^)");
    }
}