package com.epam.facultative.model.dao.impl_sql;

import com.epam.facultative.model.dao.CategoryDao;
import com.epam.facultative.model.entities.Category;
import com.epam.facultative.model.exception.DAOException;
import com.epam.facultative.model.exception.ValidateException;
import com.epam.facultative.model.service.implementation.TestServiceUtil;
import org.junit.jupiter.api.*;

import javax.sql.DataSource;

import java.sql.*;
import java.util.List;
import java.util.Map;

import static com.epam.facultative.model.dao.impl_sql.сonstants.FieldsConstants.*;
import static com.epam.facultative.model.dao.impl_sql.сonstants.SQLRequestConstants.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class MySqlCategoryDaoTest {
    private static final DataSource dataSource = mock(DataSource.class);
    Connection con = mock(Connection.class);
    PreparedStatement stmt = mock(PreparedStatement.class);
    ResultSet resultSet = mock(ResultSet.class);
    private static Category category;
    private static Map.Entry<Integer, List<Category>> categories;
    private static CategoryDao categoryDao;

    @BeforeAll
    static void init() {
        TestServiceUtil testServiceUtil = new TestServiceUtil();
        category = testServiceUtil.getCategory();
        categoryDao = new MySqlCategoryDao(dataSource);
        categories = testServiceUtil.getCategories();
    }

    @Test
    void getSuccessful() throws SQLException, DAOException {
        when(dataSource.getConnection()).thenReturn(con);
        when(con.prepareStatement(anyString())).thenReturn(stmt);
        when(stmt.executeQuery()).thenReturn(resultSet);
        setResultSet();
        Category resultCategory = categoryDao.get(SELECT_CATEGORY).orElse(null);
        assertNotNull(resultCategory);
        assertEquals(category, resultCategory);
    }

    @Test
    void getAbsent() throws SQLException, DAOException {
        when(dataSource.getConnection()).thenReturn(con);
        when(con.prepareStatement(anyString())).thenReturn(stmt);
        when(stmt.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(false);
        Category resultCategory = categoryDao.get(SELECT_CATEGORY).orElse(null);
        assertNull(resultCategory);
    }

    @Test
    void getFailed() throws SQLException {
        when(dataSource.getConnection()).thenReturn(con);
        when(con.prepareStatement(anyString())).thenReturn(stmt);
        when(stmt.executeQuery()).thenThrow(SQLException.class);
        assertThrows(DAOException.class, () -> categoryDao.get(SELECT_CATEGORY));
    }

    @Test
    void addSuccessful() throws SQLException {
        when(dataSource.getConnection()).thenReturn(con);
        when(con.prepareStatement(anyString())).thenReturn(stmt);
        when(stmt.executeUpdate()).thenReturn(1);
        setStmt();
        assertDoesNotThrow(() -> categoryDao.add(category));
    }

    @Test
    void addCopy() throws SQLException {
        when(dataSource.getConnection()).thenReturn(con);
        when(con.prepareStatement(anyString())).thenReturn(stmt);
        when(stmt.executeUpdate()).thenThrow(SQLIntegrityConstraintViolationException.class);
        assertThrows(ValidateException.class, () -> categoryDao.add(category));
    }

    @Test
    void addFailed() throws SQLException {
        when(dataSource.getConnection()).thenReturn(con);
        when(con.prepareStatement(anyString())).thenReturn(stmt);
        when(stmt.executeUpdate()).thenThrow(SQLException.class);
        assertThrows(DAOException.class, () -> categoryDao.add(category));
    }

    @Test
    void updateSuccessful() throws SQLException {
        when(dataSource.getConnection()).thenReturn(con);
        when(con.prepareStatement(anyString())).thenReturn(stmt);
        when(stmt.executeUpdate()).thenReturn(1);
        setStmt();
        assertDoesNotThrow(() -> categoryDao.update(category));
    }

    @Test
    void updateCopy() throws SQLException {
        when(dataSource.getConnection()).thenReturn(con);
        when(con.prepareStatement(anyString())).thenReturn(stmt);
        when(stmt.executeUpdate()).thenThrow(SQLIntegrityConstraintViolationException.class);
        assertThrows(ValidateException.class, () -> categoryDao.update(category));
    }

    @Test
    void updateFailed() throws SQLException {
        when(dataSource.getConnection()).thenReturn(con);
        when(con.prepareStatement(anyString())).thenReturn(stmt);
        when(stmt.executeUpdate()).thenThrow(SQLException.class);
        assertThrows(DAOException.class, () -> categoryDao.update(category));
    }

    @Test
    void deleteSuccessful() throws SQLException {
        when(dataSource.getConnection()).thenReturn(con);
        when(con.prepareStatement(anyString())).thenReturn(stmt);
        when(stmt.executeUpdate()).thenReturn(1);
        setStmt();
        assertDoesNotThrow(() -> categoryDao.delete(1));
    }

    @Test
    void deleteNoCascade() throws SQLException {
        when(dataSource.getConnection()).thenReturn(con);
        when(con.prepareStatement(anyString())).thenReturn(stmt);
        when(stmt.executeUpdate()).thenThrow(SQLIntegrityConstraintViolationException.class);
        assertThrows(ValidateException.class, () -> categoryDao.delete(1));
    }

    @Test
    void deleteFailed() throws SQLException {
        when(dataSource.getConnection()).thenReturn(con);
        when(con.prepareStatement(anyString())).thenReturn(stmt);
        when(stmt.executeUpdate()).thenThrow(SQLException.class);
        assertThrows(DAOException.class, () -> categoryDao.delete(1));
    }

    @Test
    void getAllSuccessful() throws SQLException, DAOException {
        when(dataSource.getConnection()).thenReturn(con);
        when(con.prepareStatement(anyString())).thenReturn(stmt);
        when(stmt.executeQuery()).thenReturn(resultSet);
        setResultSet();
        when(stmt.executeQuery(SELECT_FOUND_ROWS)).thenReturn(resultSet);
        when(resultSet.getInt(1)).thenReturn(1);
        Map.Entry<Integer, List<Category>> resultCategory = categoryDao.getAll(SELECT_ALL_CATEGORIES);
        assertEquals(categories, resultCategory);
    }

    @Test
    void getAllFailed() throws SQLException {
        when(dataSource.getConnection()).thenReturn(con);
        when(con.prepareStatement(anyString())).thenReturn(stmt);
        when(stmt.executeQuery()).thenThrow(SQLException.class);
        assertThrows(DAOException.class, () -> categoryDao.getAll(SELECT_ALL_CATEGORIES));
    }

    private void setStmt() throws SQLException {
        doNothing().when(stmt).setInt(isA(int.class), isA(int.class));
        doNothing().when(stmt).setString(isA(int.class), isA(String.class));
    }

    private void setResultSet() throws SQLException {
        when(resultSet.next()).thenReturn(true).thenReturn(false).thenReturn(true).thenReturn(false);
        when(resultSet.getInt(CATEGORY_ID)).thenReturn(category.getId());
        when(resultSet.getString(CATEGORY_TITLE)).thenReturn(category.getTitle());
        when(resultSet.getString(CATEGORY_DESCRIPTION)).thenReturn(category.getDescription());
    }
}