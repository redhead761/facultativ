package com.epam.facultative.model.dao.impl_sql;

import com.epam.facultative.model.dao.*;

import javax.sql.DataSource;

/**
 * My SQL factory that provides My SQL DAOs (Factory pattern)
 *
 * @author Oleksandr Panchenko
 * @version 1.0
 */
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

    /**
     * Obtains single instance of the CategoryDao (Singleton pattern).
     *
     * @return MySqlCategoryDao
     */
    @Override
    public CategoryDao getCategoryDao() {
        if (this.categoryDao == null) {
            this.categoryDao = new MySqlCategoryDao(dataSource);
        }
        return categoryDao;
    }

    /**
     * Obtains single instance of the CourseDao (Singleton pattern).
     *
     * @return MySqlCourseDao
     */
    @Override
    public CourseDao getCourseDao() {
        if (this.courseDao == null) {
            this.courseDao = new MySqlCourseDao(dataSource);
        }
        return courseDao;
    }

    /**
     * Obtains single instance of the UserDao (Singleton pattern).
     *
     * @return MySqlUserDao
     */
    @Override
    public UserDao getUserDao() {
        if (this.userDao == null) {
            this.userDao = new MySqlUserDao(dataSource);
        }
        return userDao;
    }

    /**
     * Obtains single instance of the TeacherDao (Singleton pattern).
     *
     * @return MySqlTeacherDao
     */
    @Override
    public TeacherDao getTeacherDao() {
        if (this.teacherDao == null) {
            this.teacherDao = new MySqlTeacherDao(dataSource);
        }
        return teacherDao;
    }

    /**
     * Obtains single instance of the StudentDao (Singleton pattern).
     *
     * @return MySqlStudentDao
     */
    @Override
    public StudentDao getStudentDao() {
        if (this.studentDao == null) {
            this.studentDao = new MySqlStudentDao(dataSource);
        }
        return studentDao;
    }
}
