package com.epam.facultative.daos.impl;

import com.epam.facultative.daos.CategoryDao;
import com.epam.facultative.daos.CourseDao;
import com.epam.facultative.daos.DaoFactory;
import com.epam.facultative.daos.UserDao;

public class MySqlDaoFactory extends DaoFactory {

    private CategoryDao categoryDao;
    private CourseDao courseDao;
    private UserDao userDao;

    @Override
    public CategoryDao getCategoryDao() {
        if (this.categoryDao == null){
            this.categoryDao = new MySqlCategoryDao();
        }
        return categoryDao;
    }

    @Override
    public CourseDao getCourseDao() {
        if (this.courseDao == null){
            this.courseDao = new MySqlCourseDao();
        }
        return courseDao;
    }

    @Override
    public UserDao getUserDao() {
        if (this.userDao == null){
            this.userDao = new MySqlUserDao();
        }
        return userDao;
    }
}
