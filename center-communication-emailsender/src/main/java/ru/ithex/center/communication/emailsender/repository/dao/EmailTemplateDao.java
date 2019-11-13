package ru.ithex.center.communication.emailsender.repository.dao;

import ru.ithex.center.communication.emailsender.model.entity.EmailTemplate;

public interface EmailTemplateDao {
    EmailTemplate getEmailTemplateByCode(Integer code);
}
