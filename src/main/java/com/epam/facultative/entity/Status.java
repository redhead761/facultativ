package com.epam.facultative.entity;

public enum Status {

    COMING_SOON(1),
    IN_PROCESS(2),
    COMPLETED(3);

    private final int id;

    Status(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
