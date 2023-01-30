package com.epam.facultative.model.entities;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * Course entity class. Matches table 'course' in database.
 * Use Course.builder().fieldName(fieldValue).build() to create an instance
 *
 * @author Oleksandr Panchenko
 * @version 1.0
 */
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
    private Teacher teacher;
}
