package com.epam.facultative.model.service;

import com.epam.facultative.model.dto.StudentDTO;
import com.epam.facultative.model.dto.TeacherDTO;
import com.epam.facultative.model.exception.ServiceException;
import com.epam.facultative.model.exception.ValidateException;

import java.util.List;
import java.util.Map;

/**
 * TeacherService interface.
 * Implement all methods in concrete EventService
 *
 * @author Oleksandr Panchenko
 * @version 1.0
 */
public interface TeacherService {
    /**
     * Calls DAO to add grade
     *
     * @param courseId,userId - used to find
     * @param grade           - user to add
     * @throws ServiceException - may wrap DAOException or be thrown by another mistakes
     */
    void grading(int courseId, int userId, int grade) throws ServiceException;

    /**
     * Calls DAO to get relevant entities by param
     *
     * @param param - param to find
     * @return {@code Map.Entry<Integer, List < StudentDTO>>} -  relevant to Student entity
     * @throws ServiceException - may wrap DAOException or be thrown by another mistakes
     */

    Map.Entry<Integer, List<StudentDTO>> getStudentsByCourse(String param) throws ServiceException;

    /**
     * Calls DAO to update relevant entity
     *
     * @param teacherDTO - DTO to be updated as entity to database
     * @throws ServiceException  - may wrap DAOException or be thrown by another mistakes
     * @throws ValidateException - occurs in case of non-validation of data
     */
    TeacherDTO updateTeacher(TeacherDTO teacherDTO) throws ValidateException, ServiceException;
}