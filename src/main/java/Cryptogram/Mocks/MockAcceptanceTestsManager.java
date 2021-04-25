package Cryptogram.Mocks;

import Cryptogram.Controllers.CryptogramManager;
import Cryptogram.Models.Cryptogram;

import java.util.HashMap;

public class MockAcceptanceTestsManager extends CryptogramManager {

    private final String phrase = "test";

    @Override
    public Cryptogram generateCryptogram(String type) {
        HashMap<String, String> alphabet;
        if (type.equals("numbers")) alphabet = getLettersAlphabet();
        else alphabet = getNumbersAlphabet();
        HashMap<String, String> solution = getSolution();
        return new Cryptogram(phrase, alphabet, solution);
    }

    @Override
    public Cryptogram loadCryptogram(String path) {
        HashMap<String, String> alphabet = getLettersAlphabet();
        HashMap<String, String> solution = getSolution();
        return new Cryptogram(phrase, alphabet, solution);
    }

    @Override
    public boolean fileAlreadyExists(String path) {
        return true;
    }

    @Override
    public void saveCryptogramToFile(Cryptogram cryptogram, String path) {}

    private HashMap<String, String> getLettersAlphabet() {
        HashMap<String, String> map = new HashMap<>();
        map.put("t","a");
        map.put("e","b");
        map.put("s","c");
        return map;
    }

    private HashMap<String, String> getNumbersAlphabet() {
        HashMap<String, String> map = new HashMap<>();
        map.put("t","01");
        map.put("e","02");
        map.put("s","03");
        return map;
    }

    private HashMap<String, String> getSolution() {
        HashMap<String, String> map = new HashMap<>();
        map.put("t","a");
        return map;
    }
}
