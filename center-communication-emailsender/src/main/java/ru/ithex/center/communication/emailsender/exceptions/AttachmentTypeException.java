package ru.ithex.center.communication.emailsender.exceptions;

public class AttachmentTypeException extends RuntimeException {
    public AttachmentTypeException(String msg, Throwable cause) {
        super(msg, cause);
    }
}