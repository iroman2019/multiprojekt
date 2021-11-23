package eu.pontsystems.be.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class Consumer {
    @KafkaListener(topics = "#{'${kafka.topic.name:messages}'}", groupId = "group_id")
    public void consume(final String message) {
        log.info("Consuming message2.");
        log.info(message);
    }
}
