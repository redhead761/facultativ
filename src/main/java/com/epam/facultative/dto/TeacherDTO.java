package com.epam.facultative.dto;

import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.io.Serial;
import java.io.Serializable;

@Data
@SuperBuilder
public class TeacherDTO extends UserDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private String degree;
}
