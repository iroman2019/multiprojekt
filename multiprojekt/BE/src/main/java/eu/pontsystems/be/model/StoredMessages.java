package eu.pontsystems.be.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Table
public class StoredMessages {
    @PrimaryKey
    private String id;

    private String name;
    private String message;
    private boolean published;

}
