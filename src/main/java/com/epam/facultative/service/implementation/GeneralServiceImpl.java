package com.epam.facultative.service.implementation;

import com.epam.facultative.data_layer.daos.*;
import com.epam.facultative.data_layer.entities.*;
import com.epam.facultative.dto.CategoryDTO;
import com.epam.facultative.dto.CourseDTO;
import com.epam.facultative.dto.TeacherDTO;
import com.epam.facultative.dto.UserDTO;
import com.epam.facultative.exception.DAOException;
import com.epam.facultative.exception.ServiceException;
import com.epam.facultative.exception.ValidateException;
import com.epam.facultative.service.GeneralService;
import com.epam.facultative.utils.pdf_creator.PdfCreator;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Map;

import static com.epam.facultative.data_layer.daos.impl.FieldsConstants.*;
import static com.epam.facultative.dto.Converter.*;
import static com.epam.facultative.utils.hash_password.HashPassword.verify;
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
        UserDTO result = null;
        try {
            User user = userDao.getByName(login).orElseThrow(() -> new ValidateException(LOGIN_NOT_EXIST_MESSAGE));
            if (!verify(user.getPassword(), password)) {
                throw new ValidateException(WRONG_PASSWORD_MESSAGE);
            }
            int id = user.getId();
            Role role = user.getRole();
            switch (role) {
                case ADMIN -> result = convertUserToDTO(user);
                case TEACHER -> {
                    Teacher teacher = teacherDao.getById(id).orElseThrow(() -> new ValidateException(LOGIN_NOT_EXIST_MESSAGE));
                    result = convertTeacherToDTO(teacher);
                }
                case STUDENT -> {
                    Student student = studentDao.getById(id).orElseThrow(() -> new ValidateException(LOGIN_NOT_EXIST_MESSAGE));
                    result = convertStudentToDTO(student);
                }
            }
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return result;
    }

    @Override
    public Map.Entry<Integer, List<CourseDTO>> getAllCourses(int offset, int numberOfRows) throws ServiceException {
        try {
            Map.Entry<Integer, List<Course>> coursesWithRows = courseDao.getAllPagination(offset, numberOfRows);
            List<CourseDTO> courses = prepareCourses(coursesWithRows.getValue());
            return Map.entry(coursesWithRows.getKey(), courses);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Map.Entry<Integer, List<CourseDTO>> sortCoursesByAlphabet(int offset, int numberOfRows) throws ServiceException {
        try {
            Map.Entry<Integer, List<Course>> coursesWithRows = courseDao.getAllSortPagination(offset, numberOfRows, COURSE_TITLE);
            List<CourseDTO> courses = prepareCourses(coursesWithRows.getValue());
            return Map.entry(coursesWithRows.getKey(), courses);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Map.Entry<Integer, List<CourseDTO>> sortCoursesByAlphabetReverse(int offset, int numberOfRows) throws ServiceException {
        try {
            Map.Entry<Integer, List<Course>> coursesWithRows = courseDao.getAllSortPagination(offset, numberOfRows, COURSE_TITLE + " DESC");
            List<CourseDTO> courses = prepareCourses(coursesWithRows.getValue());
            return Map.entry(coursesWithRows.getKey(), courses);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Map.Entry<Integer, List<CourseDTO>> sortCoursesByDuration(int offset, int numberOfRows) throws ServiceException {
        try {
            Map.Entry<Integer, List<Course>> coursesWithRows = courseDao.getAllSortPagination(offset, numberOfRows, COURSE_DURATION);
            List<CourseDTO> courses = prepareCourses(coursesWithRows.getValue());
            return Map.entry(coursesWithRows.getKey(), courses);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Map.Entry<Integer, List<CourseDTO>> sortCoursesByAmountOfStudents(int offset, int numberOfRows) throws ServiceException {
        try {
            Map.Entry<Integer, List<Course>> coursesWithRows = courseDao.getAllSortPagination(offset, numberOfRows, COURSE_AMOUNT_STUDENTS);
            List<CourseDTO> courses = prepareCourses(coursesWithRows.getValue());
            return Map.entry(coursesWithRows.getKey(), courses);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Map.Entry<Integer, List<CourseDTO>> getCoursesByCategory(int categoryId, int offset, int numberOfRows) throws ServiceException {
        try {
            Map.Entry<Integer, List<Course>> coursesWithRows = courseDao.getByCategory(categoryId, offset, numberOfRows);
            List<CourseDTO> courses = prepareCourses(coursesWithRows.getValue());
            return Map.entry(coursesWithRows.getKey(), courses);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Map.Entry<Integer, List<CourseDTO>> getCoursesByTeacher(int teacherId, int offset, int numberOfRows) throws ServiceException {
        try {
            Map.Entry<Integer, List<Course>> coursesWithRows = courseDao.getByTeacher(teacherId, offset, numberOfRows);
            List<CourseDTO> courses = prepareCourses(coursesWithRows.getValue());
            return Map.entry(coursesWithRows.getKey(), courses);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<CategoryDTO> getAllCategories() throws ServiceException {
        try {
            List<Category> categories = categoryDao.getAll();
            return prepareCategories(categories);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<TeacherDTO> getAllTeachers() throws ServiceException {
        try {
            List<Teacher> teachers = teacherDao.getAll();
            return prepareTeachers(teachers);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public ByteArrayOutputStream downloadAllCoursesInPdf(String locale) throws ServiceException {
        Map.Entry<Integer, List<CourseDTO>> coursesWithRows = getAllCourses(0, Integer.MAX_VALUE);
        List<CourseDTO> courses = coursesWithRows.getValue();
        PdfCreator pdfCreator = new PdfCreator();
        return pdfCreator.createCoursesPdf(courses, locale);
    }
}