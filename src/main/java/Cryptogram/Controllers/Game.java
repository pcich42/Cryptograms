package Cryptogram.Controllers;

import Cryptogram.Interfaces.GameCommand;
import Cryptogram.Interfaces.ICryptogramManager;
import Cryptogram.Interfaces.IPlayers;
import Cryptogram.Commands.MenuCommands.showScoreboardCommand;
import Cryptogram.Models.Cryptogram;
import Cryptogram.Models.Player;
import Cryptogram.Commands.GameCommands.*;
import Cryptogram.Views.InputPrompt;
import Cryptogram.Views.View;

import java.util.*;
import java.util.function.Supplier;

public class Game {

    private final Player player;
    private final Cryptogram cryptogram;
    private final View view;
    private final InputPrompt prompt;
    private final ICryptogramManager manager;
    private final IPlayers playerList;

    public Game(
            Player player,
            Cryptogram cryptogram,
            ICryptogramManager manager,
            IPlayers players,
            InputPrompt prompt,
            View view) {
        this.view = view;
        this.player = player;
        this.cryptogram = cryptogram;
        this.prompt = prompt;
        this.manager = manager;
        this.playerList = players;
    }

    public void play() {
        view.displayHelp();
        boolean isExitRequested = false;
        while (!isExitRequested) {
            view.displayCryptogram(cryptogram);
            view.displayMessage("Type help to list all available commands.");
            view.displayMessage(">> Please enter a mapping in format <letter><space><cryptogram value>:");
            isExitRequested = executeCommand(prompt.getInput());
        }
    }

    protected boolean executeCommand(String[] input) {
        GameCommand gameCommand = fetchCommand(input);
        return gameCommand.execute();
    }

    private GameCommand fetchCommand(String[] input) {
        HashMap<String, Supplier<GameCommand>> commands = new HashMap<>();

        // add new game commands here

        commands.put("undo", () -> new undoLetterCommand(input, cryptogram, view));
        commands.put("save", () -> new saveGameCommand(cryptogram, player, manager, prompt, view));
        commands.put("solution", () -> new showSolutionCommand(cryptogram, view));
        commands.put("scores", () -> new showScoreboardCommand(playerList, view));
        commands.put("frequencies", () -> new showFrequenciesCommand(cryptogram, view));
        commands.put("hint", () -> new showHintCommand(cryptogram, view, player));
        commands.put("help", () -> new displayHelpCommand(view));
        commands.put("exit", () -> new exitCommand(cryptogram, player, manager, view, prompt));

        return commands.getOrDefault(
                input[0],
                () -> new enterLetterCommand(input, cryptogram, player, prompt, view))
                .get();
    }

    protected Cryptogram getCryptogram() {
        return cryptogram;
    }

    protected Player getPlayer() {
        return player;
    }

}
