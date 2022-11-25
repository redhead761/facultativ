package com.epam.facultative.service.implementation;

import com.epam.facultative.daos.*;
import com.epam.facultative.dto.*;
import com.epam.facultative.entity.*;
import com.epam.facultative.exception.DAOException;
import com.epam.facultative.exception.ServiceException;
import com.epam.facultative.service.AdminService;

import java.util.*;

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
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        for (User user : users) {
            usersDTO.add(converter.userToDTO(user));
        }
        return usersDTO;
    }

    @Override
    public void addCourse(Course course) throws ServiceException {
        try {
            courseDao.add(course);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void updateCourse(Course course) throws ServiceException {
        try {
            courseDao.update(course);
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
    public void addCategory(Category category) throws ServiceException {
        try {
            categoryDao.add(category);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void updateCategory(Category category) throws ServiceException {
        try {
            categoryDao.update(category);
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
    public void addTeacher(User user) throws ServiceException {
        user.setRole(Role.TEACHER);
        try {
            userDao.add(user);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }
}
