package com.tbagrel1.lunch.core.models.output;

public class OutputAccount extends OutputAbstractEntity {
    private final String firstname;
    private final String lastname;

    public OutputAccount(long id, String firstname, String lastname) {
        super(id);
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
