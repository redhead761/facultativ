package com.epam.facultative.data_layer.daos.impl;

import com.epam.facultative.data_layer.daos.UserDao;
import com.epam.facultative.data_layer.entities.Role;
import com.epam.facultative.data_layer.entities.User;
import com.epam.facultative.exception.DAOException;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class MySqlUserDao implements UserDao {
    private final DataSource dataSource;

    public MySqlUserDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Optional<User> getById(int id) throws DAOException {
        User user = null;
        try (Connection con = dataSource.getConnection();
             PreparedStatement stmt = con.prepareStatement(SQLRequestConstants.SELECT_USER_BY_ID)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                user = mapRow(rs);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return Optional.ofNullable(user);
    }

    @Override
    public Optional<User> getByName(String name) throws DAOException {
        User user = null;
        try (Connection con = dataSource.getConnection();
             PreparedStatement stmt = con.prepareStatement(SQLRequestConstants.SELECT_USER_BY_LOGIN)) {
            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                user = mapRow(rs);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return Optional.ofNullable(user);
    }

    @Override
    public void add(User user) throws DAOException {
        try (Connection con = dataSource.getConnection();
             PreparedStatement stmt = con.prepareStatement(SQLRequestConstants.INSERT_USER)) {
            setStatementFields(user, stmt);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public void update(User user) throws DAOException {
        try (Connection con = dataSource.getConnection();
             PreparedStatement stmt = con.prepareStatement(SQLRequestConstants.UPDATE_USER)) {
            stmt.setString(setStatementFields(user, stmt), String.valueOf(user.getId()));
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public void delete(int id) throws DAOException {
        try (Connection con = dataSource.getConnection();
             PreparedStatement stmt = con.prepareStatement(SQLRequestConstants.DELETE_USER)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public Map.Entry<Integer, List<User>> getAll(String param) throws DAOException {
        return null;
    }

    /**
     * Helping methods
     */

    private User mapRow(ResultSet rs) throws SQLException {
        return User.builder()
                .id(rs.getInt(FieldsConstants.USER_ID))
                .login(rs.getString(FieldsConstants.USER_LOGIN))
                .password(rs.getString(FieldsConstants.USER_PASSWORD))
                .name(rs.getString(FieldsConstants.USER_FIRST_NAME))
                .surname(rs.getString(FieldsConstants.USER_FAMILY_NAME))
                .email(rs.getString(FieldsConstants.USER_EMAIL))
                .role(Role.valueOf(rs.getString(FieldsConstants.ROLE_NAME)))
                .build();
    }

    private int setStatementFields(User user, PreparedStatement stmt) throws SQLException {
        int k = 0;
        stmt.setString(++k, user.getLogin());
        stmt.setString(++k, user.getPassword());
        stmt.setString(++k, user.getName());
        stmt.setString(++k, user.getSurname());
        stmt.setString(++k, user.getEmail());
        stmt.setInt(++k, user.getRole().getId());
        return ++k;
    }
}
