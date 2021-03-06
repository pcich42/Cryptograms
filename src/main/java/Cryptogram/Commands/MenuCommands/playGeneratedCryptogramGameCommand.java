package Cryptogram.Commands.MenuCommands;

import Cryptogram.Controllers.GameCommandSupplier;
import Cryptogram.Models.Cryptogram;
import Cryptogram.Interfaces.ICryptogramManager;
import Cryptogram.Models.Player;
import Cryptogram.Interfaces.IPlayers;
import Cryptogram.Views.View;
import Cryptogram.Views.InputPrompt;

import java.io.IOException;

public class playGeneratedCryptogramGameCommand extends newGameCommand {

    private final String[] input;

    public playGeneratedCryptogramGameCommand(Player player,
                                              IPlayers players,
                                              ICryptogramManager manager,
                                              View view,
                                              InputPrompt prompt,
                                              String[] input,
                                              GameCommandSupplier commands) {
        super(player, players, manager, view, prompt, commands);
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
