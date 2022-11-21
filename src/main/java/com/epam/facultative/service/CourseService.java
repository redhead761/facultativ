package com.epam.facultative.service;

import com.epam.facultative.entity.Category;
import com.epam.facultative.entity.Course;
import com.epam.facultative.entity.User;

import java.util.Comparator;
import java.util.List;

import java.util.stream.Collectors;

public class CourseService {
    private CourseDao courseDao;
    private UserDao userDao;

    public CourseService(CourseDao courseDao, UserDao userDao) {
        this.courseDao = courseDao;
        this.userDao = userDao;
    }

    public List<Course> sortByAZ() {
        return courseDao.getAll()
                .stream()
                .sorted(Comparator.comparing(Course::getTitle))
                .collect(Collectors.toList());
    }

    public List<Course> sortByZA() {
        return courseDao.getAll()
                .stream()
                .sorted((o1, o2) -> o2.getTitle().compareTo(o1.getTitle()))
                .collect(Collectors.toList());
    }

    public List<Course> sortByDuration() {
        return courseDao.getAll()
                .stream()
                .sorted(Comparator.comparingInt(Course::getDuration))
                .collect(Collectors.toList());
    }

    public List<Course> sortByNumber() {
        return courseDao.getAll()
                .stream()
                .sorted(Comparator.comparingInt(Course::getAmountStudents))
                .collect(Collectors.toList());
    }

    public int countStudentByCourse(Course course) {
        int result = 0;
        List<User> users = userDao.usersByCourse(course.getId());
        for (User user : users) {
            if (user.getRole().getTitle().equalsIgnoreCase("student"))
                result++;
        }
        return result;
    }

    public List<Course> coursesByCategory(String category) {
        Category categoryEntity = new CategoryDao().findByName(category);
        return new CourseDao().findByCategory(categoryEntity);
    }

    public List<Course> coursesByTeacher(User user) {
        List<Course> courses;
        courses = new CourseDao().findByUser(user.getId());
        return courses;
    }

    public void addUserToCourse(String userId, String courseId) {
        User user = userDao.getById(Integer.parseInt(userId));
        Course course = courseDao.getById(Integer.parseInt(courseId));
        new CourseDao().insertUserToCourse(user, course);
    }

    public List<Course> findCoursesForStudent(String userId) {
        return courseDao.findByUser(Integer.parseInt(userId));
    }

}
