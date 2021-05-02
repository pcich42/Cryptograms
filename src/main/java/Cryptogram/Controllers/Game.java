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
    private final GameCommandSupplier commands;

    public Game(
            Player player,
            Cryptogram cryptogram,
            ICryptogramManager manager,
            IPlayers players,
            InputPrompt prompt,
            View view,
            GameCommandSupplier commands) {
        this.view = view;
        this.player = player;
        this.cryptogram = cryptogram;
        this.prompt = prompt;
        this.manager = manager;
        this.playerList = players;
        this.commands = commands;
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
        GameCommand gameCommand = commands.fetchCommand(
                input,
                cryptogram,
                player,
                manager,
                playerList,
                prompt,
                view);
        return gameCommand.execute();
    }

    protected Cryptogram getCryptogram() {
        return cryptogram;
    }

    protected Player getPlayer() {
        return player;
    }

}
