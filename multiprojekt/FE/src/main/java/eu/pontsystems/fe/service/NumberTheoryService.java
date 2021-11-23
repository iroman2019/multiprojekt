package eu.pontsystems.fe.service;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NumberTheoryService {

    private int getSumOfDigitSquare(int num) {
        int sum = 0;
        while (num > 0) {
            sum+=(num%10)*(num%10);
            num/=10;
        }
        return sum;
    }

    public List getChain(Integer number) {
        List chain = new ArrayList();
        chain.add(number);
        while (number!=89 && number!=1) {
            number = getSumOfDigitSquare(number);
            chain.add(number);
        }
        return chain;
    }

    public Boolean isHappyNumber(Integer number) {
        return getChain(number).contains(1);
    }
}
