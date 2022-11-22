package com.epam.facultative.daos.impl;

import com.epam.facultative.daos.CategoryDao;
import com.epam.facultative.daos.DaoFactory;
import com.epam.facultative.entity.Category;

import org.junit.jupiter.api.*;

import java.sql.SQLIntegrityConstraintViolationException;

import static org.junit.jupiter.api.Assertions.*;


class MySqlCategoryDaoTest {
    private CategoryDao categoryDao;
    private Category testCategory;

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
        assertEquals(testCategory,category);

        testCategory.setTitle("otherCategory");
        testCategory.setDescription("otherDescription");
        categoryDao.update(testCategory);
        category = categoryDao.getById(testCategory.getId());
        assertEquals(testCategory,category);

        categoryDao.delete(testCategory.getId());
        assertEquals(0, categoryDao.getAll().size());
    }

    @Test
    void testUniqueFields() {
        Category category = new Category();
        category.setTitle(testCategory.getTitle());
        categoryDao.add(testCategory);
        assertThrows(RuntimeException.class, () -> categoryDao.add(category));
        categoryDao.delete(testCategory.getId());
    }

    @Test
    void testNotNullFields() {
        testCategory.setTitle(null);
        assertThrows(RuntimeException.class, () -> categoryDao.add(testCategory));

        testCategory.setId(-1);
        testCategory.setTitle("testTitle");
        categoryDao.add(testCategory);
        assertEquals(1, categoryDao.getAll().size());
        categoryDao.delete(testCategory.getId());
    }

}