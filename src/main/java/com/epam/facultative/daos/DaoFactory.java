package com.epam.facultative.daos;

import com.epam.facultative.daos.impl.MySqlDaoFactory;

public abstract class DaoFactory {
    private static DaoFactory instance;

    public static synchronized DaoFactory getInstance() {
        if (instance == null) {
            instance = new MySqlDaoFactory();
        }
        return instance;
    }

    public abstract CategoryDao getCategoryDao();

    public abstract CourseDao getCourseDao();

    public abstract UserDao getUserDao();

    public abstract TeacherDao getTeacherDao();

    public abstract StudentDao getStudentDao();

}
