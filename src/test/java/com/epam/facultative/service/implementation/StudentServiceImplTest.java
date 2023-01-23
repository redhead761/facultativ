package com.epam.facultative.service.implementation;

import com.epam.facultative.data_layer.daos.CourseDao;
import com.epam.facultative.data_layer.daos.StudentDao;
import com.epam.facultative.data_layer.entities.Course;
import com.epam.facultative.data_layer.entities.Status;
import com.epam.facultative.data_layer.entities.Student;
import com.epam.facultative.dto.CourseDTO;
import com.epam.facultative.dto.StudentDTO;
import com.epam.facultative.exception.DAOException;
import com.epam.facultative.exception.ServiceException;
import com.epam.facultative.exception.ValidateException;
import com.epam.facultative.service.StudentService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;

class StudentServiceImplTest {

    private final CourseDao courseDao = mock(CourseDao.class);
    private final StudentDao studentDao = mock(StudentDao.class);
    StudentService studentService = new StudentServiceImpl(courseDao, studentDao);
    private static TestServiceUtil testServiceUtil;
    private List<Course> courses;
    private List<CourseDTO> courseDTOs;
    private StudentDTO studentDTO;

    @BeforeAll
    static void beforeAll() {
        testServiceUtil = new TestServiceUtil();
    }

    @BeforeEach
    void setUp() {
        courses = testServiceUtil.getCourses();
        courseDTOs = testServiceUtil.getTCourseDTOS();
        studentDTO = testServiceUtil.getStudentDTO();
    }

//    @Test
//    void getCoursesByStudent() throws DAOException, ServiceException {
//        when(courseDao.getByStudent(isA(int.class), isA(int.class), isA(int.class))).thenReturn(courses);
//        assertIterableEquals(courseDTOs, studentService.getCoursesByStudent(1, 1, 5));
//    }

//    @ParameterizedTest
//    @MethodSource("invalidIntValues")
//    void getCoursesByStudentWithIllegalArguments(int studentId, int offset, int numberOfRows) throws DAOException {
//        doThrow(DAOException.class).when(courseDao).getByStudent(isA(int.class), isA(int.class), isA(int.class));
//        assertThrows(ServiceException.class, () -> studentService.getCoursesByJournal(studentId, offset, numberOfRows));
//    }

//    @Test
//    void getCoursesComingSoon() throws DAOException, ServiceException {
//        when(courseDao.getByStatus(isA(int.class), isA(Status.class), isA(int.class), isA(int.class))).thenReturn(courses);
//        assertIterableEquals(courseDTOs, studentService.getCoursesComingSoon(1, 1, 5));
//    }

//    @ParameterizedTest
//    @MethodSource("invalidIntValues")
//    void getCoursesComingSoonWithIllegalArguments(int studentId, int offset, int numberOfRows) throws DAOException {
//        doThrow(DAOException.class).when(courseDao).getByJournal(isA(int.class), isA(Status.class), isA(int.class), isA(int.class));
//        assertThrows(ServiceException.class, () -> studentService.getCoursesComingSoon(studentId, offset, numberOfRows));
//    }

//    @Test
//    void getCoursesInProgress() throws DAOException, ServiceException {
//        when(courseDao.getByStatus(isA(int.class), isA(Status.class), isA(int.class), isA(int.class))).thenReturn(courses);
//        assertIterableEquals(courseDTOs, studentService.getCoursesInProgress(1, 1, 5));
//    }

//    @ParameterizedTest
//    @MethodSource("invalidIntValues")
//    void getCoursesInProgressWithIllegalArguments(int studentId, int offset, int numberOfRows) throws DAOException {
//        doThrow(DAOException.class).when(courseDao).getByJournal(isA(int.class), isA(Status.class), isA(int.class), isA(int.class));
//        assertThrows(ServiceException.class, () -> studentService.getCoursesInProgress(studentId, offset, numberOfRows));
//    }

//    @Test
//    void getCoursesCompleted() throws DAOException, ServiceException {
//        when(courseDao.getByStatus(isA(int.class), isA(Status.class), isA(int.class), isA(int.class))).thenReturn(courses);
//        assertIterableEquals(courseDTOs, studentService.getCoursesCompleted(1, 1, 5));
//    }

//    @ParameterizedTest
//    @MethodSource("invalidIntValues")
//    void getCoursesCompletedWithIllegalArguments(int studentId, int offset, int numberOfRows) throws DAOException {
//        doThrow(DAOException.class).when(courseDao).getByJournal(isA(int.class), isA(Status.class), isA(int.class), isA(int.class));
//        assertThrows(ServiceException.class, () -> studentService.getCoursesCompleted(studentId, offset, numberOfRows));
//    }

    @Test
    void enroll() throws DAOException {
        doNothing().when(courseDao).insertJournal(isA(int.class), isA(int.class));
        assertDoesNotThrow(() -> studentService.enroll(1, 1));
    }

    @Test
    void enrollWithIllegalArguments() throws DAOException {
        doThrow(DAOException.class).when(courseDao).insertJournal(isA(int.class), isA(int.class));
        assertThrows(ServiceException.class, () -> studentService.enroll(-1, -1));
    }

    @Test
    void addStudent() throws DAOException, ValidateException {
        doNothing().when(studentDao).add(isA(Student.class));
        assertDoesNotThrow(() -> studentService.addStudent(studentDTO));
    }

    @Test
    void addStudentWithIllegalArgumentOfLogin() throws DAOException, ValidateException {
        doNothing().when(studentDao).add(isA(Student.class));
        studentDTO.setLogin("!@#$");
        assertThrows(ValidateException.class, () -> studentService.addStudent(studentDTO));
    }

    @Test
    void addStudentWithIllegalArgumentOfPassword() throws DAOException, ValidateException {
        doNothing().when(studentDao).add(isA(Student.class));
        studentDTO.setPassword("!@#$");
        assertThrows(ValidateException.class, () -> studentService.addStudent(studentDTO));
    }

    @Test
    void addStudentWithNotUniqueLogin() throws DAOException, ValidateException {
        doThrow(ValidateException.class).when(studentDao).add(isA(Student.class));
        assertThrows(ValidateException.class, () -> studentService.addStudent(studentDTO));
    }

    @Test
    void addStudentFailed() throws DAOException, ValidateException {
        doThrow(DAOException.class).when(studentDao).add(isA(Student.class));
        assertThrows(ServiceException.class, () -> studentService.addStudent(studentDTO));
    }

    @Test
    void getGrade() throws DAOException {
        when(studentDao.getGrade(isA(int.class), isA(int.class))).thenReturn(5);
        assertDoesNotThrow(() -> studentService.getGrade(1, 1));
    }

    @Test
    void getGradeWithIllegalArguments() throws DAOException {
        doThrow(DAOException.class).when(studentDao).getGrade(isA(int.class), isA(int.class));
        assertThrows(ServiceException.class, () -> studentService.getGrade(-1, -1));
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