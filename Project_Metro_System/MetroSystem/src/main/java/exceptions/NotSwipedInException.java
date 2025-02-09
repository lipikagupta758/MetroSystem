package exceptions;

public class NotSwipedInException extends Exception {
    public NotSwipedInException() {
        super();
    }

    public NotSwipedInException(String message) {
        super(message);
    }
}
