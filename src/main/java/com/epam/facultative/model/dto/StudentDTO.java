package com.epam.facultative.model.dto;

import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.io.Serial;
import java.io.Serializable;

@Data
@SuperBuilder
public class StudentDTO extends UserDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private int courseNumber;
    private boolean block;
    private int grade;
}
