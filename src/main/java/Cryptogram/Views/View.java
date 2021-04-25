package Cryptogram.Views;

import Cryptogram.Models.Cryptogram;

public class View {

    public void displayCryptogram(Cryptogram cryptogram) {
        System.out.println();

        StringBuilder decryptedLine = new StringBuilder();
        StringBuilder encryptedLine = new StringBuilder();

        for (String guess : cryptogram.getPhrase().split("")) {
            if (guess.equals(" ")) {
                // two spaces
                decryptedLine.append("  ");
                encryptedLine.append("  ");
            } else {
                String encryptedValue = cryptogram.getCryptogramAlphabet().get(guess);
                String letterOrDash = getDecryptedLetterOrDash(cryptogram, encryptedValue);

                decryptedLine
                        .append(letterOrDash)
                        .append(" ");
                encryptedLine
                        .append(encryptedValue)
                        .append(" ");
            }
        }

        System.out.println(decryptedLine);
        System.out.println(encryptedLine);

    }

    private String getDecryptedLetterOrDash(Cryptogram cryptogram, String encryptedValue) {
        StringBuilder decryptedPhrase = new StringBuilder();

        if (encryptedValue.length() == 2) decryptedPhrase.append(" ");

        if (cryptogram.valueHasMapping(encryptedValue))
            decryptedPhrase.append(getKey(cryptogram, encryptedValue));
        else decryptedPhrase.append("_");

        return decryptedPhrase.toString();
    }

    private String getKey(Cryptogram cryptogram, String encryptedValue) {
        return cryptogram.getSolution().entrySet().stream()
                .filter((entry) -> entry.getValue().equals(encryptedValue))
                .findFirst()
                .get()
                .getKey();
    }

    public void displayMessage(String string) {
        System.out.println(string);
    }

    public void displayHelp() {
        System.out.println("Here are all available commands:");
        System.out.println("<guess> <encrypted value>:              To map a letter.");
        System.out.println("undo <guessed letter to be removed>:    To undo a mapping.");
        System.out.println("new <cryptogram type[letters/numbers]>: To generate a new game.");
        System.out.println("load:                                   To load saved game.");
        System.out.println("save:                                   To save a game for later.");
        System.out.println("frequencies:                            To see the frequencies.");
        System.out.println("hint:                                   To get a hint.");
        System.out.println("solution:                               To see the solution and finish this game.");
        System.out.println("scores:                                 To see the scoreboard.");
        System.out.println("help:                                   To get help.");
        System.out.println("exit:                                   To exit.");
    }

}
