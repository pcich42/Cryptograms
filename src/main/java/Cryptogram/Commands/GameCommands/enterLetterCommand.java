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

    public enterLetterCommand(String[] input, Cryptogram cryptogram, Player player, InputPrompt prompt, View view) {
        super(cryptogram, view, player);
        this.prompt = prompt;
        this.input = input;
    }

    @Override
    public boolean execute() {
        if (!hasCorrectLength(input)) return false;

        String guess = input[0];
        String valueToMap = input[1];

//        if (!cryptogram.valueHasMapping(valueToMap)) return mapGuessAndCheckIfFinished(guess, valueToMap);
//        else return askToRemapGuessAndCheckIfFinished(guess, valueToMap);
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


    /**
     * This method check if inputPrompt is in correct format, if the crypto value is in the alphabet
     * and if the letter guessed is not already in use.
     * It does not check if crypto value already has a letter mapped to it.
     *
     * @param input = inputPrompt from the user
     * @return returns true if inputPrompt is valid and ready to be passed to enterLetter
     */
//    private boolean isInputValid(String[] input) {
//        return hasCorrectLength(input);
////        if (!valueIsInAlphabet(input[1])) return false;
////        if (guessIsAlreadyUsed(input[0])) return false;
//    }

//    private boolean guessIsAlreadyUsed(String guess) {
//        if (!cryptogram.isLetterAlreadyUsed(guess)) return false;
//
//        view.displayMessage(">> The letter you are trying to use, has already been used.\n" +
//                ">> Try a different one or erase that letter.");
//        return true;
//    }
//
//    private boolean valueIsInAlphabet(String valueToMap) {
//        if (cryptogram.isValueInAlphabet(valueToMap)) return true;
//
//        view.displayMessage(">> The value you're trying to map to, is not present in this cryptogram.\n" +
//                ">> Try again with a different value.");
//        return false;
//    }

    private boolean hasCorrectLength(String[] input) {
        if (input.length == 2 && input[0].length() == 1 &&
                (input[1].length() == 1 || input[1].length() == 2)) return true;

        view.displayMessage(">> Couldn't enter this mapping. Try again.");
        return false;
    }

}
