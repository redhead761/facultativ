package com.epam.facultative.service.implementation;

import com.epam.facultative.daos.*;
import com.epam.facultative.dto.*;
import com.epam.facultative.entity.*;
import com.epam.facultative.exception.*;
import com.epam.facultative.service.AdminService;

import java.util.*;

import static com.epam.facultative.utils.HashPassword.*;
import static com.epam.facultative.utils.validator.Validator.*;

public class AdminServiceImpl implements AdminService {
    private final CourseDao courseDao;
    private final CategoryDao categoryDao;
    private final UserDao userDao;
    private final Converter converter;

    public AdminServiceImpl(CourseDao courseDao, CategoryDao categoryDao, UserDao userDao) {
        this.courseDao = courseDao;
        this.categoryDao = categoryDao;
        this.userDao = userDao;
        this.converter = new Converter();
    }

    @Override
    public List<UserDTO> getStudents(int courseId) throws ServiceException {
        List<UserDTO> usersDTO = new ArrayList<>();
        List<User> users;
        try {
            users = userDao.getUsersByCourse(courseId);
            for (User user : users) {
                usersDTO.add(converter.userToDTO(user));
            }
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return usersDTO;
    }

    @Override
    public void addCourse(Course course) throws ServiceException, ValidateException {
        try {
            if (validateCourseData(course.getTitle(), course.getDescription(), course.getDuration())) {
                courseDao.add(course);
            }
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void updateCourse(Course course) throws ServiceException, ValidateException {
        try {
            if (validateCourseData(course.getTitle(), course.getDescription(), course.getDuration())) {
                courseDao.add(course);
            }
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteCourse(int courseId) throws ServiceException {
        try {
            courseDao.delete(courseId);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }


    @Override
    public void addCategory(Category category) throws ServiceException, ValidateException {
        try {
            if (categoryDao.getByName(category.getTitle()) != null) {
                throw new ValidateException("Login not unique");
            }
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        try {
            if (validateCategoryData(category.getTitle(), category.getDescription())) {
                categoryDao.add(category);
            }
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void updateCategory(Category category) throws ServiceException, ValidateException {
        try {
            if (validateCategoryData(category.getTitle(), category.getDescription())) {
                categoryDao.update(category);
            }
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteCategory(int categoryId) throws ServiceException {
        try {
            categoryDao.delete(categoryId);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void assigned(int idCourse, int idUser) throws ServiceException {
        try {
            courseDao.addUserToCourse(idCourse, idUser);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void blockStudent(int userId) throws ServiceException {
        try {
            userDao.blockUnblockStudent(userId, true);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void unblockStudent(int userId) throws ServiceException {
        try {
            userDao.blockUnblockStudent(userId, false);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void addTeacher(User user) throws ServiceException, ValidateException, DAOException {
        if (userDao.getByName(user.getLogin()) != null) {
            throw new ValidateException("Login not unique");
        }
        try {
            if (validateLogin(user.getLogin())
                    && validatePassword(user.getPassword())
                    && validateNameAndSurname(user.getName(), user.getSurname())
                    && validateEmail(user.getEmail())) {
                user.setRole(Role.TEACHER);
                user.setPassword(encode(user.getPassword()));
                userDao.add(user);
            }
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
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
    public Category getCategory(int id) throws ServiceException {
        try {
            return categoryDao.getById(id);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }
}
