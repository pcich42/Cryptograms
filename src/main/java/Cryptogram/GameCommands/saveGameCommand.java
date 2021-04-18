package Cryptogram.GameCommands;

import Cryptogram.*;

public class saveGameCommand implements GameCommand {

    private final Cryptogram cryptogram;
    private final ICryptogramManager manager;
    private final Player player;
    private final InputPrompt prompt;
    private final View view;

    public saveGameCommand(Cryptogram cryptogram, Player player, ICryptogramManager manager, InputPrompt prompt, View view) {
        this.cryptogram = cryptogram;
        this.manager = manager;
        this.player = player;
        this.prompt = prompt;
        this.view = view;
    }

    @Override
    public boolean execute() {
        String path = player.getUsername() + "Game.txt";

        if (!manager.fileAlreadyExists(path)) {
            manager.saveCryptogramToFile(cryptogram, path);
            view.displayMessage(">> Cryptogram saved.");
            return false;
        }

        view.displayMessage(">> Do you wish to overwrite currently stored game?");
        if (prompt.confirmChoice()) {
            manager.saveCryptogramToFile(cryptogram, path);
            view.displayMessage(">> Cryptogram saved.");
        }
        return false;
    }


}
