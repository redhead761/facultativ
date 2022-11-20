package com.epam.facultative.entity;

import java.util.Objects;

//public enum Role {
//    ADMIN(1),
//    TEACHER(2),
//    STUDENT(3);
//
//    private final int role;
//
//    Role(int role) {
//        this.role = role;
//    }
//
//    public int getId() {
//        return role;
//    }
//
//    public static Role of(int id) {
//        return Arrays.stream(Role.values())
//                .filter(role -> role.getId() == id)
//                .findAny()
//                .orElseThrow();
//    }
//}

public class Role {
    private int id;
    private String title;

    public Role() {
    }

    public Role(int id, String title) {
        this.id = id;
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Role role = (Role) o;
        return id == role.id && title.equals(role.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title);
    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", title='" + title + '\'' +
                '}';
    }
}