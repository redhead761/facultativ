package com.epam.facultative.service.implementation;

import com.epam.facultative.daos.*;
import com.epam.facultative.dto.*;
import com.epam.facultative.entities.*;
import com.epam.facultative.exception.DAOException;
import com.epam.facultative.exception.ServiceException;
import com.epam.facultative.exception.ValidateException;
import com.epam.facultative.service.StudentService;

import static com.epam.facultative.utils.HashPassword.*;
import static com.epam.facultative.utils.validator.Validator.*;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.*;

public class StudentServiceImpl implements StudentService {
    private final CourseDao courseDao;
    private final UserDao userDao;
    private final Converter converter;
    private final StudentDao studentDao;

    public StudentServiceImpl(CourseDao courseDao, UserDao userDao, StudentDao studentDao) {
        this.courseDao = courseDao;
        this.userDao = userDao;
        this.studentDao = studentDao;
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
    public List<CourseDTO> getCoursesComingSoon(int studentId, int offset, int numberOfRows) throws ServiceException {
        try {
            return prepareCourses(courseDao.getByStatus(studentId, Status.COMING_SOON, offset, numberOfRows));
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<CourseDTO> getCoursesInProgress(int studentId, int offset, int numberOfRows) throws ServiceException {
        try {
            return prepareCourses(courseDao.getByStatus(studentId, Status.IN_PROCESS, offset, numberOfRows));
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<UserDTO> getCoursesCompleted(int studentId, int offset, int numberOfRows) throws ServiceException {
//        try {
//            User user = userDao.getById(studentId);
//            List<UserDTO> students = new ArrayList<>();
//            List<CourseDTO> courses = prepareCourses(courseDao.getByStatus(studentId, Status.COMPLETED,offset,numberOfRows));
//            for (CourseDTO course : courses) {
//                int grade = courseDao.getGrade(course.getId(), studentId);
//                students.add(converter.userToStudent(user, course, grade));
//            }
//            return students;
//        } catch (DAOException e) {
//            throw new RuntimeException(e);
//        }
        return null;
    }

    @Override
    public void enroll(int courseId, int userId) throws ServiceException {
        try {
            courseDao.insertJournal(courseId, userId);
            courseDao.addNumberStudentsToCourse(courseId);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void addStudent(Student student) throws ServiceException, ValidateException {
        try {
            if (validateLogin(student.getLogin())
                    && validatePassword(student.getPassword())
                    && validateNameAndSurname(student.getName(), student.getSurname())
                    && validateEmail(student.getEmail())) {
                student.setRole(Role.STUDENT);
                student.setPassword(encode(student.getPassword()));
                studentDao.add(student);
            }
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public int getNoOfRecordsCourses() {
        return courseDao.getNoOfRecords();
    }


    private List<CourseDTO> prepareCourses(List<Course> courses) {
        List<CourseDTO> coursesDTO = new ArrayList<>();
        for (Course course : courses) {
            coursesDTO.add(converter.courseToDTO(course));
        }
        return coursesDTO;
    }
}