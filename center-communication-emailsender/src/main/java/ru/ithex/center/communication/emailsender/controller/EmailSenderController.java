package ru.ithex.center.communication.emailsender.controller;

import org.springframework.stereotype.Service;
import ru.ithex.center.communication.emailsender.model.dto.EmailDTO;
import ru.ithex.center.communication.emailsender.services.EmailSender;

@Service
public class EmailSenderController {
    private final EmailSender emailSender;

    public EmailSenderController(EmailSender emailSender) {
        this.emailSender = emailSender;
    }

    public void sender(EmailDTO emailDTO){
        emailSender.sendMail(emailDTO);
    }
}
