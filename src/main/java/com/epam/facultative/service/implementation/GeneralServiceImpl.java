package com.epam.facultative.service.implementation;

import com.epam.facultative.data_layer.daos.*;
import com.epam.facultative.data_layer.entities.*;
import com.epam.facultative.dto.CategoryDTO;
import com.epam.facultative.dto.CourseDTO;
import com.epam.facultative.dto.UserDTO;
import com.epam.facultative.exception.DAOException;
import com.epam.facultative.exception.ServiceException;
import com.epam.facultative.exception.ValidateException;
import com.epam.facultative.service.GeneralService;

import java.util.ArrayList;
import java.util.List;

import static com.epam.facultative.data_layer.daos.impl.FieldsConstants.*;
import static com.epam.facultative.dto.Converter.*;
import static com.epam.facultative.utils.HashPassword.verify;
import static com.epam.facultative.utils.validator.ValidateExceptionMessageConstants.*;

public class GeneralServiceImpl implements GeneralService {
    private final CourseDao courseDao;
    private final UserDao userDao;
    private final CategoryDao categoryDao;
    private final TeacherDao teacherDao;
    private final StudentDao studentDao;

    public GeneralServiceImpl(CourseDao courseDao, UserDao userDao, CategoryDao categoryDao, TeacherDao teacherDao, StudentDao studentDao) {
        this.courseDao = courseDao;
        this.userDao = userDao;
        this.categoryDao = categoryDao;
        this.teacherDao = teacherDao;
        this.studentDao = studentDao;
    }

    @Override
    public UserDTO authorization(String login, String password) throws ServiceException, ValidateException {
        try {
            User user = userDao.getByName(login)
                    .orElseThrow(() -> new ValidateException(LOGIN_NOT_EXIST_MESSAGE));
            if (!verify(user.getPassword(), password)) {
                throw new ValidateException(WRONG_PASSWORD_MESSAGE);
            }
            int id = user.getId();
            Role role = user.getRole();
            switch (role) {
                case ADMIN -> {
                    return convertUserToDTO(user);
                }
                case TEACHER -> {
                    Teacher teacher = teacherDao.getById(id)
                            .orElseThrow(() -> new ValidateException(LOGIN_NOT_EXIST_MESSAGE));
                    return convertTeacherToDTO(teacher);
                }
                case STUDENT -> {
                    Student student = studentDao.getById(id)
                            .orElseThrow(() -> new ValidateException(LOGIN_NOT_EXIST_MESSAGE));
                    return convertStudentToDTO(student);
                }
            }

        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return null;
    }

    @Override
    public List<CourseDTO> getAllCourses(int offset, int numberOfRows) throws ServiceException {
        try {
            return prepareCourses(courseDao.getAllPagination(offset, numberOfRows));
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<CourseDTO> sortCoursesByAlphabet(int offset, int numberOfRows) throws ServiceException {
        try {
            List<Course> courses = courseDao.getAllSortPagination(offset, numberOfRows, COURSE_TITLE);
            return prepareCourses(courses);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<CourseDTO> sortCoursesByAlphabetReverse(int offset, int numberOfRows) throws ServiceException {
        try {
            List<Course> courses = courseDao.getAllSortPagination(offset, numberOfRows, COURSE_TITLE + " DESC");
            return prepareCourses(courses);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<CourseDTO> sortCoursesByDuration(int offset, int numberOfRows) throws ServiceException {
        try {
            List<Course> courses = courseDao.getAllSortPagination(offset, numberOfRows, COURSE_DURATION);
            return prepareCourses(courses);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<CourseDTO> sortCoursesBuAmountOfStudents(int offset, int numberOfRows) throws ServiceException {
        try {
            List<Course> courses = courseDao.getAllSortPagination(offset, numberOfRows, COURSE_AMOUNT_STUDENTS);
            return prepareCourses(courses);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<CourseDTO> getCoursesByCategory(int categoryId, int offset, int numberOfRows) throws ServiceException {
        try {
            return prepareCourses(courseDao.getByCategory(categoryId, offset, numberOfRows));
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<CourseDTO> getCoursesByTeacher(int teacherId, int offset, int numberOfRows) throws ServiceException {
        try {
            return prepareCourses(courseDao.getByTeacher(teacherId, offset, numberOfRows));
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<CategoryDTO> getAllCategories() throws ServiceException {
        try {
            List<CategoryDTO> categories = new ArrayList<>();
            for (Category category : categoryDao.getAll()) {
                categories.add(convertCategoryToDTO(category));
            }
            return categories;
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<UserDTO> getAllTeachers() throws ServiceException {
        List<UserDTO> usersDTO = new ArrayList<>();
        try {
            List<Teacher> users = teacherDao.getAll();
            for (User user : users) {
                usersDTO.add(convertUserToDTO(user));
            }
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return usersDTO;
    }

    @Override
    public int getNoOfRecordsCourses() {
        return courseDao.getNoOfRecords();
    }

    private List<CourseDTO> prepareCourses(List<Course> courses) {
        List<CourseDTO> coursesDTO = new ArrayList<>();
        for (Course course : courses) {
            coursesDTO.add(convertCourseToDTO(course));
        }
        return coursesDTO;
    }
}