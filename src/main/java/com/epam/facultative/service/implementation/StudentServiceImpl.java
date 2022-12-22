package com.epam.facultative.service.implementation;

import com.epam.facultative.dto.*;
import com.epam.facultative.entities.*;
import com.epam.facultative.exception.DAOException;
import com.epam.facultative.exception.ServiceException;
import com.epam.facultative.exception.ValidateException;
import com.epam.facultative.repositories.StudentRepository;
import com.epam.facultative.service.StudentService;

import static com.epam.facultative.utils.HashPassword.*;
import static com.epam.facultative.utils.validator.Validator.*;

import java.util.*;

public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;
    private final Converter converter;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
        this.converter = new Converter();
    }

    @Override
    public List<CourseDTO> getCoursesByStudent(int studentId, int offset, int numberOfRows) throws ServiceException {
        try {
            return prepareCourses(studentRepository.getCoursesByStudent(studentId, offset, numberOfRows));
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<CourseDTO> getCoursesComingSoon(int studentId, int offset, int numberOfRows) throws ServiceException {
        try {
            return prepareCourses(studentRepository.getCoursesComingSoon(studentId, offset, numberOfRows));
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<CourseDTO> getCoursesInProgress(int studentId, int offset, int numberOfRows) throws ServiceException {
        try {
            return prepareCourses(studentRepository.getCoursesInProgress(studentId, offset, numberOfRows));
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<CourseDTO> getCoursesCompleted(int studentId, int offset, int numberOfRows) throws ServiceException {
        try {
            return prepareCourses(studentRepository.getCoursesCompleted(studentId, offset, numberOfRows));
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void enroll(int courseId, int userId) throws ServiceException {
        try {
            studentRepository.enroll(courseId, userId);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void addStudent(User user) throws ServiceException, ValidateException {
        try {
            if (studentRepository.getByLogin(user.getLogin()) != null) {
                throw new ValidateException("Login not unique");
            }
            if (validateLogin(user.getLogin())
                    && validatePassword(user.getPassword())
                    && validateNameAndSurname(user.getName(), user.getSurname())
                    && validateEmail(user.getEmail())) {
                user.setRole(Role.STUDENT);
                user.setPassword(encode(user.getPassword()));
                studentRepository.addStudent((Student) user);
            }
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public int getNoOfRecordsCourses() {
        return studentRepository.getNoOfRecordsCourses();
    }


    private List<CourseDTO> prepareCourses(List<Course> courses) {
        List<CourseDTO> coursesDTO = new ArrayList<>();
        for (Course course : courses) {
            coursesDTO.add(converter.courseToDTO(course));
        }
        return coursesDTO;
    }
}
