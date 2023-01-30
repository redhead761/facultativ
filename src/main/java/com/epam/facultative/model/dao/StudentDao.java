package com.epam.facultative.model.dao;

import com.epam.facultative.model.entities.Student;
import com.epam.facultative.model.exception.DAOException;

import java.util.List;
import java.util.Map;

/**
 * Student DAO interface.
 * Implement methods due to database type
 *
 * @author Oleksandr Panchenko
 * @version 1.0
 */
public interface StudentDao extends Dao<Student> {
    /**
     * Obtains list of all students from database by parameter on course
     *
     * @param param -  parameter value
     * @return students list
     * @throws DAOException is wrapper for SQLException
     */
    Map.Entry<Integer, List<Student>> getStudentsByCourse(String param) throws DAOException;

    /**
     * Updates the status of a blocked student in the database
     *
     * @param studentId,block - must be not null
     * @throws DAOException is wrapper for SQLException
     */
    void updateBlock(int studentId, boolean block) throws DAOException;

    /**
     * Gets the student's grade for the course
     *
     * @param studentId,courseId - must be not null
     * @throws DAOException is wrapper for SQLException
     */
    int getGrade(int courseId, int studentId) throws DAOException;

}
