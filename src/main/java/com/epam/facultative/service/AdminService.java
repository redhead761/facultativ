package com.epam.facultative.service;

import com.epam.facultative.dto.CourseDTO;
import com.epam.facultative.dto.UserDTO;
import com.epam.facultative.entities.Category;
import com.epam.facultative.entities.Course;
import com.epam.facultative.entities.User;
import com.epam.facultative.exception.ServiceException;
import com.epam.facultative.exception.ValidateException;

import java.util.List;

public interface AdminService {
    //Course
    void addCourse(Course course) throws ServiceException, ValidateException;

    void updateCourse(Course course) throws ServiceException, ValidateException;

    void deleteCourse(int courseId) throws ServiceException;

    //Category
    void addCategory(Category category) throws ServiceException, ValidateException;

    void updateCategory(Category category) throws ServiceException, ValidateException;

    void deleteCategory(int categoryId) throws ServiceException;

    List<Category> getAllCategoriesPagination(int offset, int numberOfRows) throws ServiceException;

    int getNoOfRecordsCategories();

    //User
    void assigned(int idCourse, int idUser) throws ServiceException;

    void blockStudent(int userId) throws ServiceException;

    void unblockStudent(int userId) throws ServiceException;

    void addTeacher(User user) throws ServiceException, ValidateException;

    List<UserDTO> getAllStudentsPagination(int offset, int noOfRecords) throws ServiceException;

    List<UserDTO> getAllTeachersPagination(int offset, int noOfRecords) throws ServiceException;

    Category getCategory(int id) throws ServiceException;

    CourseDTO getCourse(int id) throws ServiceException;

    UserDTO getTeacher(int id) throws ServiceException;

    int getNoOfRecordsTeachers();

    int getNoOfRecordsStudents();
}
