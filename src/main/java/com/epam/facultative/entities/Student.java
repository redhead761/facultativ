package com.epam.facultative.entities;

import lombok.Builder;

import java.io.Serial;
import java.io.Serializable;

@Builder
public class Student extends User implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private String course_number;
    private boolean block;
}
