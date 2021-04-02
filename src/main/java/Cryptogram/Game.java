package Cryptogram;

import java.io.*;
import java.math.BigDecimal;
import java.math.MathContext;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

class Game {

    private final Player player;
    private final Players playerList;
    private HashMap<String, String> currentSolution; // player to games
    private Cryptogram cryptogram;

    public Game(String username) throws IOException {
        Player temp_player;
        this.playerList = new Players();
        temp_player = playerList.getPlayer(username);
        if (temp_player == null) {
            System.out.println("Player doesn't yet exist, creating a new player profile.");
            playerList.addPlayer(username);
            temp_player = playerList.getPlayer(username);
        }
        this.player = temp_player;
        this.currentSolution = new HashMap<>();
    }

    /**
     * Getter for cryptogram
     *
     * @return cryptogram
     */
    public Cryptogram getCryptogram() {
        return cryptogram;
    }

    /**
     * Getter for Cryptogram.Player
     *
     * @return player
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * @return Hashmap from real letter to crypto value of the solution
     */
    public HashMap<String, String> getCurrentSolution() {
        return currentSolution;
    }

    /**
     * A method called in constructor. Generates a new cryptogram to play
     *
     * @param cryptType The type of the cryptogram ("letters" for letters and "numbers" for numbers), if neither matches
     *                  it tries to load from file
     */
    public void generateCryptogram(String cryptType) throws IOException {
        Cryptogram cryptogram;
        this.currentSolution = new HashMap<>();
        // while generating a new cryptogram when already playing new solution needs to be created
        if (cryptType.equals("numbers")) {
            cryptogram = new NumbersCryptogram();
        } else {
            cryptogram = new LettersCryptogram();
        }
        player.incrementCryptogramsPlayed();
        this.cryptogram = cryptogram;
    }

    /**
     * This method maps the two letters from input to solution
     *
     * @param input users input
     * @param remap override boolean, if true, player wants to override existing mapping
     */
    public void enterLetter(String[] input, boolean remap) throws IllegalStateException {
        if (isInputValid(input)) {
            String guess = input[0];
            String valueToMap = input[1];
            if (isAlreadyMapped(valueToMap)) {
                if (!remap) {
                    throw new IllegalStateException();
                } else {
                    remapLetter(guess, valueToMap);
                    player.updateAccuracy(isGuessCorrect(guess, valueToMap));
                }
            } else {
                this.currentSolution.put(guess, valueToMap);
                player.updateAccuracy(isGuessCorrect(guess, valueToMap));
            }
        }
    }

    /**
     * helper function for remapping a letter
     *
     * @param guess      users guess
     * @param valueToMap users value to map input
     */
    public void remapLetter(String guess, String valueToMap) {
        for (String key : currentSolution.keySet())
            if (currentSolution.get(key).equals(valueToMap)) {
                currentSolution.remove(key);
                break;
            }
        this.currentSolution.put(guess, valueToMap);
    }

    /**
     * This method check if input is in correct format, if the crypto value is in the alphabet
     * and if the letter guessed is not already in use.
     * It does not check if crypto value already has a letter mapped to it.
     *
     * @param input = input from the user
     * @return returns true if input is valid and ready to be passed to enterLetter
     */
    private boolean isInputValid(String[] input) {
        if (!isInputFormattedCorrectly(input)) {
            System.out.println(">> Couldn't enter this mapping. Try again.");
            return false;
        }

        String guess = input[0];
        String valueToMap = input[1];

        if (!valueInAlphabet(valueToMap)) {
            System.out.println(">> The value you're trying to map to, is not present in this cryptogram.\n" +
                    ">> Try again with a different value.");
            return false;
        } else if (isLetterAlreadyUsed(guess)) {
            System.out.println(">> The letter you are trying to use, has already been used.\n" +
                    ">> Try a different one or erase that letter.");
            return false;
        } else {
            return true;
        }
    }

    /**
     * removes a mapping from the solution
     *
     * @param input user input of from "undo <letter to be removed>"
     */
    public void undoLetter(String[] input) {
        if (input.length == 2) {
            String remove = input[1];
            if (!isLetterAlreadyUsed(remove)) {
                throw new IllegalStateException();

            } else {
                currentSolution.remove(remove);
            }
        } else {
            throw new IndexOutOfBoundsException();
        }

    }

    /**
     * Checks if a letter mapping is correct
     *
     * @param guess      players real letter guess
     * @param valueToMap a value to map the letter to
     * @return true if letter was correct
     */
    public boolean isGuessCorrect(String guess, String valueToMap) {
        if (cryptogram.getCryptogramAlphabet().containsKey(guess)) {
            return valueToMap.equals(cryptogram.getCryptogramAlphabet().get(guess));
        } else {
            return false;
        }
    }

    /**
     * Checks if the input was is correct format
     *
     * @param input raw input from the player
     * @return true if input was correctly inputted, false otherwise
     */
    public boolean isInputFormattedCorrectly(String[] input) {
        return (input.length == 2 &&
                input[0].length() == 1 &&
                (input[1].length() == 1 || input[1].length() == 2));
    }

    /**
     * Checks if a letter is already used in another mapping
     *
     * @param guess letter to check
     * @return true is letter is used in another mapping
     */
    public boolean isLetterAlreadyUsed(String guess) {
        return currentSolution.containsKey(guess);
    }

    /**
     * Checks if a crypto value is in the alphabet
     *
     * @param valueToMap crypto value to check for
     * @return true if value is in the alphabet
     */
    public boolean valueInAlphabet(String valueToMap) {
        return (cryptogram.getCryptogramAlphabet().containsValue(valueToMap));
    }

    /**
     * Checks if a crypto value already has a guess
     *
     * @param valueToMap crypto value to check for
     * @return true if this value already has a letter mapped to it
     */
    public boolean isAlreadyMapped(String valueToMap) {
        return currentSolution.containsValue(valueToMap);
    }

    /**
     * checks if game has been successfully completed
     *
     * @return 1 if game has been successfully completed, 0 if not completed, -1 if unsuccessfully completed
     */
    public int isSuccessfullyFinished() {
        if (currentSolution.size() == cryptogram.getCryptogramAlphabet().size()) {
            if (currentSolution.equals(cryptogram.getCryptogramAlphabet())) {
                player.incrementCryptogramsCompleted();
                return 1;
            } else {
                return -1;
            }
        }
        return 0;
    }

    /**
     * returns the frequencies of each letter that is part of the unencrypted phrase
     */
    public String getFrequencies() {
        StringBuilder frequenciesFormatted = new StringBuilder();

        char[] phrase = cryptogram.getPhrase().toCharArray();
        int[] frequencies = new int[26];
        // i.e. frequencies[0] = a , frequencies[1] = b frequencies[2] = c

        // get frequencies
        int shiftAmountAlphabetToIndex = 97;
        for (char c : phrase) {
            int index = ((int) c - shiftAmountAlphabetToIndex);
            if (index >= 0 && index <= 26)
                frequencies[index] += 1;
        }

        // format it into a string <letter> <frequency>,<letter> <frequency>,
        for (int i = 0; i < frequencies.length; i++) {
            int size = cryptogram.getPhrase().replaceAll("\\s+", "").length();
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

    public void updatePlayersFile() {
        playerList.savePlayers();
    }

    public void loadCryptogram() throws IOException, IllegalStateException {
        cryptogram = new Cryptogram(player.getUsername());
        currentSolution = getSolutionFromFile();
    }

    /**
     * fills the correct solution in
     */
    public void showSolution() {
        currentSolution = new HashMap<>();
        for (Map.Entry<String, String> entry : cryptogram.getCryptogramAlphabet().entrySet()) {
            currentSolution.put(entry.getKey(), entry.getValue());
        }
    }

    public HashMap<String, String> getSolutionFromFile() throws IOException, IllegalStateException {
        // restore cryptograms solution
        BufferedReader bf = new BufferedReader(new FileReader(getClass().getResource("PlayerGameFiles/" + player.getUsername() + "Game.txt").getPath()));
        String line;
        HashMap<String, String> solution = new HashMap<>();
        while (!bf.readLine().equals("Solution:")) ;
        while ((line = bf.readLine()) != null) {
            String[] tokens = line.split(":");
            if (tokens[0].length() != 1 ||
                    tokens[1].length() > 2 ||
                    tokens[1].length() < 1 ||
                    tokens[0].charAt(0) < 'a' ||
                    tokens[0].charAt(0) > 'z') {
                throw new IllegalStateException();
            }
            solution.put(tokens[0], tokens[1]);
        }
        return solution;
    }

    public void saveGameToFile(boolean override) throws RuntimeException {

        File directory = new File(getClass().getResource("PlayerGameFiles").getPath());
        directory.mkdirs();
        File file = new File(getClass().getResource("PlayerGameFiles/" + player.getUsername() + "Game.txt").getPath());
        if (file.exists() && !override) {
            throw new RuntimeException();
        }

        try {
            file.createNewFile();
        } catch (IOException e) {
            System.out.println(">> Could not create the file");
        }

        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(file));

            // 1. save the phrase
            bw.write(cryptogram.getPhrase());
            bw.newLine();

            // 2. save the alphabet
            bw.write("Alphabet:");
            bw.newLine();

            for (Map.Entry<String, String> entry : cryptogram.getCryptogramAlphabet().entrySet()) {
                bw.write(entry.getKey() + ":" + entry.getValue()); // write realLetter:encryptedValue to the file
                bw.newLine();
            }

            // 3. save the solution
            bw.write("Solution:");
            bw.newLine();

            for (Map.Entry<String, String> entry : currentSolution.entrySet()) {
                bw.write(entry.getKey() + ":" + entry.getValue()); // write guess:encryptedValue to the file
                bw.newLine();
            }

            bw.flush();
            bw.close();
        } catch (Exception e) {
            System.err.println(">> Could not save cryptogram: " + e.getMessage());
        }
    }

    public ArrayList<String> getScoreboard() {
        HashMap<Player, Integer> gamesCompleted = playerList.getAllPlayersCompletedGames();
        ArrayList<Map.Entry<Player, Integer>> scoreboardToSort = new ArrayList<>(gamesCompleted.entrySet());
        scoreboardToSort.sort(Collections.reverseOrder(Map.Entry.comparingByValue()));


        ArrayList<String> scoreboard = new ArrayList<>();
        for (Map.Entry<Player, Integer> entry : scoreboardToSort) {
            if (entry.getValue() != 0) {
                scoreboard.add(entry.getKey().getUsername() + " - " + entry.getValue());
            }
        }
        return scoreboard;
    }

    /**
     * Shows hints by how common they are in the english language
     */
    public void hint() {
        String[] commonLetters = cryptogram.getCryptogramAlphabet().keySet().toArray(new String[0]); // all used letters in the cryptogram
        for (String letter : commonLetters) {
            if (isLetterAlreadyUsed(letter)) { //Checks if letter is used
                if (!isGuessCorrect(letter, getCurrentSolution().get(letter))) { //If letter is used and incorrect it will be replaced
                    undoLetter(new String[]{"undo", letter});
                    remapLetter(letter, cryptogram.getCryptogramAlphabet().get(letter));
                    System.out.println("The letter " + letter + " replaced your wrong guess.");
                    return;
                }
            } else {
                remapLetter(letter, cryptogram.getCryptogramAlphabet().get(letter)); //if letter isn't used add the hint letter
                System.out.println("The letter " + letter + " is revealed.");
                return;
            }
        }
    }
}
