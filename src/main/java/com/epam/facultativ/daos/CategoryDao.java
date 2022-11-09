package com.epam.facultativ.daos;

import com.epam.facultativ.DataSource;
import com.epam.facultativ.entity.Category;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.epam.facultativ.daos.Fields.*;

public class CategoryDao implements Dao<Category> {

    private static final String SELECT_All_CATEGORIES = "SELECT * FROM categories";
    private static final String SELECT_CATEGORY_BY_ID = "SELECT * FROM categories WHERE id=?";
    private static final String UPDATE_CATEGORY = "UPDATE categories SET title=?, description=? WHERE id=?";
    private static final String INSERT_CATEGORY = "INSERT INTO categories VALUES (DEFAULT,?,?)";
    private static final String DELETE_CATEGORY = "DELETE FROM categories WHERE id=?";

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
    public void insert(Category category) {
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
