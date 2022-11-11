package com.epam.facultativ.daos;

import com.epam.facultativ.entity.Category;
import org.junit.jupiter.api.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CategoryDaoTest {

    private static String saveProperties;
    private static final String testProperties =
            "connection.url = jdbc:mysql://localhost:3306/facultative_test\nuser.name = root\npassword = 132465";
    private static CategoryDao categoryDao;
    private static Category category;

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
        categoryDao = new CategoryDao();
        category = new Category(11, "Geography", "some description");
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
    }

    @Order(2)
    @Test
    void insert() {
        categoryDao.insert(category);
        assertEquals(category.toString(), categoryDao.findByTitle("Geography").toString());
    }

    @Order(3)
    @Test
    void update() {
        category.setTitle("Chemical");
        categoryDao.update(category);
        assertEquals(category.toString(), categoryDao.findById(category.getId()).toString());
    }

    @Order(4)
    @Test
    void findById() {
        assertEquals(category.toString(), categoryDao.findById(category.getId()).toString());
    }

    @Order(5)
    @Test
    void findByTitle() {
        assertEquals(category.toString(), categoryDao.findByTitle(category.getTitle()).toString());
    }

    @Order(6)
    @Test
    void delete() {
        categoryDao.delete(category);
        assertEquals(3, categoryDao.findAll().size());
    }
}