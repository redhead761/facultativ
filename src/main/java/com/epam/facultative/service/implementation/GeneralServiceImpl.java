package com.epam.facultative.service.implementation;

import com.epam.facultative.daos.*;
import com.epam.facultative.dto.*;
import com.epam.facultative.entity.*;
import com.epam.facultative.exception.DAOException;
import com.epam.facultative.exception.ServiceException;
import com.epam.facultative.exception.ValidateException;
import com.epam.facultative.service.GeneralService;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static com.epam.facultative.utils.HashPassword.verify;

public class GeneralServiceImpl implements GeneralService {
    private final CourseDao courseDao;
    private final UserDao userDao;
    private final CategoryDao categoryDao;
    private final Converter converter;


    public GeneralServiceImpl(CourseDao courseDao, UserDao userDao, CategoryDao categoryDao) {
        this.courseDao = courseDao;
        this.userDao = userDao;
        this.categoryDao = categoryDao;
        this.converter = new Converter();

    }

    @Override
    public UserDTO authorization(String login, String password) throws ServiceException, ValidateException {
        try {
            User user = userDao.getByName(login);
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
    public List<CourseDTO> getAllCourses() throws ServiceException {
        try {
            return prepareCourses(courseDao.getAll());
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<CourseDTO> sortCoursesByAlphabet() throws ServiceException {
        try {
            return prepareCourses(courseDao.getAll()).stream()
                    .sorted(Comparator.comparing(CourseDTO::getTitle))
                    .collect(Collectors.toList());
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<CourseDTO> sortCoursesByAlphabetReverse() throws ServiceException {
        try {
            return prepareCourses(courseDao.getAll()).stream()
                    .sorted((o1, o2) -> o2.getTitle().compareTo(o1.getTitle()))
                    .collect(Collectors.toList());
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<CourseDTO> sortCoursesByDuration() throws ServiceException {
        try {
            return prepareCourses(courseDao.getAll()).stream()
                    .sorted(Comparator.comparing(CourseDTO::getDuration))
                    .collect(Collectors.toList());
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<CourseDTO> sortCoursesBuAmountOfStudents() throws ServiceException {
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

    @Override
    public List<UserDTO> getAllStudents() throws ServiceException {
        List<UserDTO> usersDTO = new ArrayList<>();
        try {
            List<User> users = userDao.getByRole(Role.STUDENT.getId());
            for (User user : users) {
                usersDTO.add(converter.userToDTO(user));
            }
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return usersDTO;
    }

    @Override
    public List<Category> getAllCategories() throws ServiceException {
        try {
            return categoryDao.getAll();
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<UserDTO> getAllTeachers() throws ServiceException {
        List<UserDTO> usersDTO = new ArrayList<>();
        try {
            List<User> users = userDao.getByRole(Role.TEACHER.getId());
            for (User user : users) {
                usersDTO.add(converter.userToDTO(user));
            }
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return usersDTO;
    }

    private List<CourseDTO> prepareCourses(List<Course> courses) throws ServiceException {
        List<CourseDTO> coursesDTO = new ArrayList<>();
        try {
            for (Course course : courses) {
                List<User> users;
                UserDTO teacher = null;
                users = userDao.getUsersByCourse(course.getId());
                for (User user : users) {
                    if (user.getRole().equals(Role.TEACHER)) {
                        teacher = converter.userToDTO(user);
                    }
                }
                coursesDTO.add(converter.courseToDTO(course, teacher));
            }
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return coursesDTO;
    }
}
