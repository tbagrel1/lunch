package com.tbagrel1.lunch.core.models.input;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class InputAnswer {
    private final boolean isYes;
    private final String comment;

    @JsonCreator
    public InputAnswer(
        @JsonProperty("isYes") boolean isYes,
        @JsonProperty("comment") String comment
    ) {
        this.isYes = isYes;
        this.comment = comment;
    }

    public boolean isYes() {
        return isYes;
    }

    public String getComment() {
        return comment;
    }
}
