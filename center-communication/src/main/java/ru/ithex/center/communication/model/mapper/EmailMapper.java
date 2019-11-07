package ru.ithex.center.communication.model.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.ithex.center.communication.emailsender.exceptions.EmailMappingException;
import ru.ithex.center.communication.emailsender.model.dto.EmailDTO;

import java.io.IOException;
import java.util.Map;

public class EmailMapper {
    public static EmailDTO map(Map<String, Object> params){
        ObjectMapper objectMapper = new ObjectMapper();
        EmailDTO emailDTO = null;
        try {
            emailDTO = objectMapper.readValue(objectMapper.writeValueAsString(params), EmailDTO.class);
        } catch (JsonProcessingException e){
            throw new EmailMappingException("Ошибка преобразования входных параметров", e);
        } catch (IOException e){
            throw new EmailMappingException("Ошибка формирования объекта из входных параметров", e);
        }
        emailDTO.setParams(params);
        return emailDTO;
    }
}
