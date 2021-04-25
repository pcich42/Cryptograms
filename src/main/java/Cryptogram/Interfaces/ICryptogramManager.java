package Cryptogram.Interfaces;

import Cryptogram.Models.Cryptogram;

import java.io.IOException;
import java.util.Optional;

public interface ICryptogramManager {
    Cryptogram generateCryptogram(String type) throws IOException;

    Cryptogram loadCryptogram(String path) throws IOException;

    boolean fileAlreadyExists(String path);

    void saveCryptogramToFile(Cryptogram cryptogram, String path);
}
