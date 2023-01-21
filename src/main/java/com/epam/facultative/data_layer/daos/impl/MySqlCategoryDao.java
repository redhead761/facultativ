package com.epam.facultative.data_layer.daos.impl;

import com.epam.facultative.data_layer.daos.CategoryDao;
import com.epam.facultative.data_layer.entities.Category;
import com.epam.facultative.exception.DAOException;
import com.epam.facultative.exception.ValidateException;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.epam.facultative.data_layer.daos.impl.SQLRequestConstants.*;
import static com.epam.facultative.utils.validator.ValidateExceptionMessageConstants.*;

public class MySqlCategoryDao implements CategoryDao {
    private final DataSource dataSource;

    public MySqlCategoryDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Optional<Category> getById(int id) throws DAOException {
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
        return Optional.ofNullable(category);
    }

    @Override
    public Optional<Category> getByName(String name) throws DAOException {
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
        return Optional.ofNullable(category);
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
    public void update(Category category) throws DAOException, ValidateException {
        try (Connection con = dataSource.getConnection();
             PreparedStatement stmt = con.prepareStatement(UPDATE_CATEGORY)) {
            stmt.setString(setStatementFields(category, stmt), String.valueOf(category.getId()));
            stmt.executeUpdate();
        } catch (SQLIntegrityConstraintViolationException e) {
            throw new ValidateException(TITLE_NOT_UNIQUE_MESSAGE);
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public void delete(int id) throws DAOException {
        try (Connection con = dataSource.getConnection();
             PreparedStatement stmt = con.prepareStatement(SQLRequestConstants.DELETE_CATEGORY)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public Map.Entry<Integer, List<Category>> getAll(String param) throws DAOException {
        List<Category> categories = new ArrayList<>();
        int noOfRecords = 0;
        try (Connection con = dataSource.getConnection();
             PreparedStatement stmt = con.prepareStatement(String.format(SELECT_ALL_CATEGORIES_PAGINATION, param))) {
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    categories.add(mapRow(rs));
                }
            }
            ResultSet rs = stmt.executeQuery(SELECT_FOUND_ROWS);
            if (rs.next()) {
                noOfRecords = rs.getInt(1);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return Map.entry(noOfRecords, categories);
    }

    /**
     * Helping methods
     */
    private Category mapRow(ResultSet rs) throws SQLException {
        return Category.builder()
                .id(rs.getInt(FieldsConstants.CATEGORY_ID))
                .title(rs.getString(FieldsConstants.CATEGORY_TITLE))
                .description(rs.getString(FieldsConstants.CATEGORY_DESCRIPTION))
                .build();
    }

    private int setStatementFields(Category category, PreparedStatement stmt) throws SQLException {
        int k = 0;
        stmt.setString(++k, category.getTitle());
        stmt.setString(++k, category.getDescription());
        return ++k;
    }
}
