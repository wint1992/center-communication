package ru.ithex.center.communication.emailsender.exceptions;

public class EmailSenderException extends Exception {
    public EmailSenderException(Exception cause){ super(cause);}
    public EmailSenderException(){ super();}
    public EmailSenderException(String msg){ super(msg);}
}
