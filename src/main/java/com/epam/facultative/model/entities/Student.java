package com.epam.facultative.model.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

import java.io.Serial;
import java.io.Serializable;

/**
 * Student entity class. Matches table 'student' in database.
 * Use Student.builder().fieldName(fieldValue).build() to create an instance
 *
 * @author Oleksandr Panchenko
 * @version 1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
public class Student extends User implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private int courseNumber;
    private boolean block;
    private int grade;
}
