package com.epam.facultativ.entity;

import java.time.LocalDate;
import java.util.Objects;

public class Course {
    private int id;
    private String title;
    private int duration;
    private LocalDate startDate;
    private String description;
    private int studentsOnCourse;
    private Category category;
    private Status status;


    public Course() {
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

    public int getStudentsOnCourse() {
        return studentsOnCourse;
    }

    public void setStudentsOnCourse(int studentsOnCourse) {
        this.studentsOnCourse = studentsOnCourse;
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
                ", description='" + description + '\'' +
                ", category=" + category.getTitle() +
                ", course status=" + status.getTitle() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return id == course.id
                && duration == course.duration
                && category == course.category
                && status == course.status
                && title.equals(course.title)
                && startDate.equals(course.startDate)
                && description.equals(course.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, duration, startDate, description, category, status);
    }


}
