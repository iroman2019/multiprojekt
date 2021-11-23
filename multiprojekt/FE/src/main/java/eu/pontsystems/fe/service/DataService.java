package eu.pontsystems.fe.service;

import eu.pontsystems.fe.dao.Messages;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class DataService {
    public int getNextYear() {
        LocalDate now = LocalDate.now();
        return now.getYear() + 1;
    }

    public Object sendDataToKafka(Messages messages){
        return messages;
    }
}
