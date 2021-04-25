package Cryptogram.Exceptions;

public class GuessAlreadyUsedException extends RuntimeException {

    public GuessAlreadyUsedException(String message) {
        super(message);
    }

}
