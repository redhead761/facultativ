package com.epam.facultative.service.implementation;

import com.epam.facultative.daos.*;
import com.epam.facultative.dto.*;
import com.epam.facultative.entity.*;
import com.epam.facultative.exception.DAOException;
import com.epam.facultative.exception.ServiceException;
import com.epam.facultative.service.GeneralService;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class GeneralServiceImpl implements GeneralService {
    private final CourseDao courseDao;
    private final UserDao userDao;
    private final Converter converter;

    public GeneralServiceImpl(CourseDao courseDao, UserDao userDao) {
        this.courseDao = courseDao;
        this.userDao = userDao;
        this.converter = new Converter();
    }

    @Override
    public List<CourseDTO> sortByAlphabet() throws ServiceException {
        try {
            return prepareCourses(courseDao.getAll()).stream()
                    .sorted(Comparator.comparing(CourseDTO::getTitle))
                    .collect(Collectors.toList());
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<CourseDTO> sortByAlphabetReverse() throws ServiceException {
        try {
            return prepareCourses(courseDao.getAll()).stream()
                    .sorted((o1, o2) -> o2.getTitle().compareTo(o1.getTitle()))
                    .collect(Collectors.toList());
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<CourseDTO> sortByDuration() throws ServiceException {
        try {
            return prepareCourses(courseDao.getAll()).stream()
                    .sorted(Comparator.comparing(CourseDTO::getDuration))
                    .collect(Collectors.toList());
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<CourseDTO> sortByNumberStudentsOnCourse() throws ServiceException {
        try {
            return prepareCourses(courseDao.getAll()).stream()
                    .sorted(Comparator.comparing(CourseDTO::getAmountStudents))
                    .collect(Collectors.toList());
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<CourseDTO> getCoursesByCategory(int categoryId) throws ServiceException {
        try {
            return prepareCourses(courseDao.getByCategory(categoryId));
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<CourseDTO> getCoursesByTeacher(int teacherId) throws ServiceException {
        try {
            return prepareCourses(courseDao.getByUser(teacherId));
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    private List<CourseDTO> prepareCourses(List<Course> courses) throws ServiceException {
        List<CourseDTO> coursesDTO = new ArrayList<>();
        try {
            for (Course course : courses) {
                List<User> users;
                users = userDao.getUsersByCourse(course.getId());
                for (User user : users) {
                    if (user.getRole().equals(Role.TEACHER)) {
                        UserDTO teacher = converter.userToDTO(user);
                        coursesDTO.add(converter.courseToDTO(course, teacher));
                    }
                }
            }
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return coursesDTO;
    }
}
