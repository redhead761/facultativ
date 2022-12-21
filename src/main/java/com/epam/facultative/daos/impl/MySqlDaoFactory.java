package com.epam.facultative.daos.impl;

import com.epam.facultative.daos.*;

public class MySqlDaoFactory extends DaoFactory {

    private CategoryDao categoryDao;
    private CourseDao courseDao;
    private UserDao userDao;
    private TeacherDao teacherDao;
    private StudentDao studentDao;

    @Override
    public CategoryDao getCategoryDao() {
        if (this.categoryDao == null) {
            this.categoryDao = new MySqlCategoryDao();
        }
        return categoryDao;
    }

    @Override
    public CourseDao getCourseDao() {
        if (this.courseDao == null) {
            this.courseDao = new MySqlCourseDao();
        }
        return courseDao;
    }

    @Override
    public UserDao getUserDao() {
        if (this.userDao == null) {
            this.userDao = new MySqlUserDao();
        }
        return userDao;
    }

    @Override
    public TeacherDao getTeacherDao() {
        if(this.teacherDao == null){
            this.teacherDao = new MySqlTeacherDao();
        }
        return teacherDao;
    }

    @Override
    public StudentDao getStudentDao() {
        if(this.studentDao == null){
            this.studentDao = new MySqlStudentDao();
        }
        return studentDao;
    }
}
