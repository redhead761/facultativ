package com.epam.facultativ.daos;

import com.epam.facultativ.DataSource;
import com.epam.facultativ.entity.Role;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.epam.facultativ.daos.Fields.*;

public class RoleDao implements Dao<Role> {

    private static final String SELECT_All_ROLES = "SELECT * FROM roles";
    private static final String SELECT_ROLE_BY_ID = "SELECT * FROM roles WHERE id=?";
    private static final String UPDATE_ROLE = "UPDATE roles SET title=? WHERE id=?";
    private static final String INSERT_ROLE = "INSERT INTO roles VALUES (DEFAULT,?)";
    private static final String DELETE_ROLE = "DELETE FROM roles WHERE id=?";


    @Override
    public List<Role> findAll() {
        List<Role> roles = new ArrayList<>();
        try (Connection con = DataSource.getConnection();
             Statement stmt = con.createStatement()) {
            ResultSet rs = stmt.executeQuery(SELECT_All_ROLES);
            while (rs.next()) {
                roles.add(mapRow(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return roles;
    }

    @Override
    public Role findById(int id) {
        Role role = null;
        try (Connection con = DataSource.getConnection();
             PreparedStatement stmt = con.prepareStatement(SELECT_ROLE_BY_ID)) {
            stmt.setString(1, String.valueOf(id));
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                role = mapRow(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return role;
    }

    @Override
    public void update(Role role) {
        try (Connection con = DataSource.getConnection();
             PreparedStatement stmt = con.prepareStatement(UPDATE_ROLE)) {
            int k = 0;
            stmt.setString(++k, role.getTitle());
            stmt.setString(++k, String.valueOf(role.getId()));
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void insert(Role role) {
        try (Connection con = DataSource.getConnection();
             PreparedStatement stmt = con.prepareStatement(INSERT_ROLE, Statement.RETURN_GENERATED_KEYS)) {
            int k = 0;
            stmt.setString(++k, role.getTitle());
            stmt.executeUpdate();
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    role.setId(generatedKeys.getInt(1));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Role role) {
        try (Connection con = DataSource.getConnection();
             PreparedStatement stmt = con.prepareStatement(DELETE_ROLE)) {
            stmt.setString(1, String.valueOf(role.getId()));
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
    private Role mapRow(ResultSet rs) {
        try {
            Role role = new Role();
            role.setId(rs.getInt(ROLE_ID));
            role.setTitle(rs.getString(ROLE_TITLE));
            return role;
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }
}
