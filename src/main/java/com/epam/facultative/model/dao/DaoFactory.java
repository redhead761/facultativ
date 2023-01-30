package com.epam.facultative.model.dao;

import com.epam.facultative.model.dao.impl_sql.MySqlDaoFactory;

import javax.sql.DataSource;

/**
 * Abstract factory that provides concrete factories to obtain DAOs (Abstract factory pattern)
 *
 * @author Oleksandr Pacnhenko
 * @version 1.0
 */
public abstract class DaoFactory {
    private static DaoFactory instance;

    /**
     * Constructor should be used only in subclasses
     */
    protected DaoFactory() {
    }

    /**
     * Obtains single instance of the class. Synchronized to avoid multithreading collisions
     *
     * @param dataSource - datasource to connect to database
     * @return concrete DAO factory
     */
    public static synchronized DaoFactory getInstance(DataSource dataSource) {
        if (instance == null) {
            instance = new MySqlDaoFactory(dataSource);
        }
        return instance;
    }

    /**
     * Obtains concrete instance of DAO class
     *
     * @return CategoryDao for required database type
     */
    public abstract CategoryDao getCategoryDao();

    /**
     * Obtains concrete instance of DAO class
     *
     * @return CourseDao for required database type
     */
    public abstract CourseDao getCourseDao();

    /**
     * Obtains concrete instance of DAO class
     *
     * @return UserDao for required database type
     */
    public abstract UserDao getUserDao();

    /**
     * Obtains concrete instance of DAO class
     *
     * @return TeacherDao for required database type
     */
    public abstract TeacherDao getTeacherDao();

    /**
     * Obtains concrete instance of DAO class
     *
     * @return StudentDao for required database type
     */
    public abstract StudentDao getStudentDao();

}
