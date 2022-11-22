package com.epam.facultative.daos.impl;

import com.epam.facultative.daos.CategoryDao;
import com.epam.facultative.daos.DaoFactory;
import com.epam.facultative.entity.Category;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MySqlCategoryDaoTest {
    static CategoryDao categoryDao;
    static Category testCategory;

    @BeforeEach
    void setUp() {
        categoryDao = DaoFactory.getInstance().getCategoryDao();
        testCategory = new Category();
        testCategory.setTitle("testCategory");
        testCategory.setDescription("test description");
    }


    @Test
    void testCRUD() {
        categoryDao.add(testCategory);

        Category category = categoryDao.getByName(testCategory.getTitle());
        assertEquals(testCategory.getId(), category.getId());
        assertEquals(testCategory.getTitle(), category.getTitle());
        assertEquals(testCategory.getDescription(), category.getDescription());

        testCategory.setTitle("otherCategory");
        testCategory.setDescription("otherDescription");

        categoryDao.update(testCategory);
        category = categoryDao.getById(testCategory.getId());
        assertEquals(testCategory.getId(), category.getId());
        assertEquals(testCategory.getTitle(), category.getTitle());
        assertEquals(testCategory.getDescription(), category.getDescription());

        categoryDao.delete(testCategory.getId());
        assertEquals(0, categoryDao.getAll().size());
    }

    @Test
    void testTwiceAdd() {
        categoryDao.add(testCategory);
        assertThrows(RuntimeException.class, () -> categoryDao.add(testCategory));
        categoryDao.delete(testCategory.getId());
    }
}