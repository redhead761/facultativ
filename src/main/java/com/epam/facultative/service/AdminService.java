package com.epam.facultative.service;

import com.epam.facultative.dto.CategoryDTO;
import com.epam.facultative.dto.CourseDTO;
import com.epam.facultative.dto.StudentDTO;
import com.epam.facultative.dto.TeacherDTO;
import com.epam.facultative.exception.ServiceException;
import com.epam.facultative.exception.ValidateException;

import java.util.List;
import java.util.Map;

public interface AdminService {
    void courseLaunchNewsLetter(CourseDTO courseDTO) throws ServiceException;

    //Course
    void addCourse(CourseDTO courseDTO) throws ServiceException, ValidateException;

    void updateCourse(CourseDTO courseDTO) throws ServiceException, ValidateException;

    void deleteCourse(int courseId) throws ServiceException, ValidateException;

    //Category
    void addCategory(CategoryDTO categoryDTO) throws ServiceException, ValidateException;

    void updateCategory(CategoryDTO categoryDTO) throws ServiceException, ValidateException;

    void deleteCategory(int categoryId) throws ServiceException, ValidateException;

    Map.Entry<Integer, List<CategoryDTO>> getAllCategoriesPagination(String param) throws ServiceException;

    //User
    void assigned(int idCourse, int idUser) throws ServiceException, ValidateException;

    void blockStudent(int userId) throws ServiceException;

    void unblockStudent(int userId) throws ServiceException;

    void addTeacher(TeacherDTO teacherDTO) throws ServiceException, ValidateException;

    Map.Entry<Integer, List<StudentDTO>> getAllStudentsPagination(String param) throws ServiceException;

    Map.Entry<Integer, List<TeacherDTO>> getAllTeachersPagination(String param) throws ServiceException;

    CategoryDTO getCategory(int id) throws ServiceException, ValidateException;

    CourseDTO getCourse(int id) throws ServiceException, ValidateException;

    TeacherDTO getTeacher(int id) throws ServiceException, ValidateException;

    void deleteTeacher(int id) throws ValidateException, ServiceException;
}