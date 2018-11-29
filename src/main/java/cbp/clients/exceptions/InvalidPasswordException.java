package cbp.clients.exceptions;

public class InvalidPasswordException extends Exception {

    public InvalidPasswordException() {}

    public InvalidPasswordException(String message) {
        super(message);
    }
}
