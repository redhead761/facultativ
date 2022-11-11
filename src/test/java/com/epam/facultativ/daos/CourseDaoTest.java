package com.epam.facultativ.daos;

import com.epam.facultativ.entity.Course;
import org.junit.jupiter.api.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CourseDaoTest {

    private static String saveProperties;
    private static final String testProperties =
            "connection.url = jdbc:mysql://localhost:3306/facultative_test\nuser.name = root\npassword = 132465";
    private static CourseDao courseDao;
    private static Course course;

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
        courseDao = new CourseDao();
        course = new Course();
        course.setId(111);
        course.setTitle("English");
        course.setDuration(100);
        course.setStartDate(LocalDate.parse("2022-12-12"));
        course.setDescription("some description");
        course.setCategory(new CategoryDao().findById(1));
        course.setCourseStatus(new StatusDao().findById(1));
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
        assertEquals(3,courseDao.findAll().size());
    }

    @Order(2)
    @Test
    void insert() {
        courseDao.insert(course);
        assertEquals(course.toString(), courseDao.findById(course.getId()).toString());
    }

    @Order(3)
    @Test
    void update() {
        course.setTitle("Spanish");
        courseDao.update(course);
        assertEquals(course.toString(), courseDao.findById(course.getId()).toString());
    }

    @Order(4)
    @Test
    void findById() {
        assertEquals(course.toString(), courseDao.findById(course.getId()).toString());
    }

    @Order(5)
    @Test
    void delete() {
        courseDao.delete(course);
        assertEquals(3, courseDao.findAll().size());
    }
}