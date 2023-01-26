package com.epam.facultative.service.implementation;

import com.epam.facultative.model.dao.CourseDao;
import com.epam.facultative.model.dao.StudentDao;
import com.epam.facultative.model.dao.TeacherDao;
import com.epam.facultative.model.exception.DAOException;
import com.epam.facultative.model.exception.ServiceException;
import com.epam.facultative.model.service.TeacherService;
import com.epam.facultative.model.service.implementation.TeacherServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;

class TeacherServiceImplTest {

    private final CourseDao courseDao = mock(CourseDao.class);
    private final StudentDao studentDao = mock(StudentDao.class);
    private final TeacherDao teacherDao = mock(TeacherDao.class);
    TeacherService teacherService = new TeacherServiceImpl(courseDao, studentDao, teacherDao);
    private static TestServiceUtil testServiceUtil;

    @BeforeAll
    static void beforeAll() {
        testServiceUtil = new TestServiceUtil();
    }

    @Test
    void grading() throws DAOException {
        doNothing().when(courseDao).updateJournal(isA(int.class), isA(int.class), isA(int.class));
        assertDoesNotThrow(() -> teacherService.grading(1, 1, 1));
    }

    @ParameterizedTest
    @MethodSource("invalidIntValues")
    void gradingWithIllegalArgument(int courseId, int userId, int grade) throws DAOException {
        doThrow(DAOException.class).when(courseDao).updateJournal(isA(int.class), isA(int.class), isA(int.class));
        assertThrows(ServiceException.class, () -> teacherService.grading(courseId, userId, grade));
    }

//    @Test
//    void getStudentsByCourse() throws DAOException, ServiceException {
//        List<Student> students = testServiceUtil.getStudents();
//        List<StudentDTO> studentDTOS = testServiceUtil.getStudentDTOS();
//        when(studentDao.getStudentsByCourse(isA(int.class), isA(int.class), isA(int.class))).thenReturn(students);
//        assertIterableEquals(studentDTOS, teacherService.getStudentsByCourse(1, 1, 5));
//    }

//    @ParameterizedTest
//    @MethodSource("invalidIntValues")
//    void getStudentsByCourseWithIllegalArgument(int courseId, int offset, int numberOfRows) throws DAOException {
//        doThrow(DAOException.class).when(studentDao).getStudentsByCourse(isA(int.class), isA(int.class), isA(int.class));
//        assertThrows(ServiceException.class, () -> teacherService.getStudentsByCourse(courseId, offset, numberOfRows));
//    }

//    @Test
//    void getTeacherCourses() throws DAOException, ServiceException {
//        List<Course> courses = testServiceUtil.getCourses();
//        List<CourseDTO> courseDTOS = testServiceUtil.getTCourseDTOS();
//        when(courseDao.getByTeacher(isA(int.class), isA(int.class), isA(int.class))).thenReturn(courses);
//        assertIterableEquals(courseDTOS, teacherService.getTeacherCourses(1, 1, 5));
//    }

//    @ParameterizedTest
//    @MethodSource("invalidIntValues")
//    void getTeacherCoursesWithIllegalArgument(int courseId, int offset, int numberOfRows) throws DAOException {
//        doThrow(DAOException.class).when(courseDao).getByTeacher(isA(int.class), isA(int.class), isA(int.class));
//        assertThrows(ServiceException.class, () -> teacherService.getTeacherCourses(courseId, offset, numberOfRows));
//    }

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