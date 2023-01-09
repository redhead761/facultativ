package com.epam.facultative.service.implementation;

import com.epam.facultative.data_layer.daos.*;
import com.epam.facultative.data_layer.entities.User;
import com.epam.facultative.dto.UserDTO;
import com.epam.facultative.exception.DAOException;
import com.epam.facultative.service.GeneralService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

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


    @BeforeEach
    void setUp() {
    }

    @Test
    void authorizationAdmin() throws DAOException {
        UserDTO admin = testServiceUtil.getAdminDTO();


    }

    @Test
    void getAllCourses() {
    }

    @Test
    void sortCoursesByAlphabet() {
    }

    @Test
    void sortCoursesByAlphabetReverse() {
    }

    @Test
    void sortCoursesByDuration() {
    }

    @Test
    void sortCoursesBuAmountOfStudents() {
    }

    @Test
    void getCoursesByCategory() {
    }

    @Test
    void getCoursesByTeacher() {
    }

    @Test
    void getAllCategories() {
    }

    @Test
    void getAllTeachers() {
    }

    @Test
    void getNoOfRecordsCourses() {
    }
}