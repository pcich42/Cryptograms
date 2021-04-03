package Cryptogram;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LettersCryptogram extends Cryptogram {

    LettersCryptogram(String phrase) throws IOException {
        super(phrase);
    }

    @Override
    public ArrayList<String> getPossibleValues() {
        return new ArrayList<String>(Arrays.asList(("abcdefghijklmnopqrstuvwxyz".split("(?!^)"))));
    }
}