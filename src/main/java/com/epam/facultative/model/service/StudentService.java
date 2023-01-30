package com.epam.facultative.model.service;

import com.epam.facultative.controller.app_context.AppContext;
import com.epam.facultative.model.dto.CourseDTO;
import com.epam.facultative.model.dto.StudentDTO;
import com.epam.facultative.model.exception.ServiceException;
import com.epam.facultative.model.exception.ValidateException;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Map;

/**
 * StudentService interface.
 * Implement all methods in concrete EventService
 *
 * @author Oleksandr Panchenko
 * @version 1.0
 */
public interface StudentService {
    /**
     * Calls DAO to get relevant entities by param
     *
     * @param param - param to find
     * @return Map.Entry<Integer, List < CourseDTO>> -  relevant to Course entity
     * @throws ServiceException - may wrap DAOException or be thrown by another mistakes
     */
    Map.Entry<Integer, List<CourseDTO>> getCoursesByJournal(String param) throws ServiceException;

    /**
     * Calls DAO to add entities
     *
     * @param courseId,userId - DTOs with this id to be added as entity to database in table 'journal'
     * @throws ServiceException - may wrap DAOException or be thrown by another mistakes
     */
    void enroll(int courseId, int userId) throws ServiceException;

    /**
     * Calls DAO to add relevant entity
     *
     * @param student - DTO to be added as entity to database
     * @throws ServiceException  - may wrap DAOException or be thrown by another mistakes
     * @throws ValidateException - occurs in case of non-validation of data
     */
    void addStudent(StudentDTO student) throws ServiceException, ValidateException;

    /**
     * Calls DAO to get relevant grade by id
     *
     * @param courseId,userId -  id to find
     * @return grade - value
     * @throws ServiceException - may wrap DAOException or be thrown by another mistakes
     */
    int getGrade(int courseId, int userId) throws ServiceException;

    /**
     * Calls PdfCreator to create pdf
     *
     * @param studentDTO,courseId,grade - used to create certificate
     * @param appContext                - used to call PdfCreator
     * @return ByteArrayOutputStream - for download
     * @throws ServiceException - may wrap DAOException or be thrown by another mistakes
     */
    ByteArrayOutputStream downloadCertificate(StudentDTO studentDTO, int courseId, int grade, AppContext appContext) throws ServiceException, ValidateException;

    /**
     * Calls EmailSender to launch newsletter and PdfCreator to create pdf
     *
     * @param studentDTO,courseId,grade - used to create certificate
     * @param appContext                - used to call EmailSender
     * @throws ServiceException - may wrap DAOException or be thrown by another mistakes
     */
    void sendCertificate(StudentDTO studentDTO, int courseId, int grade, AppContext appContext) throws ValidateException, ServiceException;

    /**
     * Calls DAO to update relevant entity
     *
     * @param studentDTO - DTO to be updated as entity to database
     * @throws ServiceException  - may wrap DAOException or be thrown by another mistakes
     * @throws ValidateException - occurs in case of non-validation of data
     */
    StudentDTO updateStudent(StudentDTO studentDTO) throws ValidateException, ServiceException;
}