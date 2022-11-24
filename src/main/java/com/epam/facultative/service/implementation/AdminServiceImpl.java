package com.epam.facultative.service.implementation;

import com.epam.facultative.daos.*;
import com.epam.facultative.dto.*;
import com.epam.facultative.entity.*;
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
    public List<UserDTO> getStudents(int courseId) {
        List<UserDTO> usersDTO = new ArrayList<>();
        List<User> users = userDao.getUsersByCourse(courseId);
        for (User user : users) {
            usersDTO.add(converter.userToDTO(user));
        }
        return usersDTO;
    }

    @Override
    public void addCourse(Course course) {
        courseDao.add(course);
    }

    @Override
    public void updateCourse(Course course) {
        courseDao.update(course);
    }

    @Override
    public void deleteCourse(int courseId) {
        courseDao.delete(courseId);
    }

    @Override
    public void addCategory(Category category) {
        categoryDao.add(category);
    }

    @Override
    public void updateCategory(Category category) {
        categoryDao.update(category);
    }

    @Override
    public void deleteCategory(int categoryId) {
        categoryDao.delete(categoryId);
    }

    @Override
    public void assigned(int idCourse, int idUser) {
        courseDao.addUserToCourse(idCourse, idUser);
    }

    @Override
    public void blockStudent(int userId) {
        userDao.blockUnblockStudent(userId, true);
    }

    @Override
    public void unblockStudent(int userId) {
        userDao.blockUnblockStudent(userId, false);
    }

    @Override
    public void addTeacher(User user) {
        user.setRole(Role.TEACHER);
        userDao.add(user);
    }
}
