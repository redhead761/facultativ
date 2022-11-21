package com.epam.facultative.daos.impl;

import com.epam.facultative.daos.CourseDao;
import com.epam.facultative.entity.Course;

import java.util.List;

public class MySqlCourseDao implements CourseDao {

    @Override
    public List<Course> findAll() {
        return null;
    }

    @Override
    public Course findById(int id) {
        return null;
    }

    @Override
    public Course findByName(String name) {
        return null;
    }

    @Override
    public void add(Course course) {

    }

    @Override
    public void update(Course course) {

    }

    @Override
    public void delete(Course course) {

    }

    @Override
    public List<Course> getByUser(int userId) {
        return null;
    }

    @Override
    public List<Course> getByCategory(int categoryId) {
        return null;
    }

    @Override
    public void addUserToCourse(int courseId, int userId) {

    }

    @Override
    public void updateUsersCourse(int courseId, int userId, int grade) {

    }
}
