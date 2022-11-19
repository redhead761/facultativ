package com.epam.facultativ.daos;

import com.epam.facultativ.connection.DataSource;
import com.epam.facultativ.entity.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.epam.facultativ.daos.Constants.*;
import static com.epam.facultativ.daos.Fields.*;

public class UserDao implements Dao<User> {

    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        try (Connection con = DataSource.getConnection();
             Statement stmt = con.createStatement()) {
            ResultSet rs = stmt.executeQuery(SELECT_All_USERS);
            while (rs.next()) {
                users.add(mapRow(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public User findById(int id) {
        User user = null;
        try (Connection con = DataSource.getConnection();
             PreparedStatement stmt = con.prepareStatement(SELECT_USER_BY_ID)) {
            stmt.setString(1, String.valueOf(id));
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                user = mapRow(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return user;
    }

    @Override
    public void update(User user) {
        try (Connection con = DataSource.getConnection();
             PreparedStatement stmt = con.prepareStatement(UPDATE_USER)) {
            int k = 0;
            stmt.setString(++k, user.getLogin());
            stmt.setString(++k, user.getPassword());
            stmt.setString(++k, user.getFirstName());
            stmt.setString(++k, user.getLastName());
            stmt.setString(++k, user.getEmail());
            stmt.setBoolean(++k, user.isBlock());
            stmt.setInt(++k, user.getRole().getId());
            stmt.setString(++k, String.valueOf(user.getId()));

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void insert(User user) {
        try (Connection con = DataSource.getConnection();
             PreparedStatement stmt = con.prepareStatement(INSERT_USER, Statement.RETURN_GENERATED_KEYS)) {
            int k = 0;
            stmt.setString(++k, user.getLogin());
            stmt.setString(++k, user.getPassword());
            stmt.setString(++k, user.getFirstName());
            stmt.setString(++k, user.getLastName());
            stmt.setString(++k, user.getEmail());
            stmt.setBoolean(++k, user.isBlock());
            stmt.setInt(++k, new RoleDao().findById(user.getRole().getId()).getId());

            stmt.executeUpdate();
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    user.setId(generatedKeys.getInt(1));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(User user) {
        try (Connection con = DataSource.getConnection();
             PreparedStatement stmt = con.prepareStatement(DELETE_USER)) {
            stmt.setString(1, String.valueOf(user.getId()));

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public User findByName(String login) {
        User user = null;
        try (Connection con = DataSource.getConnection();
             PreparedStatement stmt = con.prepareStatement(SELECT_USER_BY_LOGIN)) {
            stmt.setString(1, login);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                user = mapRow(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return user;
    }

    public List<User> findUsersByRole(String role) {
        List<User> users = new ArrayList<>();
        try (Connection con = DataSource.getConnection();
             PreparedStatement stmt = con.prepareStatement(SELECT_USERS_BY_ROLE)) {
            stmt.setString(1, String.valueOf(new RoleDao().findByName(role).getId()));
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                users.add(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return users;
    }

    public List<User> usersByCourse(int id) {
        List<User> users = new ArrayList<>();
        try (Connection con = DataSource.getConnection();
             PreparedStatement stmt = con.prepareStatement(SELECT_USERS_COURSE)) {
            stmt.setString(1, String.valueOf(id));
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                users.add(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return users;
    }

    /*
    *******
    helper methods
    *******
     */
    private User mapRow(ResultSet rs) {
        try {
            User user = new User();
            user.setId(rs.getInt(USER_ID));
            user.setLogin(rs.getString(USER_LOGIN));
            user.setPassword(rs.getString(USER_PASSWORD));
            user.setFirstName(rs.getString(USER_FIRST_NAME));
            user.setLastName(rs.getString(USER_FAMILY_NAME));
            user.setEmail(rs.getString(USER_EMAIL));
            user.setBlock(rs.getBoolean(USER_IS_BLOCK));
            user.setRole(new RoleDao().findById(rs.getInt(USER_ROLE_ID)));
            return user;
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }
}
