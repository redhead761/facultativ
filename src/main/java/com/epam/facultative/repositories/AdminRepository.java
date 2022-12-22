package com.epam.facultative.repositories;

import com.epam.facultative.entities.*;
import com.epam.facultative.exception.DAOException;

import java.util.List;

public interface AdminRepository {
    //Course
    void addCourse(Course course) throws DAOException;

    void updateCourse(Course course) throws DAOException;

    void deleteCourse(int courseId) throws DAOException;

    Course getCourse(int id) throws DAOException;

    //Category
    void addCategory(Category category) throws DAOException;

    void updateCategory(Category category) throws DAOException;

    void deleteCategory(int categoryId) throws DAOException;

    List<Category> getAllCategoriesPagination(int offset, int numberOfRows) throws DAOException;

    Category getCategory(int id) throws DAOException;

    int getNoOfRecordsCategories();

    //User
    void assigned(int courseId, int userId) throws DAOException;

    void blockStudent(int userId) throws DAOException;

    void unblockStudent(int userId) throws DAOException;

    void addTeacher(Teacher teacher) throws DAOException;

    List<Student> getAllStudentsPagination(int offset, int noOfRecords) throws DAOException;

    List<Teacher> getAllTeachersPagination(int offset, int noOfRecords) throws DAOException;

    Teacher getTeacher(int id) throws DAOException;

    int getNoOfRecordsTeachers();

    int getNoOfRecordsStudents();

}
