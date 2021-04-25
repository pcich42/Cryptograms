package Cryptogram.Models;


import java.util.*;
import java.util.stream.Collectors;

public class NumbersCryptogram extends Cryptogram {

    public NumbersCryptogram(String phrase) {
        this.phrase = phrase;
        this.solution = new HashMap<>();
        this.alphabet = generateAlphabet();
    }

    private Map<String, String> generateAlphabet() {
        List<String> possibleValues = new ArrayList<>(Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08", "09", "10",
                "11", "12", "13", "14", "15", "16", "17", "18", "19", "20",
                "21", "22", "23", "24", "25", "26"));

        return phrase.chars()
                .distinct()
                .mapToObj((cha) -> (char) cha)
                .collect(Collectors.toMap(
                        String::valueOf, (Character cha) -> popRandomValue(possibleValues))
                );
    }

    private String popRandomValue(List<String> listOfValues) {
        Random r = new Random();
        int index = r.nextInt(listOfValues.size());
        return listOfValues.remove(index);
    }

}
