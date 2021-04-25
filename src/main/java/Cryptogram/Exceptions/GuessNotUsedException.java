package Cryptogram.Exceptions;

public class GuessNotUsedException extends RuntimeException {
    public GuessNotUsedException(String message) {
        super(message);
    }
}
