package ru.ithex.center.communication.core.exceptions;

public class InputValidationException extends Exception {
    public InputValidationException(Exception cause){ super(cause);}
    public InputValidationException(){ super();}
    public InputValidationException(String msg){ super(msg);}

    public InputValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}
