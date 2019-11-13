package ru.ithex.center.communication.emailsender.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ithex.center.communication.emailsender.model.entity.EmailTemplate;

public interface EmailTemplateRepository extends JpaRepository<EmailTemplate, Integer> {
    EmailTemplate findByEmailTemplateCode(Integer code);
}
