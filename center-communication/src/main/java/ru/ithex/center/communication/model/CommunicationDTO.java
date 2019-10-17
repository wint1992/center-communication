package ru.ithex.center.communication.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import ru.ithex.baseweb.model.Validation;
import ru.ithex.center.communication.exception.CommunicationDtoValidationException;

import java.util.HashMap;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CommunicationDTO implements Validation {
    private final Integer code;
    private final Map<String, Object> params;

    public CommunicationDTO(Integer code, Map<String, Object> params) {
        this.code = code;
        this.params = params == null ? new HashMap<>() : params;
    }

    public Map<String, Object> getParams(){
        return params;
    }

    public Integer getCode() {
        return code;
    }

    @Override
    public void validate(){
        if (code == null)
            throw new CommunicationDtoValidationException("Incorrect input params: code is required");
    }
}