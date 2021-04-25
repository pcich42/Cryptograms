package Cryptogram.Commands.GameCommands;

import Cryptogram.Interfaces.GameCommand;
import Cryptogram.Models.Cryptogram;
import Cryptogram.Views.View;

public class showFrequenciesCommand implements GameCommand {

    private final Cryptogram cryptogram;
    private final View view;
    private static final String FREQUENCIES =  "a:8.12, b:1.49, c:2.71, d:4.32, e:12.02, f:2.30,\n" +
            "g:2.03, h:5.92, i:7.31, j:0.10, k:0.69, l:3.98,\n" +
            "m:2.61, n:6.95, o:7.68, p:1.82, q:0.11, r:6.02,\n" +
            "s:6.28, t:9.10, u:2.88, v:1.11, w:2.09, x:0.17,\n" +
            "y:2.11, z:0.07";

    public showFrequenciesCommand(Cryptogram cryptogram, View view) {
        this.cryptogram = cryptogram;
        this.view = view;
    }

    @Override
    public boolean execute() {
        view.displayMessage("The frequencies of letters in English language are as follows:");
        view.displayMessage(FREQUENCIES);
        view.displayMessage("Current cryptogram has the following frequencies:");
        view.displayMessage(cryptogram.getFrequencies());
        return false;
    }

}
