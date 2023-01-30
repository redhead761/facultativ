package com.epam.facultative.model.service;

import com.epam.facultative.controller.app_context.AppContext;
import com.epam.facultative.model.dto.CategoryDTO;
import com.epam.facultative.model.dto.CourseDTO;
import com.epam.facultative.model.dto.StudentDTO;
import com.epam.facultative.model.dto.TeacherDTO;
import com.epam.facultative.model.exception.ServiceException;
import com.epam.facultative.model.exception.ValidateException;

import java.util.List;
import java.util.Map;

/**
 * AdminService interface.
 * Implement all methods in concrete EventService
 *
 * @author Oleksandr Panchenko
 * @version 1.0
 */
public interface AdminService {

    /**
     * Calls EmailSender to launch newsletter
     *
     * @param courseDTO  - DTO to be sent
     * @param appContext - used to call EmailSender
     * @throws ServiceException - may wrap DAOException or be thrown by another mistakes
     */
    void courseLaunchNewsLetter(CourseDTO courseDTO, AppContext appContext) throws ServiceException;

    /**
     * Calls DAO to add relevant entity
     *
     * @param courseDTO - DTO to be added as entity to database
     * @throws ServiceException  - may wrap DAOException or be thrown by another mistakes
     * @throws ValidateException - occurs in case of non-validation of data
     */
    void addCourse(CourseDTO courseDTO) throws ServiceException, ValidateException;

    /**
     * Calls DAO to update relevant entity
     *
     * @param courseDTO - DTO to be updated as entity to database
     * @throws ServiceException  - may wrap DAOException or be thrown by another mistakes
     * @throws ValidateException - occurs in case of non-validation of data
     */
    void updateCourse(CourseDTO courseDTO) throws ServiceException, ValidateException;

    /**
     * Calls DAO to delete relevant entity
     *
     * @param courseId - DTO with this id to be deleted as entity to database
     * @throws ServiceException  - may wrap DAOException or be thrown by another mistakes
     * @throws ValidateException - occurs in case of non-validation of data
     */
    void deleteCourse(int courseId) throws ServiceException, ValidateException;

    /**
     * Calls DAO to add relevant entity
     *
     * @param categoryDTO - DTO to be added as entity to database
     * @throws ServiceException  - may wrap DAOException or be thrown by another mistakes
     * @throws ValidateException - occurs in case of non-validation of data
     */
    void addCategory(CategoryDTO categoryDTO) throws ServiceException, ValidateException;

    /**
     * Calls DAO to update relevant entity
     *
     * @param categoryDTO - DTO to be updated as entity to database
     * @throws ServiceException  - may wrap DAOException or be thrown by another mistakes
     * @throws ValidateException - occurs in case of non-validation of data
     */
    void updateCategory(CategoryDTO categoryDTO) throws ServiceException, ValidateException;

    /**
     * Calls DAO to delete relevant entity
     *
     * @param categoryId - DTO with this id to be deleted as entity to database
     * @throws ServiceException  - may wrap DAOException or be thrown by another mistakes
     * @throws ValidateException - occurs in case of non-validation of data
     */
    void deleteCategory(int categoryId) throws ServiceException, ValidateException;

    /**
     * Calls DAO to add entities
     *
     * @param courseId,userId - DTOs with this id to be added as entity to database
     * @throws ServiceException  - may wrap DAOException or be thrown by another mistakes
     * @throws ValidateException - occurs in case of non-validation of data
     */
    void assigned(int courseId, int userId) throws ServiceException, ValidateException;

    /**
     * Calls DAO to update relevant entity
     *
     * @param userId - DTO with this id to be updated as entity to database
     * @throws ServiceException - may wrap DAOException or be thrown by another mistakes
     */
    void blockStudent(int userId) throws ServiceException;

    /**
     * Calls DAO to update relevant entity
     *
     * @param userId - DTO with this id to be updated as entity to database
     * @throws ServiceException - may wrap DAOException or be thrown by another mistakes
     */
    void unblockStudent(int userId) throws ServiceException;

    /**
     * Calls DAO to add relevant entity
     *
     * @param teacherDTO - DTO to be added as entity to database
     * @throws ServiceException  - may wrap DAOException or be thrown by another mistakes
     * @throws ValidateException - occurs in case of non-validation of data
     */
    void addTeacher(TeacherDTO teacherDTO) throws ServiceException, ValidateException;

    /**
     * Calls DAO to get relevant entities by param
     *
     * @param param - param to find
     * @return Map.Entry<Integer, List < StudentDTO>> -  relevant to Student entity
     * @throws ServiceException - may wrap DAOException or be thrown by another mistakes
     */
    Map.Entry<Integer, List<StudentDTO>> getStudents(String param) throws ServiceException;

    /**
     * Calls DAO to get relevant entity by id
     *
     * @param id - category id to find
     * @return CategoryDTO relevant to Category entity
     * @throws ServiceException  - may wrap DAOException or be thrown by another mistakes
     * @throws ValidateException - occurs in case of non-validation of data
     */
    CategoryDTO getCategory(int id) throws ServiceException, ValidateException;

    /**
     * Calls DAO to get relevant entity by id
     *
     * @param id - course id to find
     * @return CourseDTO relevant to Course entity
     * @throws ServiceException  - may wrap DAOException or be thrown by another mistakes
     * @throws ValidateException - occurs in case of non-validation of data
     */
    CourseDTO getCourse(int id) throws ServiceException, ValidateException;

    /**
     * Calls DAO to get relevant entity by id
     *
     * @param id - teacher id to find
     * @return TeacherDTO - relevant to Teacher entity
     * @throws ServiceException  - may wrap DAOException or be thrown by another mistakes
     * @throws ValidateException - occurs in case of non-validation of data
     */
    TeacherDTO getTeacher(int id) throws ServiceException, ValidateException;

    /**
     * Calls DAO to delete relevant entity
     *
     * @param id - DTO with this id to be deleted as entity to database
     * @throws ServiceException  - may wrap DAOException or be thrown by another mistakes
     * @throws ValidateException - occurs in case of non-validation of data
     */
    void deleteTeacher(int id) throws ValidateException, ServiceException;
}