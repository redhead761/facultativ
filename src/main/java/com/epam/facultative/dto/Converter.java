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

    public CourseDTO courseToDTO(Course course, UserDTO userDTO) {
        CourseDTO courseDTO = new CourseDTO();
        courseDTO.setId(course.getId());
        courseDTO.setTitle(course.getTitle());
        courseDTO.setDuration(course.getDuration());
        courseDTO.setStartDate(course.getStartDate());
        courseDTO.setAmountStudents(course.getAmountStudents());
        courseDTO.setDescription(course.getDescription());
        courseDTO.setCategory(course.getCategory());
        courseDTO.setStatus(course.getStatus());
        courseDTO.setTeacher(userDTO);
        return courseDTO;
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
