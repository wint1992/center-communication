package ru.ithex.center.communication.emailsender.services;

import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import ru.ithex.center.communication.core.exceptions.InputValidationException;
import ru.ithex.center.communication.emailsender.exceptions.EmailSenderException;
import ru.ithex.center.communication.emailsender.model.EmailTypes;
import ru.ithex.center.communication.emailsender.model.dto.EmailDTO;
import ru.ithex.center.communication.emailsender.model.entity.EmailTemplate;
import ru.ithex.center.communication.emailsender.services.dictionaries.EmailTemplateService;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;

@Service
public class EmailSender {

    private final Configuration configuration;
    private final EmailBaseSender emailBaseSender;
    private final EmailTemplateService emailTemplateService;
    private final String MAIL_FROM;
    private final Integer maxAttemptCount;

    public EmailSender(Configuration configuration, EmailBaseSender emailBaseSender, EmailTemplateService emailTemplateService, @Value("${app.mail.from}") String MAIL_FROM, @Value("${app.mail.max-attempt-count}") Integer maxAttemptCount) {
        this.configuration = configuration;
        this.emailBaseSender = emailBaseSender;
        this.emailTemplateService = emailTemplateService;
        this.MAIL_FROM = MAIL_FROM;
        this.maxAttemptCount = maxAttemptCount;
    }

    public void sendMail(EmailDTO emailDTO) throws InputValidationException, EmailSenderException {
        // Валидация входных параметров
        if (emailDTO.getTemplateCode() == null) throw new InputValidationException(new IllegalArgumentException("Incorrect input params: For sending email parameter 'templateCode' is required!"));

        EmailTemplate emailTemplate = emailTemplateService.getEmailTemplateByCode(emailDTO.getTemplateCode());
        if (emailTemplate == null) throw new InputValidationException(new IllegalArgumentException(String.format("Incorrect input params: Email template with parameter 'templateCode' = %s doesn't exists!", emailDTO.getTemplateCode())));

        String mailBody = prepareText(emailTemplate, emailDTO.getParams());
        boolean successfulSending = false;
        int attempt = 0;
        while(!successfulSending && attempt < maxAttemptCount) {
            successfulSending = emailBaseSender.send(MAIL_FROM, emailDTO.getEmailSubject() != null ? emailDTO.getEmailSubject() : emailTemplate.getEmailTemplateSubject(), mailBody, EmailTypes.HTML, emailDTO.getEmails(), emailDTO.getBcc(), emailDTO.getCopy());
            attempt++;
        }
        if (!successfulSending)
            throw new EmailSenderException("Mail server connection failed. Attempt count = " + attempt);

    }

    private String prepareText(EmailTemplate emailTemplate, Map<String, Object> params) throws InputValidationException {
        try {
            params.put("dateMillis",System.currentTimeMillis());
            String templateString = emailTemplate.getEmailTemplateString();
            Template template = new Template("templateName", new StringReader(templateString), configuration);
            return FreeMarkerTemplateUtils.processTemplateIntoString(template, params);
        } catch (Exception e) {
            throw new InputValidationException(e.getMessage(), e);
        }
    }
}
