package Cryptogram.Commands.GameCommands;

import Cryptogram.Models.Cryptogram;
import Cryptogram.Interfaces.GameCommand;
import Cryptogram.Views.InputPrompt;
import Cryptogram.Views.View;
import Cryptogram.Models.Player;
import Cryptogram.Interfaces.ICryptogramManager;

public class exitCommand implements GameCommand {

    private final Cryptogram cryptogram;
    private final Player player;
    private final ICryptogramManager manager;
    private final View view;
    private final InputPrompt prompt;

    public exitCommand(Cryptogram cryptogram,
                       Player player,
                       ICryptogramManager manager,
                       View view,
                       InputPrompt prompt) {
        this.cryptogram = cryptogram;
        this.player = player;
        this.manager = manager;
        this.view = view;
        this.prompt = prompt;
    }

    @Override
    public boolean execute() {
        askToSaveCurrentGame();
        return true;
    }

    private void askToSaveCurrentGame() {
        view.displayMessage("Do you wish to save before you exit?");
        if (prompt.confirmChoice()) {
            GameCommand command = new saveGameCommand(cryptogram, player, manager, prompt, view);
            command.execute();
        }
    }

}
