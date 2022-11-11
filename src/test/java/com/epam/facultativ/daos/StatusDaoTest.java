package com.epam.facultativ.daos;

import com.epam.facultativ.entity.Status;
import org.junit.jupiter.api.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class StatusDaoTest {

    private static String saveProperties;
    private static final String testProperties =
            "connection.url = jdbc:mysql://localhost:3306/facultative_test\nuser.name = root\npassword = 132465";
    private static StatusDao statusDao;
    private static Status status;

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
        statusDao = new StatusDao();
        status = new Status(11, "Complete and delete");
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
        assertEquals(3, statusDao.findAll().size());
    }

    @Order(2)
    @Test
    void insert() {
        statusDao.insert(status);
        assertEquals(status.toString(), statusDao.findByTitle(status.getTitle()).toString());
    }

    @Order(3)
    @Test
    void update() {
        status.setTitle("Over");
        statusDao.update(status);
        assertEquals(status.toString(), statusDao.findById(status.getId()).toString());
    }

    @Order(4)
    @Test
    void findById() {
        assertEquals(status.toString(), statusDao.findById(status.getId()).toString());
    }

    @Order(5)
    @Test
    void findByTitle() {
        assertEquals(status.toString(), statusDao.findByTitle(status.getTitle()).toString());
    }


    @Order(6)
    @Test
    void delete() {
        statusDao.delete(status);
        assertEquals(3, statusDao.findAll().size());
    }
}