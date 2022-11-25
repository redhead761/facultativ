package com.epam.facultative.service.implementation;

import com.epam.facultative.daos.*;
import com.epam.facultative.dto.*;
import com.epam.facultative.entity.*;
import com.epam.facultative.exception.DAOException;
import com.epam.facultative.exception.ServiceException;
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
    public List<CourseDTO> getCoursesByStudent(int studentId) throws ServiceException {
        try {
            return prepareCourses(courseDao.getByUser(studentId));
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<CourseDTO> getCoursesComingSoon(int studentId) throws ServiceException {
        List<CourseDTO> result = new ArrayList<>();
        List<CourseDTO> coursesDTO;
        try {
            coursesDTO = getCoursesByStudent(studentId);
        } catch (ServiceException e) {
            throw new ServiceException(e);
        }
        for (CourseDTO course : coursesDTO) {
            if (course.getStatus().equals(Status.COMING_SOON)) {
                result.add(course);
            }
        }
        return result;
    }

    @Override
    public List<CourseDTO> getCoursesInProgress(int studentId) throws ServiceException {
        List<CourseDTO> result = new ArrayList<>();
        List<CourseDTO> coursesDTO;
        try {
            coursesDTO = getCoursesByStudent(studentId);
        } catch (ServiceException e) {
            throw new ServiceException(e);
        }
        for (CourseDTO course : coursesDTO) {
            if (course.getStatus().equals(Status.IN_PROCESS)) {
                result.add(course);
            }
        }
        return result;
    }

    @Override
    public List<CourseDTO> getCoursesCompleted(int studentId) throws ServiceException {
        List<CourseDTO> result = new ArrayList<>();
        List<CourseDTO> coursesDTO;
        try {
            coursesDTO = getCoursesByStudent(studentId);
        } catch (ServiceException e) {
            throw new ServiceException(e);
        }
        for (CourseDTO course : coursesDTO) {
            if (course.getStatus().equals(Status.COMING_SOON)) {
                try {
                    course.setGrade(courseDao.getGrade(course.getId(), studentId));
                } catch (DAOException e) {
                    throw new ServiceException(e);
                }
                result.add(course);
            }
        }
        return result;
    }

    @Override
    public void enroll(int courseId, int userId) throws ServiceException {
        try {
            courseDao.addUserToCourse(courseId, userId);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void addStudent(User user) throws ServiceException {
        try {
            userDao.add(user);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    private List<CourseDTO> prepareCourses(List<Course> courses) throws ServiceException {
        List<CourseDTO> coursesDTO = new ArrayList<>();
        for (Course course : courses) {
            List<User> users;
            try {
                users = userDao.getUsersByCourse(course.getId());
            } catch (DAOException e) {
                throw new ServiceException(e);
            }
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
