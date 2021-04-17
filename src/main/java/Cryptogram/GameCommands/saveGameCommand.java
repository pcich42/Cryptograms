package Cryptogram.GameCommands;

import Cryptogram.GameCommand;
import Cryptogram.Cryptogram;
import Cryptogram.ICryptogramManager;
import Cryptogram.Player;
import Cryptogram.InputPrompt;

public class saveGameCommand implements GameCommand {

    private final Cryptogram cryptogram;
    private final ICryptogramManager manager;
    private final Player player;
    private final InputPrompt prompt;

    public saveGameCommand(Cryptogram cryptogram, Player player, ICryptogramManager manager, InputPrompt prompt) {
        this.cryptogram = cryptogram;
        this.manager = manager;
        this.player = player;
        this.prompt = prompt;
    }

    @Override
    public boolean execute() {
        String path = player.getUsername() + "Game.txt";

        if (manager.fileAlreadyExists(path)) {
            System.out.println(">> Do you wish to overwrite currently stored game?");
            if (prompt.confirmChoice()) {
                manager.saveCryptogramToFile(cryptogram, path);
            }
        } else {
            manager.saveCryptogramToFile(cryptogram, path);
        }
        return false;
    }


}
