package com.epam.facultative.dto;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.io.Serializable;

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
