package ru.ithex.center.communication.emailsender.services;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import ru.ithex.center.communication.emailsender.exception.EmailSenderException;
import ru.ithex.center.communication.emailsender.exception.FreeMarkerTemplateException;
import ru.ithex.center.communication.emailsender.model.EmailTypes;
import ru.ithex.center.communication.emailsender.model.dto.EmailDTO;
import ru.ithex.center.communication.emailsender.model.entity.EmailTemplate;
import ru.ithex.center.communication.emailsender.repository.dao.EmailTemplateDao;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class EmailSender {
    private final Configuration configuration;
    private final EmailBaseSender emailBaseSender;
    private final EmailTemplateDao emailTemplateDao;
    private final String MAIL_FROM;
    private final Integer maxAttemptCount;

    public EmailSender(Configuration configuration, EmailBaseSender emailBaseSender, EmailTemplateDao emailTemplateDao, @Value("${app.mail.from}") String MAIL_FROM, @Value("${app.mail.max-attempt-count}") Integer maxAttemptCount) {
        this.configuration = configuration;
        this.emailBaseSender = emailBaseSender;
        this.emailTemplateDao = emailTemplateDao;
        this.MAIL_FROM = MAIL_FROM;
        this.maxAttemptCount = maxAttemptCount;
    }

    public void sendMail(EmailDTO emailDTO){
        EmailTemplate emailTemplate = emailTemplateDao.getEmailTemplateByCode(emailDTO.getTemplateCode());

        String mailBody = prepareText(emailTemplate, emailDTO.getEmailParams());
        boolean successfulSending = false;
        int attempt = 0;
        while(!successfulSending && attempt < maxAttemptCount) {
            successfulSending = emailBaseSender.send(
                    MAIL_FROM,
                    emailDTO.getEmailSubject() != null ? emailDTO.getEmailSubject() : emailTemplate.getEmailTemplateSubject(),
                    mailBody,
                    EmailTypes.HTML,
                    emailDTO.getEmails(),
                    emailDTO.getBcc(),
                    emailDTO.getCopy(),
                    emailDTO.getAttachments()
            );
            attempt++;
        }
        if (!successfulSending)
            throw new EmailSenderException("Mail server connection failed. Attempt count = " + attempt);

    }

    private String prepareText(EmailTemplate emailTemplate, Map<String, Object> params) {
        if (params == null)
            params = new HashMap<>();
        params.put("dateMillis",System.currentTimeMillis());
        String templateString = emailTemplate.getEmailTemplateText();
        String mailText = null;
        try {
            mailText = FreeMarkerTemplateUtils.processTemplateIntoString(new Template("templateName", templateString, configuration), params);
        } catch (IOException | TemplateException e) {
            throw new FreeMarkerTemplateException(String.format("Ошибка формирования письма из шаблона: %s", e.getMessage()));
        }
        return mailText;
    }
}
