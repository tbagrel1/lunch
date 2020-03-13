package com.tbagrel1.lunch.api.db.models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

@Entity
@IdClass(EventId.class)
public class Event {
    @Id
    private Date instant;

    @Id
    private String username;

    private String content;

    public Event() {
    }

    public Event(Date instant, String username, String content) {
        this.instant = instant;
        this.username = username;
        this.content = content;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
