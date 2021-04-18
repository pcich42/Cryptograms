package Cryptogram;

import java.io.IOException;

public interface ICryptogramManager {
    Cryptogram generateCryptogram(String type) throws IOException;

    Cryptogram loadCryptogram(String path) throws IOException;

    boolean fileAlreadyExists(String path);

    void saveCryptogramToFile(Cryptogram cryptogram, String path);
}
