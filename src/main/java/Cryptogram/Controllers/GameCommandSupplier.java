package Cryptogram.Controllers;

import Cryptogram.Commands.GameCommands.*;
import Cryptogram.Commands.MenuCommands.showScoreboardCommand;
import Cryptogram.Interfaces.GameCommand;
import Cryptogram.Interfaces.ICryptogramManager;
import Cryptogram.Interfaces.IPlayers;
import Cryptogram.Models.Cryptogram;
import Cryptogram.Models.Player;
import Cryptogram.Views.InputPrompt;
import Cryptogram.Views.View;

import java.util.HashMap;
import java.util.function.Supplier;

public class GameCommandSupplier {

    public GameCommand fetchCommand(
            String[] input,
            Cryptogram cryptogram,
            Player player,
            ICryptogramManager manager,
            IPlayers playerList,
            InputPrompt prompt,
            View view) {
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

}
