package Cryptogram.Commands.MenuCommands;

import Cryptogram.Controllers.GameCommandSupplier;
import Cryptogram.Models.Cryptogram;
import Cryptogram.Interfaces.IPlayers;
import Cryptogram.Models.Player;
import Cryptogram.Interfaces.ICryptogramManager;
import Cryptogram.Views.View;
import Cryptogram.Views.InputPrompt;

import java.io.IOException;

public class playLoadedCryptogramGameCommand extends newGameCommand {

    public playLoadedCryptogramGameCommand(Player player,
                                           IPlayers players,
                                           ICryptogramManager manager,
                                           View view,
                                           InputPrompt prompt,
                                           GameCommandSupplier commands) {
        super(player, players, manager, view, prompt, commands);
    }

    protected Cryptogram requestCryptogram() {
        try {
            return manager.loadCryptogram(player.getUsername() + "Game.txt");
        } catch (IOException e) {
            System.out.println(">> No game saved. Save a game using 'save' before loading or type: new <[letters]/numbers> - to create a new cryptogram.");
            return null;
        } catch (IllegalStateException e2) {
            System.out.println(">> Couldn't load. Invalid file format.");
            return null;
        }
    }

}
