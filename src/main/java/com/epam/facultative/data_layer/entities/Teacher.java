package com.epam.facultative.data_layer.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

import java.io.Serial;
import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper=true)
@SuperBuilder
public class Teacher extends User implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private String degree;

}
