package com.epam.facultative.data_layer.daos;

import com.epam.facultative.data_layer.entities.Course;
import com.epam.facultative.data_layer.entities.Status;
import com.epam.facultative.exception.DAOException;

import java.util.List;
import java.util.Map;

public interface CourseDao extends Dao<Course> {
    Map.Entry<Integer, List<Course>> getByJournal(String param) throws DAOException;

    void insertJournal(int courseId, int studentId) throws DAOException;

    void updateJournal(int courseId, int studentId, int grade) throws DAOException;
}
