package com.epam.facultative.entities;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.io.Serializable;

@Data
@EqualsAndHashCode(of = {"id", "title"})
@Builder
public class Category implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private int id;
    private String title;
    private String description;
}
