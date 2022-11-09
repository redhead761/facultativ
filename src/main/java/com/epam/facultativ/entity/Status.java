package com.epam.facultativ.entity;

import java.util.Objects;

public class Status {

//    COMING_SOON("coming_soon"),
//    IN_PROCESS("in_process"),
//    COMPLETED("completed");
//
//    private final String courseStatus;
//
//    Status(String courseStatus) {
//        this.courseStatus = courseStatus;
//    }
//
//    public String getCourseStatus() {
//        return courseStatus;
//    }

    private int id;
    private String title;

    public Status() {
    }

    public Status(int id, String title) {
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
        Status status = (Status) o;
        return id == status.id && title.equals(status.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title);
    }

    @Override
    public String toString() {
        return "Status{" +
                "id=" + id +
                ", title='" + title + '\'' +
                '}';
    }
}
