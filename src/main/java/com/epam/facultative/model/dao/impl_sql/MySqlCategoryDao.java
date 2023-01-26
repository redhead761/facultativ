package com.epam.facultative.model.dao.impl_sql;

import com.epam.facultative.model.dao.CategoryDao;
import com.epam.facultative.model.dao.сonstants.FieldsConstants;
import com.epam.facultative.model.dao.сonstants.SQLRequestConstants;
import com.epam.facultative.model.entities.Category;
import com.epam.facultative.model.exception.DAOException;
import com.epam.facultative.model.exception.ValidateException;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.epam.facultative.model.utils.validator.ValidateExceptionMessageConstants.CAN_NOT_DELETE_CATEGORY;
import static com.epam.facultative.model.utils.validator.ValidateExceptionMessageConstants.TITLE_NOT_UNIQUE_MESSAGE;

public class MySqlCategoryDao implements CategoryDao {
    private final DataSource dataSource;

    public MySqlCategoryDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Optional<Category> get(String param) throws DAOException {
        Category category = null;
        try (Connection con = dataSource.getConnection();
             PreparedStatement stmt = con.prepareStatement(String.format(SQLRequestConstants.SELECT_CATEGORY, param))) {
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
             PreparedStatement stmt = con.prepareStatement(SQLRequestConstants.INSERT_CATEGORY)) {
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
             PreparedStatement stmt = con.prepareStatement(SQLRequestConstants.UPDATE_CATEGORY)) {
            stmt.setString(setStatementFields(category, stmt), String.valueOf(category.getId()));
            stmt.executeUpdate();
        } catch (SQLIntegrityConstraintViolationException e) {
            throw new ValidateException(TITLE_NOT_UNIQUE_MESSAGE);
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public void delete(int id) throws DAOException, ValidateException {
        try (Connection con = dataSource.getConnection();
             PreparedStatement stmt = con.prepareStatement(SQLRequestConstants.DELETE_CATEGORY)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLIntegrityConstraintViolationException e) {
            throw new ValidateException(CAN_NOT_DELETE_CATEGORY);
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public Map.Entry<Integer, List<Category>> getAll(String param) throws DAOException {
        List<Category> categories = new ArrayList<>();
        int noOfRecords = 0;
        try (Connection con = dataSource.getConnection();
             PreparedStatement stmt = con.prepareStatement(String.format(SQLRequestConstants.SELECT_ALL_CATEGORIES_PAGINATION, param))) {
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    categories.add(mapRow(rs));
                }
            }
            ResultSet rs = stmt.executeQuery(SQLRequestConstants.SELECT_FOUND_ROWS);
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
