package Cryptogram;

public interface IView {

    String[] getInput();

    /**
     * Method to display the current solution and encrypted phrase
     */
    default void displayCryptogram(Cryptogram cryptogram) {
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

    boolean confirmChoice();

    String promptForUsername();
}
