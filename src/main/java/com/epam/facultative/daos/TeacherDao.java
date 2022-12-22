package com.epam.facultative.daos;

import com.epam.facultative.entities.Teacher;
import com.epam.facultative.exception.DAOException;

public interface TeacherDao extends Dao<Teacher> {
    void updateJournal(int courseId, int userId, int grade) throws DAOException;
}
