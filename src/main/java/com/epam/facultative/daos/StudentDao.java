package com.epam.facultative.daos;

import com.epam.facultative.entities.Student;
import com.epam.facultative.exception.DAOException;

import java.util.List;

public interface StudentDao extends Dao<Student> {
    List<Student> getStudentsByCourse(int courseId, int offset, int numberOfRows) throws DAOException;

}
