package com.epam.facultative.daos.impl;

import com.epam.facultative.daos.UserDao;
import com.epam.facultative.entity.User;

import java.util.List;

public class MySqlUserDao implements UserDao {
    @Override
    public List<User> findAll() {
        return null;
    }

    @Override
    public User findById(int id) {
        return null;
    }

    @Override
    public User findByName(String name) {
        return null;
    }

    @Override
    public void add(User user) {

    }

    @Override
    public void update(User user) {

    }

    @Override
    public void delete(User user) {

    }

    @Override
    public User getByRole(int roleId) {
        return null;
    }

    @Override
    public List<User> getUsersByCourse(int courseId) {
        return null;
    }
}
