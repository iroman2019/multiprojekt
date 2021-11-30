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
    private String conSeparator;

    private final String SEP = conSeparator;

    @KafkaListener(topics = "#{'${kafka.topic.name:messages}'}", groupId = "group_id")
    public void consume(final String message) {

        log.info("Consuming message2.");
        log.info(message);
        System.out.println("Kiírom: " + message);
        System.out.println("Separatokintról: " + conSeparator);
        StoredMessages storedMessage = new StoredMessages();
        System.out.println("A teljes üzi: " + message);
        String myMessage = message;
        System.out.println("Separatot: " + conSeparator);
        String[] parts = myMessage.split(conSeparator);
        storedMessage.setName(parts[0]);
        storedMessage.setMessage(parts[1]);
        System.out.println("A neve: " + storedMessage.getName());
        System.out.println("Az üzenete: " + storedMessage.getMessage());

        storedMessageService.createStoredMessage(storedMessage);

    }
}


