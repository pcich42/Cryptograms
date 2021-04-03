package Cryptogram;

import java.util.*;

/**
 * The class responsible for user interaction - getting input and commands from the player
 */
public class View {

    private final Scanner scanner;

    /**
     * Constructor for Cryptogram.View class
     */
    public View(Scanner scanner) {
        this.scanner = scanner;
    }

    /**
     * This method prompt user for input and returns it as an array of Strings
     *
     * @return Array of string tokens generated from players input
     */
    public String[] getInput() {
        String line = scanner.nextLine().toLowerCase();
        return line.split(" ");
    }

    /**
     * Method to display the current solution and encrypted phrase
     */
    public void displayCryptogram(Cryptogram cryptogram) {
        System.out.println();

        String decryptedPhrase = "";
        String encryptedPhrase = "";

        for (char value : cryptogram.getPhrase().toCharArray()) {
            if (value == ' ') {
                decryptedPhrase = decryptedPhrase.concat(" ");
                encryptedPhrase = encryptedPhrase.concat(" ");
            } else {
                // get length of the key and add that many spaces
                String correctKey = cryptogram.getCryptogramAlphabet().get(String.valueOf(value));
                String decryptedValue = "";
                if (cryptogram.getSolution().containsValue(correctKey)) {
                    for (String key : cryptogram.getSolution().keySet())
                        if (cryptogram.getSolution().get(key).equals(correctKey)) {
                            decryptedValue = key;
                        }
                } else {
                    decryptedValue = "_";
                }

                String encryptedValue = cryptogram.getCryptogramAlphabet().get(String.valueOf(value));
                if (encryptedValue.length() == 2) {
                    decryptedPhrase = decryptedPhrase.concat(" ");
                }
                decryptedPhrase = decryptedPhrase.concat(decryptedValue);
                encryptedPhrase = encryptedPhrase.concat(encryptedValue);
            }
            decryptedPhrase = decryptedPhrase.concat(" ");
            encryptedPhrase = encryptedPhrase.concat(" ");
        }

        System.out.println(decryptedPhrase);
        System.out.println(encryptedPhrase);

    }

    /**
     * This method prompts the user to confirm if they want to remap a letter
     *
     * @return true if user confirms, false if they don't
     */
    public boolean confirmChoice() {

        boolean answer;
        while (true) {
            String confirmation = scanner.nextLine();
            if (confirmation.equalsIgnoreCase("y")
                    || confirmation.equalsIgnoreCase("yes")) {
                answer = true;
                break;
            } else if (confirmation.equalsIgnoreCase("n")
                    || confirmation.equalsIgnoreCase("no")) {
                answer = false;
                break;
            } else {
                System.out.println("Invalid response, try again with \"yes\" or \"no\" ");
            }
        }
        return answer;
    }

    /**
     * Prompts user for a username
     *
     * @return inputted username
     */
    public String promptForUsername() {
        System.out.println("Welcome to the cryptogram game!\n" +
                "Please enter your name: ");
        return scanner.nextLine();
    }

}
