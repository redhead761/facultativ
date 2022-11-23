package com.epam.facultative.daos.impl;

import com.epam.facultative.daos.CategoryDao;
import com.epam.facultative.daos.DaoFactory;
import com.epam.facultative.entity.Category;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;


class MySqlCategoryDaoTest {
    private static CategoryDao categoryDao;
    private static Category testCategory;
    static Preparation prep;

    @BeforeAll
    static void setUp() {
        prep = new Preparation();
        categoryDao = DaoFactory.getInstance().getCategoryDao();
        testCategory = prep.getTestCategory();
    }

    @BeforeEach
    void refreshDB() throws SQLException, FileNotFoundException {
        prep.refreshDB();
    }

    @Test
    void testCRUD() {
        categoryDao.add(testCategory);

        Category category = categoryDao.getByName(testCategory.getTitle());
        assertEquals(testCategory, category);

        testCategory.setTitle("otherCategory");
        testCategory.setDescription("otherDescription");
        categoryDao.update(testCategory);
        category = categoryDao.getById(testCategory.getId());
        assertEquals(testCategory, category);

        categoryDao.delete(testCategory.getId());
        assertEquals(0, categoryDao.getAll().size());
    }

    @Test
    void testUniqueFields() {
        Category category = new Category();
        category.setTitle(testCategory.getTitle());
        categoryDao.add(testCategory);
        assertThrows(RuntimeException.class, () -> categoryDao.add(category));

    }

    @Test
    void testNotNullFields() {
        testCategory.setTitle(null);
        assertThrows(RuntimeException.class, () -> categoryDao.add(testCategory));

        testCategory.setId(-1);
        testCategory.setTitle("testTitle");
        categoryDao.add(testCategory);
        assertEquals(1, categoryDao.getAll().size());
    }

}