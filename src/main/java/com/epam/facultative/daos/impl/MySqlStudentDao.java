package com.epam.facultative.daos.impl;

import com.epam.facultative.daos.StudentDao;
import com.epam.facultative.daos.connection.DataSource;
import com.epam.facultative.entities.Role;
import com.epam.facultative.entities.Student;
import com.epam.facultative.exception.DAOException;
import com.epam.facultative.exception.ValidateException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.epam.facultative.daos.impl.Constants.*;
import static com.epam.facultative.daos.impl.Fields.*;

public class MySqlStudentDao implements StudentDao {
    private int noOfRecords;

    @Override
    public List<Student> getAll() throws DAOException {
        List<Student> students = new ArrayList<>();
        try (Connection con = DataSource.getConnection();
             Statement stmt = con.createStatement()) {
            ResultSet rs = stmt.executeQuery(SELECT_ALL_STUDENTS);
            while (rs.next()) {
                students.add(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return students;
    }

    @Override
    public Student getById(int id) throws DAOException {
        Student student = null;
        try (Connection con = DataSource.getConnection();
             PreparedStatement stmt = con.prepareStatement(SELECT_STUDENT_BY_ID)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next())
                student = mapRow(rs);
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return student;
    }

    @Override
    public Student getByName(String name) throws DAOException {
        Student student = null;
        try (Connection con = DataSource.getConnection();
             PreparedStatement stmt = con.prepareStatement(SELECT_STUDENT_BY_LOGIN)) {
            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();
            if (rs.next())
                student = mapRow(rs);
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return student;
    }

    @Override
    public void add(Student student) throws DAOException, ValidateException {
        Connection con = null;
        PreparedStatement stmt = null;
        try {
            con = DataSource.getConnection();
            stmt = con.prepareStatement(INSERT_USER, Statement.RETURN_GENERATED_KEYS);
            con.setAutoCommit(false);
            int k = 0;
            stmt.setString(++k, student.getLogin());
            stmt.setString(++k, student.getPassword());
            stmt.setString(++k, student.getName());
            stmt.setString(++k, student.getSurname());
            stmt.setString(++k, student.getEmail());
            stmt.setInt(++k, student.getRole().getId());
            int count = stmt.executeUpdate();
            if (count > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        student.setId(rs.getInt(1));
                    }
                }
            }
            stmt = con.prepareStatement(INSERT_STUDENT);
            k = 0;
            stmt.setInt(++k, student.getId());
            stmt.setInt(++k, student.getCourseNumber());
            stmt.setBoolean(++k, student.isBlock());
            stmt.executeUpdate();
            con.commit();
        } catch (SQLIntegrityConstraintViolationException e) {
            rollback(con);
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
    public void update(Student student) throws DAOException {
        Connection con = null;
        PreparedStatement stmt = null;
        try {
            con = DataSource.getConnection();
            con.setAutoCommit(false);
            stmt = con.prepareStatement(UPDATE_USER);
            int k = 0;
            stmt.setString(++k, student.getLogin());
            stmt.setString(++k, student.getPassword());
            stmt.setString(++k, student.getName());
            stmt.setString(++k, student.getSurname());
            stmt.setString(++k, student.getEmail());
            stmt.setInt(++k, student.getRole().getId());
            stmt.executeUpdate();
            stmt = con.prepareStatement(UPDATE_STUDENT);
            k = 0;
            stmt.setInt(++k, student.getCourseNumber());
            stmt.setBoolean(++k, student.isBlock());
            stmt.setInt(++k, student.getId());
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
             PreparedStatement stmt = con.prepareStatement(DELETE_STUDENT)) {
            int k = 0;
            stmt.setString(++k, String.valueOf(id));
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public List<Student> getAllPagination(int offset, int numberOfRows) throws DAOException {
        List<Student> students = new ArrayList<>();
        try (Connection con = DataSource.getConnection();
             PreparedStatement stmt = con.prepareStatement(SELECT_ALL_STUDENTS_PAGINATION)) {
            int k = 0;
            stmt.setInt(++k, offset);
            stmt.setInt(++k, numberOfRows);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                students.add(mapRow(rs));
            }
            rs.close();
            rs = stmt.executeQuery(SELECT_FOUND_ROWS);
            if (rs.next())
                this.noOfRecords = rs.getInt(1);
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return students;
    }

    @Override
    public List<Student> getStudentsByCourse(int courseId, int offset, int numberOfRows) throws DAOException {
        List<Student> students = new ArrayList<>();
        try (Connection con = DataSource.getConnection();
             PreparedStatement stmt = con.prepareStatement(SELECT_STUDENTS_BY_COURSE)) {
            int k = 0;
            stmt.setInt(++k, courseId);
            stmt.setInt(++k, offset);
            stmt.setInt(++k, numberOfRows);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Student student = mapRow(rs);
                student.setGrade(rs.getInt(GRADE));
                students.add(student);
            }
            rs.close();
            rs = stmt.executeQuery(SELECT_FOUND_ROWS);
            if (rs.next())
                this.noOfRecords = rs.getInt(1);
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return students;
    }

    @Override
    public void updateBlock(int studentId, boolean block) throws DAOException {
        try (Connection con = DataSource.getConnection();
             PreparedStatement stmt = con.prepareStatement(UPDATE_BLOCK)) {
            int k = 0;
            stmt.setBoolean(++k, block);
            stmt.setInt(++k, studentId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e);
        }

    }

    @Override
    public int getGrade(int courseId, int studentId) throws DAOException {
        int grade = -1;
        try (Connection con = DataSource.getConnection();
             PreparedStatement stmt = con.prepareStatement(SELECT_GRADE)) {
            int k = 0;
            stmt.setInt(++k, courseId);
            stmt.setInt(++k, studentId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next())
                grade = rs.getInt(GRADE);
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return grade;
    }

    @Override
    public int getNoOfRecords() {
        return noOfRecords;
    }

    /**
     * Helping methods
     */
    private Student mapRow(ResultSet rs) throws SQLException {
        return Student.builder()
                .id(rs.getInt(STUDENT_ID))
                .login(rs.getString(USER_LOGIN))
                .password(rs.getString(USER_PASSWORD))
                .name(rs.getString(USER_FIRST_NAME))
                .surname(rs.getString(USER_FAMILY_NAME))
                .email(rs.getString(USER_EMAIL))
                .courseNumber(rs.getInt(STUDENT_COURSE_NUMBER))
                .block(rs.getBoolean(STUDENT_BLOCK))
                .role(Role.STUDENT)
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
