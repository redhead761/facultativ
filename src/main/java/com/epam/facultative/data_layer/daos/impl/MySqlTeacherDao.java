package com.epam.facultative.data_layer.daos.impl;

import com.epam.facultative.data_layer.daos.TeacherDao;
import com.epam.facultative.data_layer.entities.Role;
import com.epam.facultative.data_layer.entities.Teacher;
import com.epam.facultative.exception.DAOException;
import com.epam.facultative.exception.ValidateException;

import javax.sql.DataSource;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.*;

import static com.epam.facultative.data_layer.daos.impl.SQLRequestConstants.*;
import static com.epam.facultative.data_layer.daos.impl.FieldsConstants.*;
import static com.epam.facultative.utils.validator.ValidateExceptionMessageConstants.*;

public class MySqlTeacherDao implements TeacherDao {
    private final DataSource dataSource;

    public MySqlTeacherDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Optional<Teacher> get(String param) throws DAOException {
        Teacher teacher = null;
        try (Connection con = dataSource.getConnection();
             PreparedStatement stmt = con.prepareStatement(String.format(SELECT_TEACHER, param))) {
            ResultSet rs = stmt.executeQuery();
            if (rs.next())
                teacher = mapRow(rs);
        } catch (SQLException | IOException e) {
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
    public void update(Teacher teacher) throws DAOException, ValidateException {
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
    public void delete(int id) throws DAOException, ValidateException {
        Connection con = null;
        PreparedStatement stmt = null;
        try {
            con = dataSource.getConnection();
            stmt = con.prepareStatement(DELETE_TEACHER);
            con.setAutoCommit(false);
            stmt.setInt(1, id);
            stmt.executeUpdate();
            stmt = con.prepareStatement(DELETE_USER);
            stmt.setInt(1, id);
            stmt.executeUpdate();
            con.commit();
        } catch (SQLIntegrityConstraintViolationException e) {
            rollback(con);
            throw new ValidateException(CAN_NOT_DELETE_TEACHER);
        } catch (SQLException e) {
            rollback(con);
            throw new DAOException(e);
        } finally {
            close(stmt);
            close(con);
        }
    }

    @Override
    public Map.Entry<Integer, List<Teacher>> getAll(String param) throws DAOException {
        List<Teacher> teachers = new ArrayList<>();
        int noOfRecords = 0;
        try (Connection con = dataSource.getConnection();
             PreparedStatement stmt = con.prepareStatement(String.format(SELECT_ALL_TEACHERS_PAGINATION, param))) {
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    teachers.add(mapRow(rs));
                }
            }
            ResultSet rs = stmt.executeQuery(SELECT_FOUND_ROWS);
            if (rs.next()) {
                noOfRecords = rs.getInt(1);
            }
        } catch (SQLException | IOException e) {
            throw new DAOException(e);
        }
        return Map.entry(noOfRecords, teachers);
    }

    /**
     * Helping methods
     */
    private Teacher mapRow(ResultSet rs) throws SQLException, IOException {
        String avatar = null;
        if (rs.getBlob(USER_AVATAR) != null) {
            Blob blob = rs.getBlob(USER_AVATAR);
            InputStream inputStream = blob.getBinaryStream();
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[4096];
            int bytesRead = -1;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            byte[] imageBytes = outputStream.toByteArray();
            avatar = Base64.getEncoder().encodeToString(imageBytes);
        }

        return Teacher.builder()
                .id(rs.getInt(TEACHER_ID))
                .login(rs.getString(USER_LOGIN))
                .password(rs.getString(USER_PASSWORD))
                .name(rs.getString(USER_FIRST_NAME))
                .surname(rs.getString(USER_FAMILY_NAME))
                .email(rs.getString(USER_EMAIL))
                .role(Role.TEACHER)
                .degree(rs.getString(TEACHER_DEGREE))
                .avatar(avatar)
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
