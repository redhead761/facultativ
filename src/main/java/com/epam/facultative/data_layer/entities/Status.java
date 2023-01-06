package com.epam.facultative.data_layer.entities;

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
