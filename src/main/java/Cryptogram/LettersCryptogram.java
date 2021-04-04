package Cryptogram;

import java.util.ArrayList;
import java.util.Arrays;

public class LettersCryptogram extends Cryptogram {

    LettersCryptogram(String phrase){
        super(phrase);
    }

    @Override
    public ArrayList<String> getPossibleValues() {
        return new ArrayList<>(Arrays.asList(("abcdefghijklmnopqrstuvwxyz".split("(?!^)"))));
    }
}