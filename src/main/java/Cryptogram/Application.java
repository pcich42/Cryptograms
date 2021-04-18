package Cryptogram;

import Cryptogram.MenuCommands.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Supplier;

public class Application {

    private Player player;
    private final IPlayers playerList;
    private final ICryptogramManager manager;
    private final InputPrompt prompt;
    private final View view;

    public Application(IPlayers playerList,
                       ICryptogramManager manager,
                       View view,
                       InputPrompt prompt) {
        this.playerList = playerList;
        this.manager = manager;
        this.prompt = prompt;
        this.view = view;
    }

    public Application() {
        this(
            new Players(),
            new CryptogramManager(),
            new View(),
            new InputPrompt(new Scanner(System.in))
        );
    }

    protected Player fetchPlayer() {
        String username = prompt.promptForUsername();
        Player player = playerList.getPlayer(username);
        if (player == null) {
            view.displayMessage("Player doesn't yet exist, creating a new player profile.");
            playerList.addPlayer(username);
            player = playerList.getPlayer(username);
        }
        return player;
    }

    public void run() {
        if(!playerList.isInstantiated()){
            return;
        }

        setUpPlayer();
        view.displayHelp();
        String[] input;
        while (!(input = prompt.getInput("Choose what do you want to do next."))[0].contains("exit")) {
            executeMenuCommand(input);
        }
        playerList.savePlayerDetails();
    }

    protected MenuCommand executeMenuCommand(String[] input) {
        MenuCommand command = fetchCommand(input);
        command.perform();
        return command;
    }

    protected void setUpPlayer() {
        this.player = fetchPlayer();
    }

    private MenuCommand fetchCommand(String[] input) {
        Map<String, Supplier<MenuCommand>> commands = new HashMap<>();

        // register new menu commands here
        commands.put("new", () -> new playGeneratedCryptogramGameCommand(player, playerList, manager, view, prompt, input));
        commands.put("load", () -> new playLoadedCryptogramGameCommand(player, playerList,  manager, view, prompt));
//        commands.put("login", () -> new changePlayerCommand(playerList));
        commands.put("scores", () -> new showScoreboardCommand(playerList, view));

        return commands.get(input[0]).get();
    }

}