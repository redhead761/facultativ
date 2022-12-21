package com.epam.facultative.entities;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

@Data
@EqualsAndHashCode(of = {"id", "title"})
@Builder
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
}
