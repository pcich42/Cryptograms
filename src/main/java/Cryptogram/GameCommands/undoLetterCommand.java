package Cryptogram.GameCommands;

import Cryptogram.GameCommand;
import Cryptogram.Cryptogram;
import Cryptogram.View;

public class undoLetterCommand implements GameCommand {

    private final Cryptogram cryptogram;
    private final String[] input;
    private final View view;

    public undoLetterCommand(String[] input, Cryptogram cryptogram, View view) {
        this.cryptogram = cryptogram;
        this.input = input;
        this.view = view;
    }

    @Override
    public boolean execute() {
        if (input.length != 2) {
            view.displayMessage("Invalid number of arguments");
            return false;
        }

        String remove = input[1];
        if (!cryptogram.isLetterAlreadyUsed(remove)) {
            view.displayMessage(">> The letter you are trying to delete is not in play");
            return false;
        }

        cryptogram.removeMapping(remove);
        return false;
    }

}
