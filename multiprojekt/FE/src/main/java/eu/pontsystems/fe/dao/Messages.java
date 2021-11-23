package eu.pontsystems.fe.dao;

import lombok.Data;

import javax.validation.constraints.NotEmpty;


@Data
public class Messages {

    @NotEmpty(message = "The Name can't be null")
    @NotEmpty
    private String name;

    @NotEmpty(message = "The Message can't be null")
    @NotEmpty
    private String message;
}
