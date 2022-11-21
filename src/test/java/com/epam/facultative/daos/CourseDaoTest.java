package com.epam.facultative.daos;

import com.epam.facultative.entity.Course;
import org.junit.jupiter.api.*;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CourseDaoTest {
    private static CourseDao courseDao;
    private static Course course;

    @BeforeAll
    static void setUp() {
        courseDao = new CourseDao();
        course = new Course();
        course.setId(111);
        course.setTitle("English");
        course.setDuration(100);
        course.setStartDate(LocalDate.parse("2022-12-12"));
        course.setDescription("some description");
        course.setCategory(new CategoryDao().getById(1));
        course.setStatus(new StatusDao().getById(1));
    }

    @Order(1)
    @Test
    void findAll() {
        assertEquals(3, courseDao.getAll().size());
    }

    @Order(2)
    @Test
    void insert() {
        courseDao.add(course);
        assertEquals(course.toString(), courseDao.getById(course.getId()).toString());
    }

    @Order(3)
    @Test
    void update() {
        course.setTitle("Spanish");
        courseDao.update(course);
        assertEquals(course.toString(), courseDao.getById(course.getId()).toString());
    }

    @Order(4)
    @Test
    void findById() {
        assertEquals(course.toString(), courseDao.getById(course.getId()).toString());
    }

    @Order(5)
    @Test
    void findByName() {
        assertEquals(course.toString(), courseDao.getByName(course.getTitle()).toString());
    }

    @Order(6)
    @Test
    void delete() {
        courseDao.delete(course);
        assertEquals(3, courseDao.getAll().size());
    }


// need to implement
//    @Test
//    void findByCategory() {
//    }
//@Test
//void findByUser() {
//}

    //    @Test
//    void insertUserToCourse() {
//    }


}