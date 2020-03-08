package com.tbagrel1.lunch.core.models.output;

public class OutputSession {
    private final String username;
    private final boolean isLogged;

    public OutputSession(String username, boolean isLogged) {
        this.username = username;
        this.isLogged = isLogged;
    }
}
