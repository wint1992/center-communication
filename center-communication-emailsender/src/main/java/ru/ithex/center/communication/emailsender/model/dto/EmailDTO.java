package ru.ithex.center.communication.emailsender.model.dto;

import java.util.List;
import java.util.Map;

public class EmailDTO {
    private final Integer templateCode;
    private final String emailSubject;
    private final List<String> emails;
    private final List<String> copy;
    private final List<String> bcc;
    private Map<String, Object> params;

    public EmailDTO(Integer templateCode, String emailSubject, List<String> emails, List<String> copy, List<String> bcc) {
        this.templateCode = templateCode;
        this.emailSubject = emailSubject;
        this.emails = emails;
        this.copy = copy;
        this.bcc = bcc;
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
}
