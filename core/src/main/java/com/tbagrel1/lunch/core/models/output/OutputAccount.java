package com.tbagrel1.lunch.core.models.output;

public class OutputAccount {
    private final String username;
    private final String firstname;
    private final String lastname;
    private final String displayedName;
    private final boolean enabled;

    public OutputAccount(String username, String firstname, String lastname, String displayedName, boolean enabled) {
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
        this.displayedName = displayedName;
        this.enabled = enabled;
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

    public boolean isEnabled() {
        return enabled;
    }
}
