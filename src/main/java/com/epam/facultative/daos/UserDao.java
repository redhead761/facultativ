package com.epam.facultative.daos;

import com.epam.facultative.entity.User;

import java.util.List;

public interface UserDao extends Dao<User> {
    User getByRole(int roleId);

    List<User> getUsersByCourse(int courseId);
}
