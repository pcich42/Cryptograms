package Cryptogram;

/**
 * The class responsible for user interaction - getting input and commands from the player
 */
public class View {

    public void displayCryptogram(Cryptogram cryptogram) {
        System.out.println();

        String decryptedPhrase = "";
        String encryptedPhrase = "";

        for (char value : cryptogram.getPhrase().toCharArray()) {
            if (value == ' ') {
                decryptedPhrase = decryptedPhrase.concat(" ");
                encryptedPhrase = encryptedPhrase.concat(" ");
            } else {
                // get length of the key and add that many spaces
                String correctKey = cryptogram.getCryptogramAlphabet().get(String.valueOf(value));
                String decryptedValue = "";
                if (cryptogram.getSolution().containsValue(correctKey)) {
                    for (String key : cryptogram.getSolution().keySet())
                        if (cryptogram.getSolution().get(key).equals(correctKey)) {
                            decryptedValue = key;
                        }
                } else {
                    decryptedValue = "_";
                }

                String encryptedValue = cryptogram.getCryptogramAlphabet().get(String.valueOf(value));
                if (encryptedValue.length() == 2) {
                    decryptedPhrase = decryptedPhrase.concat(" ");
                }
                decryptedPhrase = decryptedPhrase.concat(decryptedValue);
                encryptedPhrase = encryptedPhrase.concat(encryptedValue);
            }
            decryptedPhrase = decryptedPhrase.concat(" ");
            encryptedPhrase = encryptedPhrase.concat(" ");
        }

        System.out.println(decryptedPhrase);
        System.out.println(encryptedPhrase);

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
