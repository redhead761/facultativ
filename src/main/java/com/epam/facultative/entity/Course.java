package com.epam.facultative.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

@Data
@EqualsAndHashCode(of = {"id", "title"})
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
    private User teacher;

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

}
