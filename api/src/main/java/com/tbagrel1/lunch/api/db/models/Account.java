package com.tbagrel1.lunch.api.db.models;

import com.tbagrel1.lunch.core.models.input.InputAccount;
import com.tbagrel1.lunch.core.models.output.OutputAccount;

import javax.persistence.Entity;

@Entity
public class Account extends AbstractDbEntity {
    private String firstname;

    private String lastname;

    public Account() {

    }

    public Account(String firstname, String lastname) {
        this.firstname = firstname;
        this.lastname = lastname;
    }

    @Override
    public String toString() {
        return String.format("Account[id=%d, firstname='%s', lastname='%s']", id, firstname, lastname);
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public static Account fromInput(InputAccount inputAccount) {
        return new Account(inputAccount.getFirstname(), inputAccount.getLastname());
    }

    public OutputAccount toOutput() {
        return new OutputAccount(id, firstname, lastname);
    }
}
