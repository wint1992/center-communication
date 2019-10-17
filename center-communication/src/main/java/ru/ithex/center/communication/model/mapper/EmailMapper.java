package ru.ithex.center.communication.model.mapper;

import ru.ithex.center.communication.emailsender.model.dto.EmailDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class EmailMapper {
    public static EmailDTO map(Map<String, Object> params){
        EmailDTO emailDTO = new EmailDTO(
                (Integer) params.get("templateCode"),
                (String) params.get("emailSubject"),
                ((List<String>) params.getOrDefault("emails", new ArrayList<>())).stream().filter(in -> in != null && !in.isEmpty()).peek(s -> s.trim()).collect(Collectors.toList()),
                ((List<String>) params.getOrDefault("copy", new ArrayList<>())).stream().filter(in -> in != null && !in.isEmpty()).peek(s -> s.trim()).collect(Collectors.toList()),
                ((List<String>) params.getOrDefault("bcc", new ArrayList<>())).stream().filter(in -> in != null && !in.isEmpty()).peek(s -> s.trim()).collect(Collectors.toList())
        );
        emailDTO.setParams(params);
        return emailDTO;
    }
}
