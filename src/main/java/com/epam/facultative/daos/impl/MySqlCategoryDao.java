package com.epam.facultative.daos.impl;

import com.epam.facultative.daos.CategoryDao;
import com.epam.facultative.entities.Category;
import com.epam.facultative.exception.DAOException;
import com.epam.facultative.exception.ValidateException;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.epam.facultative.daos.impl.SQLRequestConstants.*;
import static com.epam.facultative.daos.impl.FieldsConstants.*;
import static com.epam.facultative.utils.validator.ValidateExceptionMessageConstants.*;

public class MySqlCategoryDao implements CategoryDao {
    private final DataSource dataSource;
    private int noOfRecords;

    public MySqlCategoryDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<Category> getAll() throws DAOException {
        List<Category> categories = new ArrayList<>();
        try (Connection con = dataSource.getConnection();
             Statement stmt = con.createStatement()) {
            ResultSet rs = stmt.executeQuery(SELECT_All_CATEGORIES);
            while (rs.next()) {
                categories.add(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return categories;
    }

    @Override
    public Category getById(int id) throws DAOException {
        Category category = null;
        try (Connection con = dataSource.getConnection();
             PreparedStatement stmt = con.prepareStatement(SELECT_CATEGORY_BY_ID)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                category = mapRow(rs);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return category;
    }

    @Override
    public Category getByName(String name) throws DAOException {
        Category category = null;
        try (Connection con = dataSource.getConnection();
             PreparedStatement stmt = con.prepareStatement(SELECT_CATEGORY_BY_TITLE)) {
            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                category = mapRow(rs);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return category;
    }

    @Override
    public void add(Category category) throws DAOException, ValidateException {
        try (Connection con = dataSource.getConnection();
             PreparedStatement stmt = con.prepareStatement(INSERT_CATEGORY)) {
            setStatementFields(category, stmt);
            stmt.executeUpdate();
        } catch (SQLIntegrityConstraintViolationException e) {
            throw new ValidateException(TITLE_NOT_UNIQUE_MESSAGE);
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public void update(Category category) throws DAOException {
        try (Connection con = dataSource.getConnection();
             PreparedStatement stmt = con.prepareStatement(UPDATE_CATEGORY)) {
            stmt.setString(setStatementFields(category, stmt), String.valueOf(category.getId()));
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public void delete(int id) throws DAOException {
        try (Connection con = dataSource.getConnection();
             PreparedStatement stmt = con.prepareStatement(DELETE_CATEGORY)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public List<Category> getAllPagination(int offset, int numberOfRows) throws DAOException {
        List<Category> categories = new ArrayList<>();
        try (Connection con = dataSource.getConnection();
             PreparedStatement stmt = con.prepareStatement(SELECT_ALL_CATEGORIES_PAGINATION)) {
            int k = 0;
            stmt.setInt(++k, offset);
            stmt.setInt(++k, numberOfRows);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                categories.add(mapRow(rs));
            }
            rs.close();
            rs = stmt.executeQuery(SELECT_FOUND_ROWS);
            if (rs.next())
                this.noOfRecords = rs.getInt(1);
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return categories;
    }

    @Override
    public int getNoOfRecords() {
        return noOfRecords;
    }

    /**
     * Helping methods
     */
    private Category mapRow(ResultSet rs) throws SQLException {
        return Category.builder()
                .id(rs.getInt(CATEGORY_ID))
                .title(rs.getString(CATEGORY_TITLE))
                .description(rs.getString(CATEGORY_DESCRIPTION))
                .build();
    }

    private int setStatementFields(Category category, PreparedStatement stmt) throws SQLException {
        int k = 0;
        stmt.setString(++k, category.getTitle());
        stmt.setString(++k, category.getDescription());
        return ++k;
    }
}
