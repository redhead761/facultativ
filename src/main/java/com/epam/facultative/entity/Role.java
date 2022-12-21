package com.epam.facultative.entity;

public enum Role {
    ADMIN(1),
    TEACHER(2),
    STUDENT(3);

    private final int id;

    Role(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

}

