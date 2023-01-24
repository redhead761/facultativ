package com.epam.facultative.service.implementation;

import com.epam.facultative.data_layer.daos.*;
import com.epam.facultative.data_layer.entities.*;
import com.epam.facultative.dto.*;
import com.epam.facultative.exception.DAOException;
import com.epam.facultative.exception.ServiceException;
import com.epam.facultative.exception.ValidateException;
import com.epam.facultative.service.AdminService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;


class AdminServiceImplTest {
    private final CourseDao courseDao = mock(CourseDao.class);
    private final CategoryDao categoryDao = mock(CategoryDao.class);
    private final StudentDao studentDao = mock(StudentDao.class);
    private final TeacherDao teacherDao = mock(TeacherDao.class);
    AdminService adminService = new AdminServiceImpl(courseDao, categoryDao, studentDao, teacherDao);
    private static TestServiceUtil testServiceUtil;
    private CourseDTO courseDTO;
    private CategoryDTO categoryDTO;
    private TeacherDTO teacherDTO;

    @BeforeAll
    static void beforeAll() {
        testServiceUtil = new TestServiceUtil();
    }

    @BeforeEach
    void setUp() {
        courseDTO = testServiceUtil.getCourseDTO();
        categoryDTO = testServiceUtil.getCategoryDTO();
        teacherDTO = testServiceUtil.getTeacherDTO();
    }

    @Test
    void addCourse() throws DAOException, ValidateException {
        doNothing().when(courseDao).add(isA(Course.class));
        assertDoesNotThrow(() -> adminService.addCourse(courseDTO));
    }

    @Test
    void addCourseWithInvalidateParam() throws DAOException, ValidateException {
        doNothing().when(courseDao).add(isA(Course.class));
        courseDTO.setDuration(-1);
        assertThrows(ValidateException.class, () -> adminService.addCourse(courseDTO));
    }

    @Test
    void addCourseWithNotUniqueTitle() throws DAOException, ValidateException {
        doThrow(ValidateException.class).when(courseDao).add(isA(Course.class));
        assertThrows(ValidateException.class, () -> adminService.addCourse(courseDTO));
    }

    @Test
    void addCourseFailed() throws DAOException, ValidateException {
        doThrow(DAOException.class).when(courseDao).add(isA(Course.class));
        assertThrows(ServiceException.class, () -> adminService.addCourse(courseDTO));
    }

    @Test
    void updateCourse() throws DAOException, ValidateException {
        doNothing().when(courseDao).update(isA(Course.class));
        assertDoesNotThrow(() -> adminService.updateCourse(courseDTO));
    }

    @Test
    void updateCourseWithInvalidateParam() throws DAOException, ValidateException {
        doNothing().when(courseDao).update(isA(Course.class));
        courseDTO.setDuration(-1);
        assertThrows(ValidateException.class, () -> adminService.updateCourse(courseDTO));
    }

    @Test
    void updateCourseWithNotUniqueTitle() throws DAOException, ValidateException {
        doThrow(ValidateException.class).when(courseDao).update(isA(Course.class));
        assertThrows(ValidateException.class, () -> adminService.updateCourse(courseDTO));
    }

    @Test
    void updateCourseFailed() throws DAOException, ValidateException {
        doThrow(DAOException.class).when(courseDao).update(isA(Course.class));
        assertThrows(ServiceException.class, () -> adminService.updateCourse(courseDTO));
    }

//    @Test
//    void deleteCourse() throws DAOException {
//        doNothing().when(courseDao).delete(isA(int.class));
//        assertDoesNotThrow(() -> adminService.deleteCourse(1));
//    }

//    @Test
//    void deleteCourseWithIllegalArgument() throws DAOException {
//        doThrow(DAOException.class).when(courseDao).delete(isA(int.class));
//        assertThrows(ServiceException.class, () -> adminService.deleteCourse(-1));
//    }

    @Test
    void addCategory() throws DAOException, ValidateException {
        doNothing().when(categoryDao).add(isA(Category.class));
        assertDoesNotThrow(() -> adminService.addCategory(categoryDTO));
    }

    @Test
    void addCategoryWithInvalidateParam() throws DAOException, ValidateException {
        doThrow(ValidateException.class).when(categoryDao).add(isA(Category.class));
        categoryDTO.setTitle("!@#$%^&*())");
        assertThrows(ValidateException.class, () -> adminService.addCategory(categoryDTO));
    }

    @Test
    void addCategoryWithNotUniqueTitle() throws DAOException, ValidateException {
        doThrow(ValidateException.class).when(categoryDao).add(isA(Category.class));
        assertThrows(ValidateException.class, () -> adminService.addCategory(categoryDTO));
    }

    @Test
    void addCategoryFailed() throws DAOException, ValidateException {
        doThrow(DAOException.class).when(categoryDao).add(isA(Category.class));
        assertThrows(ServiceException.class, () -> adminService.addCategory(categoryDTO));
    }

    @Test
    void updateCategory() throws DAOException, ValidateException {
        doNothing().when(categoryDao).update(isA(Category.class));
        assertDoesNotThrow(() -> adminService.updateCategory(categoryDTO));
    }

    @Test
    void updateCategoryWithInvalidateParam() throws DAOException, ValidateException {
        doThrow(ValidateException.class).when(categoryDao).update(isA(Category.class));
        categoryDTO.setTitle("!@#$%^&*())");
        assertThrows(ValidateException.class, () -> adminService.updateCategory(categoryDTO));
    }

    @Test
    void updateCategoryWithNotUniqueTitle() throws DAOException, ValidateException {
        doThrow(ValidateException.class).when(categoryDao).update(isA(Category.class));
        assertThrows(ValidateException.class, () -> adminService.updateCategory(categoryDTO));
    }

    @Test
    void updateCategoryFailed() throws DAOException, ValidateException {
        doThrow(DAOException.class).when(categoryDao).update(isA(Category.class));
        assertThrows(ServiceException.class, () -> adminService.updateCategory(categoryDTO));
    }

//    @Test
//    void deleteCategory() throws DAOException {
//        doNothing().when(categoryDao).delete(isA(int.class));
//        assertDoesNotThrow(() -> adminService.deleteCategory(1));
//    }

//    @Test
//    void deleteCategoryWithIllegalArgument() throws DAOException {
//        doThrow(DAOException.class).when(categoryDao).delete(isA(int.class));
//        assertThrows(ServiceException.class, () -> adminService.deleteCategory(-1));
//    }

//    @Test
//    void getAllCategoriesPagination() throws DAOException, ServiceException {
//        List<Category> categories = testServiceUtil.getCategories();
//        List<CategoryDTO> categoryDTOS = testServiceUtil.getCategoryDTOS();
//        when(categoryDao.getAllPagination(isA(int.class), isA(int.class))).thenReturn(categories);
//        assertIterableEquals(categoryDTOS, adminService.getAllCategoriesPagination(1, 5));
//    }

//
//    @ParameterizedTest
//    @MethodSource("invalidIntValues")
//    void getAllCategoriesPaginationWithIllegalArgument(int offset, int numberOfRows) throws DAOException {
//        doThrow(DAOException.class).when(categoryDao).getAll(isA(int.class), isA(int.class));
//        assertThrows(ServiceException.class, () -> adminService.getAllCategoriesPagination(offset, numberOfRows));
//    }

    @Test
    void assigned() throws DAOException, ValidateException {
        Course course = testServiceUtil.getCourse();
        Teacher teacher = testServiceUtil.getTeacher();
        when(courseDao.getById(isA(int.class))).thenReturn(Optional.ofNullable(course));
        when(teacherDao.getById(isA(int.class))).thenReturn(Optional.ofNullable(teacher));
        doNothing().when(courseDao).update(isA(Course.class));
        assertDoesNotThrow(() -> adminService.assigned(1, 1));
    }

    @ParameterizedTest
    @MethodSource("invalidIntValues")
    void assignedWithIllegalArgument(int courseId, int teacherId) throws DAOException, ValidateException {
        Course course = testServiceUtil.getCourse();
        Teacher teacher = testServiceUtil.getTeacher();
        when(courseDao.getById(isA(int.class))).thenReturn(Optional.ofNullable(course));
        when(teacherDao.getById(isA(int.class))).thenReturn(Optional.ofNullable(teacher));
        doThrow(DAOException.class).when(courseDao).update(isA(Course.class));
        assertThrows(ServiceException.class, () -> adminService.assigned(courseId, teacherId));

    }

    @Test
    void blockStudent() throws DAOException {
        doNothing().when(studentDao).updateBlock(isA(int.class), isA(boolean.class));
        assertDoesNotThrow(() -> adminService.blockStudent(1));
    }

    @Test
    void blockStudentWithIllegalArgument() throws DAOException {
        doThrow(DAOException.class).when(studentDao).updateBlock(isA(int.class), isA(boolean.class));
        assertThrows(ServiceException.class, () -> adminService.blockStudent(-1));
    }

    @Test
    void unblockStudent() throws DAOException {
        doNothing().when(studentDao).updateBlock(isA(int.class), isA(boolean.class));
        assertDoesNotThrow(() -> adminService.unblockStudent(1));
    }

    @Test
    void unblockStudentWithIllegalArgument() throws DAOException {
        doThrow(DAOException.class).when(studentDao).updateBlock(isA(int.class), isA(boolean.class));
        assertThrows(ServiceException.class, () -> adminService.unblockStudent(-1));
    }

    @Test
    void addTeacher() throws DAOException, ValidateException {
        doNothing().when(teacherDao).add(isA(Teacher.class));
        assertDoesNotThrow(() -> adminService.addTeacher(teacherDTO));
    }

    @Test
    void addTeacherWithIllegalArgumentOfLogin() throws DAOException, ValidateException {
        doNothing().when(teacherDao).add(isA(Teacher.class));
        teacherDTO.setLogin("!@#$");
        assertThrows(ValidateException.class, () -> adminService.addTeacher(teacherDTO));
    }

    @Test
    void addTeacherWithIllegalArgumentOfPassword() throws DAOException, ValidateException {
        doNothing().when(teacherDao).add(isA(Teacher.class));
        teacherDTO.setPassword("!@#$");
        assertThrows(ValidateException.class, () -> adminService.addTeacher(teacherDTO));
    }

    @Test
    void addTeacherWithNotUniqueLogin() throws DAOException, ValidateException {
        doThrow(ValidateException.class).when(teacherDao).add(isA(Teacher.class));
        assertThrows(ValidateException.class, () -> adminService.addTeacher(teacherDTO));
    }

    @Test
    void addTeacherFailed() throws DAOException, ValidateException {
        doThrow(DAOException.class).when(teacherDao).add(isA(Teacher.class));
        assertThrows(ServiceException.class, () -> adminService.addTeacher(teacherDTO));
    }

//    @Test
//    void getAllStudentsPagination() throws DAOException, ServiceException {
//        List<Student> students = testServiceUtil.getStudents();
//        List<StudentDTO> studentDTOS = testServiceUtil.getStudentDTOS();
//        when(studentDao.getAllPagination(isA(int.class), isA(int.class))).thenReturn(students);
//        assertIterableEquals(studentDTOS, adminService.getAllStudentsPagination(1, 5));
//    }

//    @ParameterizedTest
//    @MethodSource("invalidIntValues")
//    void getAllStudentsPaginationWithIllegalArgument(int offset, int numberOfRows) throws DAOException {
//        doThrow(DAOException.class).when(studentDao).getAll(isA(int.class), isA(int.class));
//        assertThrows(ServiceException.class, () -> adminService.getAllStudentsPagination(offset, numberOfRows));
//    }

//    @Test
//    void getAllTeachersPagination() throws DAOException, ServiceException {
//        List<Teacher> teachers = testServiceUtil.getTeachers();
//        List<TeacherDTO> teacherDTOS = testServiceUtil.getTeacherDTOS();
//        when(teacherDao.getAllPagination(isA(int.class), isA(int.class))).thenReturn(teachers);
//        assertIterableEquals(teacherDTOS, adminService.getAllTeachersPagination(1, 5));
//    }

//    @ParameterizedTest
//    @MethodSource("invalidIntValues")
//    void getAllTeachersPaginationWithIllegalArgument(int offset, int numberOfRows) throws DAOException {
//        doThrow(DAOException.class).when(teacherDao).getAll(isA(int.class), isA(int.class));
//        assertThrows(ServiceException.class, () -> adminService.getAllTeachersPagination(offset, numberOfRows));
//    }

    @Test
    void getCategory() throws DAOException, ServiceException, ValidateException {
        Category category = testServiceUtil.getCategory();
        when(categoryDao.getById(isA(int.class))).thenReturn(Optional.ofNullable(category));
        assertEquals(categoryDTO, adminService.getCategory(1));
    }

    @Test
    void getCategoryWithIllegalArgument() throws DAOException {
        when(categoryDao.getById(isA(int.class))).thenReturn(Optional.empty());
        assertThrows(ValidateException.class, () -> adminService.getCategory(-1));
    }

    @Test
    void getCategoryFailed() throws DAOException {
        doThrow(DAOException.class).when(categoryDao).getById(isA(int.class));
        assertThrows(ServiceException.class, () -> adminService.getCategory(-1));
    }

    @Test
    void getCourse() throws DAOException, ValidateException, ServiceException {
        Course course = testServiceUtil.getCourse();
        when(courseDao.getById(isA(int.class))).thenReturn(Optional.ofNullable(course));
        assertEquals(courseDTO, adminService.getCourse(1));
    }

    @Test
    void getCourseWithIllegalArgument() throws DAOException {
        when(courseDao.getById(isA(int.class))).thenReturn(Optional.empty());
        assertThrows(ValidateException.class, () -> adminService.getCourse(-1));
    }

    @Test
    void getCourseFailed() throws DAOException {
        doThrow(DAOException.class).when(courseDao).getById(isA(int.class));
        assertThrows(ServiceException.class, () -> adminService.getCourse(-1));
    }

    @Test
    void getTeacher() throws DAOException, ServiceException, ValidateException {
        Teacher teacher = testServiceUtil.getTeacher();
        when(teacherDao.getById(isA(int.class))).thenReturn(Optional.ofNullable(teacher));
        assertEquals(teacherDTO, adminService.getTeacher(1));
    }

    @Test
    void getTeacherWithIllegalArgument() throws DAOException {
        when(teacherDao.getById(isA(int.class))).thenReturn(Optional.empty());
        assertThrows(ValidateException.class, () -> adminService.getTeacher(-1));
    }

    @Test
    void getTeacherFailed() throws DAOException {
        doThrow(DAOException.class).when(teacherDao).getById(isA(int.class));
        assertThrows(ServiceException.class, () -> adminService.getTeacher(-1));
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