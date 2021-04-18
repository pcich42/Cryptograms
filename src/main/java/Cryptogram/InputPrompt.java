package Cryptogram;

import java.util.Arrays;
import java.util.Scanner;

public class InputPrompt {

    protected Scanner scanner;

    /**
     * Constructor for InputPrompt class
     */
    public InputPrompt(Scanner scanner) {
        this.scanner = scanner;
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

    /**
     * This method prompt user for input and returns it as an array of Strings
     *
     * @return Array of string tokens generated from players input
     */
    public String[] getInput() {
        String line = scanner.nextLine().toLowerCase();
        return line.split(" ");
    }

    public String[] getInput(String string) {
        System.out.println(string);
        String line = scanner.nextLine().toLowerCase();
        return line.split(" ");
    }

    public void injectInput(String[] input) {

        scanner.close();
        String result = Arrays.stream(input).reduce((String acc, String next) -> acc + "\n" + next ).get();
        scanner = new Scanner(result);
    }
}
