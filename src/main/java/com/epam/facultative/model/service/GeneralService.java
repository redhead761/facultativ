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

/**
 * GeneralService interface.
 * Implement all methods in concrete EventService
 *
 * @author Oleksandr Panchenko
 * @version 1.0
 */
public interface GeneralService {
    /**
     * Calls DAO to get relevant entities by login
     *
     * @param login    - param to find
     * @param password -  to check if matches with user password
     * @return UserDTO -  relevant to User entity
     * @throws ServiceException  - may wrap DAOException or be thrown by another mistakes
     * @throws ValidateException - occurs in case of non-validation of data
     */
    UserDTO authorization(String login, String password) throws ServiceException, ValidateException;

    /**
     * Calls DAO to get relevant entities by param
     *
     * @param param - param to find
     * @return Map.Entry<Integer, List < CourseDTO>> -  relevant to Course entity
     * @throws ServiceException - may wrap DAOException or be thrown by another mistakes
     */
    Map.Entry<Integer, List<CourseDTO>> getCourses(String param) throws ServiceException;

    /**
     * Calls DAO to get relevant entities by param
     *
     * @param param - param to find
     * @return Map.Entry<Integer, List < CategoryDTO>> -  relevant to Category entity
     * @throws ServiceException - may wrap DAOException or be thrown by another mistakes
     */
    Map.Entry<Integer, List<CategoryDTO>> getCategories(String param) throws ServiceException;

    /**
     * Calls DAO to get relevant entities by param
     *
     * @param param - param to find
     * @return Map.Entry<Integer, List < TeacherDTO>> -  relevant to Teacher entity
     * @throws ServiceException - may wrap DAOException or be thrown by another mistakes
     */
    Map.Entry<Integer, List<TeacherDTO>> getTeachers(String param) throws ServiceException;

    /**
     * Calls PdfCreator to create pdf
     *
     * @param locale     - used for document localization
     * @param appContext - used to call PdfCreator
     * @return ByteArrayOutputStream - for download
     * @throws ServiceException - may wrap DAOException or be thrown by another mistakes
     */
    ByteArrayOutputStream downloadAllCoursesInPdf(String locale, AppContext appContext) throws ServiceException;

    /**
     * Calls EmailSender to send new password and calls DAO to update User with new password
     *
     * @param email      - used for send email
     * @param appContext - used to call EmailSender
     * @throws ServiceException - may wrap DAOException or be thrown by another mistakes
     */
    void recoveryPassword(String email, AppContext appContext) throws ServiceException, ValidateException;

    /**
     * Calls DAO to update User with new password
     *
     * @param userId      - id to find user by
     * @param oldPassword - old password
     * @param newPassword - new password
     * @throws ServiceException  - may wrap DAOException or be thrown by another mistakes
     * @throws ValidateException - occurs in case of non-validation of data
     */
    void changePassword(String oldPassword, String newPassword, int userId) throws ServiceException, ValidateException;

    /**
     * Calls DAO to add avatar User
     *
     * @param userId - id to find user by
     * @param avatar - image for add
     * @return UserDTO - user with new avatar
     * @throws ServiceException  - may wrap DAOException or be thrown by another mistakes
     * @throws ValidateException - occurs in case of non-validation of data
     */
    UserDTO addAvatar(int userId, InputStream avatar) throws ServiceException, ValidateException;
}