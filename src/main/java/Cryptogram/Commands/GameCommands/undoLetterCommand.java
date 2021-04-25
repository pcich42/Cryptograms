package Cryptogram.Commands.GameCommands;

import Cryptogram.Exceptions.GuessNotUsedException;
import Cryptogram.Interfaces.GameCommand;
import Cryptogram.Models.Cryptogram;
import Cryptogram.Views.View;

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

        try {
            cryptogram.removeMapping(remove);
        } catch (GuessNotUsedException e) {
            view.displayMessage(">> The letter you are trying to delete is not in play");
        }
        return false;
    }

}
