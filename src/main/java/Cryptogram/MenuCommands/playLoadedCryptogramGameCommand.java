package Cryptogram.MenuCommands;

import Cryptogram.Cryptogram;
import Cryptogram.MenuCommand;
import Cryptogram.Game;
import Cryptogram.IPlayers;
import Cryptogram.Player;
import Cryptogram.ICryptogramManager;
import Cryptogram.View;
import Cryptogram.InputPrompt;

import java.io.IOException;

public class playLoadedCryptogramGameCommand extends newGameCommand {

    public playLoadedCryptogramGameCommand(Player player,
                                           IPlayers players,
                                           ICryptogramManager manager,
                                           View view,
                                           InputPrompt prompt) {
        super(player, players, manager, view, prompt);
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
