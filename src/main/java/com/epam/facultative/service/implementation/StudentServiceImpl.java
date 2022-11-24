package com.epam.facultative.service.implementation;

import com.epam.facultative.daos.*;
import com.epam.facultative.dto.*;
import com.epam.facultative.entity.*;
import com.epam.facultative.service.StudentService;

import java.util.*;

public class StudentServiceImpl implements StudentService {
    private final CourseDao courseDao;
    private final UserDao userDao;
    private final Converter converter;

    public StudentServiceImpl(CourseDao courseDao, UserDao userDao) {
        this.courseDao = courseDao;
        this.userDao = userDao;
        this.converter = new Converter();
    }

    @Override
    public List<CourseDTO> getCoursesByStudent(int studentId) {
        return prepareCourses(courseDao.getByUser(studentId));
    }

    @Override
    public List<CourseDTO> getCoursesComingSoon(int studentId) {
        List<CourseDTO> result = new ArrayList<>();
        List<CourseDTO> coursesDTO = getCoursesByStudent(studentId);
        for (CourseDTO course : coursesDTO) {
            if (course.getStatus().equals(Status.COMING_SOON)) {
                result.add(course);
            }
        }
        return result;
    }

    @Override
    public List<CourseDTO> getCoursesInProgress(int studentId) {
        List<CourseDTO> result = new ArrayList<>();
        List<CourseDTO> coursesDTO = getCoursesByStudent(studentId);
        for (CourseDTO course : coursesDTO) {
            if (course.getStatus().equals(Status.IN_PROCESS)) {
                result.add(course);
            }
        }
        return result;
    }

    @Override
    public List<CourseDTO> getCoursesCompleted(int studentId) {
        List<CourseDTO> result = new ArrayList<>();
        List<CourseDTO> coursesDTO = getCoursesByStudent(studentId);
        for (CourseDTO course : coursesDTO) {
            if (course.getStatus().equals(Status.COMING_SOON)) {
                course.setGrade(courseDao.getGrade(course.getId(), studentId));
                result.add(course);
            }
        }
        return result;
    }

    @Override
    public void enroll(int courseId, int userId) {
        courseDao.addUserToCourse(courseId, userId);
    }

    @Override
    public void addStudent(User user) {
        userDao.add(user);
    }

    private List<CourseDTO> prepareCourses(List<Course> courses) {
        List<CourseDTO> coursesDTO = new ArrayList<>();
        for (Course course : courses) {
            List<User> users = userDao.getUsersByCourse(course.getId());
            for (User user : users) {
                if (user.getRole().equals(Role.TEACHER)) {
                    UserDTO teacher = converter.userToDTO(user);
                    coursesDTO.add(converter.courseToDTO(course, teacher));
                }
            }
        }
        return coursesDTO;
    }
}
