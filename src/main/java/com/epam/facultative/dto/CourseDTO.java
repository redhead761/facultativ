package com.epam.facultative.dto;

import com.epam.facultative.entity.Category;
import com.epam.facultative.entity.Status;

import java.time.LocalDate;
import java.util.Objects;

public class CourseDTO {
    private int id;
    private String title;
    private int duration;
    private LocalDate startDate;
    private int amountStudents;
    private String description;
    private Category category;
    private Status status;
    private UserDTO teacher;
    private int grade;

    public CourseDTO() {
    }

    public CourseDTO(int id, String title, int duration, LocalDate startDate, int amountStudents, String description, Category category, Status status, UserDTO teacher) {
        this.id = id;
        this.title = title;
        this.duration = duration;
        this.startDate = startDate;
        this.amountStudents = amountStudents;
        this.description = description;
        this.category = category;
        this.status = status;
        this.teacher = teacher;
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

    public int getAmountStudents() {
        return amountStudents;
    }

    public void setAmountStudents(int amountStudents) {
        this.amountStudents = amountStudents;
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

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public UserDTO getTeacher() {
        return teacher;
    }

    public void setTeacher(UserDTO teacher) {
        this.teacher = teacher;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CourseDTO courseDTO = (CourseDTO) o;
        return id == courseDTO.id && duration == courseDTO.duration && amountStudents == courseDTO.amountStudents && Objects.equals(title, courseDTO.title) && Objects.equals(startDate, courseDTO.startDate) && Objects.equals(description, courseDTO.description) && Objects.equals(category, courseDTO.category) && status == courseDTO.status && Objects.equals(teacher, courseDTO.teacher);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, duration, startDate, amountStudents, description, category, status, teacher);
    }

    @Override
    public String toString() {
        return "CourseDTO{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", duration=" + duration +
                ", startDate=" + startDate +
                ", amountStudents=" + amountStudents +
                ", description='" + description + '\'' +
                ", category=" + category +
                ", status=" + status +
                ", teacher=" + teacher +
                '}';
    }
}
