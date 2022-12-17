package com.epam.facultative.daos.impl;

import com.epam.facultative.daos.connection.DataSource;
import com.epam.facultative.daos.UserDao;
import com.epam.facultative.entity.Category;
import com.epam.facultative.entity.Role;
import com.epam.facultative.entity.User;
import com.epam.facultative.exception.DAOException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.epam.facultative.daos.impl.Constants.*;
import static com.epam.facultative.daos.impl.Fields.*;

public class MySqlUserDao implements UserDao {
    private int noOfRecords;

    @Override
    public List<User> getAll() throws DAOException {
        List<User> users = new ArrayList<>();
        try (Connection con = DataSource.getConnection();
             Statement stmt = con.createStatement()) {
            ResultSet rs = stmt.executeQuery(SELECT_All_USERS);
            while (rs.next()) {
                users.add(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return users;
    }

    @Override
    public User getById(int id) throws DAOException {
        User user = null;
        try (Connection con = DataSource.getConnection();
             PreparedStatement stmt = con.prepareStatement(SELECT_USER_BY_ID)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                user = mapRow(rs);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return user;
    }

    @Override
    public User getByName(String name) throws DAOException {
        User user = null;
        try (Connection con = DataSource.getConnection();
             PreparedStatement stmt = con.prepareStatement(SELECT_USER_BY_LOGIN)) {
            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                user = mapRow(rs);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return user;
    }

    @Override
    public void add(User user) throws DAOException {
        try (Connection con = DataSource.getConnection();
             PreparedStatement stmt = con.prepareStatement(INSERT_USER, Statement.RETURN_GENERATED_KEYS)) {
            int k = 0;
            stmt.setString(++k, user.getLogin());
            stmt.setString(++k, user.getPassword());
            stmt.setString(++k, user.getName());
            stmt.setString(++k, user.getSurname());
            stmt.setString(++k, user.getEmail());
            stmt.setBoolean(++k, user.isBlock());
            stmt.setInt(++k, user.getRole().getId());
            stmt.executeUpdate();
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    user.setId(generatedKeys.getInt(1));
                }
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public void update(User user) throws DAOException {
        try (Connection con = DataSource.getConnection();
             PreparedStatement stmt = con.prepareStatement(UPDATE_USER)) {
            int k = 0;
            stmt.setString(++k, user.getLogin());
            stmt.setString(++k, user.getPassword());
            stmt.setString(++k, user.getName());
            stmt.setString(++k, user.getSurname());
            stmt.setString(++k, user.getEmail());
            stmt.setBoolean(++k, user.isBlock());
            stmt.setInt(++k, user.getRole().getId());
            stmt.setString(++k, String.valueOf(user.getId()));
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public void delete(int id) throws DAOException {
        try (Connection con = DataSource.getConnection();
             PreparedStatement stmt = con.prepareStatement(DELETE_USER)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public List<User> getByRole(int roleId) throws DAOException {
        List<User> users = new ArrayList<>();
        try (Connection con = DataSource.getConnection();
             PreparedStatement stmt = con.prepareStatement(SELECT_USERS_BY_ROLE)) {
            stmt.setInt(1, roleId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                users.add(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return users;
    }

    @Override
    public List<User> getByRolePagination(int roleId, int offset, int numberOfRows) throws DAOException {
        List<User> users = new ArrayList<>();
        try (Connection con = DataSource.getConnection();
             PreparedStatement stmt = con.prepareStatement("SELECT SQL_CALC_FOUND_ROWS user.id, user.login, user.password, user.name, user.surname, user.email, user.block, role.name AS role_name " +
                     "FROM user  JOIN role ON role.id = user.role_id WHERE user.role_id=? LIMIT ?,?")) {
            int k = 0;
            stmt.setInt(++k, roleId);
            stmt.setInt(++k, offset);
            stmt.setInt(++k, numberOfRows);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                users.add(mapRow(rs));
            }
            rs.close();

            rs = stmt.executeQuery("SELECT FOUND_ROWS()");
            if (rs.next())
                this.noOfRecords = rs.getInt(1);

        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return users;
    }

    @Override
    public List<User> getUsersByCourse(int courseId) throws DAOException {
        List<User> users = new ArrayList<>();
        try (Connection con = DataSource.getConnection();
             PreparedStatement stmt = con.prepareStatement(SELECT_USERS_COURSE)) {
            stmt.setInt(1, courseId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                users.add(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return users;
    }

    @Override
    public void blockUnblockStudent(int userId, boolean block) throws DAOException {
        try (Connection con = DataSource.getConnection();
             PreparedStatement stmt = con.prepareStatement(BLOCK_UNBLOCK_USER)) {
            int k = 0;
            stmt.setBoolean(++k, block);
            stmt.setInt(++k, userId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }


    @Override
    public int getNoOfRecords() {
        return noOfRecords;
    }

    private User mapRow(ResultSet rs) throws DAOException {
        try {
            User user = new User();
            user.setId(rs.getInt(USER_ID));
            user.setLogin(rs.getString(USER_LOGIN));
            user.setPassword(rs.getString(USER_PASSWORD));
            user.setName(rs.getString(USER_FIRST_NAME));
            user.setSurname(rs.getString(USER_FAMILY_NAME));
            user.setEmail(rs.getString(USER_EMAIL));
            user.setBlock(rs.getBoolean(USER_IS_BLOCK));
            user.setRole(Role.valueOf(rs.getString(USER_ROLE_NAME)));
            return user;
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }
}
