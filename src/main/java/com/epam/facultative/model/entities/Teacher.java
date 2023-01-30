package com.epam.facultative.model.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

import java.io.Serial;
import java.io.Serializable;

/**
 * Teacher entity class. Matches table 'teacher' in database.
 * Use Teacher.builder().fieldName(fieldValue).build() to create an instance
 *
 * @author Oleksandr Panchenko
 * @version 1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
public class Teacher extends User implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private String degree;

}
