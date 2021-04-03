package Cryptogram;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class CryptogramManager implements ICryptogramManager {

    private final String gameFilesDirectory;
    private final String quotesDirectory;

    public CryptogramManager(){
        this("PlayerFiles", "CryptogramPhrases");
    }

    public CryptogramManager(String gameFilesDirectory, String quotesDirectory){
        this.gameFilesDirectory = gameFilesDirectory;
        this.quotesDirectory = quotesDirectory;
    }

    @Override
    public Cryptogram generateCryptogram(String type, String path) throws IOException {
        String phrase = loadPhraseFromFile(getClass().getResource(quotesDirectory + "/" + path).getPath());
        Cryptogram cryptogram;
        if (type.equals("numbers")) {
            cryptogram = new NumbersCryptogram(phrase);
        } else {
            cryptogram = new LettersCryptogram(phrase);
        }
        return cryptogram;
    }

    @Override
    public Cryptogram loadCryptogram(String path) throws IOException {
        String playerFilePath = gameFilesDirectory + "/PlayerGameFiles/" + path;
        String phrase = loadPhraseFromFile(playerFilePath);
        HashMap<String, String> alphabet = loadAlphabetFromFile(playerFilePath);
        HashMap<String, String> solution = loadSolutionFromFile(playerFilePath);
        return new Cryptogram(phrase, alphabet, solution);
    }

    private String loadPhraseFromFile(String path) throws IOException {
        BufferedReader bf = new BufferedReader(new FileReader(path));
        String line = bf.readLine();
        if (line == null || line.equals("")) {
            throw new IllegalStateException();
        }
        return line;
    }

    private HashMap<String, String> loadAlphabetFromFile(String path) throws IOException, IllegalStateException {
        BufferedReader br = new BufferedReader(new FileReader(path));
        String line;
        HashMap<String, String> alphabet = new HashMap<>();
        do {
            line = br.readLine();
            if (line == null) {
                throw new IllegalStateException();
            }
        } while (!line.equals("Alphabet:"));
        while (!(line = br.readLine()).equals("Solution:")) {
            String[] tokens = line.split(":");
            if (tokens[0].length() != 1 ||
                    tokens[1].length() > 2 ||
                    tokens[1].length() < 1 ||
                    tokens[0].charAt(0) < 'a' ||
                    tokens[0].charAt(0) > 'z') {
                throw new IllegalStateException();
            }
            alphabet.put(tokens[0], tokens[1]);
        }
        return alphabet;
    }

    private HashMap<String, String> loadSolutionFromFile(String path) throws IOException, IllegalStateException {
        // restore cryptograms solution
        BufferedReader bf = new BufferedReader(new FileReader(path));
        String line;
        HashMap<String, String> solution = new HashMap<>();
        while (!bf.readLine().equals("Solution:")) ;
        while ((line = bf.readLine()) != null) {
            String[] tokens = line.split(":");
            if (tokens[0].length() != 1 ||
                    tokens[1].length() > 2 ||
                    tokens[1].length() < 1 ||
                    tokens[0].charAt(0) < 'a' ||
                    tokens[0].charAt(0) > 'z') {
                throw new IllegalStateException();
            }
            solution.put(tokens[0], tokens[1]);
        }
        return solution;
    }

    @Override
    public boolean fileAlreadyExists(String path) {
        File file = new File(gameFilesDirectory + "/PlayerGameFiles/" + path);
        return file.exists();
    }

    @Override
    public void saveCryptogramToFile(Cryptogram cryptogram, String path) {

        String dirPath = gameFilesDirectory + "/PlayerGameFiles";
        File directory = new File(dirPath);
        directory.mkdir();
        File file = new File(dirPath + "/" + path);
        try {
            file.createNewFile();
        } catch (IOException e) {
            System.out.println(">> Could not create the file");
        }

        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(file));

            // 1. save the phrase
            bw.write(cryptogram.getPhrase());
            bw.newLine();

            // 2. save the alphabet
            bw.write("Alphabet:");
            bw.newLine();

            for (Map.Entry<String, String> entry : cryptogram.getCryptogramAlphabet().entrySet()) {
                bw.write(entry.getKey() + ":" + entry.getValue()); // write realLetter:encryptedValue to the file
                bw.newLine();
            }

            // 3. save the solution
            bw.write("Solution:");
            bw.newLine();

            for (Map.Entry<String, String> entry : cryptogram.getSolution().entrySet()) {
                bw.write(entry.getKey() + ":" + entry.getValue()); // write guess:encryptedValue to the file
                bw.newLine();
            }

            bw.flush();
            bw.close();
        } catch (Exception e) {
            System.err.println(">> Could not save cryptogram: " + e.getMessage());
        }
    }
}
