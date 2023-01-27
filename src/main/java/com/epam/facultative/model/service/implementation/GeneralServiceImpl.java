package com.epam.facultative.model.service.implementation;

import com.epam.facultative.controller.app_context.AppContext;
import com.epam.facultative.model.dao.*;
import com.epam.facultative.model.dto.CategoryDTO;
import com.epam.facultative.model.dto.CourseDTO;
import com.epam.facultative.model.dto.TeacherDTO;
import com.epam.facultative.model.dto.UserDTO;
import com.epam.facultative.model.entities.*;
import com.epam.facultative.model.exception.DAOException;
import com.epam.facultative.model.exception.ServiceException;
import com.epam.facultative.model.exception.ValidateException;
import com.epam.facultative.model.service.GeneralService;
import com.epam.facultative.model.utils.email_sender.EmailSender;
import com.epam.facultative.model.utils.pdf_creator.PdfCreator;
import com.epam.facultative.model.utils.param_builder.ParamBuilderForQuery;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.security.SecureRandom;
import java.util.List;
import java.util.Map;

import static com.epam.facultative.model.exception.ConstantsValidateMessage.*;
import static com.epam.facultative.model.utils.converter.Converter.*;
import static com.epam.facultative.model.utils.email_sender.EmailConstants.EMAIL_MESSAGE_FOR_RECOVERY_PASSWORD;
import static com.epam.facultative.model.utils.email_sender.EmailConstants.EMAIL_SUBJECT_FOR_RECOVERY_PASSWORD;
import static com.epam.facultative.model.utils.hash_password.HashPassword.encode;
import static com.epam.facultative.model.utils.hash_password.HashPassword.verify;
import static com.epam.facultative.model.utils.param_builder.ParamBuilderForQueryUtil.*;
import static com.epam.facultative.model.utils.validator.Validator.validatePassword;

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
            User user = userDao
                    .get(userParamBuilderForQuery().setUserLoginFilter(login).getParam())
                    .orElseThrow(() -> new ValidateException(LOGIN_NOT_EXIST_MESSAGE));
            verifyPassword(user.getPassword(), password);
            return getLoggedUser(user);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Map.Entry<Integer, List<CourseDTO>> getCourses(String param) throws ServiceException {
        try {
            Map.Entry<Integer, List<Course>> coursesWithRows = courseDao.getAll(param);
            List<CourseDTO> courses = prepareCourses(coursesWithRows.getValue());
            return Map.entry(coursesWithRows.getKey(), courses);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Map.Entry<Integer, List<CategoryDTO>> getCategories(String param) throws ServiceException {
        try {
            Map.Entry<Integer, List<Category>> categoriesWithRows = categoryDao.getAll(param);
            List<CategoryDTO> categoryDTOS = prepareCategories(categoriesWithRows.getValue());
            return Map.entry(categoriesWithRows.getKey(), categoryDTOS);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Map.Entry<Integer, List<TeacherDTO>> getTeachers(String param) throws ServiceException {
        try {
            Map.Entry<Integer, List<Teacher>> teachersWithRows = teacherDao.getAll(param);
            List<TeacherDTO> teacherDTOS = prepareTeachers(teachersWithRows.getValue());
            return Map.entry(teachersWithRows.getKey(), teacherDTOS);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public ByteArrayOutputStream downloadAllCoursesInPdf(String locale, AppContext appContext) throws ServiceException {
        Map.Entry<Integer, List<CourseDTO>> coursesWithRows = getCourses(courseParamBuilderForQuery().setLimits("1", String.valueOf(Integer.MAX_VALUE)).getParam());
        List<CourseDTO> courses = coursesWithRows.getValue();
        PdfCreator pdfCreator = appContext.getPdfCreator();
        return pdfCreator.createCoursesPdf(courses, locale);
    }

    @Override
    public void recoveryPassword(String email, AppContext appContext) throws ServiceException, ValidateException {
        try {
            User user = userDao.get(userParamBuilderForQuery().setUserEmailFilter(email).getParam())
                    .orElseThrow(() -> new ValidateException(LOGIN_NOT_EXIST_MESSAGE));
            String newPass = getNewPassword();
            user.setPassword(newPass);
            EmailSender emailSender = appContext.getEmailSender();
            emailSender.send(email, EMAIL_SUBJECT_FOR_RECOVERY_PASSWORD, EMAIL_MESSAGE_FOR_RECOVERY_PASSWORD + newPass);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void changePassword(String oldPassword, String newPassword, int userId) throws ServiceException, ValidateException {
        try {
            User user = userDao
                    .get(userParamBuilderForQuery().setUserIdFilter(String.valueOf(userId)).getParam())
                    .orElseThrow(() -> new ValidateException(LOGIN_NOT_EXIST_MESSAGE));
            verifyPassword(user.getPassword(), oldPassword);
            validatePassword(newPassword);
            user.setPassword(encode(newPassword));
            userDao.update(user);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public UserDTO addAvatar(int userId, InputStream avatar) throws ServiceException, ValidateException {
        try {
            User user = userDao
                    .get(userParamBuilderForQuery().setUserIdFilter(String.valueOf(userId)).getParam())
                    .orElseThrow(() -> new ValidateException(LOGIN_NOT_EXIST_MESSAGE));
            userDao.addAvatar(userId, avatar);
            return getLoggedUser(user);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    private void verifyPassword(String userPassword, String inputPassword) throws ValidateException {
        if (!verify(userPassword, inputPassword)) throw new ValidateException(WRONG_PASSWORD_MESSAGE);
    }

    private UserDTO getLoggedUser(User user) throws DAOException, ValidateException {
        UserDTO userDTO = null;
        Role role = user.getRole();
        ParamBuilderForQuery paramBuilder = userParamBuilderForQuery().setUserIdFilter(String.valueOf(user.getId()));
        switch (role) {
            case ADMIN -> userDTO = convertUserToDTO(user);
            case TEACHER -> {
                Teacher teacher = teacherDao.get(paramBuilder.getParam())
                        .orElseThrow(() -> new ValidateException(LOGIN_NOT_EXIST_MESSAGE));
                userDTO = convertTeacherToDTO(teacher);
            }
            case STUDENT -> {
                Student student = studentDao.get(paramBuilder.getParam())
                        .orElseThrow(() -> new ValidateException(LOGIN_NOT_EXIST_MESSAGE));
                checkBlocStudent(student);
                userDTO = convertStudentToDTO(student);
            }
        }
        return userDTO;
    }

    private void checkBlocStudent(Student student) throws ValidateException {
        if (student.isBlock()) throw new ValidateException(STUDENT_BLOCKED_MESSAGE);
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