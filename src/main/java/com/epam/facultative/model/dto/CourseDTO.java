package com.epam.facultative.model.dto;

import com.epam.facultative.model.entities.Status;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

@Data
@EqualsAndHashCode(of = {"id", "title"})
@Builder
public class CourseDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private int id;
    private String title;
    private int duration;
    private LocalDate startDate;
    private int amountStudents;
    private String description;
    private CategoryDTO category;
    private Status status;
    private TeacherDTO teacher;

    @Override
    public String toString() {
        return "Title = " + title + "<br>" +
                "Duration = " + duration + "<br>" +
                "Start date = " + startDate + "<br>" +
                "Category " + category + "<br>" +
                "Status = " + status + "<br>" +
                "Description = " + description;
    }
}
