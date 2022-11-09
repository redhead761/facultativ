package com.epam.facultativ.entity;

import java.time.LocalDate;
import java.util.Objects;

public class Course {
    private int id;
    private String title;
    private int duration;
    private LocalDate startDate;
    private String description;
    private Category category;
    private CourseStatus courseStatus;

    public Course() {
    }

    public Course(int id, String title, int duration, LocalDate startDate, String description, Category category, CourseStatus courseStatus) {
        this.id = id;
        this.title = title;
        this.duration = duration;
        this.startDate = startDate;
        this.description = description;
        this.category = category;
        this.courseStatus = courseStatus;
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

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public CourseStatus getCourseStatus() {
        return courseStatus;
    }

    public void setCourseStatus(CourseStatus courseStatus) {
        this.courseStatus = courseStatus;
    }

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", duration=" + duration +
                ", startDate=" + startDate +
                ", description='" + description + '\'' +
                ", categoryId=" + category +
                ", courseStatusId=" + courseStatus +
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
                && courseStatus == course.courseStatus
                && title.equals(course.title)
                && startDate.equals(course.startDate)
                && description.equals(course.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, duration, startDate, description, category, courseStatus);
    }
}
