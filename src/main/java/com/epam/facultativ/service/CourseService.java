package com.epam.facultativ.service;

import com.epam.facultativ.daos.CourseDao;
import com.epam.facultativ.daos.UserDao;
import com.epam.facultativ.entity.Category;
import com.epam.facultativ.entity.Course;
import com.epam.facultativ.entity.User;

import java.util.Comparator;
import java.util.List;

import java.util.stream.Collectors;

public class CourseService {

    public List<Course> sortByAZ() {
        return new CourseDao().findAll()
                .stream()
                .sorted(Comparator.comparing(Course::getTitle))
                .collect(Collectors.toList());
    }

    public List<Course> sortByZA() {
        return new CourseDao().findAll()
                .stream()
                .sorted((o1, o2) -> o2.getTitle().compareTo(o1.getTitle()))
                .collect(Collectors.toList());
    }

    public List<Course> sortByDuration() {
        return new CourseDao().findAll()
                .stream()
                .sorted(Comparator.comparingInt(Course::getDuration))
                .collect(Collectors.toList());
    }

    public int countStudentByCourse(Course course) {
        int result = 0;
        List<User> users = new UserDao().usersByCourse(course.getId());
        for (User user : users) {
            if (user.getRole().getTitle().equalsIgnoreCase("student"))
                result++;
        }
        return result;
    }

    public List<Course> coursesByCategory(Category category) {
        return new CourseDao().findByCategory(category);
    }

    public List<Course> coursesByTeacher(User user) {
        List<Course> courses;
        courses = new CourseDao().findByUser(user.getId());
        return courses;
    }

    public void addUserToCourse(User user, Course course) {
        new CourseDao().insertUserToCourse(user, course);
    }

}
