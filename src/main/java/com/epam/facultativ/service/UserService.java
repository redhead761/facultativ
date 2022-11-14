package com.epam.facultativ.service;

import com.epam.facultativ.daos.UserDao;
import com.epam.facultativ.entity.User;

public class UserService {
    public User getUserByLogin(String login) {
        UserDao userDao = new UserDao();
        User user = userDao.findByName(login);
        return user;

    }
}
