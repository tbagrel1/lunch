package com.tbagrel1.lunch.core.models.output;

public abstract class OutputAbstractEntity {
    protected final long id;

    public OutputAbstractEntity(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }
}
