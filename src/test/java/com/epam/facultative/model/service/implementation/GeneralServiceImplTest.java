package com.epam.facultative.model.service.implementation;

import com.epam.facultative.controller.app_context.AppContext;
import com.epam.facultative.model.dao.*;
import com.epam.facultative.model.dto.*;
import com.epam.facultative.model.entities.*;
import com.epam.facultative.model.exception.DAOException;
import com.epam.facultative.model.exception.ServiceException;
import com.epam.facultative.model.exception.ValidateException;
import com.epam.facultative.model.service.GeneralService;
import com.epam.facultative.model.utils.converter.Converter;
import com.epam.facultative.model.utils.email_sender.EmailSender;
import com.epam.facultative.model.utils.hash_password.HashPassword;
import com.epam.facultative.model.utils.pdf_creator.PdfCreator;
import com.epam.facultative.model.utils.validator.Validator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GeneralServiceImplTest {

    private final CourseDao courseDao = mock(CourseDao.class);
    private final UserDao userDao = mock(UserDao.class);
    private final CategoryDao categoryDao = mock(CategoryDao.class);
    private final TeacherDao teacherDao = mock(TeacherDao.class);
    private final StudentDao studentDao = mock(StudentDao.class);
    private final AppContext appContext = mock(AppContext.class);
    private final PdfCreator pdfCreator = mock(PdfCreator.class);
    private final EmailSender emailSender = mock(EmailSender.class);
    private final InputStream AVATAR = mock(InputStream.class);
    GeneralService generalService = new GeneralServiceImpl(courseDao, userDao, categoryDao, teacherDao, studentDao);
    private final String TEST_PARAM = "PARAM";
    private static User admin;
    private static UserDTO adminDTO;
    private static Teacher teacher;
    private static TeacherDTO teacherDTO;
    private static Student student;
    private static StudentDTO studentDTO;
    private static Map.Entry<Integer, List<Course>> courses;
    private static Map.Entry<Integer, List<CourseDTO>> coursesDTO;
    private static Map.Entry<Integer, List<Category>> categories;
    private static Map.Entry<Integer, List<CategoryDTO>> categoriesDTO;
    private static Map.Entry<Integer, List<Teacher>> teachers;
    private static Map.Entry<Integer, List<TeacherDTO>> teachersDTO;

    @BeforeAll
    static void beforeAll() {
        TestServiceUtil testServiceUtil = new TestServiceUtil();
        admin = testServiceUtil.getAdmin();
        adminDTO = testServiceUtil.getAdminDTO();
        teacher = testServiceUtil.getTeacher();
        teacherDTO = testServiceUtil.getTeacherDTO();
        student = testServiceUtil.getStudent();
        studentDTO = testServiceUtil.getStudentDTO();
        courses = testServiceUtil.getCourses();
        coursesDTO = testServiceUtil.getTCourseDTOS();
        categories = testServiceUtil.getCategories();
        categoriesDTO = testServiceUtil.getCategoryDTOS();
        teachers = testServiceUtil.getTeachers();
        teachersDTO = testServiceUtil.getTeacherDTOS();
    }

    @Test
    void authorizationAdminSuccessful() throws DAOException, ValidateException, ServiceException {
        try (MockedStatic<HashPassword> hashPassword = mockStatic(HashPassword.class);
             MockedStatic<Converter> converter = mockStatic(Converter.class)) {
            when(userDao.get(anyString())).thenReturn(Optional.ofNullable(admin));
            hashPassword.when(() -> HashPassword.verify(anyString(), anyString())).thenAnswer(invocation -> true);
            converter.when(() -> Converter.convertUserToDTO(admin)).thenReturn(adminDTO);
            assertEquals(adminDTO, generalService.authorization(adminDTO.getLogin(), adminDTO.getPassword()));
        }
    }

    @Test
    void authorizationTeacherSuccessful() throws DAOException, ValidateException, ServiceException {
        try (MockedStatic<HashPassword> hashPassword = mockStatic(HashPassword.class);
             MockedStatic<Converter> converter = mockStatic(Converter.class)) {
            when(userDao.get(anyString())).thenReturn(Optional.ofNullable(teacher));
            hashPassword.when(() -> HashPassword.verify(anyString(), anyString())).thenAnswer(invocation -> true);
            when(teacherDao.get(anyString())).thenReturn(Optional.ofNullable(teacher));
            converter.when(() -> Converter.convertTeacherToDTO(teacher)).thenReturn(teacherDTO);
            assertEquals(teacherDTO, generalService.authorization(teacherDTO.getLogin(), teacherDTO.getPassword()));
        }
    }

    @Test
    void authorizationStudentSuccessful() throws DAOException, ValidateException, ServiceException {
        try (MockedStatic<HashPassword> hashPassword = mockStatic(HashPassword.class);
             MockedStatic<Converter> converter = mockStatic(Converter.class)) {
            when(userDao.get(anyString())).thenReturn(Optional.ofNullable(student));
            hashPassword.when(() -> HashPassword.verify(anyString(), anyString())).thenAnswer(invocation -> true);
            when(studentDao.get(anyString())).thenReturn(Optional.ofNullable(student));
            converter.when(() -> Converter.convertStudentToDTO(student)).thenReturn(studentDTO);
            assertEquals(studentDTO, generalService.authorization(studentDTO.getLogin(), studentDTO.getPassword()));
        }
    }

    @Test
    void authorizationWithWrongPassword() throws DAOException {
        try (MockedStatic<HashPassword> hashPassword = mockStatic(HashPassword.class)) {
            when(userDao.get(anyString())).thenReturn(Optional.ofNullable(admin));
            hashPassword.when(() -> HashPassword.verify(anyString(), anyString())).thenAnswer(invocation -> false);
            assertThrows(ValidateException.class, () -> generalService.authorization(adminDTO.getLogin(), adminDTO.getPassword()));
        }
    }

    @Test
    void authorizationFailed() throws DAOException {
        doThrow(DAOException.class).when(userDao).get(anyString());
        assertThrows(ServiceException.class, () -> generalService.authorization(TEST_PARAM, TEST_PARAM));
    }

    @Test
    void getCoursesSuccessful() throws DAOException, ServiceException {
        try (MockedStatic<Converter> converter = mockStatic(Converter.class)) {
            when(courseDao.getAll(anyString())).thenReturn(courses);
            converter.when(() -> Converter.prepareCourses(courses.getValue())).thenReturn(coursesDTO.getValue());
            assertEquals(coursesDTO, generalService.getCourses(TEST_PARAM));
        }
    }

    @Test
    void getCoursesFailed() throws DAOException {
        doThrow(DAOException.class).when(courseDao).getAll(anyString());
        assertThrows(ServiceException.class, () -> generalService.getCourses(TEST_PARAM));
    }

    @Test
    void getCategoriesSuccessful() throws DAOException, ServiceException {
        try (MockedStatic<Converter> converter = mockStatic(Converter.class)) {
            when(categoryDao.getAll(anyString())).thenReturn(categories);
            converter.when(() -> Converter.prepareCategories(categories.getValue())).thenReturn(categoriesDTO.getValue());
            assertEquals(categoriesDTO, generalService.getCategories(TEST_PARAM));
        }
    }

    @Test
    void getCategoriesFailed() throws DAOException {
        doThrow(DAOException.class).when(categoryDao).getAll(anyString());
        assertThrows(ServiceException.class, () -> generalService.getCategories(TEST_PARAM));
    }

    @Test
    void getTeachersSuccessful() throws DAOException, ServiceException {
        try (MockedStatic<Converter> converter = mockStatic(Converter.class)) {
            when(teacherDao.getAll(anyString())).thenReturn(teachers);
            converter.when(() -> Converter.prepareTeachers(teachers.getValue())).thenReturn(teachersDTO.getValue());
            assertEquals(teachersDTO, generalService.getTeachers(TEST_PARAM));
        }
    }

    @Test
    void getAllTeachersFailed() throws DAOException {
        doThrow(DAOException.class).when(teacherDao).getAll(anyString());
        assertThrows(ServiceException.class, () -> generalService.getTeachers(TEST_PARAM));
    }

    @Test
    void downloadAllCoursesInPdfSuccessful() throws DAOException {
        try (MockedStatic<Converter> converter = mockStatic(Converter.class)) {
            when(courseDao.getAll(anyString())).thenReturn(courses);
            converter.when(() -> Converter.prepareCourses(courses.getValue())).thenReturn(coursesDTO.getValue());
            when(appContext.getPdfCreator()).thenReturn(pdfCreator);
            assertDoesNotThrow(() -> generalService.downloadAllCoursesInPdf(TEST_PARAM, appContext));
        }
    }

    @Test
    void recoveryPasswordSuccessful() throws DAOException {
        when(userDao.get(anyString())).thenReturn(Optional.ofNullable(admin));
        when(appContext.getEmailSender()).thenReturn(emailSender);
        assertDoesNotThrow(() -> generalService.recoveryPassword(TEST_PARAM, appContext));
    }

    @Test
    void recoveryPasswordFailed() throws DAOException {
        doThrow(DAOException.class).when(userDao).get(anyString());
        when(appContext.getEmailSender()).thenReturn(emailSender);
        assertThrows(ServiceException.class, () -> generalService.recoveryPassword(TEST_PARAM, appContext));
    }

    @Test
    void changePasswordSuccessful() throws DAOException, ValidateException {
        try (MockedStatic<HashPassword> hashPassword = mockStatic(HashPassword.class);
             MockedStatic<Validator> validator = mockStatic(Validator.class)) {
            when(userDao.get(anyString())).thenReturn(Optional.ofNullable(admin));
            hashPassword.when(() -> HashPassword.verify(anyString(), anyString())).thenAnswer(invocation -> true);
            validator.when(() -> Validator.validatePassword(anyString())).thenAnswer(invocation -> null);
            doNothing().when(userDao).update(admin);
            assertDoesNotThrow(() -> generalService.changePassword(TEST_PARAM, TEST_PARAM, 1));
        }
    }

    @Test
    void changePasswordWithInvalidOldPassword() throws DAOException, ValidateException {
        try (MockedStatic<HashPassword> hashPassword = mockStatic(HashPassword.class);
             MockedStatic<Validator> validator = mockStatic(Validator.class)) {
            when(userDao.get(anyString())).thenReturn(Optional.ofNullable(admin));
            hashPassword.when(() -> HashPassword.verify(anyString(), anyString())).thenAnswer(invocation -> false);
            validator.when(() -> Validator.validatePassword(anyString())).thenAnswer(invocation -> null);
            doNothing().when(userDao).update(admin);
            assertThrows(ValidateException.class, () -> generalService.changePassword(TEST_PARAM, TEST_PARAM, 1));
        }
    }

    @Test
    void changePasswordWithInvalidNewPassword() throws DAOException, ValidateException {
        try (MockedStatic<HashPassword> hashPassword = mockStatic(HashPassword.class);
             MockedStatic<Validator> validator = mockStatic(Validator.class)) {
            when(userDao.get(anyString())).thenReturn(Optional.ofNullable(admin));
            hashPassword.when(() -> HashPassword.verify(anyString(), anyString())).thenAnswer(invocation -> true);
            validator.when(() -> Validator.validatePassword(anyString())).thenThrow(ValidateException.class);
            doNothing().when(userDao).update(admin);
            assertThrows(ValidateException.class, () -> generalService.changePassword(TEST_PARAM, TEST_PARAM, 1));
        }
    }

    @Test
    void changePasswordWithInvalidFailed() throws DAOException, ValidateException {
        try (MockedStatic<HashPassword> hashPassword = mockStatic(HashPassword.class);
             MockedStatic<Validator> validator = mockStatic(Validator.class)) {
            doThrow(DAOException.class).when(userDao).get(anyString());
            hashPassword.when(() -> HashPassword.verify(anyString(), anyString())).thenAnswer(invocation -> true);
            validator.when(() -> Validator.validatePassword(anyString())).thenAnswer(invocation -> null);
            doNothing().when(userDao).update(admin);
            assertThrows(ServiceException.class, () -> generalService.changePassword(TEST_PARAM, TEST_PARAM, -1));
        }
    }

    @Test
    void addAvatarSuccessful() throws DAOException {
        when(userDao.get(anyString())).thenReturn(Optional.ofNullable(admin));
        doNothing().when(userDao).addAvatar(anyInt(), isA(InputStream.class));
        assertDoesNotThrow(() -> generalService.addAvatar(1, AVATAR));
    }

    @Test
    void addAvatarFailed() throws DAOException {
        when(userDao.get(anyString())).thenReturn(Optional.ofNullable(admin));
        doThrow(DAOException.class).when(userDao).addAvatar(anyInt(), isA(InputStream.class));
        assertThrows(ServiceException.class, () -> generalService.addAvatar(1, AVATAR));
    }
}