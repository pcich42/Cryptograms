package Cryptogram.MenuCommands;

import Cryptogram.Cryptogram;
import Cryptogram.ICryptogramManager;
import Cryptogram.Player;
import Cryptogram.IPlayers;
import Cryptogram.View;
import Cryptogram.InputPrompt;

import java.io.IOException;

public class playGeneratedCryptogramGameCommand extends newGameCommand {

    private final String[] input;

    public playGeneratedCryptogramGameCommand(Player player,
                                              IPlayers players,
                                              ICryptogramManager manager,
                                              View view,
                                              InputPrompt prompt,
                                              String[] input) {
        super(player, players, manager, view, prompt);
        this.input = input;
    }

    @Override
    protected Cryptogram requestCryptogram() {

        try {
            Cryptogram cryptogram = manager.generateCryptogram(getCryptogramType(input));
            player.incrementCryptogramsPlayed();
            return cryptogram;
        } catch (IOException ioException) {
            System.out.println("Cryptograms file doesn't exists, Exiting...");
            return null;
        }
    }

    private String getCryptogramType(String[] input) {
        try {
            return input[1];
        } catch (IndexOutOfBoundsException e){
            return "letters";
        }
    }

}
