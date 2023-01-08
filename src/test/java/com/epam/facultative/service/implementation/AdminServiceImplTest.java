package com.epam.facultative.service.implementation;

import com.epam.facultative.data_layer.daos.*;
import com.epam.facultative.data_layer.entities.Category;
import com.epam.facultative.data_layer.entities.Course;
import com.epam.facultative.data_layer.entities.Student;
import com.epam.facultative.data_layer.entities.Teacher;
import com.epam.facultative.dto.*;
import com.epam.facultative.exception.DAOException;
import com.epam.facultative.exception.ServiceException;
import com.epam.facultative.exception.ValidateException;
import com.epam.facultative.service.AdminService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;


class AdminServiceImplTest {
    private final CourseDao courseDao = mock(CourseDao.class);
    private final CategoryDao categoryDao = mock(CategoryDao.class);
    private final StudentDao studentDao = mock(StudentDao.class);
    private final TeacherDao teacherDao = mock(TeacherDao.class);
    AdminService adminService = new AdminServiceImpl(courseDao, categoryDao, studentDao, teacherDao);

    private CourseDTO courseDTO;
    private CategoryDTO categoryDTO;
    private TeacherDTO teacherDTO;
    private List<Student> students;
    private List<Teacher> teachers;
    private List<Category> categories;
    private List<StudentDTO> studentDTOS;
    private List<TeacherDTO> teacherDTOS;

    @BeforeEach
    void init() {
        courseDTO = CourseDTO.builder()
                .title("Test course")
                .duration(100)
                .description("Test desc")
                .build();

        categoryDTO = CategoryDTO.builder()
                .title("Test title")
                .description("Test desc")
                .build();

        teacherDTO = TeacherDTO.builder()
                .id(1)
                .login("testlogin")
                .password("Test1234")
                .name("Testname")
                .surname("Testsurname")
                .email("test@fa.ve")
                .build();

        categories = new ArrayList<>();
        teachers = new ArrayList<>();
        students = new ArrayList<>();
        studentDTOS = new ArrayList<>();
        teacherDTOS = new ArrayList<>();
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
    void deleteCourse() throws DAOException {
        doNothing().when(courseDao).delete(isA(int.class));
        assertDoesNotThrow(() -> adminService.deleteCourse(1));
    }

    @Test
    void deleteCourseWithIllegalArgument() throws DAOException {
        doThrow(DAOException.class).when(courseDao).delete(isA(int.class));
        assertThrows(ServiceException.class, () -> adminService.deleteCourse(-1));
    }

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
    void deleteCategory() throws DAOException {
        doNothing().when(categoryDao).delete(isA(int.class));
        assertDoesNotThrow(() -> adminService.deleteCategory(1));
    }

    @Test
    void deleteCategoryWithIllegalArgument() throws DAOException {
        doThrow(DAOException.class).when(categoryDao).delete(isA(int.class));
        assertThrows(ServiceException.class, () -> adminService.deleteCategory(-1));
    }

    @Test
    void getAllCategoriesPagination() throws DAOException, ServiceException {
        when(categoryDao.getAllPagination(isA(int.class), isA(int.class))).thenReturn(categories);
        assertEquals(categories, adminService.getAllCategoriesPagination(1, 5));
    }


    @ParameterizedTest
    @MethodSource("invalidIntValues")
    void getAllCategoriesPaginationWithIllegalArgument(int offset, int numberOfRows) throws DAOException {
        doThrow(DAOException.class).when(categoryDao).getAllPagination(isA(int.class), isA(int.class));
        assertThrows(ServiceException.class, () -> adminService.getAllCategoriesPagination(offset, numberOfRows));
    }

    @Test
    void getNoOfRecordsCategories() {
        assertDoesNotThrow(() -> adminService.getNoOfRecordsCategories());
    }

//    @Test
//    void assigned() throws DAOException, ValidateException {
//        when(courseDao.getById(1)).thenReturn(courseDTO);
//        when(teacherDao.getById(1)).thenReturn(teacherDTO);
//        doNothing().when(courseDao).update(courseDTO);
//        assertDoesNotThrow(() -> adminService.assigned(1, 1));
//    }
//
//    @ParameterizedTest
//    @MethodSource("invalidIntValues")
//    void assignedWithIllegalArgument(int courseId, int teacherId) throws DAOException, ValidateException {
//        when(courseDao.getById(courseId)).thenReturn(courseDTO);
//        when(teacherDao.getById(teacherId)).thenReturn(teacherDTO);
//        doThrow(DAOException.class).when(courseDao).update(courseDTO);
//        assertThrows(ServiceException.class, () -> adminService.assigned(courseId, teacherId));
//
//    }

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
    void getAllStudentsPagination() throws DAOException, ServiceException {
        when(studentDao.getAllPagination(isA(int.class), isA(int.class))).thenReturn(students);
        assertIterableEquals(studentDTOS, adminService.getAllStudentsPagination(1, 5));
    }


    @ParameterizedTest
    @MethodSource("invalidIntValues")
    void getAllStudentsPaginationWithIllegalArgument(int offset, int numberOfRows) throws DAOException {
        doThrow(DAOException.class).when(studentDao).getAllPagination(isA(int.class), isA(int.class));
        assertThrows(ServiceException.class, () -> adminService.getAllStudentsPagination(offset, numberOfRows));
    }

    @Test
    void getAllTeachersPagination() throws DAOException, ServiceException {
        when(teacherDao.getAllPagination(isA(int.class), isA(int.class))).thenReturn(teachers);
        assertIterableEquals(teacherDTOS, adminService.getAllTeachersPagination(1, 5));
    }

    @ParameterizedTest
    @MethodSource("invalidIntValues")
    void getAllTeachersPaginationWithIllegalArgument(int offset, int numberOfRows) throws DAOException {
        doThrow(DAOException.class).when(teacherDao).getAllPagination(isA(int.class), isA(int.class));
        assertThrows(ServiceException.class, () -> adminService.getAllTeachersPagination(offset, numberOfRows));
    }

//    @Test
//    void getCategory() throws DAOException, ServiceException {
//        when(categoryDao.getById(isA(int.class))).thenReturn(categoryDTO);
//        assertEquals(categoryDTO, adminService.getCategory(1));
//    }

//    @Test
//    void getCategoryWithIllegalArgument() throws DAOException, ServiceException {
//        when(categoryDao.getById(isA(int.class))).thenReturn(null);
//        assertNull(adminService.getCategory(-1));
//    }


    //Заглушка, переделать
//    @Test
//    void getCourse() throws DAOException {
//        when(courseDao.getById(isA(int.class))).thenReturn(courseDTO);
//        assertThrows(NullPointerException.class, () -> adminService.getCourse(1));
//    }

    @Test
    void getCourseWithIllegalArgument() throws DAOException {
        doThrow(DAOException.class).when(courseDao).getById(isA(int.class));
        assertThrows(ServiceException.class, () -> adminService.getCourse(-1));
    }

//    @Test
//    void getTeacher() throws DAOException, ServiceException {
//        when(userDao.getById(isA(int.class))).thenReturn(teacherDTO);
//        UserDTO userDTO = UserDTO.builder()
//                .id(1)
//                .login("testlogin")
//                .name("Testname")
//                .surname("Testsurname")
//                .email("test@fa.ve")
//                .build();
//        assertEquals(userDTO, adminService.getTeacher(1));
//    }

//    @Test
//    void getTeacherWithIllegalArgument() throws DAOException {
//        doThrow(DAOException.class).when(userDao).getById(isA(int.class));
//        assertThrows(ServiceException.class, () -> adminService.getTeacher(-1));
//    }

    @Test
    void getNoOfRecordsTeachers() {
        assertDoesNotThrow(() -> adminService.getNoOfRecordsTeachers());
    }

    @Test
    void getNoOfRecordsStudents() {
        assertDoesNotThrow(() -> adminService.getNoOfRecordsTeachers());
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