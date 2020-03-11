package com.tbagrel1.lunch.api.db.models;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Objects;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

public class NonCandidateDayId implements Serializable {
    private String username;

    @Temporal(TemporalType.DATE)
    private Calendar day;

    public NonCandidateDayId(String username, Calendar day) {
        this.username = username;
        this.day = day;
    }

    @Override public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        NonCandidateDayId that = (NonCandidateDayId) obj;
        return username.equals(that.username) &&
            day.get(Calendar.YEAR) == that.day.get(Calendar.YEAR) &&
            day.get(Calendar.MONTH) == that.day.get(Calendar.MONTH) &&
            day.get(Calendar.DAY_OF_MONTH) == that.day.get(Calendar.DAY_OF_MONTH);
    }

    @Override public int hashCode() {
        return Objects.hash(
            username,
            day.get(Calendar.YEAR),
            day.get(Calendar.MONTH),
            day.get(Calendar.DAY_OF_MONTH)
        );
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
