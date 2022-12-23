package com.epam.facultative.service.implementation;

import com.epam.facultative.daos.*;
import com.epam.facultative.dto.*;
import com.epam.facultative.entities.Course;
import com.epam.facultative.entities.Role;
import com.epam.facultative.entities.Status;
import com.epam.facultative.entities.User;
import com.epam.facultative.exception.DAOException;
import com.epam.facultative.exception.ServiceException;
import com.epam.facultative.exception.ValidateException;
import com.epam.facultative.service.StudentService;

import static com.epam.facultative.utils.HashPassword.*;
import static com.epam.facultative.utils.validator.Validator.*;

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
    public List<CourseDTO> getCoursesByStudent(int studentId, int offset, int numberOfRows) throws ServiceException {
        try {
            return prepareCourses(courseDao.getByUser(studentId, offset, numberOfRows));
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<CourseDTO> getCoursesComingSoon(int studentId,int offset,int numberOfRows) throws ServiceException {
        try {
            return prepareCourses(courseDao.getByStatus(studentId, Status.COMING_SOON,offset,numberOfRows));
        } catch (DAOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<CourseDTO> getCoursesInProgress(int studentId,int offset,int numberOfRows) throws ServiceException {
        try {
            return prepareCourses(courseDao.getByStatus(studentId, Status.IN_PROCESS,offset,numberOfRows));
        } catch (DAOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<UserDTO> getCoursesCompleted(int studentId,int offset,int numberOfRows) throws ServiceException {
        try {
            User user = userDao.getById(studentId);
            List<UserDTO> students = new ArrayList<>();
            List<CourseDTO> courses = prepareCourses(courseDao.getByStatus(studentId, Status.COMPLETED,offset,numberOfRows));
            for (CourseDTO course : courses) {
                int grade = courseDao.getGrade(course.getId(), studentId);
                students.add(converter.userToStudent(user, course, grade));
            }
            return students;
        } catch (DAOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void enroll(int courseId, int userId) throws ServiceException {
        try {
            courseDao.addUserToCourse(courseId, userId);
            courseDao.addNumberStudentsToCourse(courseId);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void addStudent(User user) throws ServiceException, ValidateException {
        try {
            if (userDao.getByName(user.getLogin()) != null) {
                throw new ValidateException("Login not unique");
            }
            if (validateLogin(user.getLogin())
                    && validatePassword(user.getPassword())
                    && validateNameAndSurname(user.getName(), user.getSurname())
                    && validateEmail(user.getEmail())) {
                user.setRole(Role.STUDENT);
                user.setPassword(encode(user.getPassword()));
                userDao.add(user);
            }
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public int getNoOfRecordsCourses() {
        return courseDao.getNoOfRecords();
    }


    private List<CourseDTO> prepareCourses(List<Course> courses) throws ServiceException {
        List<CourseDTO> coursesDTO = new ArrayList<>();
        for (Course course : courses) {
            List<User> users;
            try {
                users = userDao.getUsersByCourse(course.getId());
                for (User user : users) {
                    if (user.getRole().equals(Role.TEACHER)) {
                        UserDTO teacher = converter.userToDTO(user);
                        coursesDTO.add(converter.courseToDTO(course, teacher));
                    }
                }
            } catch (DAOException e) {
                throw new ServiceException(e);
            }
        }
        return coursesDTO;
    }
}