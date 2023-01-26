package com.epam.facultative.data_layer.daos.impl;

import com.epam.facultative.data_layer.daos.UserDao;
import com.epam.facultative.data_layer.entities.Role;
import com.epam.facultative.data_layer.entities.User;
import com.epam.facultative.exception.DAOException;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.*;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.epam.facultative.data_layer.daos.impl.SQLRequestConstants.*;

public class MySqlUserDao implements UserDao {
    private final DataSource dataSource;

    public MySqlUserDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Optional<User> get(String param) throws DAOException {
        System.out.println("Int get PARAM =" + param);
        User user = null;
        try (Connection con = dataSource.getConnection();
             PreparedStatement stmt = con.prepareStatement(String.format(SELECT_USER, param))) {
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
             PreparedStatement stmt = con.prepareStatement(INSERT_USER)) {
            setStatementFields(user, stmt);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public void update(User user) throws DAOException {
        try (Connection con = dataSource.getConnection();
             PreparedStatement stmt = con.prepareStatement(UPDATE_USER)) {
            stmt.setString(setStatementFields(user, stmt), String.valueOf(user.getId()));
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public void delete(int id) throws DAOException {
        try (Connection con = dataSource.getConnection();
             PreparedStatement stmt = con.prepareStatement(DELETE_USER)) {
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

    @Override
    public void addAvatar(int userId, InputStream avatar) throws DAOException {
        try (Connection con = dataSource.getConnection();
             PreparedStatement stmt = con.prepareStatement(ADD_AVATAR)) {
            int k = 0;
            stmt.setBlob(++k, avatar);
            stmt.setInt(++k, userId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e);
        }
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
