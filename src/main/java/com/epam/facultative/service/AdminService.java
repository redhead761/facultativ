package com.epam.facultative.service;

import com.epam.facultative.dto.CategoryDTO;
import com.epam.facultative.dto.CourseDTO;
import com.epam.facultative.dto.StudentDTO;
import com.epam.facultative.dto.TeacherDTO;
import com.epam.facultative.exception.ServiceException;
import com.epam.facultative.exception.ValidateException;

import java.util.List;

public interface AdminService {
    public void courseLaunchNewsLetter(CourseDTO courseDTO) throws ServiceException;

    //Course
    void addCourse(CourseDTO courseDTO) throws ServiceException, ValidateException;

    void updateCourse(CourseDTO courseDTO) throws ServiceException, ValidateException;

    void deleteCourse(int courseId) throws ServiceException;

    //Category
    void addCategory(CategoryDTO categoryDTO) throws ServiceException, ValidateException;

    void updateCategory(CategoryDTO categoryDTO) throws ServiceException, ValidateException;

    void deleteCategory(int categoryId) throws ServiceException;

    List<CategoryDTO> getAllCategoriesPagination(int offset, int numberOfRows) throws ServiceException;

    int getNoOfRecordsCategories();

    //User
    void assigned(int idCourse, int idUser) throws ServiceException, ValidateException;

    void blockStudent(int userId) throws ServiceException;

    void unblockStudent(int userId) throws ServiceException;

    void addTeacher(TeacherDTO teacherDTO) throws ServiceException, ValidateException;

    List<StudentDTO> getAllStudentsPagination(int offset, int noOfRecords) throws ServiceException;

    List<TeacherDTO> getAllTeachersPagination(int offset, int noOfRecords) throws ServiceException;

    CategoryDTO getCategory(int id) throws ServiceException, ValidateException;

    CourseDTO getCourse(int id) throws ServiceException, ValidateException;

    TeacherDTO getTeacher(int id) throws ServiceException, ValidateException;

    int getNoOfRecordsTeachers();

    int getNoOfRecordsStudents();
}