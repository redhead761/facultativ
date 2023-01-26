package com.epam.facultative.model.service;

import com.epam.facultative.model.dto.CategoryDTO;
import com.epam.facultative.model.dto.CourseDTO;
import com.epam.facultative.model.dto.TeacherDTO;
import com.epam.facultative.model.dto.UserDTO;
import com.epam.facultative.model.exception.ServiceException;
import com.epam.facultative.model.exception.ValidateException;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
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

    UserDTO addAvatar(String userId, InputStream avatar) throws ServiceException, ValidateException;
}