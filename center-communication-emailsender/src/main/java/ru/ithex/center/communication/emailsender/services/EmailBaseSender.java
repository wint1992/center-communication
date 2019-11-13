package ru.ithex.center.communication.emailsender.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import ru.ithex.center.communication.emailsender.exception.AttachmentMessagingException;
import ru.ithex.center.communication.emailsender.exception.EmailSenderException;
import ru.ithex.center.communication.emailsender.model.Attachment;
import ru.ithex.center.communication.emailsender.model.EmailTypes;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.List;

@Service
public class EmailBaseSender {
    private static final Logger log = LoggerFactory.getLogger(EmailBaseSender.class);

    private final JavaMailSender javaMailSender;

    public EmailBaseSender(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public boolean send(String emailFrom, String subject, String msg, EmailTypes emailType, List<String> emailTo, List<String> bccEmailTo, List<String> copy, List<Attachment> attachments){
        boolean res = false;

        if (msg == null)
            throw new EmailSenderException("Тело письма отсутствует");

        if (emailType == null)
            throw new EmailSenderException("Не указан тип письма");

        if (emailFrom == null)
            throw new EmailSenderException("Не указан исходящий email");
        try {
            validSend(emailTo, bccEmailTo, copy, emailFrom, subject, msg, emailType, attachments);
            res = true;
        }
        catch (MessagingException e){
            log.error("Email sending to {} error: {}", emailTo, e.getMessage());
        }
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
        helper.setSubject(subject == null ? "" : subject);
        helper.setFrom(emailFrom);
        helper.setText(msg,emailType.equals(EmailTypes.HTML));

        javaMailSender.send(mimeMessage);
    }
}