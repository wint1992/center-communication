package ru.ithex.center.communication.emailsender.exceptions;

public class EmailMappingException extends RuntimeException {
    public EmailMappingException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
