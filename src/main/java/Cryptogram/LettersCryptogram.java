package Cryptogram;

import java.util.*;
import java.util.stream.Collectors;

public class LettersCryptogram extends Cryptogram {

    LettersCryptogram(String phrase) {
        this.phrase = phrase;
        this.solution = new HashMap<>();
        this.alphabet = generateAlphabet();
    }

    private Map<String, String> generateAlphabet() {
        List<String> possibleValues =
                new ArrayList<>(Arrays.asList(("abcdefghijklmnopqrstuvwxyz".split("(?!^)"))));

        return phrase.chars()
                .distinct()
                .mapToObj((cha) -> (char) cha)
                .collect(
                        Collectors.toMap(
                                String::valueOf,
                                (Character cha) -> popRandomValue(possibleValues))
                );
    }

    private String popRandomValue(List<String> listOfValues) {
        Random r = new Random();
        int index = r.nextInt(listOfValues.size());
        return listOfValues.remove(index);
    }
}