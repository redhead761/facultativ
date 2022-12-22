package com.epam.facultative.repositories;

import com.epam.facultative.entities.Category;
import com.epam.facultative.entities.Course;
import com.epam.facultative.entities.Teacher;
import com.epam.facultative.entities.User;
import com.epam.facultative.exception.DAOException;

import java.util.List;

public interface GeneralRepository {
    User authorization(String login) throws DAOException;

    List<Course> getAllCourses(int offset, int numberOfRows) throws DAOException;

    List<Course> sortCoursesByAlphabet(int offset, int numberOfRows) throws DAOException;

    List<Course> sortCoursesByAlphabetReverse(int offset, int numberOfRows) throws DAOException;

    List<Course> sortCoursesByDuration(int offset, int numberOfRows) throws DAOException;

    List<Course> sortCoursesBuAmountOfStudents(int offset, int numberOfRows) throws DAOException;

    List<Course> getCoursesByCategory(int categoryId, int offset, int numberOfRows) throws DAOException;

    List<Course> getCoursesByTeacher(int teacherId, int offset, int numberOfRows) throws DAOException;

    List<Category> getAllCategories() throws DAOException;

    List<Teacher> getAllTeachers() throws DAOException;

    int getNoOfRecordsCourses();
}
