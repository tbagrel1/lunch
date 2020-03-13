package com.tbagrel1.lunch.api.db.models;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

public class EventId implements Serializable {
    @Temporal(TemporalType.TIMESTAMP)
    private Date instant;

    private String username;

    public EventId() {
    }

    public EventId(Date instant, String username) {
        this.instant = instant;
        this.username = username;
    }

    @Override public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        EventId that = (EventId) obj;
        return instant.equals(that.instant) && username.equals(that.username);
    }

    @Override public int hashCode() {
        return Objects.hash(
            instant,
            username
        );
    }

    public Date getInstant() {
        return instant;
    }

    public void setInstant(Date instant) {
        this.instant = instant;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
