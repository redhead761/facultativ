package com.epam.facultative.daos.impl;

import com.epam.facultative.daos.CategoryDao;
import com.epam.facultative.daos.CourseDao;
import com.epam.facultative.daos.DaoFactory;
import com.epam.facultative.daos.UserDao;
import com.epam.facultative.entity.Category;
import com.epam.facultative.entity.Course;
import com.epam.facultative.entity.Role;
import com.epam.facultative.entity.User;

import org.junit.jupiter.api.*;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MySqlUserDaoTest {
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
    }

    @Test
    void testCRUD() {
        userDao.add(testUser);

        User user = userDao.getByName(testUser.getLogin());
        assertEquals(testUser, user);

        testUser.setLogin("otherLogin");
        testUser.setPassword("otherPassword");
        testUser.setName("otherName");
        testUser.setSurname("otherSurname");
        testUser.setEmail("other@test.com");
        testUser.setBlock(true);
        testUser.setRole(Role.TEACHER);
        userDao.update(testUser);
        user = userDao.getById(testUser.getId());
        assertEquals(testUser, user);

        userDao.delete(testUser.getId());
        assertEquals(0, userDao.getAll().size());
    }

    @Test
    void testUniqueFields() {
        User user = new User();
        user.setLogin(testUser.getLogin());
        user.setPassword("some password");
        user.setName("some name");
        user.setSurname("some surname");
        user.setEmail("some@test.mail");
        user.setBlock(true);
        user.setRole(Role.TEACHER);

        userDao.add(testUser);
        assertThrows(RuntimeException.class, () -> userDao.add(user));

        user.setLogin("some login");
        user.setEmail(testUser.getEmail());
        assertThrows(RuntimeException.class, () -> userDao.add(user));
    }

    @Test
    void testNotNullFields() {
        testUser.setLogin(null);
        assertThrows(RuntimeException.class, () -> userDao.add(testUser));

        testUser.setLogin("testLogin");
        testUser.setPassword(null);
        assertThrows(RuntimeException.class, () -> userDao.add(testUser));

        testUser.setPassword("testPassword");
        testUser.setName(null);
        assertThrows(RuntimeException.class, () -> userDao.add(testUser));

        testUser.setName("testName");
        testUser.setSurname(null);
        assertThrows(RuntimeException.class, () -> userDao.add(testUser));

        testUser.setSurname("testSurname");
        testUser.setEmail(null);
        assertThrows(RuntimeException.class, () -> userDao.add(testUser));

        testUser.setEmail("test@test.com");
        testUser.setId(-1);
        userDao.add(testUser);
        assertEquals(1, userDao.getAll().size());
    }

    @Test
    void testGetUserByRole() {
        userDao.add(testUser);
        List<User> users = userDao.getByRole(testUser.getRole().getId());
        assertEquals(testUser, users.get(0));
        userDao.delete(testUser.getId());
    }

    @Test
    void testGetUserByCourse() {
        userDao.add(testUser);
        categoryDao.add(testCategory);
        testCourse.setCategory(testCategory);
        courseDao.add(testCourse);

        courseDao.addUserToCourse(testCourse.getId(),testUser.getId());
        List<User> user = userDao.getUsersByCourse(testCourse.getId());
        assertEquals(testUser,user.get(0));
    }
}