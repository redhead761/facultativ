package com.epam.facultative.service.implementation;

import com.epam.facultative.model.dao.*;
import com.epam.facultative.model.exception.DAOException;
import com.epam.facultative.model.exception.ServiceException;
import com.epam.facultative.model.exception.ValidateException;
import com.epam.facultative.model.entities.Course;
import com.epam.facultative.model.entities.User;
import com.epam.facultative.model.dto.CourseDTO;
import com.epam.facultative.model.dto.StudentDTO;
import com.epam.facultative.model.dto.UserDTO;
import com.epam.facultative.model.service.GeneralService;
import com.epam.facultative.model.service.implementation.GeneralServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.provider.Arguments;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;

class GeneralServiceImplTest {

    private final CourseDao courseDao = mock(CourseDao.class);
    private final UserDao userDao = mock(UserDao.class);
    private final CategoryDao categoryDao = mock(CategoryDao.class);
    private final TeacherDao teacherDao = mock(TeacherDao.class);
    private final StudentDao studentDao = mock(StudentDao.class);
    GeneralService generalService = new GeneralServiceImpl(courseDao, userDao, categoryDao, teacherDao, studentDao);
    private static TestServiceUtil testServiceUtil;

    @BeforeAll
    static void beforeAll() {
        testServiceUtil = new TestServiceUtil();
    }

    private List<Course> courses;
    private List<CourseDTO> courseDTOs;

    @BeforeEach
    void setUp() {
        courses = testServiceUtil.getCourses();
        courseDTOs = testServiceUtil.getTCourseDTOS();
    }

    @Test
    void authorizationAdmin() throws DAOException, ValidateException, ServiceException {
        User admin = testServiceUtil.getAdmin();
        UserDTO adminDTO = testServiceUtil.getAdminDTO();
        when(userDao.get(isA(String.class))).thenReturn(Optional.ofNullable(admin));
        assertEquals(adminDTO, generalService.authorization(adminDTO.getLogin(), adminDTO.getPassword()));
    }

//    @Test
//    void authorizationTeacher() throws DAOException, ValidateException, ServiceException {
//        Teacher teacher = testServiceUtil.getTeacher();
//        TeacherDTO teacherDTO = testServiceUtil.getTeacherDTO();
//        when(userDao.get(isA(String.class))).thenReturn(Optional.ofNullable(teacher));
//        when(teacherDao.getById(isA(int.class))).thenReturn(Optional.ofNullable(teacher));
//        assertEquals(teacherDTO, generalService.authorization(teacherDTO.getLogin(), teacherDTO.getPassword()));
//    }
//
//    @Test
//    void authorizationStudent() throws DAOException, ValidateException, ServiceException {
//        Student student = testServiceUtil.getStudent();
//        StudentDTO studentDTO = testServiceUtil.getStudentDTO();
//        when(userDao.get(isA(String.class))).thenReturn(Optional.ofNullable(student));
//        when(studentDao.getById(isA(int.class))).thenReturn(Optional.ofNullable(student));
//        assertEquals(studentDTO, generalService.authorization(studentDTO.getLogin(), studentDTO.getPassword()));
//    }

    @Test
    void authorizationWithWrongPassword() throws DAOException {
        User admin = testServiceUtil.getAdmin();
        StudentDTO studentDTO = testServiceUtil.getStudentDTO();
        when(userDao.get(isA(String.class))).thenReturn(Optional.ofNullable(admin));
        assertThrows(ValidateException.class, () -> generalService.authorization(studentDTO.getLogin(), studentDTO.getPassword()));
    }

    @Test
    void authorizationFailed() throws DAOException {
        doThrow(DAOException.class).when(userDao).get(isA(String.class));
        assertThrows(ServiceException.class, () -> generalService.authorization("test", "test"));
    }

//    @Test
//    void getAllCourses() throws DAOException, ServiceException {
//        when(courseDao.getAllPagination(isA(int.class), isA(int.class))).thenReturn(courses);
//        assertIterableEquals(courseDTOs, generalService.getAllCourses(1, 5));
//    }

//    @ParameterizedTest
//    @MethodSource("invalidIntValues")
//    void getAllCoursesByStudentWithIllegalArguments(int offset, int numberOfRows) throws DAOException {
//        doThrow(DAOException.class).when(courseDao).getAll(isA(int.class), isA(int.class));
//        assertThrows(ServiceException.class, () -> generalService.getAllCourses(offset, numberOfRows));
//    }

//    @Test
//    void sortCoursesByAlphabet() throws DAOException, ServiceException {
//        when(courseDao.getAllSortPagination(isA(int.class), isA(int.class), isA(String.class))).thenReturn(courses);
//        assertIterableEquals(courseDTOs, generalService.sortCoursesByAlphabet(1, 5));
//    }

//    @ParameterizedTest
//    @MethodSource("invalidIntValues")
//    void sortCoursesByAlphabetWithIllegalArguments(int offset, int numberOfRows) throws DAOException {
//        doThrow(DAOException.class).when(courseDao).getAllSortPagination(isA(int.class), isA(int.class), isA(String.class));
//        assertThrows(ServiceException.class, () -> generalService.sortCoursesByAlphabet(offset, numberOfRows));
//    }

//    @Test
//    void sortCoursesByAlphabetReverse() throws DAOException, ServiceException {
//        when(courseDao.getAllSortPagination(isA(int.class), isA(int.class), isA(String.class))).thenReturn(courses);
//        assertIterableEquals(courseDTOs, generalService.sortCoursesByAlphabetReverse(1, 5));
//    }

//    @ParameterizedTest
//    @MethodSource("invalidIntValues")
//    void sortCoursesByAlphabetReverseWithIllegalArguments(int offset, int numberOfRows) throws DAOException {
//        doThrow(DAOException.class).when(courseDao).getAllSortPagination(isA(int.class), isA(int.class), isA(String.class));
//        assertThrows(ServiceException.class, () -> generalService.sortCoursesByAlphabetReverse(offset, numberOfRows));
//    }

//    @Test
//    void sortCoursesByDuration() throws DAOException, ServiceException {
//        when(courseDao.getAllSortPagination(isA(int.class), isA(int.class), isA(String.class))).thenReturn(courses);
//        assertIterableEquals(courseDTOs, generalService.sortCoursesByDuration(1, 5));
//    }

//    @ParameterizedTest
//    @MethodSource("invalidIntValues")
//    void sortCoursesByDurationWithIllegalArguments(int offset, int numberOfRows) throws DAOException {
//        doThrow(DAOException.class).when(courseDao).getAllSortPagination(isA(int.class), isA(int.class), isA(String.class));
//        assertThrows(ServiceException.class, () -> generalService.sortCoursesByDuration(offset, numberOfRows));
//    }

//    @Test
//    void sortCoursesBuAmountOfStudents() throws DAOException, ServiceException {
//        when(courseDao.getAllSortPagination(isA(int.class), isA(int.class), isA(String.class))).thenReturn(courses);
//        assertIterableEquals(courseDTOs, generalService.sortCoursesByAmountOfStudents(1, 5));
//    }

//    @ParameterizedTest
//    @MethodSource("invalidIntValues")
//    void sortCoursesBuAmountOfStudentsWithIllegalArguments(int offset, int numberOfRows) throws DAOException {
//        doThrow(DAOException.class).when(courseDao).getAllSortPagination(isA(int.class), isA(int.class), isA(String.class));
//        assertThrows(ServiceException.class, () -> generalService.sortCoursesByAmountOfStudents(offset, numberOfRows));
//    }

//    @Test
//    void getCoursesByCategory() throws DAOException, ServiceException {
//        when(courseDao.getByCategory(isA(int.class), isA(int.class), isA(int.class))).thenReturn(courses);
//        assertIterableEquals(courseDTOs, generalService.getCoursesByCategory(1, 1, 5));
//    }

//    @ParameterizedTest
//    @MethodSource("invalidIntValuesThird")
//    void getCoursesByCategoryWithIllegalArguments(int categoryId, int offset, int numberOfRows) throws DAOException {
//        doThrow(DAOException.class).when(courseDao).getByCategory(isA(int.class), isA(int.class), isA(int.class));
//        assertThrows(ServiceException.class, () -> generalService.getCoursesByCategory(categoryId, offset, numberOfRows));
//    }

//    @Test
//    void getCoursesByTeacher() throws DAOException, ServiceException {
//        when(courseDao.getByTeacher(isA(int.class), isA(int.class), isA(int.class))).thenReturn(courses);
//        assertIterableEquals(courseDTOs, generalService.getCoursesByTeacher(1, 1, 5));
//    }

//    @ParameterizedTest
//    @MethodSource("invalidIntValuesThird")
//    void getCoursesByTeacherWithIllegalArguments(int categoryId, int offset, int numberOfRows) throws DAOException {
//        doThrow(DAOException.class).when(courseDao).getByTeacher(isA(int.class), isA(int.class), isA(int.class));
//        assertThrows(ServiceException.class, () -> generalService.getCoursesByTeacher(categoryId, offset, numberOfRows));
//    }

//    @Test
//    void getAllCategories() throws DAOException, ServiceException {
//        List<Category> categories = testServiceUtil.getCategories();
//        List<CategoryDTO> categoryDTOS = testServiceUtil.getCategoryDTOS();
//        when(categoryDao.getAll()).thenReturn(categories);
//        assertIterableEquals(categoryDTOS, generalService.getAllCategories());
//    }

//    @Test
//    void getAllCategoriesFailed() throws DAOException {
//        doThrow(DAOException.class).when(categoryDao).getAll();
//        assertThrows(ServiceException.class, () -> generalService.getAllCategories());
//    }

//    @Test
//    void getAllTeachers() throws DAOException, ServiceException {
//        List<Teacher> teachers = testServiceUtil.getTeachers();
//        List<TeacherDTO> teacherDTOS = testServiceUtil.getTeacherDTOS();
//        when(teacherDao.getAll()).thenReturn(teachers);
//        assertIterableEquals(teacherDTOS, generalService.getAllTeachers());
//    }

//    @Test
//    void getAllTeachersFailed() throws DAOException {
//        doThrow(DAOException.class).when(teacherDao).getAll();
//        assertThrows(ServiceException.class, () -> generalService.getAllTeachers());
//    }

    private static Stream<Arguments> invalidIntValues() {
        return Stream.of(
                Arguments.of(0, 1),
                Arguments.of(1, 0),
                Arguments.of(0, 0),
                Arguments.of(-1, 1),
                Arguments.of(1, -1),
                Arguments.of(-1, -1));
    }

    private static Stream<Arguments> invalidIntValuesThird() {
        return Stream.of(Arguments.of(0, 1, 1),
                Arguments.of(1, 0, 1),
                Arguments.of(1, 1, 0),
                Arguments.of(0, 0, 0),
                Arguments.of(-1, 1, 1),
                Arguments.of(1, -1, 1),
                Arguments.of(1, 1, -1),
                Arguments.of(-1, -1, -1));
    }
}