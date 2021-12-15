package eu.pontsystems.fe.service;

import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class DataService {
    public int getNextYear() {
        LocalDate now = LocalDate.now();
        return now.getYear();
    }
}
