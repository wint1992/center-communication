package ru.ithex.center.communication.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import ru.ithex.center.communication.controller.CommunicationController;
import ru.ithex.center.communication.model.CommunicationDTO;


@Service
public class KafkaConsumer {
    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaConsumer.class);

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final CommunicationController communicationController;
    private final ObjectMapper objectMapper;
    private final String TO_EVENT_TOPIC;

    public KafkaConsumer(KafkaTemplate<String, String> kafkaTemplate, CommunicationController communicationController, ObjectMapper objectMapper, @Value("${app.topic.response}") String TO_EVENT_TOPIC) {
        this.kafkaTemplate = kafkaTemplate;
        this.communicationController = communicationController;
        this.objectMapper = objectMapper;
        this.TO_EVENT_TOPIC = TO_EVENT_TOPIC;
    }

    @KafkaListener(id = "KafkaCommunicationListener", topics = "${app.topic.request}", containerFactory = "kafkaListenerContainerFactory")
    public void listen(@Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) String key, @Payload String data) {
        try {
            LOGGER.info("key = {}; msg = {}", key, data);
            String replData = data
                    .replace("\"{", "{")
                    .replace("}\"", "}")
                    .replace("\"[", "[")
                    .replace("]\"", "]")
                    .replace("\\", "")
                    .replace("'", "\"");
            CommunicationDTO communicationDTO = objectMapper.readValue(replData, CommunicationDTO.class);
            String response = objectMapper.writeValueAsString(communicationController.communicate(communicationDTO));
            kafkaTemplate.send(
                    TO_EVENT_TOPIC
                    , key
                    , response
            );
        } catch (Exception exp) {
            LOGGER.error("KafkaConsumer.listen exception: ", exp);
        }
    }
}
