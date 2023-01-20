package com.epam.facultative.service;

import com.epam.facultative.data_layer.entities.Course;
import com.epam.facultative.dto.CategoryDTO;
import com.epam.facultative.dto.CourseDTO;
import com.epam.facultative.dto.TeacherDTO;
import com.epam.facultative.dto.UserDTO;
import com.epam.facultative.exception.ServiceException;
import com.epam.facultative.exception.ValidateException;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Map;

public interface GeneralService {

    UserDTO authorization(String login, String password) throws ServiceException, ValidateException;

    Map.Entry<Integer, List<CourseDTO>> getAllCourses(int offset, int numberOfRows) throws ServiceException;

    Map.Entry<Integer, List<CourseDTO>> sortCoursesByAlphabet(int offset, int numberOfRows) throws ServiceException;

    Map.Entry<Integer, List<CourseDTO>> sortCoursesByAlphabetReverse(int offset, int numberOfRows) throws ServiceException;

    Map.Entry<Integer, List<CourseDTO>> sortCoursesByDuration(int offset, int numberOfRows) throws ServiceException;

    Map.Entry<Integer, List<CourseDTO>> sortCoursesByAmountOfStudents(int offset, int numberOfRows) throws ServiceException;

    Map.Entry<Integer, List<CourseDTO>> getCoursesByCategory(int categoryId, int offset, int numberOfRows) throws ServiceException;

    Map.Entry<Integer, List<CourseDTO>> getCoursesByTeacher(int teacherId, int offset, int numberOfRows) throws ServiceException;

    List<CategoryDTO> getAllCategories() throws ServiceException;

    List<TeacherDTO> getAllTeachers() throws ServiceException;

    ByteArrayOutputStream downloadAllCoursesInPdf(String locale) throws ServiceException;

}