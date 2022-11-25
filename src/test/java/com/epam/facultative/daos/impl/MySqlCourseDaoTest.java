package com.epam.facultative.daos.impl;

import com.epam.facultative.daos.*;
import com.epam.facultative.entity.*;
import com.epam.facultative.exception.DAOException;
import org.junit.jupiter.api.*;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MySqlCourseDaoTest {

    private static Preparation prep;
    private static UserDao userDao;
    private static CourseDao courseDao;
    private static CategoryDao categoryDao;
    private static User testUser;
    private static Course testCourse;
    private static Category testCategory;

    @BeforeAll
    static void setUp() {
        prep = new Preparation();
        userDao = DaoFactory.getInstance().getUserDao();
        courseDao = DaoFactory.getInstance().getCourseDao();
        categoryDao = DaoFactory.getInstance().getCategoryDao();
        testUser = prep.getTestUser();
        testCourse = prep.getTestCourse();
        testCategory = prep.getTestCategory();
    }

    @BeforeEach
    void refreshDB() throws SQLException, FileNotFoundException {
        prep.refreshDB();
        testCourse = prep.getTestCourse();
    }

    @Test
    void testCRUD() throws DAOException {
        categoryDao.add(testCategory);
        testCourse.setCategory(testCategory);
        courseDao.add(testCourse);

        Course course = courseDao.getByName(testCourse.getTitle());
        assertEquals(testCourse, course);

        testCourse.setTitle("someTitle");
        testCourse.setDuration(50);
        testCourse.setDescription("someDescription");
        testCourse.setStatus(Status.IN_PROCESS);
        courseDao.update(testCourse);
        course = courseDao.getById(testCourse.getId());
        assertEquals(testCourse, course);

        courseDao.delete(testCourse.getId());
        assertEquals(0, courseDao.getAll().size());
    }

    @Test
    void testUniqueFields() throws DAOException {
        categoryDao.add(testCategory);
        testCourse.setCategory(testCategory);

        Course course = new Course();
        course.setTitle(testCourse.getTitle());
        course.setDuration(100);
        course.setStartDate(LocalDate.now());
        course.setDescription("testDescription");
        course.setStatus(Status.COMING_SOON);
        course.setCategory(testCourse.getCategory());

        courseDao.add(testCourse);
        assertThrows(RuntimeException.class, () -> courseDao.add(course));
    }

    @Test
    void testNotNullFields() throws DAOException {
        categoryDao.add(testCategory);
        testCourse.setCategory(testCategory);

        testCourse.setTitle(null);
        assertThrows(RuntimeException.class, () -> courseDao.add(testCourse));

        testCourse.setTitle("testTitle");
        testCourse.setDuration(-1);
        assertThrows(RuntimeException.class, () -> courseDao.add(testCourse));

        testCourse.setDuration(50);
        testCourse.setStartDate(null);
        assertThrows(RuntimeException.class, () -> courseDao.add(testCourse));

        testCourse.setStartDate(LocalDate.now());
        testCourse.setStatus(null);
        assertThrows(RuntimeException.class, () -> courseDao.add(testCourse));

        testCourse.setStatus(Status.IN_PROCESS);
        testCourse.setCategory(null);
        assertThrows(RuntimeException.class, () -> courseDao.add(testCourse));
    }

    @Test
    void addUserToCourseAndGetByUser() throws DAOException {
        fillDB();
        List<Course> course = courseDao.getByUser(testUser.getId());
        assertEquals(testCourse, course.get(0));
    }

    @Test
    void getByCategory() throws DAOException {
        fillDB();
        List<Course> courses = courseDao.getByCategory(testCategory.getId());
        assertEquals(testCourse, courses.get(0));
    }

    @Test
    void updateUsersCourseAndGetGrade() throws DAOException {
        fillDB();
        courseDao.updateUsersCourse(testCourse.getId(), testUser.getId(), 5);
        assertEquals(5, courseDao.getGrade(testCourse.getId(), testUser.getId()));
    }

    @Test
    void deleteManyToMany() throws DAOException {
        fillDB();
        assertDoesNotThrow(() -> courseDao.delete(testCourse.getId()));
        assertDoesNotThrow(() -> userDao.delete(testUser.getId()));
    }

    void fillDB() throws DAOException {
        categoryDao.add(testCategory);
        testCourse.setCategory(testCategory);
        courseDao.add(testCourse);
        userDao.add(testUser);
        courseDao.addUserToCourse(testCourse.getId(), testUser.getId());
        testCourse.setAmountStudents(testCourse.getAmountStudents() + 1);
    }
}