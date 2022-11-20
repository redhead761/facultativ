package com.epam.facultative.daos;

import com.epam.facultative.entity.Category;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CategoryDaoTest {
    private static CategoryDao categoryDao;
    private static Category category;

    @BeforeAll
    static void setUp() {
        categoryDao = new CategoryDao();
        category = new Category(11, "Geography", "some description");
    }

    @Order(1)
    @Test
    void findAll() {
        assertEquals(3, categoryDao.findAll().size());
    }

    @Order(2)
    @Test
    void insert() {
        categoryDao.insert(category);
        assertEquals(category.toString(), categoryDao.findByName(category.getTitle()).toString());
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
        assertEquals(category.toString(), categoryDao.findByName(category.getTitle()).toString());
    }

    @Order(6)
    @Test
    void delete() {
        categoryDao.delete(category);
        assertEquals(3, categoryDao.findAll().size());
    }
}