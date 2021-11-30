package eu.pontsystems.be.services;

import com.datastax.driver.core.utils.UUIDs;
import eu.pontsystems.be.model.StoredMessages;
import eu.pontsystems.be.repository.StoredMessagesRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "http://localhost:8081")
@Service
@Slf4j
@RequiredArgsConstructor
public class StoredMessagesService {

    private final StoredMessagesRepository storedMessagesRepository;

    public List<StoredMessages> getAllStoredMessages(String name) {
            List<StoredMessages> messages = new ArrayList<StoredMessages>();


            if (name == null)
                storedMessagesRepository.findAll().forEach(messages::add);
            else
               storedMessagesRepository.findByNameContaining(name).forEach(messages::add);

            if (messages.isEmpty()) {
                return null;
            }
            messages.stream().sorted();
            return messages;
    }


//    public Optional<StoredMessages> getStoredMessageById(UUID id, StoredMessagesRepository storedMessagesRepository) {
//        Optional<StoredMessages> messageData = storedMessagesRepository.findById(id);
//
//        if (messageData.isPresent()) {
//            return messageData;
//        } else {
//            return null;
//        }
//    }


    public void createStoredMessage(StoredMessages message){
        try {
            StoredMessages _messages = storedMessagesRepository.save(new StoredMessages(UUIDs.timeBased(), message.getName(), message.getMessage(), false));

        } catch (Exception e) {
            System.out.println("Hiba lett a beírásnál "+ e.getMessage());
        }
    }

}
