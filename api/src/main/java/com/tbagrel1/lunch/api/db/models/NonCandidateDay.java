package com.tbagrel1.lunch.api.db.models;

import java.util.Calendar;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

@Entity
@IdClass(NonCandidateDayId.class)
public class NonCandidateDay {
    @Id
    private String username;

    @Id
    private Calendar day;

    public NonCandidateDay() {
    }

    public NonCandidateDay(String username, Calendar day) {
        this.username = username;
        this.day = day;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Calendar getDay() {
        return day;
    }

    public void setDay(Calendar day) {
        this.day = day;
    }
}
