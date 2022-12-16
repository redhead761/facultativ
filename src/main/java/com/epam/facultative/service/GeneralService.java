package com.epam.facultative.service;

import com.epam.facultative.dto.CourseDTO;
import com.epam.facultative.dto.UserDTO;
import com.epam.facultative.entity.Category;
import com.epam.facultative.exception.ServiceException;
import com.epam.facultative.exception.ValidateException;

import java.util.List;

public interface GeneralService {

    UserDTO authorization(String login, String password) throws ServiceException, ValidateException;

    List<CourseDTO> getAllCourses(int offset, int numberOfRows) throws ServiceException;

    List<CourseDTO> sortCoursesByAlphabet(int offset, int numberOfRows) throws ServiceException;

    List<CourseDTO> sortCoursesByAlphabetReverse() throws ServiceException;

    List<CourseDTO> sortCoursesByDuration() throws ServiceException;

    List<CourseDTO> sortCoursesBuAmountOfStudents() throws ServiceException;

    List<CourseDTO> getCoursesByCategory(int categoryId) throws ServiceException;

    List<CourseDTO> getCoursesByTeacher(int teacherId) throws ServiceException;

    List<UserDTO> getAllStudents() throws ServiceException;

    List<Category> getAllCategories() throws ServiceException;

    List<UserDTO> getAllTeachers() throws ServiceException;

    public int getNoOfRecords();
}
