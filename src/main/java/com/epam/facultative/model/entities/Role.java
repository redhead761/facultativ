package com.epam.facultative.model.entities;

/**
 * Role entity enum. Matches table 'role' in database.
 *
 * @author Oleksandr Panchenko
 * @version 1.0
 */
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

