package com.epam.facultative.model.dto;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.io.Serializable;

/**
 * CategoryDTO class. Fields are similar to Event entity.
 * Use CategoryDTO.builder().fieldName(fieldValue).build() to create an instance
 *
 * @author Oleksandr Panchenko
 * @version 1.0
 */
@Data
@EqualsAndHashCode(of = {"id", "title"})
@Builder
public class CategoryDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private int id;
    private String title;
    private String description;

    @Override
    public String toString() {
        return "title = " + title + "<br>" +
                "Category description= " + description;
    }
}
