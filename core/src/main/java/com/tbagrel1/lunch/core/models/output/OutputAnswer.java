package com.tbagrel1.lunch.core.models.output;

public class OutputAnswer {
    private final OutputAccount account;

    private final boolean isYes;

    private final String comment;

    public OutputAnswer(OutputAccount account, boolean isYes, String comment) {
        this.account = account;
        this.isYes = isYes;
        this.comment = comment;
    }

    public OutputAccount getAccount() {
        return account;
    }

    public boolean isYes() {
        return isYes;
    }

    public String getComment() {
        return comment;
    }
}
