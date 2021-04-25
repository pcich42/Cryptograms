package Cryptogram.Commands.MenuCommands;

import Cryptogram.Models.Cryptogram;
import Cryptogram.Interfaces.MenuCommand;
import Cryptogram.Controllers.Game;
import Cryptogram.Interfaces.IPlayers;
import Cryptogram.Models.Player;
import Cryptogram.Interfaces.ICryptogramManager;
import Cryptogram.Views.View;
import Cryptogram.Views.InputPrompt;

public abstract class newGameCommand implements MenuCommand {

    protected final IPlayers players;
    protected final Player player;
    protected final ICryptogramManager manager;
    protected final View view;
    protected final InputPrompt prompt;
    protected Game game;

    public newGameCommand(
            Player player,
            IPlayers players,
            ICryptogramManager manager,
            View view,
            InputPrompt prompt) {
        this.players = players;
        this.player = player;
        this.manager = manager;
        this.view = view;
        this.prompt = prompt;
    }


    public void perform() {
        Cryptogram cryptogram = requestCryptogram();
        if(cryptogram != null) {
            game = new Game(player, cryptogram, manager, players, prompt, view);
            game.play();
            players.savePlayerDetails();
        }
    }

    protected abstract Cryptogram requestCryptogram();

}
