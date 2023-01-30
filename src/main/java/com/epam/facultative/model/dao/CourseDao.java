package com.epam.facultative.model.dao;

import com.epam.facultative.model.entities.Course;
import com.epam.facultative.model.exception.DAOException;

import java.util.List;
import java.util.Map;

/**
 * Course DAO interface.
 * Implement methods due to database type
 *
 * @author Oleksandr Panchenko
 * @version 1.0
 */
public interface CourseDao extends Dao<Course> {
    /**
     * Obtains list of all courses from database by parameter in journal
     *
     * @param param -  parameter value
     * @return courses list
     * @throws DAOException is wrapper for SQLException
     */
    Map.Entry<Integer, List<Course>> getByJournal(String param) throws DAOException;

    /**
     * Inserts course and student to journal
     *
     * @param courseId,studentId - must be not null
     * @throws DAOException is wrapper for SQLException
     */
    void insertJournal(int courseId, int studentId) throws DAOException;

    /**
     * Updates the student grade for the course
     *
     * @param courseId,studentId,grade - must be not null
     * @throws DAOException is wrapper for SQLException
     */
    void updateJournal(int courseId, int studentId, int grade) throws DAOException;
}
