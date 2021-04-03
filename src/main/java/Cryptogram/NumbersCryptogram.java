package Cryptogram;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class NumbersCryptogram extends Cryptogram {

    NumbersCryptogram(String phrase) throws IOException {
        super(phrase);
    }

    @Override
    public ArrayList<String> getPossibleValues() {
        return new ArrayList<String>(Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08", "09", "10",
                "11", "12", "13", "14", "15", "16", "17", "18", "19", "20",
                "21", "22", "23", "24", "25", "26"));
    }
}
