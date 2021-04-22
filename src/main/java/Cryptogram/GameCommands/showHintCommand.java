package Cryptogram.GameCommands;

import Cryptogram.Cryptogram;
import Cryptogram.Player;
import Cryptogram.View;

import java.util.Map;

public class showHintCommand extends FinishableGameCommand {

    public showHintCommand(Cryptogram cryptogram, View view, Player player) {
        super(cryptogram, view, player);
    }

    @Override
    public boolean execute() {
        cryptogram.getCryptogramAlphabet().entrySet().stream()
                .filter(this::valueIsNotGuessed)
                .findAny()
                .ifPresentOrElse(this::remapWrongGuess, this::displayNoHintAvailableMessage);
        return isFinished();
    }

    private void remapWrongGuess(Map.Entry<String, String> entry) {
        if(cryptogram.isLetterAlreadyUsed(entry.getKey())) cryptogram.removeMapping(entry.getKey());
        cryptogram.mapLetters(entry.getKey(), entry.getValue());
        System.out.println("The letter " + entry.getKey() + " is revealed.");
    }


    private void displayNoHintAvailableMessage() {
        view.displayMessage(
                "There are no places left. Unmap something to be able to get a hint");
    }

    private boolean valueIsNotGuessed(Map.Entry<String, String> entry) {
        return !cryptogram.valueHasMapping(entry.getValue());
    }
}
