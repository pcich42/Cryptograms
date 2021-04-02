package Cryptogram;

public class Player {

    private final String username;
    private int correctGuesses;
    private int totalGuesses;
    private int cryptogramsPlayed;
    private int cryptogramsCompleted;

    public Player(String username, int correctGuesses, int totalGuesses, int cryptogramsPlayed, int cryptogramsCompleted) {
        this.username = username;
        this.correctGuesses = correctGuesses;
        this.totalGuesses = totalGuesses;
        this.cryptogramsPlayed = cryptogramsPlayed;
        this.cryptogramsCompleted = cryptogramsCompleted;
    }

    public Player(String username) {
        this(username, 0, 0, 0, 0);
    }

    public double getAccuracy() {
        if (correctGuesses == 0 && totalGuesses == 0) {
            return 1;
        }
        return correctGuesses * 1.0 / totalGuesses;
    }

    /**
     * updates the accuracy and total guesses by one if the guess was correct
     *
     * @param isGuessCorrect boolean value of whether the guess was correct
     */
    public void updateAccuracy(Boolean isGuessCorrect) {
        if (isGuessCorrect) {
            correctGuesses++;
        }
        totalGuesses++;
    }

    public int getCorrectGuesses() {
        return correctGuesses;
    }

    public void incrementCryptogramsPlayed() {
        cryptogramsPlayed++;
    }

    public void incrementCryptogramsCompleted() {
        cryptogramsCompleted++;
    }

    public int getTotalGuesses() {
        return totalGuesses;
    }

    public String getUsername() {
        return username;
    }

    public int getCryptogramsPlayed() {
        return cryptogramsPlayed;
    }

    public int getCryptogramsCompleted() {
        return cryptogramsCompleted;
    }

}