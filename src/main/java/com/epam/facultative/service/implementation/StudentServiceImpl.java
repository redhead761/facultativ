package com.epam.facultative.service.implementation;

import com.epam.facultative.data_layer.daos.CourseDao;
import com.epam.facultative.data_layer.daos.StudentDao;
import com.epam.facultative.dto.Converter;
import com.epam.facultative.dto.CourseDTO;
import com.epam.facultative.data_layer.entities.Course;
import com.epam.facultative.data_layer.entities.Role;
import com.epam.facultative.data_layer.entities.Status;
import com.epam.facultative.data_layer.entities.Student;
import com.epam.facultative.exception.DAOException;
import com.epam.facultative.exception.ServiceException;
import com.epam.facultative.exception.ValidateException;
import com.epam.facultative.service.StudentService;

import static com.epam.facultative.utils.HashPassword.*;
import static com.epam.facultative.utils.validator.Validator.*;

import java.util.*;

public class StudentServiceImpl implements StudentService {
    private final CourseDao courseDao;
    private final Converter converter;
    private final StudentDao studentDao;

    public StudentServiceImpl(CourseDao courseDao, StudentDao studentDao) {
        this.courseDao = courseDao;
        this.studentDao = studentDao;
        this.converter = new Converter();
    }

    @Override
    public List<CourseDTO> getCoursesByStudent(int studentId, int offset, int numberOfRows) throws ServiceException {
        try {
            return prepareCourses(courseDao.getByStudent(studentId, offset, numberOfRows));
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
    public List<CourseDTO> getCoursesCompleted(int studentId, int offset, int numberOfRows) throws ServiceException {
        try {
            return prepareCourses(courseDao.getByStatus(studentId, Status.COMPLETED, offset, numberOfRows));
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void enroll(int courseId, int userId) throws ServiceException {
        try {
            courseDao.insertJournal(courseId, userId);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void addStudent(Student student) throws ServiceException, ValidateException {
        try {
            validateLogin(student.getLogin());
            validatePassword(student.getPassword());
            validateNameAndSurname(student.getName(), student.getSurname());
            validateEmail(student.getEmail());
            student.setRole(Role.STUDENT);
            student.setPassword(encode(student.getPassword()));
            studentDao.add(student);

        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public int getGrade(int courseId, int userId) throws ServiceException {
        try {
            return studentDao.getGrade(courseId, userId);
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