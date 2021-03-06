package eu.pontsystems.be.repository;

import eu.pontsystems.be.model.StoredMessages;
import org.springframework.data.cassandra.repository.AllowFiltering;
import org.springframework.data.cassandra.repository.CassandraRepository;

import java.util.List;


public interface StoredMessagesRepository extends CassandraRepository<StoredMessages, String> {
    @AllowFiltering
    List<StoredMessages> findByPublished(boolean published);

    List<StoredMessages> findByNameContaining(String name);


}

