package com.epam.facultative.service.implementation;

import com.epam.facultative.daos.CategoryDao;
import com.epam.facultative.daos.CourseDao;
import com.epam.facultative.daos.UserDao;
import com.epam.facultative.dto.Converter;
import com.epam.facultative.dto.CourseDTO;
import com.epam.facultative.dto.UserDTO;
import com.epam.facultative.entities.*;
import com.epam.facultative.exception.DAOException;
import com.epam.facultative.exception.ServiceException;
import com.epam.facultative.exception.ValidateException;
import com.epam.facultative.repositories.GeneralRepository;
import com.epam.facultative.service.GeneralService;

import java.util.ArrayList;
import java.util.List;

import static com.epam.facultative.utils.HashPassword.verify;

public class GeneralServiceImpl implements GeneralService {
    private final GeneralRepository generalRepository;
    private final Converter converter;

    public GeneralServiceImpl(GeneralRepository generalRepository) {
        this.generalRepository = generalRepository;
        this.converter = new Converter();

    }

    @Override
    public UserDTO authorization(String login, String password) throws ServiceException, ValidateException {
        try {
            User user = generalRepository.authorization(login);
            if (user == null) {
                throw new ValidateException("Login not exist");
            }
            if (!verify(user.getPassword(), password)) {
                throw new ValidateException("Wrong password");
            }
            return converter.userToDTO(user);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<CourseDTO> getAllCourses(int offset, int numberOfRows) throws ServiceException {
        try {
            return prepareCourses(generalRepository.getAllCourses(offset, numberOfRows));
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<CourseDTO> sortCoursesByAlphabet(int offset, int numberOfRows) throws ServiceException {
        try {
            List<Course> courses = generalRepository.sortCoursesByAlphabet(offset, numberOfRows);
            return prepareCourses(courses);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<CourseDTO> sortCoursesByAlphabetReverse(int offset, int numberOfRows) throws ServiceException {
        try {
            List<Course> courses = generalRepository.sortCoursesByAlphabetReverse(offset, numberOfRows);
            return prepareCourses(courses);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<CourseDTO> sortCoursesByDuration(int offset, int numberOfRows) throws ServiceException {
        try {
            List<Course> courses = generalRepository.sortCoursesByDuration(offset, numberOfRows);
            return prepareCourses(courses);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<CourseDTO> sortCoursesBuAmountOfStudents(int offset, int numberOfRows) throws ServiceException {
        try {
            List<Course> courses = generalRepository.sortCoursesBuAmountOfStudents(offset, numberOfRows);
            return prepareCourses(courses);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<CourseDTO> getCoursesByCategory(int categoryId, int offset, int numberOfRows) throws ServiceException {
        try {
            return prepareCourses(generalRepository.getCoursesByCategory(categoryId, offset, numberOfRows));
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<CourseDTO> getCoursesByTeacher(int teacherId, int offset, int numberOfRows) throws ServiceException {
        try {
            return prepareCourses(generalRepository.getCoursesByTeacher(teacherId, offset, numberOfRows));
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Category> getAllCategories() throws ServiceException {
        try {
            return generalRepository.getAllCategories();
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<UserDTO> getAllTeachers() throws ServiceException {
        List<UserDTO> usersDTO = new ArrayList<>();
        try {
            List<Teacher> users = generalRepository.getAllTeachers();
            for (User user : users) {
                usersDTO.add(converter.userToDTO(user));
            }
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return usersDTO;
    }

    @Override
    public int getNoOfRecordsCourses() {
        return generalRepository.getNoOfRecordsCourses();
    }

    private List<CourseDTO> prepareCourses(List<Course> courses) {
        List<CourseDTO> coursesDTO = new ArrayList<>();
        for (Course course : courses) {
            coursesDTO.add(converter.courseToDTO(course));
        }
        return coursesDTO;
    }
}
