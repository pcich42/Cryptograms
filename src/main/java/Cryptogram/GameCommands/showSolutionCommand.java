package Cryptogram.GameCommands;

import Cryptogram.GameCommand;
import Cryptogram.Cryptogram;
import Cryptogram.View;

public class showSolutionCommand implements GameCommand {

    private final Cryptogram cryptogram;
    private final View view;

    public showSolutionCommand(Cryptogram cryptogram, View view) {
        this.cryptogram = cryptogram;
        this.view = view;
    }

    @Override
    public boolean execute() {
        cryptogram.fillSolution();
        view.displayCryptogram(cryptogram);
        view.displayMessage("Game finished. Try again.");
        return true;
    }

}
