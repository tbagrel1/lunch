package com.tbagrel1.lunch.core.models.security;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class AccountCredential {
    private final String username;
    private final String password;

    @JsonCreator
    public AccountCredential(
        @JsonProperty("username") String username,
        @JsonProperty("password") String password
    ) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    @Override public String toString() {
        return String.format(
            "AccountCredential[username='%s', password='%s']",
            username, password
        );
    }
}
