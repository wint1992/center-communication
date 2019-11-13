package ru.ithex.center.communication.emailsender.services;

import org.springframework.stereotype.Service;
import ru.ithex.center.communication.emailsender.exception.EmailTemplateDataException;
import ru.ithex.center.communication.emailsender.model.entity.EmailTemplate;
import ru.ithex.center.communication.emailsender.repository.dao.EmailTemplateDao;
import ru.ithex.center.communication.emailsender.repository.jpa.EmailTemplateRepository;

@Service
public class EmailTemplateDaoService implements EmailTemplateDao {
    private final EmailTemplateRepository emailTemplateRepository;

    public EmailTemplateDaoService(EmailTemplateRepository emailTemplateRepository) {
        this.emailTemplateRepository = emailTemplateRepository;
    }

    public EmailTemplate getEmailTemplateByCode(Integer emailTemplateCode){
        EmailTemplate emailTemplate = emailTemplateRepository.findByEmailTemplateCode(emailTemplateCode);
        if (emailTemplate == null)
            throw new EmailTemplateDataException("Email шаблон с введенным кодом отсутствует");
        return emailTemplate;
    }
}
