package db.entity;

import java.util.Arrays;

public enum Role {
    ADMIN(1),
    TEACHER(2),
    STUDENT(3);

    private final int role;

    Role(int role) {
        this.role = role;
    }

    public int getId() {
        return role;
    }

    public static Role of(int id) {
        return Arrays.stream(Role.values())
                .filter(role -> role.getId() == id)
                .findAny()
                .orElseThrow();
    }
}
