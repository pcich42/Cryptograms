package Cryptogram.GameCommands;

import Cryptogram.GameCommand;
import Cryptogram.Cryptogram;
import Cryptogram.Player;
import Cryptogram.View;

public class showHintCommand extends FinishableGameCommand {

    public showHintCommand(Cryptogram cryptogram, View view, Player player) {
        super(cryptogram, view, player);
    }

    @Override
    public boolean execute() {
        //TODO: simplify

        // all used letters in the cryptogram
        for (String letter : cryptogram.getCryptogramAlphabet().keySet()) {
            //Checks if letter is used
            if (cryptogram.isLetterAlreadyUsed(letter)) {
                //If letter is used and incorrect it will be replaced
                if (!cryptogram.isGuessCorrect(letter, cryptogram.getSolution().get(letter))) {
                    cryptogram.removeMapping(letter);
                    cryptogram.remapLetters(letter, cryptogram.getCryptogramAlphabet().get(letter));
                    System.out.println("The letter " + letter + " replaced your wrong guess.");
                    return isFinished();
                }
            } else {
                //if letter isn't used add the hint letter
                cryptogram.remapLetters(letter, cryptogram.getCryptogramAlphabet().get(letter));
                System.out.println("The letter " + letter + " is revealed.");
                return isFinished();
            }
        }
        return false;
    }


}
