package Cryptogram.GameCommands;

import Cryptogram.GameCommand;
import Cryptogram.View;

public class displayHelpCommand implements GameCommand {

    private final View view;

    public displayHelpCommand(View view) {
        this.view = view;
    }

    @Override
    public boolean execute() {
        view.displayHelp();
        return false;
    }
}
