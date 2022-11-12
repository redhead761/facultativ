package com.epam.facultativ.daos;

import com.epam.facultativ.entity.Role;
import com.epam.facultativ.entity.User;
import org.junit.jupiter.api.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserDaoTest {

    private static String saveProperties;
    private static final String testProperties =
            "connection.url = jdbc:mysql://localhost:3306/facultative_test\nuser.name = root\npassword = 132465";
    private static UserDao userDao;
    private static User user;

    @BeforeAll
    static void setUp() {
        Path of = Path.of("db.properties");
        try {
            saveProperties = Files.readString(of);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            Files.write(of, testProperties.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        userDao = new UserDao();
        Role role = new Role(1, "ADMIN");
        user = new User(111, "testLogin", "testPassword", "testFirstName", "testLastName", "testEmail", false, role);
    }

    @AfterAll
    static void tearDown() {
        try (PrintWriter out = new PrintWriter("db.properties")) {
            out.print(saveProperties);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Order(1)
    @Test
    void findAll() {
        assertEquals(3, userDao.findAll().size());
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
        assertEquals(3, userDao.findAll().size());
    }
}