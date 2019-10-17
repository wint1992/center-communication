package ru.ithex.center.communication.emailsender.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import ru.ithex.center.communication.core.exceptions.InputValidationException;
import ru.ithex.center.communication.emailsender.model.EmailTypes;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmailBaseSender {
    private static final Logger log = LoggerFactory.getLogger(EmailBaseSender.class);

    private final JavaMailSender javaMailSender;

    public EmailBaseSender(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public boolean send(String emailFrom, String subject, String msg, EmailTypes emailType, List<String> _emailTo, List<String> _bccEmailTo, List<String> _copy) throws InputValidationException {
        List<String> emailTo = _emailTo.stream().filter(src -> !src.isEmpty()).map(String::trim).collect(Collectors.toList());
        List<String> bccEmailTo = _bccEmailTo.stream().filter(src -> !src.isEmpty()).map(String::trim).collect(Collectors.toList());
        List<String> copy = _copy.stream().filter(src -> !src.isEmpty()).map(String::trim).collect(Collectors.toList());
        boolean res = false;
        if (((emailTo != null && emailTo.size() > 0) || (copy != null && copy.size() > 0) ) && subject != null && msg != null && emailType != null)
            try {
                validSend(emailTo, bccEmailTo, copy, emailFrom, subject, msg, emailType);
                res = true;
            }
            catch (Exception e){
                log.error("Email sending to {} error: {}", emailTo, e.getMessage());
            }
        else throw new InputValidationException(new IllegalArgumentException(String.format("Incorrect params for sending email: emailTo=%s, subject=%s, msg=%s, emailType=text/%s!", emailTo, subject, msg, emailType)));
        return  res;
    }

    private void validSend(List<String> emailTo, List<String> bccEmailTo, List<String> copy, String emailFrom, String subject, String msg, EmailTypes emailType) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, false, "utf-8");

        if (emailTo != null && emailTo.size() > 0)
            helper.setTo(emailTo.stream().toArray(String[]::new));
        if (bccEmailTo != null && bccEmailTo.size() > 0)
            helper.setBcc(bccEmailTo.stream().toArray(String[]::new));
        if (copy != null && copy.size() > 0)
            helper.setCc(copy.stream().toArray(String[]::new));
        helper.setSubject(subject);
        helper.setFrom(emailFrom);
        mimeMessage.setContent(msg, String.format("text/%s; charset=UTF-8", emailType));

        javaMailSender.send(mimeMessage);
    }
}