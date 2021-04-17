package Cryptogram.GameCommands;

import Cryptogram.*;
import Cryptogram.GameCommand;

public class enterLetterCommand extends FinishableGameCommand {

    private final InputPrompt prompt;
    private final String[] input;

    public enterLetterCommand(String[] input, Cryptogram cryptogram, Player player, InputPrompt prompt, View view) {
        super(cryptogram, view, player);
        this.prompt = prompt;
        this.input = input;
    }

    @Override
    public boolean execute() {
        if (!isInputValid(input)) {
            return false;
        }

        String guess = input[0];
        String valueToMap = input[1];

        if (!cryptogram.valueHasMapping(valueToMap)) {
            cryptogram.mapLetters(guess, valueToMap);
            player.updateAccuracy(cryptogram.isGuessCorrect(guess, valueToMap));
            return super.isFinished();
        }

        view.displayMessage(">> This value is already mapped to. Do you want to override this mapping?");
        if (prompt.confirmChoice()) {
            cryptogram.remapLetters(guess, valueToMap);
            player.updateAccuracy(cryptogram.isGuessCorrect(guess, valueToMap));
        }

        return super.isFinished();
    }


    /**
     * This method check if inputPrompt is in correct format, if the crypto value is in the alphabet
     * and if the letter guessed is not already in use.
     * It does not check if crypto value already has a letter mapped to it.
     *
     * @param input = inputPrompt from the user
     * @return returns true if inputPrompt is valid and ready to be passed to enterLetter
     */
    private boolean isInputValid(String[] input) {
        if (!(input.length == 2 &&
                input[0].length() == 1 &&
                (input[1].length() == 1 || input[1].length() == 2))) {
            view.displayMessage(">> Couldn't enter this mapping. Try again.");
            return false;
        }

        String guess = input[0];
        String valueToMap = input[1];

        if (!cryptogram.isValueInAlphabet(valueToMap)) {
            view.displayMessage(">> The value you're trying to map to, is not present in this cryptogram.\n" +
                    ">> Try again with a different value.");
            return false;
        } else if (cryptogram.isLetterAlreadyUsed(guess)) {
            view.displayMessage(">> The letter you are trying to use, has already been used.\n" +
                    ">> Try a different one or erase that letter.");
            return false;
        } else {
            return true;
        }
    }

}
