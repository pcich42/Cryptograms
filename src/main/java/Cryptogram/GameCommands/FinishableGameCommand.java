package Cryptogram.GameCommands;

import Cryptogram.Cryptogram;
import Cryptogram.GameCommand;
import Cryptogram.View;
import Cryptogram.Player;

public abstract class FinishableGameCommand implements GameCommand {

    protected Cryptogram cryptogram;
    protected View view;
    protected Player player;

    public FinishableGameCommand(Cryptogram cryptogram,
                                 View view,
                                 Player player) {
        this.cryptogram = cryptogram;
        this.view = view;
        this.player = player;
    }

    protected boolean isFinished() {
        if (!cryptogram.isSolutionFull()) {
            return false;
        }

        if (cryptogram.isSolutionCorrect()) {
            // player won
            view.displayCryptogram(cryptogram);
            view.displayMessage(">> Congratulations!!! You have successfully completed this cryptogram\n" +
                    ">> Try again with a different one now.");
            player.incrementCryptogramsCompleted();
            return true;
        } else {
            // game finished unsuccessfully, keep playing
            view.displayMessage(">> Somethings is off here. Undo some letters to map them again.");
            return false;
        }
    }
}
