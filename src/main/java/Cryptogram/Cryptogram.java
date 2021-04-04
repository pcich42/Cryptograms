package Cryptogram;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.*;

public class Cryptogram {

    // a map from real letter to crypto value
    private final HashMap<String, String> alphabet;
    private HashMap<String, String> solution;
    private final String phrase;

    /**
     * generate constructor
     * @param phrase path to the file
     */
    public Cryptogram(String phrase) {
        this.phrase = phrase;
        this.solution = new HashMap<>();
        this.alphabet = generateAlphabet();
    }

    /**
     * generate constructor
     * @param phrase path to the file
     */
    public Cryptogram(String phrase, HashMap<String, String> alphabet, HashMap<String, String> solution) {
        this.phrase = phrase;
        this.alphabet = alphabet;
        this.solution = solution;
    }

    /**
     * maps real letters to crypto values
     */
    private HashMap<String, String> generateAlphabet() {
        HashMap<String, String> alphabet = new HashMap<>();
        ArrayList<String> possibleValues = getPossibleValues();

        Random r = new Random();
        for (char c : phrase.toCharArray()) {
            if (!alphabet.containsKey(String.valueOf(c)) && c != ' ') {
                int index = r.nextInt(possibleValues.size());
                String cryptoValue = possibleValues.get(index);
                alphabet.put(String.valueOf(c), cryptoValue);
                possibleValues.remove(index);
            }
        }
        return alphabet;
    }

    /**
     * a getter for cryptogramAlphabet
     *
     * @return a mapping from real letter to crypto value
     */
    public HashMap<String, String> getCryptogramAlphabet() {
        return alphabet;
    }

    /**
     * a getter for solution
     *
     * @return a mapping from real letter to crypto value
     */
    public HashMap<String, String> getSolution() {
        return solution;
    }

    /**
     * Getter for the phrase
     *
     * @return string with a phrase
     */
    public String getPhrase() {
        return phrase;
    }

    public void mapLetters(String guess, String valueToMap) {
        solution.put(guess, valueToMap);
    }

    public void remapLetters(String guess, String valueToMap) {
        for (String key : solution.keySet())
            if (solution.get(key).equals(valueToMap)) {
                solution.remove(key);
                break;
            }
        solution.put(guess, valueToMap);
    }

    /**
     * Checks if a crypto value already has a guess
     *
     * @param valueToMap crypto value to check for
     * @return true if this value already has a letter mapped to it
     */
    public boolean isAlreadyMapped(String valueToMap) {
        return solution.containsValue(valueToMap);
    }


    /**
     * Checks if a letter is already used in another mapping
     *
     * @param guess letter to check
     * @return true is letter is used in another mapping
     */
    public boolean isLetterAlreadyUsed(String guess) {
        return solution.containsKey(guess);
    }

    /**
     * Checks if a crypto value is in the alphabet
     *
     * @param valueToMap crypto value to check for
     * @return true if value is in the alphabet
     */
    public boolean valueInAlphabet(String valueToMap) {
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

    /**
     * Checks if a letter mapping is correct
     *
     * @param guess      players real letter guess
     * @param valueToMap a value to map the letter to
     * @return true if letter was correct
     */
    public boolean isGuessCorrect(String guess, String valueToMap) {
        if (alphabet.containsKey(guess)) {
            return valueToMap.equals(alphabet.get(guess));
        } else {
            return false;
        }
    }

    /**
     * fills the correct solution in
     */
    public void fillSolution() {
        solution = new HashMap<>();
        for (Map.Entry<String, String> entry : alphabet.entrySet()) {
            solution.put(entry.getKey(), entry.getValue());
        }
    }

    public ArrayList<String> getPossibleValues() {
        return null;
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

    public String getLetter(String value) {
        if(solution.containsValue(value)){
            for(String key : solution.keySet()) {
                if (solution.get(key).equals(value)) {
                    return key;
                }
            }
        }
        return null;
    }

    public static String getEnglishFrequencies() {
        return  "a:8.12, b:1.49, c:2.71, d:4.32, e:12.02, f:2.30,\n" +
                "g:2.03, h:5.92, i:7.31, j:0.10, k:0.69, l:3.98,\n" +
                "m:2.61, n:6.95, o:7.68, p:1.82, q:0.11, r:6.02,\n" +
                "s:6.28, t:9.10, u:2.88, v:1.11, w:2.09, x:0.17,\n" +
                "y:2.11, z:0.07";
    }

}