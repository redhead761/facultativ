package com.epam.facultative.service.implementation;

import com.epam.facultative.data_layer.daos.*;
import com.epam.facultative.data_layer.entities.Course;
import com.epam.facultative.dto.Converter;
import com.epam.facultative.exception.DAOException;
import com.epam.facultative.exception.ServiceException;
import com.epam.facultative.exception.ValidateException;
import com.epam.facultative.service.AdminService;
import com.epam.facultative.utils.validator.Validator;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;

class AdminServiceImplTest {

    @Mock
    private CourseDao courseDao = mock(CourseDao.class);
    @Mock
    private CategoryDao categoryDao;
    @Mock
    private UserDao userDao;
    @Mock
    private StudentDao studentDao;
    @Mock
    private TeacherDao teacherDao;
    AdminService adminService = new AdminServiceImpl(courseDao, categoryDao, userDao, studentDao, teacherDao);


    @Test
    void addCourse() throws DAOException, ValidateException {
        try (MockedStatic<Validator> validator = mockStatic(Validator.class)) {
            validator.when(() -> Validator.validateCourseData(isA(String.class), isA(String.class), isA(int.class))).thenReturn(true);
            doNothing().when(courseDao).add(isA(Course.class));
            doThrow(ServiceException.class).when(courseDao).add(isA(Course.class));
            assertDoesNotThrow(() -> adminService.addCourse(getTestCourse()));
        }
    }

    private Course getTestCourse() {
        return Course.builder()
                .title("Test course")
                .duration(100)
                .build();
    }

    @Test
    void updateCourse() {
    }

    @Test
    void deleteCourse() {
    }

    @Test
    void addCategory() {
    }

    @Test
    void updateCategory() {
    }

    @Test
    void deleteCategory() {
    }

    @Test
    void getAllCategoriesPagination() {
    }

    @Test
    void getNoOfRecordsCategories() {
    }

    @Test
    void assigned() {
    }

    @Test
    void blockStudent() {
    }

    @Test
    void unblockStudent() {
    }

    @Test
    void addTeacher() {
    }

    @Test
    void getAllStudentsPagination() {
    }

    @Test
    void getAllTeachersPagination() {
    }

    @Test
    void getCategory() {
    }

    @Test
    void getCourse() {
    }

    @Test
    void getTeacher() {
    }

    @Test
    void getNoOfRecordsTeachers() {
    }

    @Test
    void getNoOfRecordsStudents() {
    }
}