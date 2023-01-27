package com.epam.facultative.model.service;

import com.epam.facultative.controller.app_context.AppContext;
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

    Map.Entry<Integer, List<CourseDTO>> getCourses(String param) throws ServiceException;

    Map.Entry<Integer, List<CategoryDTO>> getCategories(String param) throws ServiceException;

    Map.Entry<Integer, List<TeacherDTO>> getTeachers(String param) throws ServiceException;

    ByteArrayOutputStream downloadAllCoursesInPdf(String locale, AppContext appContext) throws ServiceException;

    void recoveryPassword(String email, AppContext appContext) throws ServiceException, ValidateException;

    void changePassword(String oldPassword, String newPassword, int userId) throws ServiceException, ValidateException;

    UserDTO addAvatar(int userId, InputStream avatar) throws ServiceException, ValidateException;
}