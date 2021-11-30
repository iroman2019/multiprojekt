package eu.pontsystems.fe.service;

import eu.pontsystems.fe.dao.Messages;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class Producer {

    @Value("${Producer.separator}")
    private String prodSeparator;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;


    @Value("${kafka.topic.name:messages}")
    String topic;


    public void send(Messages message) {
        String outputString = message.getName() + prodSeparator + message.getMessage();
        log.info("Sending message to kafka = {}", message);
        kafkaTemplate.send(topic, outputString);
    }
}
