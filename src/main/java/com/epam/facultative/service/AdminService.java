package com.epam.facultative.service;

import com.epam.facultative.dto.CourseDTO;
import com.epam.facultative.dto.UserDTO;
import com.epam.facultative.entity.Category;
import com.epam.facultative.entity.Course;
import com.epam.facultative.entity.User;
import com.epam.facultative.exception.ServiceException;
import com.epam.facultative.exception.ValidateException;

import java.util.List;

public interface AdminService {


    List<UserDTO> getStudents(int courseId) throws ServiceException;

    void addCourse(Course course) throws ServiceException, ValidateException;

    void updateCourse(Course course) throws ServiceException, ValidateException;

    void deleteCourse(int courseId) throws ServiceException;

    void addCategory(Category category) throws ServiceException, ValidateException;

    void updateCategory(Category category) throws ServiceException, ValidateException;

    void deleteCategory(int categoryId) throws ServiceException;

    void assigned(int idCourse, int idUser) throws ServiceException;

    void blockStudent(int userId) throws ServiceException;

    void unblockStudent(int userId) throws ServiceException;

    void addTeacher(User user) throws ServiceException, ValidateException;
}
