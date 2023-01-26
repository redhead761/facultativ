package com.epam.facultative.model.dao;

import com.epam.facultative.model.dao.impl_sql.MySqlDaoFactory;

import javax.sql.DataSource;

public abstract class DaoFactory {
    private static DaoFactory instance;

    public static synchronized DaoFactory getInstance(DataSource dataSource) {
        if (instance == null) {
            instance = new MySqlDaoFactory(dataSource);
        }
        return instance;
    }

    public abstract CategoryDao getCategoryDao();

    public abstract CourseDao getCourseDao();

    public abstract UserDao getUserDao();

    public abstract TeacherDao getTeacherDao();

    public abstract StudentDao getStudentDao();

}
