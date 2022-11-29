package com.epam.facultative.service;

import com.epam.facultative.dto.CourseDTO;
import com.epam.facultative.dto.UserDTO;
import com.epam.facultative.entity.Category;
import com.epam.facultative.entity.Course;
import com.epam.facultative.entity.User;
import com.epam.facultative.exception.DAOException;
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

    void addTeacher(User user) throws ServiceException, ValidateException, DAOException;

    List<Category> getAllCategories() throws ServiceException;

    List<UserDTO> getAllTeachers() throws ServiceException;

    List<UserDTO> getAllStudents() throws ServiceException;

    Category getCategory(int id) throws ServiceException;

    CourseDTO getCourse(int id) throws ServiceException;
    UserDTO getTeacher(int id) throws ServiceException;
 }
