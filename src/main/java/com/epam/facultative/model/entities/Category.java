package com.epam.facultative.model.entities;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.io.Serializable;

/**
 * Category entity class. Matches table 'category' in database.
 * Use Category.builder().fieldName(fieldValue).build() to create an instance
 *
 * @author Oleksandr Panchenko
 * @version 1.0
 */
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
