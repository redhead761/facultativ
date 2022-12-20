package com.epam.facultative.entity;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

public class Course implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private int id;
    private String title;
    private int duration;
    private LocalDate startDate;
    private int amountStudents;
    private String description;
    private Category category;
    private Status status;

    public Course() {
    }

    public Course(String title, int duration, LocalDate startDate, String description, Category category, Status status) {
        this.title = title;
        this.duration = duration;
        this.startDate = startDate;
        this.description = description;
        this.category = category;
        this.status = status;
    }

    public Course(int id, String title, int duration, LocalDate startDate, String description, Category category, Status status) {
        this.id = id;
        this.title = title;
        this.duration = duration;
        this.startDate = startDate;
        this.description = description;
        this.category = category;
        this.status = status;
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

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getAmountStudents() {
        return amountStudents;
    }

    public void setAmountStudents(int amountStudents) {
        this.amountStudents = amountStudents;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", duration=" + duration +
                ", startDate=" + startDate +
                ", amountStudents=" + amountStudents +
                ", description='" + description + '\'' +
                ", category=" + category +
                ", status=" + status +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return id == course.id
                && duration == course.duration
                && amountStudents == course.amountStudents
                && Objects.equals(title, course.title)
                && Objects.equals(startDate, course.startDate)
                && Objects.equals(description, course.description)
                && Objects.equals(category, course.category)
                && status == course.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, duration, startDate, amountStudents, description, category, status);
    }
}
