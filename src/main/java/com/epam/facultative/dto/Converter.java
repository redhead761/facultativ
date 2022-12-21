package com.epam.facultative.dto;

import com.epam.facultative.entity.Course;
import com.epam.facultative.entity.User;

public class Converter {
    public UserDTO userToDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setLogin(user.getLogin());
        userDTO.setName(user.getName());
        userDTO.setSurname(user.getSurname());
        userDTO.setEmail(user.getEmail());
        userDTO.setBlock(user.isBlock());
        userDTO.setRole(user.getRole());
        return userDTO;
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

    public UserDTO userToStudent(User user, CourseDTO courseDTO, int grade) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setLogin(user.getLogin());
        userDTO.setName(user.getName());
        userDTO.setSurname(user.getSurname());
        userDTO.setEmail(user.getEmail());
        userDTO.setBlock(user.isBlock());
        userDTO.setRole(user.getRole());
        userDTO.setCourse(courseDTO);
        userDTO.setGrade(grade);
        return userDTO;
    }
}
