package eu.pontsystems.be.services;


import eu.pontsystems.be.model.StoredMessages;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class Consumer {
    private final StoredMessagesService storedMessageService;

    @Value("${Consumer.separator}")
    private String consumerSeparator;

    @KafkaListener(topics = "#{'${kafka.topic.name:messages}'}", groupId = "group_id")
    public void consume(final String message) {

        log.info("Consuming message.");
        log.info(message);
        log.info("Separator to Kafka: " + consumerSeparator);

        StoredMessages storedMessage = new StoredMessages();
        String myMessage = message;

        String[] parts = myMessage.split(consumerSeparator);
        storedMessage.setName(parts[0]);
        storedMessage.setMessage(parts[1]);

        log.info("A neve: " + storedMessage.getName());
        log.info("Az üzenete: " + storedMessage.getMessage());

        storedMessageService.createStoredMessage(storedMessage);

    }
}


