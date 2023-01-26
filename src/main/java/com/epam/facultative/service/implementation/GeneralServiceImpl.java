package com.epam.facultative.service.implementation;

import com.epam.facultative.controller.AppContext;
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
import com.epam.facultative.utils.email_sender.EmailSender;
import com.epam.facultative.utils.pdf_creator.PdfCreator;
import com.epam.facultative.utils.param_builders.ParamBuilderForQuery;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.security.SecureRandom;
import java.util.List;
import java.util.Map;

import static com.epam.facultative.dto.Converter.*;
import static com.epam.facultative.utils.email_sender.EmailConstants.*;
import static com.epam.facultative.utils.hash_password.HashPassword.encode;
import static com.epam.facultative.utils.hash_password.HashPassword.verify;
import static com.epam.facultative.utils.param_builders.ParamBuilderForQueryUtil.*;
import static com.epam.facultative.utils.validator.ValidateExceptionMessageConstants.*;
import static com.epam.facultative.utils.validator.Validator.validatePassword;

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
            User user = userDao
                    .get(userParamBuilderForQuery().setUserLoginFilter(login).getParam())
                    .orElseThrow(() -> new ValidateException(LOGIN_NOT_EXIST_MESSAGE));
            if (!verify(user.getPassword(), password)) {
                throw new ValidateException(WRONG_PASSWORD_MESSAGE);
            }
            int id = user.getId();
            Role role = user.getRole();
            ParamBuilderForQuery paramBuilder = userParamBuilderForQuery().setUserIdFilter(String.valueOf(id));
            switch (role) {
                case ADMIN -> result = convertUserToDTO(user);
                case TEACHER -> {
                    Teacher teacher = teacherDao.get(paramBuilder.getParam())
                            .orElseThrow(() -> new ValidateException(LOGIN_NOT_EXIST_MESSAGE));
                    result = convertTeacherToDTO(teacher);
                }
                case STUDENT -> {
                    Student student = studentDao.get(paramBuilder.getParam())
                            .orElseThrow(() -> new ValidateException(LOGIN_NOT_EXIST_MESSAGE));
                    checkBlocStudent(student);
                    result = convertStudentToDTO(student);
                }
            }
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return result;
    }

    private void checkBlocStudent(Student student) throws ValidateException {
        if (student.isBlock()) throw new ValidateException(STUDENT_BLOCKED);
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

    @Override
    public void recoveryPassword(String email) throws ServiceException, ValidateException {
        try {
            User user = userDao.get(userParamBuilderForQuery().setUserEmailFilter(email).getParam())
                    .orElseThrow(() -> new ValidateException(LOGIN_NOT_EXIST_MESSAGE));
            String newPass = getNewPassword();
            user.setPassword(newPass);
            AppContext appContext = AppContext.getAppContext();
            EmailSender emailSender = appContext.getEmailSender();
            emailSender.send(email, EMAIL_SUBJECT_FOR_RECOVERY_PASSWORD, EMAIL_MESSAGE_FOR_RECOVERY_PASSWORD + newPass);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void changePassword(String oldPassword, String newPassword, String userId) throws ServiceException, ValidateException {
        User user;
        try {
            user = userDao
                    .get(userParamBuilderForQuery().setUserIdFilter(userId).getParam())
                    .orElseThrow(() -> new ValidateException(LOGIN_NOT_EXIST_MESSAGE));
            if (!verify(user.getPassword(), oldPassword)) {
                throw new ValidateException(WRONG_PASSWORD_MESSAGE);
            }
            validatePassword(newPassword);
            user.setPassword(encode(newPassword));
            userDao.update(user);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }

    }

    @Override
    public UserDTO addAvatar(String userId, InputStream avatar) throws ServiceException, ValidateException {
        User user;
        UserDTO result = null;
        try {
            user = userDao
                    .get(userParamBuilderForQuery().setUserIdFilter(userId).getParam())
                    .orElseThrow(() -> new ValidateException(LOGIN_NOT_EXIST_MESSAGE));
            userDao.addAvatar(Integer.parseInt(userId), avatar);
            Role role = user.getRole();
            ParamBuilderForQuery paramBuilder = userParamBuilderForQuery().setUserIdFilter(userId);
            switch (role) {
                case ADMIN -> result = convertUserToDTO(user);
                case TEACHER -> {
                    Teacher teacher = teacherDao.get(paramBuilder.getParam())
                            .orElseThrow(() -> new ValidateException(LOGIN_NOT_EXIST_MESSAGE));
                    result = convertTeacherToDTO(teacher);
                }
                case STUDENT -> {
                    Student student = studentDao.get(paramBuilder.getParam())
                            .orElseThrow(() -> new ValidateException(LOGIN_NOT_EXIST_MESSAGE));
                    checkBlocStudent(student);
                    result = convertStudentToDTO(student);
                }
            }
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return result;
    }

    private String getNewPassword() {
        final String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < 10; i++) {
            int randomIndex = random.nextInt(chars.length());
            sb.append(chars.charAt(randomIndex));
        }
        return "1aA" + sb;
    }
}