package com.epam.facultative.daos;

import com.epam.facultative.daos.impl.UserDao;
import com.epam.facultative.entity.Role;
import com.epam.facultative.entity.User;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserDaoTest {

    private static UserDao userDao;
    private static User user;

    @BeforeAll
    static void setUp() {
        userDao = new UserDao();
        Role role = new Role(1, "ADMIN");
        user = new User(111, "testLogin", "testPassword", "testFirstName", "testLastName", "testEmail", false, role);
    }

    @Order(1)
    @Test
    void findAll() {
        assertEquals(5, userDao.findAll().size());
    }

    @Order(2)
    @Test
    void insert() {
        userDao.insert(user);
        assertEquals(user.toString(), userDao.findById(user.getId()).toString());
    }

    @Order(3)
    @Test
    void update() {
        user.setLogin("otherTestLogin");
        userDao.update(user);
        assertEquals(user.toString(), userDao.findById(user.getId()).toString());
    }

    @Order(4)
    @Test
    void getUserByLogin() {
        assertEquals(user.toString(), userDao.findByName(user.getLogin()).toString());
    }

    @Order(5)
    @Test
    void findById() {
        assertEquals(user.toString(), userDao.findById(user.getId()).toString());
    }

    @Order(6)
    @Test
    void findUsersByRole() {
        assertEquals(1, userDao.findUsersByRole("teacher").size());
    }

    @Order(7)
    @Test
    void delete() {
        userDao.delete(user);
        assertEquals(5, userDao.findAll().size());
    }
//      Need to implements
//    @Test
//    void usersByCourse() {
//    }
}