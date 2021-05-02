package Cryptogram.Commands.MenuCommands;

import Cryptogram.Controllers.GameCommandSupplier;
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
    protected GameCommandSupplier commands;

    public newGameCommand(
            Player player,
            IPlayers players,
            ICryptogramManager manager,
            View view,
            InputPrompt prompt,
            GameCommandSupplier commands) {
        this.players = players;
        this.player = player;
        this.manager = manager;
        this.view = view;
        this.prompt = prompt;
        this.commands = commands;
    }


    public void perform() {
        Cryptogram cryptogram = requestCryptogram();
        if(cryptogram != null) {
            game = new Game(player,
                    cryptogram,
                    manager,
                    players,
                    prompt,
                    view,
                    commands);
            game.play();
            players.savePlayerDetails();
        }
    }

    protected abstract Cryptogram requestCryptogram();

}
