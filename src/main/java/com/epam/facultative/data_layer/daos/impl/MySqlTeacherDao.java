package com.epam.facultative.data_layer.daos.impl;

import com.epam.facultative.data_layer.daos.TeacherDao;
import com.epam.facultative.data_layer.entities.Role;
import com.epam.facultative.data_layer.entities.Teacher;
import com.epam.facultative.exception.DAOException;
import com.epam.facultative.exception.ValidateException;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.epam.facultative.data_layer.daos.impl.SQLRequestConstants.*;
import static com.epam.facultative.data_layer.daos.impl.FieldsConstants.*;
import static com.epam.facultative.utils.validator.ValidateExceptionMessageConstants.LOE_NOT_UNIQUE_MESSAGE;

public class MySqlTeacherDao implements TeacherDao {
    private final DataSource dataSource;

    public MySqlTeacherDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Optional<Teacher> getById(int id) throws DAOException {
        Teacher teacher = null;
        try (Connection con = dataSource.getConnection();
             PreparedStatement stmt = con.prepareStatement(SELECT_TEACHER_BY_ID)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next())
                teacher = mapRow(rs);
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return Optional.ofNullable(teacher);
    }

    @Override
    public Optional<Teacher> getByName(String name) throws DAOException {
        Teacher teacher = null;
        try (Connection con = dataSource.getConnection();
             PreparedStatement stmt = con.prepareStatement(SELECT_TEACHER_BY_LOGIN)) {
            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();
            if (rs.next())
                teacher = mapRow(rs);
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return Optional.ofNullable(teacher);
    }

    @Override
    public void add(Teacher teacher) throws DAOException, ValidateException {
        Connection con = null;
        PreparedStatement stmt = null;
        try {
            con = dataSource.getConnection();
            stmt = con.prepareStatement(INSERT_USER, Statement.RETURN_GENERATED_KEYS);
            con.setAutoCommit(false);
            setStatementFieldsUser(teacher, stmt);
            int count = stmt.executeUpdate();
            if (count > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        teacher.setId(rs.getInt(1));
                    }
                }
            }
            stmt = con.prepareStatement(INSERT_TEACHER);
            setStatementFieldsTeacher(teacher, stmt);
            stmt.executeUpdate();
            con.commit();
        } catch (SQLIntegrityConstraintViolationException e) {
            rollback(con);
            throw new ValidateException(LOE_NOT_UNIQUE_MESSAGE);
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
            con = dataSource.getConnection();
            stmt = con.prepareStatement(UPDATE_USER);
            con.setAutoCommit(false);
            int k = setStatementFieldsUser(teacher, stmt);
            stmt.setInt(++k, teacher.getId());
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
        try (Connection con = dataSource.getConnection();
             PreparedStatement stmt = con.prepareStatement(DELETE_TEACHER)) {
            int k = 0;
            stmt.setString(++k, String.valueOf(id));
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public Map.Entry<Integer, List<Teacher>> getAllPagination(int offset, int numberOfRows) throws DAOException {
        List<Teacher> teachers = new ArrayList<>();
        int noOfRecords = 0;
        try (Connection con = dataSource.getConnection();
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
            if (rs.next()) {
                noOfRecords = rs.getInt(1);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return Map.entry(noOfRecords, teachers);
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

    private int setStatementFieldsUser(Teacher teacher, PreparedStatement stmt) throws SQLException {
        int k = 0;
        stmt.setString(++k, teacher.getLogin());
        stmt.setString(++k, teacher.getPassword());
        stmt.setString(++k, teacher.getName());
        stmt.setString(++k, teacher.getSurname());
        stmt.setString(++k, teacher.getEmail());
        stmt.setInt(++k, teacher.getRole().getId());
        return k;
    }

    private void setStatementFieldsTeacher(Teacher teacher, PreparedStatement stmt) throws SQLException {
        int k = 0;
        stmt.setInt(++k, teacher.getId());
        stmt.setString(++k, teacher.getDegree());
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
