package ru.ithex.center.communication.emailsender.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.ithex.baseweb.model.dto.response.ResponseWrapperDTO;
import ru.ithex.baseweb.model.dto.response.error.InternalServerError;
import ru.ithex.center.communication.emailsender.model.dto.EmailDTO;
import ru.ithex.center.communication.emailsender.services.EmailSender;

@Service
public class EmailSenderController {
    private static final Logger LOGGER = LoggerFactory.getLogger(EmailSenderController.class);

    private final EmailSender emailSender;

    public EmailSenderController(EmailSender emailSender) {
        this.emailSender = emailSender;
    }

    public ResponseWrapperDTO sender(EmailDTO emailDTO){
        try{
            emailSender.sendMail(emailDTO);
            return ResponseWrapperDTO.ok(null);
        }catch (Exception e){
            LOGGER.error("EmailSender error: ", e);
            return ResponseWrapperDTO.error(new InternalServerError(e.getMessage()));
        }
    }
}
