package com.tbagrel1.lunch.api.db.models;

import com.tbagrel1.lunch.api.db.repositories.AccountRepository;
import com.tbagrel1.lunch.api.exceptions.NotFoundException;
import com.tbagrel1.lunch.core.models.output.OutputAccount;
import com.tbagrel1.lunch.core.models.output.OutputAnswer;

import java.util.Calendar;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

@Entity
@IdClass(AnswerId.class)
public class Answer {
    @Id
    private String username;

    @Id
    private Calendar day;

    private boolean isYes;

    private String comment;

    public Answer() {
    }

    public Answer(String username, Calendar day, boolean isYes, String comment) {
        this.username = username;
        this.day = day;
        this.isYes = isYes;
        this.comment = comment;
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

    public boolean isYes() {
        return isYes;
    }

    public void setYes(boolean yes) {
        isYes = yes;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public OutputAnswer toOutput(AccountRepository accountRepository) {
        Account account = accountRepository.findByUsername(username)
            .orElseThrow(NotFoundException::new);
        return new OutputAnswer(
            account.toOutput(),
            isYes,
            comment
        );
    }
}
