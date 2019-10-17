package ru.ithex.center.communication.emailsender.model.entity;

import javax.persistence.*;

@Entity
@Table(name = "email_template", schema = "mdm")
public class EmailTemplate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "email_template_id", columnDefinition = "[emailTemplateId] ID шаблона email сообщения")
    private Integer emailTemplateId;

    @Column(name = "email_template_code", columnDefinition = "[emailTemplateCode] Код шаблона email сообщения")
    private Integer emailTemplateCode;

    @Column(name = "email_template_file_name", columnDefinition = "[emailTemplateFileName] Название файла шаблона email сообщения")
    private String emailTemplateFileName;

    @Column(name = "email_template_subject", columnDefinition = "[emailTemplateSubject] Тема письма для шаблона email сообщения")
    private String emailTemplateSubject;

    @Column(name = "email_template_string", columnDefinition = "[emailTemplateString] Шаблон письма")
    private String emailTemplateString;

    public Integer getEmailTemplateId() {
        return emailTemplateId;
    }

    public void setEmailTemplateId(Integer emailTemplateId) {
        this.emailTemplateId = emailTemplateId;
    }

    public Integer getEmailTemplateCode() {
        return emailTemplateCode;
    }

    public void setEmailTemplateCode(Integer emailTemplateCode) {
        this.emailTemplateCode = emailTemplateCode;
    }

    public String getEmailTemplateFileName() {
        return emailTemplateFileName;
    }

    public void setEmailTemplateFileName(String emailTemplateFileName) {
        this.emailTemplateFileName = emailTemplateFileName;
    }

    public String getEmailTemplateSubject() {
        return emailTemplateSubject;
    }

    public void setEmailTemplateSubject(String emailTemplateSubject) {
        this.emailTemplateSubject = emailTemplateSubject;
    }

    public String getEmailTemplateString() {
        return emailTemplateString;
    }

    public void setEmailTemplateString(String emailTemplateString) {
        this.emailTemplateString = emailTemplateString;
    }
}
