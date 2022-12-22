package com.epam.facultative.repositories.implementation;

import com.epam.facultative.daos.CategoryDao;
import com.epam.facultative.daos.CourseDao;
import com.epam.facultative.daos.UserDao;
import com.epam.facultative.repositories.AdminRepository;

public class AdminRepositoryImpl implements AdminRepository {
    public AdminRepositoryImpl(CourseDao courseDao, CategoryDao categoryDao, UserDao userDao) {
    }
}
