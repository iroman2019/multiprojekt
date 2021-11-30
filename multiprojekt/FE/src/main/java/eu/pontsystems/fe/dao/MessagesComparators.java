package eu.pontsystems.fe.dao;

import eu.pontsystems.fe.dao.paging.Direction;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class MessagesComparators {

    @EqualsAndHashCode
    @AllArgsConstructor
    @Getter
    static class Key {
        String name;
        Direction dir;
    }

    static Map<Key, Comparator<Messages>> map = new HashMap<>();

    static {
        map.put(new Key("name", Direction.asc), Comparator.comparing(Messages::getName));
        map.put(new Key("name", Direction.desc), Comparator.comparing(Messages::getName)
                .reversed());

        map.put(new Key("message", Direction.asc), Comparator.comparing(Messages::getMessage));
        map.put(new Key("message", Direction.desc), Comparator.comparing(Messages::getMessage)
                .reversed());

    }

    public static Comparator<Messages> getComparator(String name, Direction dir) {
        return map.get(new Key(name, dir));
    }

    private MessagesComparators() {
    }
}
