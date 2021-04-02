package Cryptogram;

import java.io.IOException;
import java.util.*;
import java.util.function.Function;

/**
 * The class responsible for user interaction - getting input and commands from the player
 */
public class View {

    private final Scanner scanner;
    private Game game;
    private final HashMap<String, Function<String[], Boolean>> commands;

    /**
     * Constructor for Cryptogram.View class
     */
    public View() {
        this.scanner = new Scanner(System.in);
        this.commands = getCommands();
        String username = promptForUsername();
        try {
            game = new Game(username);
        } catch (IOException e) {
            System.out.println("Players File invalid, Exiting...");
            System.exit(1);
        }
        System.out.println(">> If you want to play a new cryptogram, type: new <[letters]/numbers>.");
        System.out.println(">> If you want to play a saved cryptogram, type: load.");
        String[] input = getInput();
        createGame(input);
    }

    /**
     * Main loop of the game
     */
    public void mainLoop() {
        displayHelp(new String[0]);
        while (true) {
            displayCryptogram();
            System.out.println("Type help to list all available commands.");
            System.out.println(">> Please enter a mapping in format <letter><space><cryptogram value>:");
            String[] input = getInput();
            // the caller of commands
            // to add command put it in getCommands method in commands array and add
            // to break out of the loop make sure the command returns true
            if (performCommand(input)) {
                // game exits - state is saved
                game.updatePlayersFile();
                break;
            }
        }

        scanner.close();
    }

    private void createGame(String[] input) {

        // case 1: player chooses load
        if (input[0].equals("load")) {
            try {
                // successful load
                game.loadCryptogram();
            } catch (IOException e) {
                // unsuccessful load - prompt for a new cryptogram
                System.out.println(">> No game saved. Type: new <[letters]/numbers> - to create a new cryptogram.");
                input = getInput();
                generateNewCryptogram(input);
            } catch (IllegalStateException e2) {
                System.out.println(">> Invalid File Format. Type: new <[letters]/numbers> - to create a new cryptogram.");
                input = getInput();
                generateNewCryptogram(input);
            }
        } else {
            // prompt for new cryptogram
            generateNewCryptogram(input);
        }

    }

    /**
     * getter for game
     *
     * @return game field
     */
    public Game getGame() {
        return game;
    }

    /**
     * helper method
     *
     * @return map from command string to function which a player can invoke
     */
    private HashMap<String, Function<String[], Boolean>> getCommands() {
        HashMap<String, Function<String[], Boolean>> commands = new HashMap<>();

        // add new commands here
        // they have to return a boolean and take exactly one argument - array of Strings (user input)
        commands.put("undo", this::undoLetter);
        commands.put("new", this::generateNewCryptogram);
        commands.put("load", this::loadCryptogram);
        commands.put("save", this::saveCryptogram);
        commands.put("solution", this::showSolution);
        commands.put("scores", this::showScoreboard);
        commands.put("frequencies", this::showFrequencies);
        commands.put("hint", this::hint);
        commands.put("help", this::displayHelp);
        commands.put("exit", this::exit);

        return commands;
    }

    private Boolean showFrequencies(String[] strings) {
        System.out.println("The frequencies of letters in English language are as follows:");
        System.out.println(Cryptogram.getFrequencies());
        System.out.println("Current cryptogram has the following frequencies:");

        System.out.println(game.getFrequencies());
        return false;
    }

    private Boolean showScoreboard(String[] strings) {
        ArrayList<String > scoreboard = game.getScoreboard();
        if (scoreboard.size() != 0){
            System.out.println("Top 10 players by number of successfully completed cryptograms: ");
            Iterator<String> it = game.getScoreboard().iterator();
            int i = 1;
            while(i < 11){
                System.out.print(i + ": ");
                if (it.hasNext()){
                    System.out.print(it.next());
                }
                System.out.println();
                i++;
            }
        } else {
            System.out.println("No players in the scoreboard.");
        }
        return false;
    }

    private Boolean showSolution(String[] strings) {

        game.showSolution();
        displayCryptogram();
        System.out.println("Cryptogram.Game finished. Try again.");

        return true;

    }

    public boolean hint(String[] input) {
        game.hint();

        return isGameFinishedRoutine();
    }

    /**
     * calls requested command
     *
     * @param input user input where first token is the name of the command
     * @return true if game has finished, false otherwise
     */
    public boolean performCommand(String[] input) {
        return commands.getOrDefault(input[0], this::performMapping).apply(input);
    }


    /**
     * Performs the the mapping routine - updates stats afterwards
     *
     * @param input user input
     * @return true if the input was successfully mapped, false otherwise
     */
    public boolean performMapping(String[] input) {
        try {
            game.enterLetter(input, false);
        } catch (IllegalStateException e1) {
            System.out.println(">> This value is already mapped to. Do you want to override this mapping?");
            try {
                game.enterLetter(input, confirmChoice());
            } catch (IllegalStateException e2) {
                return isGameFinishedRoutine();
            }
        }
        return isGameFinishedRoutine();
    }

    /**
     * Handles the finish game mechanic
     *
     * @return true if game has been finished, false otherwise
     */
    public boolean isGameFinishedRoutine() {
        int finished = game.isSuccessfullyFinished();
        if (finished == 1) {
            // player won
            displayCryptogram();
            System.out.println(">> Congratulations!!! You have successfully completed this cryptogram\n" +
                    ">> Try again with a different one now.");
            // display an end screen with options to switch player, generate new cryptogram etc...
            return true;
        } else if (finished == -1) {
            // game finished unsuccessfully, keep playing
            System.out.println(">> Somethings is off here. Undo some letters to map them again.");
            return false;
        } else {
            // game not finished, keep playing
            return false;
        }
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
    public void displayCryptogram() {
        System.out.println();

        String decryptedPhrase = "";
        String encryptedPhrase = "";

        for (char value : game.getCryptogram().getPhrase().toCharArray()) {
            if (value == ' ') {
                decryptedPhrase = decryptedPhrase.concat(" ");
                encryptedPhrase = encryptedPhrase.concat(" ");
            } else {
                // get length of the key and add that many spaces
                String correctKey = game.getCryptogram().getCryptogramAlphabet().get(String.valueOf(value));
                String decryptedValue = "";
                if (game.getCurrentSolution().containsValue(correctKey)) {
                    for (String key : game.getCurrentSolution().keySet())
                        if (game.getCurrentSolution().get(key).equals(correctKey)) {
                            decryptedValue = key;
                        }
                } else {
                    decryptedValue = "_";
                }


                String encryptedValue = game.getCryptogram().getCryptogramAlphabet().get(String.valueOf(value));
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

    /**
     * wrapper for undoLetter
     *
     * @param input user input
     * @return always false
     */
    public boolean undoLetter(String[] input) {
        try {
            game.undoLetter(input);
        } catch (IllegalStateException e1) {
            System.out.println(">> The letter you are trying to delete is not in play");
        } catch (IndexOutOfBoundsException e2) {
            System.out.println("Invalid number of arguments");
        }
        return false;
    }

    /**
     * Wrapper for exiting the app
     *
     * @param input user input - needed for all commands
     * @return always true
     */
    public boolean exit(String[] input) {
        return true;
    }

    private boolean loadCryptogram(String[] input) {
        try {
            game.loadCryptogram();
        } catch (IOException e) {
            System.out.println(">> No game saved. Save a file using 'save' before loading.");
        } catch (IllegalStateException e2) {
            System.out.println(">> Couldn't load. Invalid file format.");
        }
        return false;
    }

    private boolean saveCryptogram(String[] input) {
        try {
            // checks if file exists - if so it throws exception
            game.saveGameToFile(false);
        } catch (RuntimeException e1) {
            System.out.println(">> Do you wish to overwrite currently stored game?");
            try {
                // depending on what player says either overwrite or don't
                game.saveGameToFile(confirmChoice());
            } catch (RuntimeException e2) {
                return false;
            }
        }
        return false;
    }

    private boolean generateNewCryptogram(String[] input) {
        if (input.length != 2) {
            // default option
            input = new String[]{"new", "letters"};
        }
        try {
            game.generateCryptogram(input[1]);
        } catch (IOException ioException) {
            System.out.println("Cryptograms file doesn't exists, Exiting...");
            game.updatePlayersFile();
            System.exit(1);
        }
        return false;
    }

    /**
     * user can call this command to display help
     *
     * @param input user input - needed for all commands
     * @return always false
     */
    public boolean displayHelp(String[] input) {
        System.out.println("Here are all available commands:");
        System.out.println("<guess> <encrypted value>:              To map a letter.");
        System.out.println("undo <guessed letter to be removed>:    To undo a mapping.");
        System.out.println("new <cryptogram type[letters/numbers]>: To generate a new game.");
        System.out.println("load:                                   To load saved game.");
        System.out.println("save:                                   To save a game for later.");
        System.out.println("frequencies:                            To see the frequencies.");
        System.out.println("hint:                                   To get a hint.");
        System.out.println("solution:                               To see the solution and finish this game.");
        System.out.println("scores:                                 To see the scoreboard.");
        System.out.println("help:                                   To get help.");
        System.out.println("exit:                                   To exit.");
        return false;
    }

}
