package ru.ithex.center.communication.emailsender.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import ru.ithex.center.communication.emailsender.model.Attachment;

import java.util.List;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class EmailDTO {
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

    private Map<String, Object> params;

    public EmailDTO(
            @JsonProperty("templateCode") Integer templateCode,
            @JsonProperty("emailSubject") String emailSubject,
            @JsonProperty("emails") List<String> emails,
            @JsonProperty("copy") List<String> copy,
            @JsonProperty("bcc") List<String> bcc,
            @JsonProperty("attachments") List<Attachment> attachments) {
        this.templateCode = templateCode;
        this.emailSubject = emailSubject;
        this.emails = emails;
        this.copy = copy;
        this.bcc = bcc;
        this.attachments = attachments;
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

    public Map<String, Object> getParams() {
        return params;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }

    public List<Attachment> getAttachments() {
        return attachments;
    }
}
