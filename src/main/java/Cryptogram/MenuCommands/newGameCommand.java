package Cryptogram.MenuCommands;

import Cryptogram.Cryptogram;
import Cryptogram.MenuCommand;
import Cryptogram.Game;
import Cryptogram.IPlayers;
import Cryptogram.Player;
import Cryptogram.ICryptogramManager;
import Cryptogram.View;
import Cryptogram.InputPrompt;

public abstract class newGameCommand implements MenuCommand {

    protected IPlayers players;
    protected Player player;
    protected ICryptogramManager manager;
    protected View view;
    protected InputPrompt prompt;

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
        Game game = new Game(player, cryptogram, manager, players, prompt, view);
        game.play();
        players.savePlayerDetails();
    }

    protected abstract Cryptogram requestCryptogram();

}
