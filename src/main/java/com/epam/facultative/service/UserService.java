package com.epam.facultative.service;

import com.epam.facultative.daos.impl.UserDao;
import com.epam.facultative.entity.User;

public class UserService {
    private final UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public User getUserByLogin(String login) {
        return userDao.findByName(login);

    }
}
