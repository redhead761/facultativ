package com.epam.facultative.daos;

import com.epam.facultative.entity.User;
import com.epam.facultative.exception.DAOException;

import java.util.List;

public interface UserDao extends Dao<User> {
    List<User> getByRole(int roleId) throws DAOException;

    List<User> getByRolePagination(int roleId, int offset, int numberOfRows) throws DAOException;

    List<User> getUsersByCourse(int courseId) throws DAOException;

    void blockUnblockStudent(int userId, boolean block) throws DAOException;

    int getNoOfRecords();
}
