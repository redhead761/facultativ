package com.epam.facultative.model.service.implementation;

import com.epam.facultative.model.dao.CourseDao;
import com.epam.facultative.model.dao.StudentDao;
import com.epam.facultative.model.dao.TeacherDao;
import com.epam.facultative.model.dto.StudentDTO;
import com.epam.facultative.model.dto.TeacherDTO;
import com.epam.facultative.model.entities.Student;
import com.epam.facultative.model.entities.Teacher;
import com.epam.facultative.model.exception.DAOException;
import com.epam.facultative.model.exception.ServiceException;
import com.epam.facultative.model.exception.ValidateException;
import com.epam.facultative.model.service.TeacherService;
import com.epam.facultative.model.utils.converter.Converter;
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
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;

class TeacherServiceImplTest {

    private final CourseDao courseDao = mock(CourseDao.class);
    private final StudentDao studentDao = mock(StudentDao.class);
    private final TeacherDao teacherDao = mock(TeacherDao.class);
    TeacherService teacherService = new TeacherServiceImpl(courseDao, studentDao, teacherDao);
    private final String TEST_PARAM = "TEST";
    private static Map.Entry<Integer, List<Student>> students;
    private static Map.Entry<Integer, List<StudentDTO>> studentDTOS;
    private static Teacher teacher;
    private static TeacherDTO teacherDTO;

    @BeforeAll
    static void beforeAll() {
        TestServiceUtil testServiceUtil = new TestServiceUtil();
        students = testServiceUtil.getStudents();
        studentDTOS = testServiceUtil.getStudentDTOS();
        teacher = testServiceUtil.getTeacher();
        teacherDTO = testServiceUtil.getTeacherDTO();
    }

    @Test
    void gradingSuccessful() throws DAOException {
        doNothing().when(courseDao).updateJournal(isA(int.class), isA(int.class), isA(int.class));
        assertDoesNotThrow(() -> teacherService.grading(1, 1, 1));
    }

    @ParameterizedTest
    @MethodSource("invalidIntValues")
    void gradingWithIllegalArgument(int courseId, int userId, int grade) throws DAOException {
        doThrow(DAOException.class).when(courseDao).updateJournal(isA(int.class), isA(int.class), isA(int.class));
        assertThrows(ServiceException.class, () -> teacherService.grading(courseId, userId, grade));
    }

    @Test
    void getStudentsByCourseSuccessful() throws DAOException, ServiceException {
        try (MockedStatic<Converter> converter = mockStatic(Converter.class)) {
            when(studentDao.getStudentsByCourse(anyString())).thenReturn(students);
            converter.when(() -> Converter.prepareStudents(students.getValue())).thenReturn(studentDTOS.getValue());
            assertEquals(studentDTOS, teacherService.getStudentsByCourse(TEST_PARAM));
        }
    }

    @Test
    void getStudentsByCourseFailed() throws DAOException {
        try (MockedStatic<Converter> converter = mockStatic(Converter.class)) {
            when(studentDao.getStudentsByCourse(anyString())).thenThrow(DAOException.class);
            converter.when(() -> Converter.prepareStudents(students.getValue())).thenReturn(studentDTOS.getValue());
            assertThrows(ServiceException.class, () -> teacherService.getStudentsByCourse(TEST_PARAM));
        }
    }

    @Test
    void updateStudentSuccessful() throws DAOException, ValidateException {
        try (MockedStatic<Validator> validator = mockStatic(Validator.class);
             MockedStatic<Converter> converter = mockStatic(Converter.class)) {
            validator.when(() -> Validator.validateLogin(anyString())).thenAnswer(invocation -> null);
            validator.when(() -> Validator.validateName(anyString())).thenAnswer(invocation -> null);
            validator.when(() -> Validator.validateEmail(anyString())).thenAnswer(invocation -> null);
            converter.when(() -> Converter.convertDTOToTeacher(teacherDTO)).thenReturn(teacher);
            when(teacherDao.get(anyString())).thenReturn(Optional.ofNullable(teacher));
            doNothing().when(teacherDao).update(teacher);
            assertDoesNotThrow(() -> teacherService.updateTeacher(teacherDTO));
        }
    }

    @Test
    void updateStudentWithInvalidLogin() throws DAOException, ValidateException {
        try (MockedStatic<Validator> validator = mockStatic(Validator.class);
             MockedStatic<Converter> converter = mockStatic(Converter.class)) {
            validator.when(() -> Validator.validateLogin(anyString())).thenThrow(ValidateException.class);
            validator.when(() -> Validator.validateName(anyString())).thenAnswer(invocation -> null);
            validator.when(() -> Validator.validateEmail(anyString())).thenAnswer(invocation -> null);
            converter.when(() -> Converter.convertDTOToTeacher(teacherDTO)).thenReturn(teacher);
            when(teacherDao.get(anyString())).thenReturn(Optional.ofNullable(teacher));
            doNothing().when(teacherDao).update(teacher);
            assertThrows(ValidateException.class, () -> teacherService.updateTeacher(teacherDTO));
        }
    }

    @Test
    void updateStudentWithInvalidName() throws DAOException, ValidateException {
        try (MockedStatic<Validator> validator = mockStatic(Validator.class);
             MockedStatic<Converter> converter = mockStatic(Converter.class)) {
            validator.when(() -> Validator.validateLogin(anyString())).thenAnswer(invocation -> null);
            validator.when(() -> Validator.validateName(anyString())).thenThrow(ValidateException.class);
            validator.when(() -> Validator.validateEmail(anyString())).thenAnswer(invocation -> null);
            converter.when(() -> Converter.convertDTOToTeacher(teacherDTO)).thenReturn(teacher);
            when(teacherDao.get(anyString())).thenReturn(Optional.ofNullable(teacher));
            doNothing().when(teacherDao).update(teacher);
            assertThrows(ValidateException.class, () -> teacherService.updateTeacher(teacherDTO));
        }
    }

    @Test
    void updateStudentWithInvalidEmail() throws DAOException, ValidateException {
        try (MockedStatic<Validator> validator = mockStatic(Validator.class);
             MockedStatic<Converter> converter = mockStatic(Converter.class)) {
            validator.when(() -> Validator.validateLogin(anyString())).thenAnswer(invocation -> null);
            validator.when(() -> Validator.validateName(anyString())).thenAnswer(invocation -> null);
            validator.when(() -> Validator.validateEmail(anyString())).thenThrow(ValidateException.class);
            converter.when(() -> Converter.convertDTOToTeacher(teacherDTO)).thenReturn(teacher);
            when(teacherDao.get(anyString())).thenReturn(Optional.ofNullable(teacher));
            doNothing().when(teacherDao).update(teacher);
            assertThrows(ValidateException.class, () -> teacherService.updateTeacher(teacherDTO));
        }
    }

    @Test
    void updateStudentFailedGet() throws DAOException, ValidateException {
        try (MockedStatic<Validator> validator = mockStatic(Validator.class);
             MockedStatic<Converter> converter = mockStatic(Converter.class)) {
            validator.when(() -> Validator.validateLogin(anyString())).thenAnswer(invocation -> null);
            validator.when(() -> Validator.validateName(anyString())).thenAnswer(invocation -> null);
            validator.when(() -> Validator.validateEmail(anyString())).thenAnswer(invocation -> null);
            converter.when(() -> Converter.convertDTOToTeacher(teacherDTO)).thenReturn(teacher);
            doThrow(DAOException.class).when(teacherDao).get(anyString());
            doNothing().when(teacherDao).update(teacher);
            assertThrows(ServiceException.class, () -> teacherService.updateTeacher(teacherDTO));
        }
    }

    @Test
    void updateStudentFailed() throws DAOException, ValidateException {
        try (MockedStatic<Validator> validator = mockStatic(Validator.class);
             MockedStatic<Converter> converter = mockStatic(Converter.class)) {
            validator.when(() -> Validator.validateLogin(anyString())).thenAnswer(invocation -> null);
            validator.when(() -> Validator.validateName(anyString())).thenAnswer(invocation -> null);
            validator.when(() -> Validator.validateEmail(anyString())).thenAnswer(invocation -> null);
            converter.when(() -> Converter.convertDTOToTeacher(teacherDTO)).thenReturn(teacher);
            when(teacherDao.get(anyString())).thenReturn(Optional.ofNullable(teacher));
            doThrow(DAOException.class).when(teacherDao).update(teacher);
            assertThrows(ServiceException.class, () -> teacherService.updateTeacher(teacherDTO));
        }
    }

    private static Stream<Arguments> invalidIntValues() {
        return Stream.of(
                Arguments.of(0, 1, 1),
                Arguments.of(1, 0, 1),
                Arguments.of(1, 1, 0),
                Arguments.of(0, 0, 0),
                Arguments.of(-1, 1, 1),
                Arguments.of(1, -1, 1),
                Arguments.of(1, 1, -1),
                Arguments.of(-1, -1, -1)
        );
    }
}