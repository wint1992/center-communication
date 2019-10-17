package ru.ithex.center.communication.emailsender.services.dictionaries;

import org.springframework.stereotype.Service;
import ru.ithex.center.communication.emailsender.model.entity.EmailTemplate;
import ru.ithex.center.communication.emailsender.repositories.dictionaries.EmailTemplateRepository;

@Service
public class EmailTemplateService {
    private final EmailTemplateRepository emailTemplateRepository;

    public EmailTemplateService(EmailTemplateRepository emailTemplateRepository) {
        this.emailTemplateRepository = emailTemplateRepository;
    }

    public EmailTemplate getEmailTemplateByCode(Integer emailTemplateCode){
        return emailTemplateRepository.findByEmailTemplateCode(emailTemplateCode);
    }
}
