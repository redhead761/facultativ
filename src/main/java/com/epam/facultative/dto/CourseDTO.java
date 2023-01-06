package com.epam.facultative.dto;

import com.epam.facultative.data_layer.entities.Category;
import com.epam.facultative.data_layer.entities.Status;
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
    private Category category;
    private Status status;
    private UserDTO teacher;
    private int grade;

//    public CourseDTO() {
//    }
//
//    public CourseDTO(int id, String title, int duration, LocalDate startDate, int amountStudents, String description, Category category, Status status, UserDTO teacher) {
//        this.id = id;
//        this.title = title;
//        this.duration = duration;
//        this.startDate = startDate;
//        this.amountStudents = amountStudents;
//        this.description = description;
//        this.category = category;
//        this.status = status;
//        this.teacher = teacher;
//    }

}
