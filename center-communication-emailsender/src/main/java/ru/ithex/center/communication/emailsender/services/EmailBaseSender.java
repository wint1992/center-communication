package ru.ithex.center.communication.emailsender.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import ru.ithex.center.communication.core.exceptions.InputValidationException;
import ru.ithex.center.communication.emailsender.exceptions.AttachmentMessagingException;
import ru.ithex.center.communication.emailsender.exceptions.AttachmentTypeException;
import ru.ithex.center.communication.emailsender.model.Attachment;
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

    public boolean send(String emailFrom, String subject, String msg, EmailTypes emailType, List<String> _emailTo, List<String> _bccEmailTo, List<String> _copy, List<Attachment> attachments) throws InputValidationException {
        List<String> emailTo = _emailTo != null ? _emailTo.stream().filter(src -> !src.isEmpty()).map(String::trim).collect(Collectors.toList()) : null;
        List<String> bccEmailTo = _bccEmailTo != null ? _bccEmailTo.stream().filter(src -> !src.isEmpty()).map(String::trim).collect(Collectors.toList()) : null;
        List<String> copy = _copy != null ? _copy.stream().filter(src -> !src.isEmpty()).map(String::trim).collect(Collectors.toList()) : null;
        boolean res = false;
        if (((emailTo != null && emailTo.size() > 0) || (copy != null && copy.size() > 0) ) && subject != null && msg != null && emailType != null && emailFrom != null)
            try {
                validSend(emailTo, bccEmailTo, copy, emailFrom, subject, msg, emailType, attachments);
                res = true;
            }
            catch (MessagingException e){
                log.error("Email sending to {} error: {}", emailTo, e.getMessage());
            }
        else throw new InputValidationException(new IllegalArgumentException(String.format("Incorrect params for sending email: emailTo=%s, subject=%s, msg=%s, emailFrom=%s emailType=text/%s!", emailTo, subject, msg, emailFrom, emailType)));
        return  res;
    }

    private void validSend(List<String> emailTo, List<String> bccEmailTo, List<String> copy, String emailFrom, String subject, String msg, EmailTypes emailType, List<Attachment> attachments) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, attachments != null && attachments.size() > 0);

        if (emailTo != null && emailTo.size() > 0)
            helper.setTo(emailTo.stream().toArray(String[]::new));
        if (bccEmailTo != null && bccEmailTo.size() > 0)
            helper.setBcc(bccEmailTo.stream().toArray(String[]::new));
        if (copy != null && copy.size() > 0)
            helper.setCc(copy.stream().toArray(String[]::new));
        if (attachments != null && attachments.size() > 0)
            attachments.forEach(attachment -> {
                try {
                    if (attachment.getFileName() != null && attachment.getData() != null && attachment.getData().length > 0)
                        if (attachment.getType() != null)
                            helper.addAttachment(attachment.getFileName(), attachment, attachment.getType());
                        else
                            helper.addAttachment(attachment.getFileName(), attachment);
                } catch (MessagingException e){
                    throw new AttachmentMessagingException("Ошибка формирования вложения", e);
                }
            });
        helper.setSubject(subject);
        helper.setFrom(emailFrom);
        helper.setText(msg,emailType.equals(EmailTypes.HTML));

        try {
            javaMailSender.send(mimeMessage);
        } catch (MailSendException e){
            throw new AttachmentTypeException("Ошибочный типа вложения", e);
        }
    }
}