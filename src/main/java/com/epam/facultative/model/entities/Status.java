package com.epam.facultative.model.entities;

/**
 * Status entity enum. Matches table 'status' in database.
 *
 * @author Oleksandr Panchenko
 * @version 1.0
 */
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
