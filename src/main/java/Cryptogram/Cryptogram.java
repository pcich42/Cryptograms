package Cryptogram;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.*;

public class Cryptogram {

    // a map from real letter to crypto value
    protected Map<String, String> alphabet;
    protected Map<String, String> solution;
    protected String phrase;


    /**
     * generate constructor
     * @param phrase path to the file
     */
    public Cryptogram(String phrase, Map<String, String> alphabet, Map<String, String> solution) {
        this.phrase = phrase;
        this.alphabet = alphabet;
        this.solution = solution;
    }

    public Cryptogram() {
    }

    public Map<String, String> getCryptogramAlphabet() {
        return alphabet;
    }

    public Map<String, String> getSolution() {
        return solution;
    }

    public String getPhrase() {
        return phrase;
    }

    public void mapLetters(String guess, String valueToMap) {
        solution.put(guess, valueToMap);
    }

    public void remapLetters(String guess, String valueToMap) {
        solution.entrySet().stream()
                .filter((entry) -> entry.getValue().equals(valueToMap))
                .findFirst()
                .ifPresent((entry) -> solution.remove(entry.getKey()));

        solution.put(guess, valueToMap);
    }

    public boolean valueHasMapping(String value) {
        return solution.containsValue(value);
    }

    public boolean isLetterAlreadyUsed(String guess) {
        return solution.containsKey(guess);
    }

    public boolean isValueInAlphabet(String valueToMap) {
        return (alphabet.containsValue(valueToMap));
    }

    public void removeMapping(String remove) {
        solution.remove(remove);
    }

    public boolean isSolutionFull() {
        return solution.size() == alphabet.size();
    }

    public boolean isSolutionCorrect() {
        return solution.equals(alphabet);
    }

    public boolean isGuessCorrect(String guess, String valueToMap) {
        return alphabet.containsKey(guess) && alphabet.get(guess).equals(valueToMap);
    }

    public void fillSolution() {
        solution = new HashMap<>();
        for (var entry : alphabet.entrySet()) solution.put(entry.getKey(), entry.getValue());
    }

    /**
     * returns the frequencies of each letter that is part of the unencrypted phrase
     */
    public String getFrequencies() {
        StringBuilder frequenciesFormatted = new StringBuilder();

        char[] phraseArray = phrase.toCharArray();
        int[] frequencies = new int[26];
        // i.e. frequencies[0] = a , frequencies[1] = b frequencies[2] = c

        // get frequencies
        int shiftAmountAlphabetToIndex = 97;
        for (char c : phraseArray) {
            int index = ((int) c - shiftAmountAlphabetToIndex);
            if (index >= 0 && index <= 26)
                frequencies[index] += 1;
        }

        // format it into a string <letter> <frequency>,<letter> <frequency>,
        for (int i = 0; i < frequencies.length; i++) {
            int size = phrase.replaceAll("\\s+", "").length();
            if (frequencies[i] != 0) {
                BigDecimal bd = new BigDecimal(frequencies[i] * 100.0 / size);
                bd = bd.round(new MathContext(3));
                double freq = bd.doubleValue();
                frequenciesFormatted.append((char) (i + shiftAmountAlphabetToIndex)).append(":").append(freq);
                if (i != frequencies.length - 1)
                    frequenciesFormatted.append(", ");
            }
        }

        return frequenciesFormatted.toString();
    }

}