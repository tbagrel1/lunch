package com.tbagrel1.lunch.core.models.input;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class InputAccount {
    private final String username;
    private final String firstname;
    private final String lastname;
    private final String displayedName;
    private final String password;

    @JsonCreator
    public InputAccount(
        @JsonProperty("username") String username,
        @JsonProperty("firstname") String firstname,
        @JsonProperty("lastname") String lastname,
        @JsonProperty("displayedName") String displayedName,
        @JsonProperty("password") String password
    ) {
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
        this.displayedName = displayedName;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getDisplayedName() {
        return displayedName;
    }

    public String getPassword() {
        return password;
    }
}
