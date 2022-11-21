package com.epam.facultative.daos.impl;

import com.epam.facultative.connection.DataSource;
import com.epam.facultative.daos.Dao;
import com.epam.facultative.entity.Category;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.epam.facultative.daos.impl.Constants.*;
import static com.epam.facultative.daos.impl.Fields.*;

public class CategoryDao implements Dao<Category> {

    @Override
    public List<Category> findAll() {
        List<Category> categories = new ArrayList<>();
        try (Connection con = DataSource.getConnection();
             Statement stmt = con.createStatement()) {
            ResultSet rs = stmt.executeQuery(SELECT_All_CATEGORIES);
            while (rs.next()) {
                categories.add(mapRow(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categories;
    }

    @Override
    public Category findById(int id) {
        Category category = null;
        try (Connection con = DataSource.getConnection();
             PreparedStatement stmt = con.prepareStatement(SELECT_CATEGORY_BY_ID)) {
            stmt.setString(1, String.valueOf(id));
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                category = mapRow(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return category;
    }

    @Override
    public void update(Category category) {
        try (Connection con = DataSource.getConnection();
             PreparedStatement stmt = con.prepareStatement(UPDATE_CATEGORY)) {
            int k = 0;
            stmt.setString(++k, category.getTitle());
            stmt.setString(++k, category.getDescription());
            stmt.setString(++k, String.valueOf(category.getId()));
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void add(Category category) {
        try (Connection con = DataSource.getConnection();
             PreparedStatement stmt = con.prepareStatement(INSERT_CATEGORY, Statement.RETURN_GENERATED_KEYS)) {
            int k = 0;
            stmt.setString(++k, category.getTitle());
            stmt.setString(++k, category.getDescription());
            stmt.executeUpdate();
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    category.setId(generatedKeys.getInt(1));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Category category) {
        try (Connection con = DataSource.getConnection();
             PreparedStatement stmt = con.prepareStatement(DELETE_CATEGORY)) {
            stmt.setString(1, String.valueOf(category.getId()));
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Category findByName(String title) {
        Category category = null;
        try (Connection con = DataSource.getConnection();
             PreparedStatement stmt = con.prepareStatement(SELECT_CATEGORY_BY_TITLE)) {
            stmt.setString(1, title);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                category = mapRow(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return category;
    }

    /*
   *******
   helper methods
   *******
    */
    private Category mapRow(ResultSet rs) {
        try {
            Category category = new Category();
            category.setId(rs.getInt(CATEGORY_ID));
            category.setTitle(rs.getString(CATEGORY_TITLE));
            category.setDescription(rs.getString(CATEGORY_DESCRIPTION));
            return category;
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }
}
