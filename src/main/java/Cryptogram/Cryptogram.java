package Cryptogram;

import java.io.*;
import java.util.*;

public class Cryptogram {

    private final String phrase;
    // a map from real letter to crypto value
    private final HashMap<String, String> cryptogramAlphabet;

    public Cryptogram(String username) throws IOException, IllegalStateException {
        File file = new File(getClass().getResource("PlayerGameFiles/" + username + "Game.txt").getPath());
        if (!file.exists()) {
//            System.out.println(file.getAbsolutePath());
//            System.out.println("filenotfound");
            throw new FileNotFoundException();
        }
        phrase = loadPhraseFromFile(file);
        cryptogramAlphabet = loadAlphabetFromFile(file);
    }

    public Cryptogram() throws IOException {
        Random random = new Random();
        int fileNumber = random.nextInt(15) + 1;

        phrase = getQuoteFromFile(getClass().getResource("CryptogramPhrases/quote" + fileNumber + ".txt").getPath());
        cryptogramAlphabet = generateAlphabet();
    }

    private String loadPhraseFromFile(File file) throws IOException {
        BufferedReader bf = new BufferedReader(new FileReader(file));

        String line = bf.readLine();
        if (line == null || line.equals("")) {
            throw new IllegalStateException();
        }
        return line;
    }

    private HashMap<String, String> loadAlphabetFromFile(File file) throws IOException, IllegalStateException {
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line;
        HashMap<String, String> alphabet = new HashMap<>();
        do {
            line = br.readLine();
            if (line == null) {
                throw new IllegalStateException();
            }
        } while (!line.equals("Alphabet:"));
        while (!(line = br.readLine()).equals("Solution:")) {
            String[] tokens = line.split(":");
            if (tokens[0].length() != 1 ||
                    tokens[1].length() > 2 ||
                    tokens[1].length() < 1 ||
                    tokens[0].charAt(0) < 'a' ||
                    tokens[0].charAt(0) > 'z') {
                throw new IllegalStateException();
            }
            alphabet.put(tokens[0], tokens[1]);
        }
        return alphabet;
    }

    /**
     * helper method to return all possible crypto values of the cryptogram
     *
     * @return an array of all possible alphabet values
     */
    public String[] getPossibleValues() {
        return null;
    }

    /**
     * maps real letters to crypto values
     *
     * @return a random mapping of real letter to crypto value
     */
    public HashMap<String, String> generateAlphabet() {
        HashMap<String, String> alphabet = new HashMap<>();
        ArrayList<String> possibleValues = new ArrayList<>();
        Collections.addAll(possibleValues, getPossibleValues());

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
        return cryptogramAlphabet;
    }

    /**
     * Getter for the phrase
     *
     * @return string with a phrase
     */
    public String getPhrase() {
        return phrase;
    }

    /**
     * helper method for getting the file with phrases
     *
     * @param filePath a path to the file with phrases
     * @return String with A phrase
     * @throws IOException when a file couldn't be located or something went wrong
     */
    private String getQuoteFromFile(String filePath) throws IOException {
        File file = new File(filePath);
        BufferedReader br;
        br = new BufferedReader(new FileReader(file));

        String quote;
        quote = br.readLine();
        br.close();
        return quote;
    }

    public String getPlainLetter(String cryptoLetter) throws IllegalAccessException {
        if (cryptogramAlphabet.containsValue(cryptoLetter)) {
            for (String key : cryptogramAlphabet.keySet()) {
                if (cryptogramAlphabet.get(key).equals(cryptoLetter)) {
                    return key;
                }
            }
        }
        throw new IllegalAccessException();
    }

    public static String getFrequencies() {
        return  "a:8.12, b:1.49, c:2.71, d:4.32, e:12.02, f:2.30,\n" +
                "g:2.03, h:5.92, i:7.31, j:0.10, k:0.69, l:3.98,\n" +
                "m:2.61, n:6.95, o:7.68, p:1.82, q:0.11, r:6.02,\n" +
                "s:6.28, t:9.10, u:2.88, v:1.11, w:2.09, x:0.17,\n" +
                "y:2.11, z:0.07";
    }
}