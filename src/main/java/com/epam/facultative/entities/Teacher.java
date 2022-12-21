package com.epam.facultative.entities;

import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.io.Serial;
import java.io.Serializable;

@Data
@SuperBuilder
public class Teacher extends User implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private String degree;
}
