package com.epam.facultative.model.dao;

import com.epam.facultative.model.entities.Course;
import com.epam.facultative.model.exception.DAOException;

import java.util.List;
import java.util.Map;

public interface CourseDao extends Dao<Course> {
    Map.Entry<Integer, List<Course>> getByJournal(String param) throws DAOException;

    void insertJournal(int courseId, int studentId) throws DAOException;

    void updateJournal(int courseId, int studentId, int grade) throws DAOException;
}
