package com.epam.facultative.daos.impl;

import com.epam.facultative.daos.TeacherDao;
import com.epam.facultative.daos.connection.DataSource;
import com.epam.facultative.entities.Role;
import com.epam.facultative.entities.Teacher;
import com.epam.facultative.exception.DAOException;

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
             Statement stmt = con.createStatement()) {
            ResultSet rs = stmt.executeQuery(SELECT_TEACHER_BY_ID);
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
             Statement stmt = con.createStatement()) {
            ResultSet rs = stmt.executeQuery(SELECT_TEACHER_BY_NAME);
            if (rs.next())
                teacher = mapRow(rs);
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return teacher;
    }

    @Override
    public void add(Teacher teacher) throws DAOException {
        try (Connection con = DataSource.getConnection();
             PreparedStatement stmt = con.prepareStatement(INSERT_TEACHER)) {
            int k = 0;
            stmt.setString(++k, String.valueOf(teacher.getId()));
            stmt.setString(++k, teacher.getDegree());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public void update(Teacher teacher) throws DAOException {
        try (Connection con = DataSource.getConnection();
             PreparedStatement stmt = con.prepareStatement(UPDATE_TEACHER)) {
            int k = 0;
            stmt.setString(++k, teacher.getDegree());
            stmt.setString(++k, String.valueOf(teacher.getId()));
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e);
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
}
