package Cryptogram;

import java.io.*;
import java.util.*;
import java.util.function.Function;

class Game {

    private Player player;
    private final IPlayers playerList;
    private Cryptogram cryptogram;
    private final IView view;
    private final ICryptogramManager manager;
    private final Map<String, Function<String[], Boolean>> commands;

    public Game(IPlayers Players, ICryptogramManager manager, IView view) {
        this.commands = getCommands();
        this.view = view;
        this.playerList = Players;
        this.manager = manager;
    }

    private HashMap<String, Function<String[], Boolean>> getCommands() {
        HashMap<String, Function<String[], Boolean>> commands = new HashMap<>();

        // add new commands here
        // they have to return a boolean and take exactly one argument - array of Strings (user input)
        commands.put("undo", this::undoLetter);
        commands.put("new", this::generateNewCryptogram);
        commands.put("load", this::loadGame);
        commands.put("save", this::saveGame);
        commands.put("solution", this::showSolution);
        commands.put("scores", this::showScoreboard);
        commands.put("frequencies", this::showFrequencies);
        commands.put("hint", this::hint);
        commands.put("help", this::displayHelp);
        commands.put("exit", (String[] s) -> true);

        return commands;
    }

    public void play() {

        displayHelp(new String[0]);
        while (true) {
            view.displayCryptogram(cryptogram);
            System.out.println("Type help to list all available commands.");
            System.out.println(">> Please enter a mapping in format <letter><space><cryptogram value>:");
            String[] input = view.getInput();
            // the caller of commands
            // to add command put it in getCommands method in commands array and add
            // to break out of the loop make sure the command returns true
            if (commands.getOrDefault(input[0], this::enterLetter).apply(input)) {
                // game exits - state is saved
                playerList.savePlayers();
                break;
            }
        }
    }

    public void setup() {
        String username = view.promptForUsername();
        this.player = playerList.getPlayer(username);
        if (player == null) {
            System.out.println("Player doesn't yet exist, creating a new player profile.");
            playerList.addPlayer(username);
            player = playerList.getPlayer(username);
        }

        System.out.println(">> If you want to play a new cryptogram, type: new <[letters]/numbers>.");
        System.out.println(">> If you want to play a saved cryptogram, type: load.");
        String[] input = view.getInput();
        // case 1: player chooses load
        if (input[0].equals("load")) {
            try {
                // successful load
                cryptogram = manager.loadCryptogram(player.getUsername() + "Game.txt");
            } catch (IOException e) {
                // unsuccessful load - prompt for a new cryptogram
                System.out.println(">> No game saved. Type: new <[letters]/numbers> - to create a new cryptogram.");
                input = view.getInput();
                generateNewCryptogram(input);
            } catch (IllegalStateException e2) {
                System.out.println(">> Invalid File Format. Type: new <[letters]/numbers> - to create a new cryptogram.");
                input = view.getInput();
                generateNewCryptogram(input);
            }
            // case 2: player chooses save
        } else {
            generateNewCryptogram(input);
        }
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
     * @return player
     */
    public Player getPlayer() {
        return player;
    }

    public IView getView() {
        return view;
    }

    /**
     * This method maps the two letters from input to solution
     * @param input users input
     */
    public boolean enterLetter(String[] input) {
        if (isInputValid(input)) {
            String guess = input[0];
            String valueToMap = input[1];
            if (cryptogram.isAlreadyMapped(valueToMap)) {
                System.out.println(">> This value is already mapped to. Do you want to override this mapping?");
                if(view.confirmChoice()){
                    cryptogram.remapLetters(guess, valueToMap);
                    player.updateAccuracy(cryptogram.isGuessCorrect(guess, valueToMap));
                }
            } else {
                cryptogram.mapLetters(guess, valueToMap);
                player.updateAccuracy(cryptogram.isGuessCorrect(guess, valueToMap));
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
        if (cryptogram.isSolutionFull()) {
            if(cryptogram.isSolutionCorrect()) {
                // player won
                view.displayCryptogram(cryptogram);
                System.out.println(">> Congratulations!!! You have successfully completed this cryptogram\n" +
                        ">> Try again with a different one now.");
                player.incrementCryptogramsCompleted();
                return true;
            } else {
                // game finished unsuccessfully, keep playing
                System.out.println(">> Somethings is off here. Undo some letters to map them again.");
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * This method check if input is in correct format, if the crypto value is in the alphabet
     * and if the letter guessed is not already in use.
     * It does not check if crypto value already has a letter mapped to it.
     *
     * @param input = input from the user
     * @return returns true if input is valid and ready to be passed to enterLetter
     */
    public boolean isInputValid(String[] input) {
        if (!(input.length == 2 &&
                input[0].length() == 1 &&
                (input[1].length() == 1 || input[1].length() == 2))) {
            System.out.println(">> Couldn't enter this mapping. Try again.");
            return false;
        }

        String guess = input[0];
        String valueToMap = input[1];

        if (!cryptogram.valueInAlphabet(valueToMap)) {
            System.out.println(">> The value you're trying to map to, is not present in this cryptogram.\n" +
                    ">> Try again with a different value.");
            return false;
        } else if (cryptogram.isLetterAlreadyUsed(guess)) {
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
    public boolean undoLetter(String[] input) {
        if (input.length == 2) {
            String remove = input[1];
            if (!cryptogram.isLetterAlreadyUsed(remove)) {
                System.out.println(">> The letter you are trying to delete is not in play");
            } else {
                cryptogram.removeMapping(remove);
            }
        } else {
            System.out.println("Invalid number of arguments");
        }
        return false;
    }

    public boolean loadGame(String[] input) {
        try {
            cryptogram = manager.loadCryptogram(player.getUsername() + "Game.txt");
        } catch (IOException e) {
            System.out.println(">> No game saved. Save a game using 'save' before loading.");
        } catch (IllegalStateException e2) {
            System.out.println(">> Couldn't load. Invalid file format.");
        }
        return false;
    }

    public boolean saveGame(String[] input) {
        String path = player.getUsername() + "Game.txt";

        if (manager.fileAlreadyExists(path)) {
            System.out.println(">> Do you wish to overwrite currently stored game?");
            if(view.confirmChoice()) {
                manager.saveCryptogramToFile(cryptogram, path);
            }
        } else {
            manager.saveCryptogramToFile(cryptogram, path);
        }
        return false;
    }

    public boolean generateNewCryptogram(String[] input) {
        int index = (new Random()).nextInt(15) + 1;
        String path = "quote" + index + ".txt";
        if (input.length != 2) {
            // default option
            input = new String[]{"new", "letters"};
        }
        try {
            cryptogram = manager.generateCryptogram(input[1], path);
        } catch (IOException ioException) {
            System.out.println("Cryptograms file doesn't exists, Exiting...");
            return true;
        }
        player.incrementCryptogramsPlayed();
        return false;
    }

    /**
     * Shows hints by how common they are in the english language
     */
    public boolean hint(String[] input) {
        //TODO: simplify
        String[] commonLetters = cryptogram.getCryptogramAlphabet().keySet().toArray(new String[0]); // all used letters in the cryptogram
        for (String letter : commonLetters) {
            if (cryptogram.isLetterAlreadyUsed(letter)) { //Checks if letter is used
                if (!cryptogram.isGuessCorrect(letter, cryptogram.getSolution().get(letter))) { //If letter is used and incorrect it will be replaced
                    undoLetter(new String[]{"undo", letter});
                    cryptogram.remapLetters(letter, cryptogram.getCryptogramAlphabet().get(letter));
                    System.out.println("The letter " + letter + " replaced your wrong guess.");
                    return isGameFinishedRoutine();
                }
            } else {
                cryptogram.remapLetters(letter, cryptogram.getCryptogramAlphabet().get(letter)); //if letter isn't used add the hint letter
                System.out.println("The letter " + letter + " is revealed.");
                return isGameFinishedRoutine();
            }
        }
        return true;
    }

    public Boolean showScoreboard(String[] input) {
        HashMap<Player, Integer> gamesCompleted = playerList.getAllPlayersCompletedGames();
        ArrayList<Map.Entry<Player, Integer>> scoreboardToSort = new ArrayList<>(gamesCompleted.entrySet());
        scoreboardToSort.sort(Collections.reverseOrder(Map.Entry.comparingByValue()));

        ArrayList<String> scoreboard = new ArrayList<>();
        for (Map.Entry<Player, Integer> entry : scoreboardToSort) {
            if (entry.getValue() != 0) {
                scoreboard.add(entry.getKey().getUsername() + " - " + entry.getValue());
            }
        }
        if (scoreboard.size() != 0){
            System.out.println("Top 10 players by number of successfully completed cryptograms: ");
            Iterator<String> it = scoreboard.iterator();
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

    public Boolean showSolution(String[] strings) {

        cryptogram.fillSolution();
        view.displayCryptogram(cryptogram);
        System.out.println("Cryptogram.Game finished. Try again.");

        return true;

    }

    public Boolean showFrequencies(String[] strings) {
        System.out.println("The frequencies of letters in English language are as follows:");
        System.out.println(Cryptogram.getEnglishFrequencies());
        System.out.println("Current cryptogram has the following frequencies:");

        System.out.println(cryptogram.getFrequencies());
        return false;
    }

    /**
     * user can call this command to display help
     *
     * @param input user input - needed for all commands
     * @return always false
     */
    private boolean displayHelp(String[] input) {
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
