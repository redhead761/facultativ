package com.epam.facultative.service.implementation;

import com.epam.facultative.daos.*;
import com.epam.facultative.dto.*;
import com.epam.facultative.entities.Category;
import com.epam.facultative.entities.Course;
import com.epam.facultative.entities.Role;
import com.epam.facultative.entities.User;
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
    public void addCourse(Course course) throws ServiceException, ValidateException {
        try {
            if (courseDao.getByName(course.getTitle()) != null) {
                throw new ValidateException("Login not unique");
            }
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
                courseDao.update(course);
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
            if (categoryDao.getByName(category.getTitle()) != null) {
                throw new ValidateException("Login not unique");
            }
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
    public List<Category> getAllCategoriesPagination(int offset, int numberOfRows) throws ServiceException {
        try {
            return categoryDao.getAllPagination(offset, numberOfRows);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public int getNoOfRecordsCategories() {
        return categoryDao.getNoOfRecords();
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
    public void addTeacher(User user) throws ServiceException, ValidateException {
        try {
            if (userDao.getByName(user.getLogin()) != null) {
                throw new ValidateException("Login not unique");
            }
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
    public List<UserDTO> getAllStudentsPagination(int offset, int noOfRecords) throws ServiceException {
        try {
            List<User> users = userDao.getByRolePagination(Role.STUDENT.getId(), offset, noOfRecords);
            List<UserDTO> students = new ArrayList<>();
            for (User user :
                    users) {
                students.add(converter.userToDTO(user));
            }
            return students;
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<UserDTO> getAllTeachersPagination(int offset, int noOfRecords) throws ServiceException {
        try {
            List<User> users = userDao.getByRolePagination(Role.TEACHER.getId(), offset, noOfRecords);
            List<UserDTO> teachers = new ArrayList<>();
            for (User user :
                    users) {
                teachers.add(converter.userToDTO(user));
            }
            return teachers;
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Category getCategory(int id) throws ServiceException {
        try {
            return categoryDao.getById(id);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public CourseDTO getCourse(int id) throws ServiceException {
        try {
            Course course = courseDao.getById(id);
            return converter.courseToDTO(course, null);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public UserDTO getTeacher(int id) throws ServiceException {
        try {
            User teacher = userDao.getById(id);
            return converter.userToDTO(teacher);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public int getNoOfRecordsTeachers() {
        return userDao.getNoOfRecords();
    }

    @Override
    public int getNoOfRecordsStudents() {
        return userDao.getNoOfRecords();
    }
}