package com.tbagrel1.lunch.core.models.input;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class InputAccount {
    private final String firstname;
    private final String lastname;

    @JsonCreator
    public InputAccount(@JsonProperty("firstname") String firstname, @JsonProperty("lastname") String lastname) {
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }
}
