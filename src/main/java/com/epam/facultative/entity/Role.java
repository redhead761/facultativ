package com.epam.facultative.entity;

import java.util.Arrays;

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

    public static Role of(int id) {
        return Arrays.stream(Role.values())
                .filter(role -> role.getId() == id)
                .findAny()
                .orElseThrow();
    }
}

