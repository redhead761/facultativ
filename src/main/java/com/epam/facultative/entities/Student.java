package com.epam.facultative.entities;

import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.io.Serial;
import java.io.Serializable;

@Data
@SuperBuilder
public class Student extends User implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private int courseNumber;
    private boolean block;
    private int grade;
}
