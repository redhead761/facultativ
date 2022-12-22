package com.epam.facultative.repositories.implementation;

import com.epam.facultative.daos.CourseDao;
import com.epam.facultative.daos.UserDao;
import com.epam.facultative.repositories.TeacherRepository;

public class TeacherRepositoryImpl implements TeacherRepository {
    public TeacherRepositoryImpl(CourseDao courseDao, UserDao userDao) {
    }
}
