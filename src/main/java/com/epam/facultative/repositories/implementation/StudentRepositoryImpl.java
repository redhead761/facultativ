package com.epam.facultative.repositories.implementation;

import com.epam.facultative.daos.CourseDao;
import com.epam.facultative.daos.UserDao;
import com.epam.facultative.repositories.StudentRepository;

public class StudentRepositoryImpl implements StudentRepository {
    public StudentRepositoryImpl(CourseDao courseDao, UserDao userDao) {
    }
}
