package Cryptogram.Commands.GameCommands;

import Cryptogram.Exceptions.GuessAlreadyUsedException;
import Cryptogram.Exceptions.ValueAlreadyMappedException;
import Cryptogram.Exceptions.ValueNotInCryptogramException;
import Cryptogram.Models.Cryptogram;
import Cryptogram.Models.Player;
import Cryptogram.Views.InputPrompt;
import Cryptogram.Views.View;

public class enterLetterCommand extends FinishableGameCommand {

    private final InputPrompt prompt;
    private final String[] input;

    public enterLetterCommand(String[] input,
                              Cryptogram cryptogram,
                              Player player,
                              InputPrompt prompt,
                              View view) {
        super(cryptogram, view, player);
        this.prompt = prompt;
        this.input = input;
    }

    @Override
    public boolean execute() {
        if (!hasCorrectLength(input)) return false;

        String guess = input[0];
        String valueToMap = input[1];

        mapGuess(guess, valueToMap);
        return super.isFinished();
    }

    private void askToRemap(String guess, String valueToMap) {
        view.displayMessage(">> This value is already mapped to. Do you want to override this mapping?");

        if (prompt.confirmChoice()) {
            try {
                cryptogram.remapLetters(guess, valueToMap);
                player.updateAccuracy(cryptogram.isGuessCorrect(guess, valueToMap));
            } catch (ValueNotInCryptogramException e) {
                view.displayMessage("This value is not in the cryptogram, try a different one.");
            }
        }
    }

    private void mapGuess(String guess, String valueToMap) {
        try {
            cryptogram.mapLetters(guess, valueToMap);
            player.updateAccuracy(cryptogram.isGuessCorrect(guess, valueToMap));
        } catch (GuessAlreadyUsedException e) {
            view.displayMessage(">> The letter you are trying to use, has already been used.\n" +
                    ">> Try a different one or erase that letter.");
        } catch (ValueAlreadyMappedException e) {
            askToRemap(guess, valueToMap);
        } catch (ValueNotInCryptogramException e) {
            view.displayMessage(">> The value you're trying to map to, is not present in this cryptogram.\n" +
                    ">> Try again with a different value.");
        }
    }

    private boolean hasCorrectLength(String[] input) {
        if (input.length == 2 && input[0].length() == 1 &&
                (input[1].length() == 1 || input[1].length() == 2)) return true;

        view.displayMessage(">> Couldn't enter this mapping. Try again.");
        return false;
    }

}
