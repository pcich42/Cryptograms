package Cryptogram.Models;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LettersCryptogram extends Cryptogram {

    private final List<String> possibleValues =
            new ArrayList<>(Arrays.asList(("abcdefghijklmnopqrstuvwxyz".split("(?!^)"))));

    public LettersCryptogram(String phrase) {
        this.phrase = phrase.toLowerCase();
        this.solution = new HashMap<>();
        this.alphabet = generateAlphabet();
    }

    private Map<String, String> generateAlphabet() {

        Stream<Character> distinctCharactersOfPhrase = phrase
                .chars()
                .distinct()
                .mapToObj((cha) -> (char) cha);

        return distinctCharactersOfPhrase
                .collect(Collectors.toMap(String::valueOf, this::getRandomPossibleValue));

    }

    private String getRandomPossibleValue(Character cha) {
        Random r = new Random();
        int index = r.nextInt(possibleValues.size());
        return possibleValues.remove(index);
    }
}