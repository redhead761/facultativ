package com.epam.facultative.service.implementation;

import com.epam.facultative.data_layer.daos.CourseDao;
import com.epam.facultative.data_layer.daos.StudentDao;
import com.epam.facultative.data_layer.entities.*;
import com.epam.facultative.dto.CategoryDTO;
import com.epam.facultative.dto.CourseDTO;
import com.epam.facultative.dto.StudentDTO;
import com.epam.facultative.dto.TeacherDTO;
import com.epam.facultative.exception.DAOException;
import com.epam.facultative.exception.ServiceException;
import com.epam.facultative.service.TeacherService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;

class TeacherServiceImplTest {

    private final CourseDao courseDao = mock(CourseDao.class);
    private final StudentDao studentDao = mock(StudentDao.class);
    TeacherService teacherService = new TeacherServiceImpl(courseDao, studentDao);
    private CourseDTO courseDTO;
    private CategoryDTO categoryDTO;
    private TeacherDTO teacherDTO;
    private StudentDTO studentDTO;
    private Course course;
    private Category category;
    private Teacher teacher;
    private Student student;

    @BeforeEach
    void init() {
        categoryDTO = CategoryDTO.builder()
                .id(0)
                .title("Test title")
                .description("Test desc")
                .build();

        teacherDTO = TeacherDTO.builder()
                .id(0)
                .login("testlogin")
                .password("Test1234")
                .name("Testname")
                .surname("Testsurname")
                .email("test@fa.ve")
                .role(Role.TEACHER)
                .degree("Prof")
                .build();

        studentDTO = StudentDTO.builder()
                .id(0)
                .login("testlogin")
                .password("Test1234")
                .name("Testname")
                .surname("Testsurname")
                .email("test@fa.ve")
                .role(Role.STUDENT)
                .grade(1)
                .courseNumber(1)
                .block(false)
                .build();

        courseDTO = CourseDTO.builder()
                .id(0)
                .title("Test course")
                .duration(100)
                .startDate(LocalDate.parse("2013-01-08"))
                .status(Status.COMING_SOON)
                .teacher(teacherDTO)
                .category(categoryDTO)
                .description("Test desc")
                .build();

        category = Category.builder()
                .id(0)
                .title("Test title")
                .description("Test desc")
                .build();

        teacher = Teacher.builder()
                .id(0)
                .login("testlogin")
                .password("Test1234")
                .name("Testname")
                .surname("Testsurname")
                .email("test@fa.ve")
                .role(Role.TEACHER)
                .degree("Prof")
                .build();

        course = Course.builder()
                .id(0)
                .title("Test course")
                .duration(100)
                .startDate(LocalDate.parse("2013-01-08"))
                .status(Status.COMING_SOON)
                .teacher(teacher)
                .category(category)
                .description("Test desc")
                .build();

        student = Student.builder()
                .id(0)
                .login("testlogin")
                .password("Test1234")
                .name("Testname")
                .surname("Testsurname")
                .email("test@fa.ve")
                .role(Role.STUDENT)
                .grade(1)
                .courseNumber(1)
                .block(false)
                .build();
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

    @Test
    void getStudentsByCourse() throws DAOException, ServiceException {
        List<Student> students = new ArrayList<>();
        students.add(student);
        List<StudentDTO> studentDTOS = new ArrayList<>();
        studentDTO.setPassword(null);
        studentDTOS.add(studentDTO);
        when(studentDao.getStudentsByCourse(isA(int.class), isA(int.class), isA(int.class))).thenReturn(students);
        assertIterableEquals(studentDTOS, teacherService.getStudentsByCourse(1, 1, 5));
    }

    @ParameterizedTest
    @MethodSource("invalidIntValues")
    void getStudentsByCourseWithIllegalArgument(int courseId, int offset, int numberOfRows) throws DAOException {
        doThrow(DAOException.class).when(studentDao).getStudentsByCourse(isA(int.class), isA(int.class), isA(int.class));
        assertThrows(ServiceException.class, () -> teacherService.getStudentsByCourse(courseId, offset, numberOfRows));
    }

    @Test
    void getTeacherCourses() throws DAOException, ServiceException {
        List<Course> courses = new ArrayList<>();
        courses.add(course);
        List<CourseDTO> courseDTOS = new ArrayList<>();
        courseDTO.getTeacher().setPassword(null);
        courseDTOS.add(courseDTO);
        when(courseDao.getByTeacher(isA(int.class), isA(int.class), isA(int.class))).thenReturn(courses);
        assertIterableEquals(courseDTOS, teacherService.getTeacherCourses(1, 1, 5));
    }

    @ParameterizedTest
    @MethodSource("invalidIntValues")
    void getTeacherCoursesWithIllegalArgument(int courseId, int offset, int numberOfRows) throws DAOException {
        doThrow(DAOException.class).when(courseDao).getByTeacher(isA(int.class), isA(int.class), isA(int.class));
        assertThrows(ServiceException.class, () -> teacherService.getTeacherCourses(courseId, offset, numberOfRows));
    }

    @Test
    void getNoOfRecordsCourses() {
        assertDoesNotThrow(() -> teacherService.getNoOfRecordsCourses());
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