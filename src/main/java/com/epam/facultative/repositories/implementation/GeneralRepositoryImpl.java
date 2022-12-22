package com.epam.facultative.repositories.implementation;

import com.epam.facultative.daos.CategoryDao;
import com.epam.facultative.daos.CourseDao;
import com.epam.facultative.daos.UserDao;
import com.epam.facultative.repositories.GeneralRepository;

public class GeneralRepositoryImpl implements GeneralRepository {
    public GeneralRepositoryImpl(CourseDao courseDao, UserDao userDao, CategoryDao categoryDao) {
    }
}
