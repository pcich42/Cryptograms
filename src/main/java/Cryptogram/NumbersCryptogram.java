package Cryptogram;

import java.io.IOException;

public class NumbersCryptogram extends Cryptogram {

    NumbersCryptogram() throws IOException {
        super();
    }

    @Override
    public String[] getPossibleValues() {
        return new String[]{"01", "02", "03", "04", "05", "06", "07", "08", "09", "10",
                "11", "12", "13", "14", "15", "16", "17", "18", "19", "20",
                "21", "22", "23", "24", "25", "26"};
    }
}
