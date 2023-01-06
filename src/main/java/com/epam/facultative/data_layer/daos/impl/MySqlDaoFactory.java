package com.epam.facultative.data_layer.daos.impl;

import com.epam.facultative.data_layer.daos.*;

import javax.sql.DataSource;

public class MySqlDaoFactory extends DaoFactory {
    private final DataSource dataSource;
    private CategoryDao categoryDao;
    private CourseDao courseDao;
    private UserDao userDao;
    private TeacherDao teacherDao;
    private StudentDao studentDao;

    public MySqlDaoFactory(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public CategoryDao getCategoryDao() {
        if (this.categoryDao == null) {
            this.categoryDao = new MySqlCategoryDao(dataSource);
        }
        return categoryDao;
    }

    @Override
    public CourseDao getCourseDao() {
        if (this.courseDao == null) {
            this.courseDao = new MySqlCourseDao(dataSource);
        }
        return courseDao;
    }

    @Override
    public UserDao getUserDao() {
        if (this.userDao == null) {
            this.userDao = new MySqlUserDao(dataSource);
        }
        return userDao;
    }

    @Override
    public TeacherDao getTeacherDao() {
        if (this.teacherDao == null) {
            this.teacherDao = new MySqlTeacherDao(dataSource);
        }
        return teacherDao;
    }

    @Override
    public StudentDao getStudentDao() {
        if (this.studentDao == null) {
            this.studentDao = new MySqlStudentDao(dataSource);
        }
        return studentDao;
    }
}
