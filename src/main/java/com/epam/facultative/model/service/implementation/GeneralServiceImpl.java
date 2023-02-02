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
import lombok.RequiredArgsConstructor;

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

/**
 * Implementation of GeneralService interface.
 *
 * @author Oleksandr Panchenko
 * @version 1.0
 */
@RequiredArgsConstructor
public class GeneralServiceImpl implements GeneralService {
    private final CourseDao courseDao;
    private final UserDao userDao;
    private final CategoryDao categoryDao;
    private final TeacherDao teacherDao;
    private final StudentDao studentDao;

    /**
     * Gets login and password from action. Verify password. Convert entity to DTO. Returned user from role.
     *
     * @param login,password - used to find and verify
     * @return UserDTO - DTO from role
     * @throws ServiceException  - may wrap DAOException or be thrown by another mistakes
     * @throws ValidateException - occurs in case of non-verify password or not found user
     */
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

    /**
     * Gets parameter from action and calls DAO to get relevant entities and count rows. Convert entity to DTO.
     *
     * @param param - parameters to get
     * @return {@code Map.Entry<Integer, List < CourseDTO>>} - return relevant DTO and count rows
     * @throws ServiceException - may wrap DAOException or be thrown by another mistakes
     */
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

    /**
     * Gets parameter from action and calls DAO to get relevant entities and count rows. Convert entity to DTO.
     *
     * @param param - parameters to get
     * @return {@code Map.Entry<Integer, List < CategoryDTO>>} - return relevant DTO and count rows
     * @throws ServiceException - may wrap DAOException or be thrown by another mistakes
     */
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

    /**
     * Gets parameter from action and calls DAO to get relevant entities and count rows. Convert entity to DTO.
     *
     * @param param - parameters to get
     * @return {@code Map.Entry<Integer, List < TeacherDTO>>} - return relevant DTO and count rows
     * @throws ServiceException - may wrap DAOException or be thrown by another mistakes
     */
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

    /**
     * Gets locale and AppContext from action and calls DAO to get relevant entities and count rows.
     * Calls PdfCreator to create table with courses. Convert entity to DTO.
     *
     * @param locale     - used to table localization
     * @param appContext - used to call PdfCreator
     * @return ByteArrayOutputStream - return table
     * @throws ServiceException - may wrap DAOException or be thrown by another mistakes
     */
    @Override
    public ByteArrayOutputStream downloadAllCoursesInPdf(String locale, AppContext appContext) throws ServiceException {
        Map.Entry<Integer, List<CourseDTO>> coursesWithRows = getCourses(courseParamBuilderForQuery().setLimits("1", String.valueOf(Integer.MAX_VALUE)).getParam());
        List<CourseDTO> courses = coursesWithRows.getValue();
        PdfCreator pdfCreator = appContext.getPdfCreator();
        return pdfCreator.createCoursesPdf(courses, locale);
    }

    /**
     * Gets email and AppContext from action and calls DAO to get relevant entity and count rows. Generate new password.
     * Sent email with new password.
     *
     * @param email      - use to send email
     * @param appContext - use to call EmailSender
     * @throws ServiceException  - may wrap DAOException or be thrown by another mistakes
     * @throws ValidateException - occurs in case of non-validation of data
     */
    @Override
    public void recoveryPassword(String email, AppContext appContext) throws ServiceException, ValidateException {
        try {
            User user = userDao.get(userParamBuilderForQuery().setUserEmailFilter(email).getParam())
                    .orElseThrow(() -> new ValidateException(LOGIN_NOT_EXIST_MESSAGE));
            String newPass = getNewPassword();
            user.setPassword(encode(newPass));
            userDao.update(user);
            EmailSender emailSender = appContext.getEmailSender();
            emailSender.send(email, EMAIL_SUBJECT_FOR_RECOVERY_PASSWORD, EMAIL_MESSAGE_FOR_RECOVERY_PASSWORD + newPass);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Updates User's password. Validate passwords. Encode new password
     *
     * @param userId      - id to find user by
     * @param oldPassword - old password
     * @param newPassword - new password
     * @throws ServiceException  - may wrap DAOException or be thrown by another mistakes
     * @throws ValidateException - occurs in case of non-validation of data
     */
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

    /**
     * Gets userId and avatar from action and calls DAO to add relevant entity. Add avatar to user. Return DTO with new avatar.
     *
     * @param userId - use to find
     * @param avatar - new image
     * @throws ServiceException  - may wrap DAOException or be thrown by another mistakes
     * @throws ValidateException - occurs in case of non-validation of data
     */
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

    /**
     * Verify input password
     */
    private void verifyPassword(String userPassword, String inputPassword) throws ValidateException {
        if (!verify(userPassword, inputPassword)) throw new ValidateException(WRONG_PASSWORD_MESSAGE);
    }

    /**
     * Check user role and return relevant DTO
     */
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

    /**
     * Check block status student
     */
    private void checkBlocStudent(Student student) throws ValidateException {
        if (student.isBlock()) throw new ValidateException(STUDENT_BLOCKED_MESSAGE);
    }

    /**
     * Obtains new password for User
     *
     * @return generated password
     */
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