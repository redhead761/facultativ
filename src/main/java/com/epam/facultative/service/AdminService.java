package com.epam.facultative.service;

import com.epam.facultative.dto.CourseDTO;
import com.epam.facultative.dto.UserDTO;
import com.epam.facultative.entity.Category;
import com.epam.facultative.entity.Course;
import com.epam.facultative.entity.User;

import java.util.List;

public interface AdminService {


    List<UserDTO> getStudents(int courseId);

    void addCourse(Course course);

    void updateCourse(Course course);

    void deleteCourse(int courseId);

    void addCategory(Category category);

    void updateCategory(Category category);

    void deleteCategory(int categoryId);

    void assigned(int idCourse, int idUser);

    void blockStudent(int userId);

    void unblockStudent(int userId);

    void addTeacher(User user);
}
