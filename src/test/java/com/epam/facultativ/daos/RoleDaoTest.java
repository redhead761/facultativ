package com.epam.facultativ.daos;

import com.epam.facultativ.entity.Role;
import org.junit.jupiter.api.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class RoleDaoTest {

    private static String saveProperties;
    private static final String testProperties =
            "connection.url = jdbc:mysql://localhost:3306/facultative_test\nuser.name = root\npassword = 132465";
    private static RoleDao roleDao;
    private static Role role;

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
        roleDao = new RoleDao();
        role = new Role(11, "Other");

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
        assertEquals(3, roleDao.findAll().size());
    }

    @Order(2)
    @Test
    void insert() {
        roleDao.insert(role);
        assertEquals(role.toString(), roleDao.findById(role.getId()).toString());
    }

    @Order(3)
    @Test
    void update() {
        role.setTitle("Everybody");
        roleDao.update(role);
        assertEquals(role.toString(), roleDao.findById(role.getId()).toString());
    }

    @Order(4)
    @Test
    void findById() {
        assertEquals(role.toString(), roleDao.findById(role.getId()).toString());
    }

    @Order(5)
    @Test
    void delete() {
        roleDao.delete(role);
        assertEquals(3, roleDao.findAll().size());
    }
}