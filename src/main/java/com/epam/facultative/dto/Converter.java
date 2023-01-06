package com.epam.facultative.dto;

import com.epam.facultative.data_layer.entities.Course;
import com.epam.facultative.data_layer.entities.Student;
import com.epam.facultative.data_layer.entities.Teacher;
import com.epam.facultative.data_layer.entities.User;

public class Converter {
    public UserDTO userToDTO(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .login(user.getLogin())
                .name(user.getName())
                .surname(user.getSurname())
                .email(user.getEmail())
                .role(user.getRole())
                .build();
    }

    public TeacherDTO teacherToDTO(Teacher teacher) {
        return TeacherDTO.builder()
                .id(teacher.getId())
                .login(teacher.getLogin())
                .name(teacher.getName())
                .surname(teacher.getSurname())
                .email(teacher.getEmail())
                .role(teacher.getRole())
                .degree(teacher.getDegree())
                .build();
    }

    public StudentDTO studentToDTO(Student student) {
        return StudentDTO.builder()
                .id(student.getId())
                .login(student.getLogin())
                .name(student.getName())
                .surname(student.getSurname())
                .email(student.getEmail())
                .role(student.getRole())
                .block(student.isBlock())
                .courseNumber(student.getCourseNumber())
                .grade(student.getGrade())
                .build();
    }

    public CourseDTO courseToDTO(Course course) {
        return CourseDTO.builder()
                .id(course.getId())
                .title(course.getTitle())
                .duration(course.getDuration())
                .startDate(course.getStartDate())
                .amountStudents(course.getAmountStudents())
                .description(course.getDescription())
                .category(course.getCategory())
                .status(course.getStatus())
                .teacher(userToDTO(course.getTeacher()))
                .build();
    }
}
