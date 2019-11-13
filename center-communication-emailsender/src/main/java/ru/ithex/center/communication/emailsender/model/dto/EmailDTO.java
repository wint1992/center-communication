package ru.ithex.center.communication.emailsender.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import ru.ithex.baseweb.model.Validation;
import ru.ithex.center.communication.emailsender.exception.EmailDtoValidationException;
import ru.ithex.center.communication.emailsender.model.Attachment;

import java.util.List;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class EmailDTO implements Validation {
    @JsonProperty("templateCode")
    private final Integer templateCode;

    @JsonProperty("emailSubject")
    private final String emailSubject;

    @JsonProperty("emails")
    private final List<String> emails;

    @JsonProperty("copy")
    private final List<String> copy;

    @JsonProperty("bcc")
    private final List<String> bcc;

    @JsonProperty("attachments")
    private final List<Attachment> attachments;

    @JsonProperty("emailParams")
    private final Map<String, Object> emailParams;

    public EmailDTO(
            @JsonProperty("templateCode") Integer templateCode,
            @JsonProperty("emailSubject") String emailSubject,
            @JsonProperty("emails") List<String> emails,
            @JsonProperty("copy") List<String> copy,
            @JsonProperty("bcc") List<String> bcc,
            @JsonProperty("attachments") List<Attachment> attachments,
            @JsonProperty("emailParams") Map<String, Object> emailParams) {
        this.templateCode = templateCode;
        this.emailSubject = emailSubject;
        this.emails = emails;
        this.copy = copy;
        this.bcc = bcc;
        this.attachments = attachments;
        this.emailParams = emailParams;
    }

    public Integer getTemplateCode() {
        return templateCode;
    }

    public String getEmailSubject() {
        return emailSubject;
    }

    public List<String> getEmails() {
        return emails;
    }

    public List<String> getCopy() {
        return copy;
    }

    public List<String> getBcc() {
        return bcc;
    }

    public Map<String, Object> getEmailParams() {
        return emailParams;
    }

    public List<Attachment> getAttachments() {
        return attachments;
    }

    @Override
    public void validate() {
        if (templateCode == null)
            throw new EmailDtoValidationException("Параметр 'templateCode' обязателен!");

        checkEmails(emails);
        checkEmails(copy);
        checkEmails(bcc);

        if ((emails == null || emails.size() == 0) && (copy == null || copy.size() == 0))
            throw new EmailDtoValidationException("Кому(emails) или Копия(copy) должны быть заполнены");
    }

    private void checkEmails(List<String> emails){
        if (emails != null)
            for (String src: emails) {
                src = src.trim();
                if (src.isEmpty())
                    emails.remove(src);
            }
    }
}
