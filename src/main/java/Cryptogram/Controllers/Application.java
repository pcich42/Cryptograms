package Cryptogram.Controllers;

import Cryptogram.Interfaces.ICryptogramManager;
import Cryptogram.Interfaces.IPlayers;
import Cryptogram.Interfaces.MenuCommand;
import Cryptogram.Commands.MenuCommands.*;
import Cryptogram.Models.Player;
import Cryptogram.Views.InputPrompt;
import Cryptogram.Views.View;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
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
        if (!loadPlayers()) return;

        player = fetchPlayer();
        view.displayMessage("Welcome " + player.getUsername() + "!") ;
        view.displayHelp();
        mainLoop();
        playerList.savePlayerDetails();
    }

    private void mainLoop() {
        while (true) {
            String[] input = prompt.getInput("Choose what do you want to do next.");
            if (input[0].contains("exit")) break;
            executeMenuCommand(input);
        }
    }

    private boolean loadPlayers() {
        try {
            playerList.loadPlayersFromFile();
        } catch (FileNotFoundException notFoundException) {
            view.displayMessage("Players file not found. Exiting...");
            return false;
        } catch (IOException ioException) {
            view.displayMessage("File format invalid. Exiting...");
            return false;
        }
        return true;
    }

    protected void executeMenuCommand(String[] input) {
        MenuCommand command = fetchCommand(input);
        command.perform();
    }

    private MenuCommand fetchCommand(String[] input) {
        // supplier used to deffer initialization
        Map<String, Supplier<MenuCommand>> commands = new HashMap<>();
        GameCommandSupplier gameCommands = new GameCommandSupplier();

        // register new menu commands here
        commands.put("new", () -> new playGeneratedCryptogramGameCommand(player, playerList, manager, view, prompt, input, gameCommands));
        commands.put("load", () -> new playLoadedCryptogramGameCommand(player, playerList, manager, view, prompt, gameCommands));
        commands.put("scores", () -> new showScoreboardCommand(playerList, view));

        return commands.getOrDefault(input[0], () -> () -> view.displayMessage("Such command does not exist, please try again")).get();
    }

}
