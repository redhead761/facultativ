package com.epam.facultative.service;

import com.epam.facultative.data_layer.entities.Category;
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

    Map.Entry<Integer, List<CourseDTO>> getAllCourses(String param) throws ServiceException;

    Map.Entry<Integer, List<CategoryDTO>> getAllCategories() throws ServiceException;

    Map.Entry<Integer, List<TeacherDTO>> getAllTeachers() throws ServiceException;

    ByteArrayOutputStream downloadAllCoursesInPdf(String locale) throws ServiceException;

    void recoveryPassword(String email) throws ServiceException, ValidateException;

    void changePassword(String oldPassword, String newPassword, String userId) throws ServiceException, ValidateException;
}