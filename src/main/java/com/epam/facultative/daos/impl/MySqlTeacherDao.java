package com.epam.facultative.daos.impl;

import com.epam.facultative.daos.TeacherDao;
import com.epam.facultative.daos.connection.DataSource;
import com.epam.facultative.entities.Role;
import com.epam.facultative.entities.Teacher;
import com.epam.facultative.exception.DAOException;
import com.epam.facultative.exception.ValidateException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.epam.facultative.daos.impl.Constants.*;
import static com.epam.facultative.daos.impl.Fields.*;

public class MySqlTeacherDao implements TeacherDao {
    private int noOfRecords;

    @Override
    public List<Teacher> getAll() throws DAOException {
        List<Teacher> teachers = new ArrayList<>();
        try (Connection con = DataSource.getConnection();
             Statement stmt = con.createStatement()) {
            ResultSet rs = stmt.executeQuery(SELECT_ALL_TEACHERS);
            while (rs.next()) {
                teachers.add(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return teachers;
    }

    @Override
    public Teacher getById(int id) throws DAOException {
        Teacher teacher = null;
        try (Connection con = DataSource.getConnection();
             PreparedStatement stmt = con.prepareStatement(SELECT_TEACHER_BY_ID)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next())
                teacher = mapRow(rs);
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return teacher;
    }

    @Override
    public Teacher getByName(String name) throws DAOException {
        Teacher teacher = null;
        try (Connection con = DataSource.getConnection();
             PreparedStatement stmt = con.prepareStatement(SELECT_TEACHER_BY_LOGIN)) {
            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();
            if (rs.next())
                teacher = mapRow(rs);
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return teacher;
    }

    @Override
    public void add(Teacher teacher) throws DAOException, ValidateException {
        Connection con = null;
        PreparedStatement stmt = null;
        try {
            con = DataSource.getConnection();
            stmt = con.prepareStatement(INSERT_USER, Statement.RETURN_GENERATED_KEYS);
            con.setAutoCommit(false);
            int k = 0;
            stmt.setString(++k, teacher.getLogin());
            stmt.setString(++k, teacher.getPassword());
            stmt.setString(++k, teacher.getName());
            stmt.setString(++k, teacher.getSurname());
            stmt.setString(++k, teacher.getEmail());
            stmt.setInt(++k, teacher.getRole().getId());
            int count = stmt.executeUpdate();
            if (count > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        teacher.setId(rs.getInt(1));
                    }
                }
            }
            stmt = con.prepareStatement(INSERT_TEACHER);
            k = 0;
            stmt.setInt(++k, teacher.getId());
            stmt.setString(++k, teacher.getDegree());
            stmt.executeUpdate();
            con.commit();
        } catch (SQLIntegrityConstraintViolationException e) {
            throw new ValidateException("Title or email not unique");
        } catch (SQLException e) {
            rollback(con);
            throw new DAOException(e);
        } finally {
            close(stmt);
            close(con);
        }
    }

    @Override
    public void update(Teacher teacher) throws DAOException {
        Connection con = null;
        PreparedStatement stmt = null;
        try {
            con = DataSource.getConnection();
            stmt = con.prepareStatement(UPDATE_USER);
            con.setAutoCommit(false);
            int k = 0;
            stmt.setString(++k, teacher.getLogin());
            stmt.setString(++k, teacher.getPassword());
            stmt.setString(++k, teacher.getName());
            stmt.setString(++k, teacher.getSurname());
            stmt.setString(++k, teacher.getEmail());
            stmt.setInt(++k, teacher.getRole().getId());
            stmt.executeUpdate();
            stmt = con.prepareStatement(UPDATE_TEACHER);
            k = 0;
            stmt.setString(++k, teacher.getDegree());
            stmt.setInt(++k, teacher.getId());
            stmt.executeUpdate();
            con.commit();
        } catch (SQLException e) {
            rollback(con);
            throw new DAOException(e);
        } finally {
            close(stmt);
            close(con);
        }
    }

    @Override
    public void delete(int id) throws DAOException {
        try (Connection con = DataSource.getConnection();
             PreparedStatement stmt = con.prepareStatement(DELETE_TEACHER)) {
            int k = 0;
            stmt.setString(++k, String.valueOf(id));
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public List<Teacher> getAllPagination(int offset, int numberOfRows) throws DAOException {
        List<Teacher> teachers = new ArrayList<>();
        try (Connection con = DataSource.getConnection();
             PreparedStatement stmt = con.prepareStatement(SELECT_ALL_TEACHERS_PAGINATION)) {
            int k = 0;
            stmt.setInt(++k, offset);
            stmt.setInt(++k, numberOfRows);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                teachers.add(mapRow(rs));
            }
            rs.close();
            rs = stmt.executeQuery(SELECT_FOUND_ROWS);
            if (rs.next())
                this.noOfRecords = rs.getInt(1);
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return teachers;
    }

    @Override
    public int getNoOfRecords() {
        return noOfRecords;
    }


    /**
     * Helping methods
     */
    private Teacher mapRow(ResultSet rs) throws SQLException {
        return Teacher.builder()
                .id(rs.getInt(TEACHER_ID))
                .login(rs.getString(USER_LOGIN))
                .password(rs.getString(USER_PASSWORD))
                .name(rs.getString(USER_FIRST_NAME))
                .surname(rs.getString(USER_FAMILY_NAME))
                .email(rs.getString(USER_EMAIL))
                .role(Role.TEACHER)
                .degree(rs.getString(TEACHER_DEGREE))
                .build();
    }

    private void close(AutoCloseable stmt) throws DAOException {
        if (stmt != null) {
            try {
                stmt.close();
            } catch (Exception e) {
                throw new DAOException(e);
            }
        }
    }

    private void rollback(Connection con) throws DAOException {
        if (con != null) {
            try {
                con.rollback();
            } catch (SQLException e) {
                throw new DAOException(e);
            }
        }
    }
}
