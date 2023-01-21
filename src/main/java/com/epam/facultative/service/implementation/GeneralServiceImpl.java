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
import com.epam.facultative.utils.query_builders.ParamBuilderForQuery;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import static com.epam.facultative.data_layer.daos.impl.FieldsConstants.*;
import static com.epam.facultative.dto.Converter.*;
import static com.epam.facultative.utils.hash_password.HashPassword.verify;
import static com.epam.facultative.utils.query_builders.ParamBuilderForQueryUtil.*;
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
    public Map.Entry<Integer, List<CourseDTO>> getAllCourses(String param) throws ServiceException {
        try {
            Map.Entry<Integer, List<Course>> coursesWithRows = courseDao.getAll(param);
            List<CourseDTO> courses = prepareCourses(coursesWithRows.getValue());
            return Map.entry(coursesWithRows.getKey(), courses);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Map.Entry<Integer, List<CategoryDTO>> getAllCategories() throws ServiceException {
        try {
            ParamBuilderForQuery paramBuilderForQuery = categoryParamBuilderForQuery().setLimits("1", String.valueOf(Integer.MAX_VALUE));
            Map.Entry<Integer, List<Category>> categoriesWithRows = categoryDao.getAll(paramBuilderForQuery.getParam());
            List<CategoryDTO> categoryDTOS = prepareCategories(categoriesWithRows.getValue());
            return Map.entry(categoriesWithRows.getKey(), categoryDTOS);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Map.Entry<Integer, List<TeacherDTO>> getAllTeachers() throws ServiceException {
        try {
            ParamBuilderForQuery paramBuilderForQuery = teacherParamBuilderForQuery().setLimits("1", String.valueOf(Integer.MAX_VALUE));
            Map.Entry<Integer, List<Teacher>> teachersWithRows = teacherDao.getAll(paramBuilderForQuery.getParam());
            List<TeacherDTO> teacherDTOS = prepareTeachers(teachersWithRows.getValue());
            return Map.entry(teachersWithRows.getKey(), teacherDTOS);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public ByteArrayOutputStream downloadAllCoursesInPdf(String locale) throws ServiceException {
        ParamBuilderForQuery paramBuilderForQuery = courseParamBuilderForQuery().setLimits("1", String.valueOf(Integer.MAX_VALUE));
        Map.Entry<Integer, List<CourseDTO>> coursesWithRows = getAllCourses(paramBuilderForQuery.getParam());
        List<CourseDTO> courses = coursesWithRows.getValue();
        PdfCreator pdfCreator = new PdfCreator();
        return pdfCreator.createCoursesPdf(courses, locale);
    }
}