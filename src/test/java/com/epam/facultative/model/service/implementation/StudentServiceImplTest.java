package com.epam.facultative.model.service.implementation;

import com.epam.facultative.controller.app_context.AppContext;
import com.epam.facultative.model.dao.CourseDao;
import com.epam.facultative.model.dao.StudentDao;
import com.epam.facultative.model.entities.Course;
import com.epam.facultative.model.entities.Student;
import com.epam.facultative.model.dto.CourseDTO;
import com.epam.facultative.model.dto.StudentDTO;
import com.epam.facultative.model.exception.DAOException;
import com.epam.facultative.model.exception.ServiceException;
import com.epam.facultative.model.exception.ValidateException;
import com.epam.facultative.model.service.StudentService;
import com.epam.facultative.model.utils.converter.Converter;
import com.epam.facultative.model.utils.email_sender.EmailSender;
import com.epam.facultative.model.utils.hash_password.HashPassword;
import com.epam.facultative.model.utils.pdf_creator.PdfCreator;
import com.epam.facultative.model.utils.validator.Validator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.MockedStatic;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class StudentServiceImplTest {

    private final CourseDao courseDao = mock(CourseDao.class);
    private final StudentDao studentDao = mock(StudentDao.class);
    private final AppContext appContext = mock(AppContext.class);
    private final EmailSender emailSender = mock(EmailSender.class);
    private final PdfCreator pdfCreator = mock(PdfCreator.class);
    StudentService studentService = new StudentServiceImpl(courseDao, studentDao);
    private final String TEST_PARAM = "TEST";
    private static Map.Entry<Integer, List<Course>> courses;
    private static Map.Entry<Integer, List<CourseDTO>> courseDTOs;
    private static Student student;
    private static StudentDTO studentDTO;
    private static Course course;

    @BeforeAll
    static void beforeAll() {
        TestServiceUtil testServiceUtil = new TestServiceUtil();
        courses = testServiceUtil.getCourses();
        courseDTOs = testServiceUtil.getTCourseDTOS();
        student = testServiceUtil.getStudent();
        studentDTO = testServiceUtil.getStudentDTO();
        course = testServiceUtil.getCourse();
    }

    @Test
    void getCoursesByJournalSuccessful() throws DAOException, ServiceException {
        try (MockedStatic<Converter> converter = mockStatic(Converter.class)) {
            when(courseDao.getByJournal(anyString())).thenReturn(courses);
            converter.when(() -> Converter.prepareCourses(courses.getValue())).thenReturn(courseDTOs.getValue());
            assertEquals(courseDTOs, studentService.getCoursesByJournal(TEST_PARAM));
        }
    }

    @Test
    void getCoursesByJournalFailed() throws DAOException {
        try (MockedStatic<Converter> converter = mockStatic(Converter.class)) {
            doThrow(DAOException.class).when(courseDao).getByJournal(anyString());
            converter.when(() -> Converter.prepareCourses(courses.getValue())).thenReturn(courseDTOs.getValue());
            assertThrows(ServiceException.class, () -> studentService.getCoursesByJournal(TEST_PARAM));
        }
    }


    @Test
    void enroll() throws DAOException {
        doNothing().when(courseDao).insertJournal(anyInt(), anyInt());
        assertDoesNotThrow(() -> studentService.enroll(1, 1));
    }

    @Test
    void enrollWithIllegalArguments() throws DAOException {
        doThrow(DAOException.class).when(courseDao).insertJournal(anyInt(), anyInt());
        assertThrows(ServiceException.class, () -> studentService.enroll(-1, -1));
    }

    @Test
    void addStudentSuccessful() throws DAOException, ValidateException {
        try (MockedStatic<Validator> validator = mockStatic(Validator.class);
             MockedStatic<Converter> converter = mockStatic(Converter.class);
             MockedStatic<HashPassword> hashPassword = mockStatic(HashPassword.class)) {
            validator.when(() -> Validator.validateLogin(anyString())).thenAnswer(invocation -> null);
            validator.when(() -> Validator.validatePassword(anyString())).thenAnswer(invocation -> null);
            validator.when(() -> Validator.validateName(anyString())).thenAnswer(invocation -> null);
            validator.when(() -> Validator.validateEmail(anyString())).thenAnswer(invocation -> null);
            hashPassword.when(() -> HashPassword.encode(anyString())).thenReturn(TEST_PARAM);
            converter.when(() -> Converter.convertDTOToStudent(studentDTO)).thenReturn(student);
            doNothing().when(studentDao).add(student);
            assertDoesNotThrow(() -> studentService.addStudent(studentDTO));
        }
    }

    @Test
    void addTeacherWithInvalidateLogin() throws DAOException, ValidateException {
        try (MockedStatic<Validator> validator = mockStatic(Validator.class);
             MockedStatic<Converter> converter = mockStatic(Converter.class);
             MockedStatic<HashPassword> hashPassword = mockStatic(HashPassword.class)) {
            validator.when(() -> Validator.validateLogin(anyString())).thenThrow(ValidateException.class);
            validator.when(() -> Validator.validatePassword(anyString())).thenAnswer(invocation -> null);
            validator.when(() -> Validator.validateName(anyString())).thenAnswer(invocation -> null);
            validator.when(() -> Validator.validateEmail(anyString())).thenAnswer(invocation -> null);
            hashPassword.when(() -> HashPassword.encode(anyString())).thenReturn(TEST_PARAM);
            converter.when(() -> Converter.convertDTOToStudent(studentDTO)).thenReturn(student);
            doNothing().when(studentDao).add(student);
            assertThrows(ValidateException.class, () -> studentService.addStudent(studentDTO));
        }
    }

    @Test
    void addTeacherWithInvalidatePassword() throws DAOException, ValidateException {
        try (MockedStatic<Validator> validator = mockStatic(Validator.class);
             MockedStatic<Converter> converter = mockStatic(Converter.class);
             MockedStatic<HashPassword> hashPassword = mockStatic(HashPassword.class)) {
            validator.when(() -> Validator.validateLogin(anyString())).thenAnswer(invocation -> null);
            validator.when(() -> Validator.validatePassword(anyString())).thenThrow(ValidateException.class);
            validator.when(() -> Validator.validateName(anyString())).thenAnswer(invocation -> null);
            validator.when(() -> Validator.validateEmail(anyString())).thenAnswer(invocation -> null);
            hashPassword.when(() -> HashPassword.encode(anyString())).thenReturn(TEST_PARAM);
            converter.when(() -> Converter.convertDTOToStudent(studentDTO)).thenReturn(student);
            doNothing().when(studentDao).add(student);
            assertThrows(ValidateException.class, () -> studentService.addStudent(studentDTO));
        }
    }

    @Test
    void addTeacherWithInvalidateName() throws DAOException, ValidateException {
        try (MockedStatic<Validator> validator = mockStatic(Validator.class);
             MockedStatic<Converter> converter = mockStatic(Converter.class);
             MockedStatic<HashPassword> hashPassword = mockStatic(HashPassword.class)) {
            validator.when(() -> Validator.validateLogin(anyString())).thenAnswer(invocation -> null);
            validator.when(() -> Validator.validatePassword(anyString())).thenAnswer(invocation -> null);
            validator.when(() -> Validator.validateName(anyString())).thenThrow(ValidateException.class);
            validator.when(() -> Validator.validateEmail(anyString())).thenAnswer(invocation -> null);
            hashPassword.when(() -> HashPassword.encode(anyString())).thenReturn(TEST_PARAM);
            converter.when(() -> Converter.convertDTOToStudent(studentDTO)).thenReturn(student);
            doNothing().when(studentDao).add(student);
            assertThrows(ValidateException.class, () -> studentService.addStudent(studentDTO));
        }
    }

    @Test
    void addTeacherWithInvalidateEmail() throws DAOException, ValidateException {
        try (MockedStatic<Validator> validator = mockStatic(Validator.class);
             MockedStatic<Converter> converter = mockStatic(Converter.class);
             MockedStatic<HashPassword> hashPassword = mockStatic(HashPassword.class)) {
            validator.when(() -> Validator.validateLogin(anyString())).thenAnswer(invocation -> null);
            validator.when(() -> Validator.validatePassword(anyString())).thenAnswer(invocation -> null);
            validator.when(() -> Validator.validateName(anyString())).thenAnswer(invocation -> null);
            validator.when(() -> Validator.validateEmail(anyString())).thenThrow(ValidateException.class);
            hashPassword.when(() -> HashPassword.encode(anyString())).thenReturn(TEST_PARAM);
            converter.when(() -> Converter.convertDTOToStudent(studentDTO)).thenReturn(student);
            doNothing().when(studentDao).add(student);
            assertThrows(ValidateException.class, () -> studentService.addStudent(studentDTO));
        }
    }

    @Test
    void addTeacherWithNotUniqueLogin() throws DAOException, ValidateException {
        try (MockedStatic<Validator> validator = mockStatic(Validator.class);
             MockedStatic<Converter> converter = mockStatic(Converter.class);
             MockedStatic<HashPassword> hashPassword = mockStatic(HashPassword.class)) {
            validator.when(() -> Validator.validateLogin(anyString())).thenAnswer(invocation -> null);
            validator.when(() -> Validator.validatePassword(anyString())).thenAnswer(invocation -> null);
            validator.when(() -> Validator.validateName(anyString())).thenAnswer(invocation -> null);
            validator.when(() -> Validator.validateEmail(anyString())).thenAnswer(invocation -> null);
            hashPassword.when(() -> HashPassword.encode(anyString())).thenReturn(TEST_PARAM);
            converter.when(() -> Converter.convertDTOToStudent(studentDTO)).thenReturn(student);
            doThrow(ValidateException.class).when(studentDao).add(student);
            assertThrows(ValidateException.class, () -> studentService.addStudent(studentDTO));
        }
    }

    @Test
    void addTeacherFailed() throws DAOException, ValidateException {
        try (MockedStatic<Validator> validator = mockStatic(Validator.class);
             MockedStatic<Converter> converter = mockStatic(Converter.class);
             MockedStatic<HashPassword> hashPassword = mockStatic(HashPassword.class)) {
            validator.when(() -> Validator.validateLogin(anyString())).thenAnswer(invocation -> null);
            validator.when(() -> Validator.validatePassword(anyString())).thenAnswer(invocation -> null);
            validator.when(() -> Validator.validateName(anyString())).thenAnswer(invocation -> null);
            validator.when(() -> Validator.validateEmail(anyString())).thenAnswer(invocation -> null);
            hashPassword.when(() -> HashPassword.encode(anyString())).thenReturn(TEST_PARAM);
            converter.when(() -> Converter.convertDTOToStudent(studentDTO)).thenReturn(student);
            doThrow(DAOException.class).when(studentDao).add(student);
            assertThrows(ServiceException.class, () -> studentService.addStudent(studentDTO));
        }
    }

    @Test
    void getGrade() throws DAOException {
        when(studentDao.getGrade(anyInt(), anyInt())).thenReturn(5);
        assertDoesNotThrow(() -> studentService.getGrade(1, 1));
    }

    @ParameterizedTest
    @MethodSource("invalidIntValues")
    void getGradeWithIllegalArguments(int courseId, int userId) throws DAOException {
        doThrow(DAOException.class).when(studentDao).getGrade(anyInt(), anyInt());
        assertThrows(ServiceException.class, () -> studentService.getGrade(courseId, userId));
    }

    @Test
    void downloadCertificateSuccessful() throws DAOException {
        when(courseDao.get(anyString())).thenReturn(Optional.ofNullable(course));
        when(appContext.getPdfCreator()).thenReturn(pdfCreator);
        assertDoesNotThrow(() -> studentService.downloadCertificate(studentDTO, 1, 2, appContext));

    }

    @Test
    void downloadCertificateFailed() throws DAOException {
        doThrow(DAOException.class).when(courseDao).get(anyString());
        when(appContext.getPdfCreator()).thenReturn(pdfCreator);
        assertThrows(ServiceException.class, () -> studentService.downloadCertificate(studentDTO, -1, 2, appContext));
    }

    @Test
    void sendCertificateSuccessful() throws DAOException {
        when(courseDao.get(anyString())).thenReturn(Optional.ofNullable(course));
        when(appContext.getPdfCreator()).thenReturn(pdfCreator);
        when(appContext.getEmailSender()).thenReturn(emailSender);
        assertDoesNotThrow(() -> studentService.sendCertificate(studentDTO, 1, 2, appContext));

    }

    @Test
    void sendCertificateFailed() throws DAOException {
        doThrow(DAOException.class).when(courseDao).get(anyString());
        when(appContext.getPdfCreator()).thenReturn(pdfCreator);
        when(appContext.getEmailSender()).thenReturn(emailSender);
        assertThrows(ServiceException.class, () -> studentService.sendCertificate(studentDTO, -1, 2, appContext));

    }

    @Test
    void updateStudentSuccessful() throws DAOException, ValidateException {
        try (MockedStatic<Validator> validator = mockStatic(Validator.class);
             MockedStatic<Converter> converter = mockStatic(Converter.class)) {
            validator.when(() -> Validator.validateLogin(anyString())).thenAnswer(invocation -> null);
            validator.when(() -> Validator.validateName(anyString())).thenAnswer(invocation -> null);
            validator.when(() -> Validator.validateEmail(anyString())).thenAnswer(invocation -> null);
            converter.when(() -> Converter.convertDTOToStudent(studentDTO)).thenReturn(student);
            when(studentDao.get(anyString())).thenReturn(Optional.ofNullable(student));
            doNothing().when(studentDao).update(student);
            assertDoesNotThrow(() -> studentService.updateStudent(studentDTO));
        }
    }

    @Test
    void updateStudentWithInvalidLogin() throws DAOException, ValidateException {
        try (MockedStatic<Validator> validator = mockStatic(Validator.class);
             MockedStatic<Converter> converter = mockStatic(Converter.class)) {
            validator.when(() -> Validator.validateLogin(anyString())).thenThrow(ValidateException.class);
            validator.when(() -> Validator.validateName(anyString())).thenAnswer(invocation -> null);
            validator.when(() -> Validator.validateEmail(anyString())).thenAnswer(invocation -> null);
            converter.when(() -> Converter.convertDTOToStudent(studentDTO)).thenReturn(student);
            when(studentDao.get(anyString())).thenReturn(Optional.ofNullable(student));
            doNothing().when(studentDao).update(student);
            assertThrows(ValidateException.class, () -> studentService.updateStudent(studentDTO));
        }
    }

    @Test
    void updateStudentWithInvalidName() throws DAOException, ValidateException {
        try (MockedStatic<Validator> validator = mockStatic(Validator.class);
             MockedStatic<Converter> converter = mockStatic(Converter.class)) {
            validator.when(() -> Validator.validateLogin(anyString())).thenAnswer(invocation -> null);
            validator.when(() -> Validator.validateName(anyString())).thenThrow(ValidateException.class);
            validator.when(() -> Validator.validateEmail(anyString())).thenAnswer(invocation -> null);
            converter.when(() -> Converter.convertDTOToStudent(studentDTO)).thenReturn(student);
            when(studentDao.get(anyString())).thenReturn(Optional.ofNullable(student));
            doNothing().when(studentDao).update(student);
            assertThrows(ValidateException.class, () -> studentService.updateStudent(studentDTO));
        }
    }

    @Test
    void updateStudentWithInvalidEmail() throws DAOException, ValidateException {
        try (MockedStatic<Validator> validator = mockStatic(Validator.class);
             MockedStatic<Converter> converter = mockStatic(Converter.class)) {
            validator.when(() -> Validator.validateLogin(anyString())).thenAnswer(invocation -> null);
            validator.when(() -> Validator.validateName(anyString())).thenAnswer(invocation -> null);
            validator.when(() -> Validator.validateEmail(anyString())).thenThrow(ValidateException.class);
            converter.when(() -> Converter.convertDTOToStudent(studentDTO)).thenReturn(student);
            when(studentDao.get(anyString())).thenReturn(Optional.ofNullable(student));
            doNothing().when(studentDao).update(student);
            assertThrows(ValidateException.class, () -> studentService.updateStudent(studentDTO));
        }
    }

    @Test
    void updateStudentFailedGet() throws DAOException, ValidateException {
        try (MockedStatic<Validator> validator = mockStatic(Validator.class);
             MockedStatic<Converter> converter = mockStatic(Converter.class)) {
            validator.when(() -> Validator.validateLogin(anyString())).thenAnswer(invocation -> null);
            validator.when(() -> Validator.validateName(anyString())).thenAnswer(invocation -> null);
            validator.when(() -> Validator.validateEmail(anyString())).thenAnswer(invocation -> null);
            converter.when(() -> Converter.convertDTOToStudent(studentDTO)).thenReturn(student);
            doThrow(DAOException.class).when(studentDao).get(anyString());
            doNothing().when(studentDao).update(student);
            assertThrows(ServiceException.class, () -> studentService.updateStudent(studentDTO));
        }
    }

    @Test
    void updateStudentFailed() throws DAOException, ValidateException {
        try (MockedStatic<Validator> validator = mockStatic(Validator.class);
             MockedStatic<Converter> converter = mockStatic(Converter.class)) {
            validator.when(() -> Validator.validateLogin(anyString())).thenAnswer(invocation -> null);
            validator.when(() -> Validator.validateName(anyString())).thenAnswer(invocation -> null);
            validator.when(() -> Validator.validateEmail(anyString())).thenAnswer(invocation -> null);
            converter.when(() -> Converter.convertDTOToStudent(studentDTO)).thenReturn(student);
            when(studentDao.get(anyString())).thenReturn(Optional.ofNullable(student));
            doThrow(DAOException.class).when(studentDao).update(student);
            assertThrows(ServiceException.class, () -> studentService.updateStudent(studentDTO));
        }
    }

    private static Stream<Arguments> invalidIntValues() {
        return Stream.of(
                Arguments.of(0, 1),
                Arguments.of(1, 0),
                Arguments.of(0, 0),
                Arguments.of(-1, 1),
                Arguments.of(1, -1),
                Arguments.of(-1, -1)
        );
    }
}