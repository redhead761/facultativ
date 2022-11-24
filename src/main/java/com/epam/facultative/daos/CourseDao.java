package com.epam.facultative.daos;

import com.epam.facultative.entity.Course;

import java.util.List;

public interface CourseDao extends Dao<Course> {
    List<Course> getByUser(int userId);

    List<Course> getByCategory(int categoryId);

    void addUserToCourse(int courseId, int userId);

    void updateUsersCourse(int courseId, int userId, int grade);

    int getGrade(int courseId,int userId);
}
