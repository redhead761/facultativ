package com.epam.facultative.daos;

import com.epam.facultative.daos.impl.MySqlDaoFactory;

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
