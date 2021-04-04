package Cryptogram;

import java.util.*;

/**
 * The class responsible for user interaction - getting input and commands from the player
 */
public class View implements IView {

    protected Scanner scanner;

    /**
     * Constructor for Cryptogram.View class
     */
    public View() {
        this.scanner = new Scanner(System.in);
    }

    /**
     * This method prompt user for input and returns it as an array of Strings
     *
     * @return Array of string tokens generated from players input
     */
    @Override
    public String[] getInput() {
        String line = scanner.nextLine().toLowerCase();
        return line.split(" ");
    }

    /**
     * This method prompts the user to confirm if they want to remap a letter
     *
     * @return true if user confirms, false if they don't
     */
    @Override
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
    @Override
    public String promptForUsername() {
        System.out.println("Welcome to the cryptogram game!\n" +
                "Please enter your name: ");
        return scanner.nextLine();
    }

}
