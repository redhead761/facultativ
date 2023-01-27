package com.epam.facultative.model.dao.impl_sql;

import com.epam.facultative.model.dao.CourseDao;
import com.epam.facultative.model.entities.*;
import com.epam.facultative.model.exception.DAOException;
import com.epam.facultative.model.exception.ValidateException;

import javax.sql.DataSource;
import java.sql.*;
import java.sql.Date;
import java.util.*;

import static com.epam.facultative.model.dao.сonstants.SQLRequestConstants.*;
import static com.epam.facultative.model.dao.сonstants.FieldsConstants.*;
import static com.epam.facultative.model.exception.ConstantsValidateMessage.TITLE_NOT_UNIQUE_MESSAGE;

public class MySqlCourseDao implements CourseDao {
    private final DataSource dataSource;

    public MySqlCourseDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Optional<Course> get(String param) throws DAOException {
        Course course = null;
        try (Connection con = dataSource.getConnection();
             PreparedStatement stmt = con.prepareStatement(String.format(SELECT_COURSE, param))) {
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                course = mapRowCourse(rs);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return Optional.ofNullable(course);
    }

    @Override
    public void add(Course course) throws DAOException, ValidateException {
        try (Connection con = dataSource.getConnection();
             PreparedStatement stmt = con.prepareStatement(INSERT_COURSE)) {
            setStatementFields(course, stmt);
            stmt.executeUpdate();
        } catch (SQLIntegrityConstraintViolationException e) {
            throw new ValidateException(TITLE_NOT_UNIQUE_MESSAGE);
        } catch (SQLException e) {
            throw new DAOException();
        }
    }

    @Override
    public void update(Course course) throws DAOException, ValidateException {
        try (Connection con = dataSource.getConnection();
             PreparedStatement stmt = con.prepareStatement(UPDATE_COURSE)) {
            int k = setStatementFields(course, stmt);
            if (course.getTeacher() != null) {
                stmt.setInt(++k, course.getTeacher().getId());
            } else {
                stmt.setNull(++k, Types.INTEGER);
            }
            stmt.setString(++k, String.valueOf(course.getId()));
            stmt.executeUpdate();
        } catch (SQLIntegrityConstraintViolationException e) {
            throw new ValidateException(TITLE_NOT_UNIQUE_MESSAGE);
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public void delete(int id) throws DAOException {
        try (Connection con = dataSource.getConnection();
             PreparedStatement stmt = con.prepareStatement(DELETE_COURSE)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public Map.Entry<Integer, List<Course>> getByJournal(String param) throws DAOException {
        List<Course> courses = new ArrayList<>();
        int noOfRecords;
        try (Connection con = dataSource.getConnection();
             PreparedStatement stmt = con.prepareStatement(String.format(SELECT_COURSE_BY_JOURNAL, param))) {
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    courses.add(mapRowCourse(rs));
                }
            }
            noOfRecords = setFoundRows(stmt);
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return Map.entry(noOfRecords, courses);
    }

    @Override
    public Map.Entry<Integer, List<Course>> getAll(String param) throws DAOException {
        List<Course> courses = new ArrayList<>();
        int noOfRecords;
        try (Connection con = dataSource.getConnection();
             PreparedStatement stmt = con.prepareStatement(String.format(SELECT_ALL_PAGINATION, param))) {
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    courses.add(mapRowCourse(rs));
                }
            }
            noOfRecords = setFoundRows(stmt);
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return Map.entry(noOfRecords, courses);
    }

    @Override
    public void insertJournal(int courseId, int studentId) throws DAOException {
        Connection con = null;
        PreparedStatement stmt = null;
        try {
            con = dataSource.getConnection();
            con.setAutoCommit(false);
            stmt = con.prepareStatement(INSERT_JOURNAL);
            int k = 0;
            stmt.setInt(++k, courseId);
            stmt.setInt(++k, studentId);
            stmt.executeUpdate();
            stmt = con.prepareStatement(ADD_NUMBER_STUDENTS_TO_COURSE);
            stmt.setInt(1, courseId);
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
    public void updateJournal(int courseId, int studentId, int grade) throws DAOException {
        try (Connection con = dataSource.getConnection();
             PreparedStatement stmt = con.prepareStatement(UPDATE_JOURNAL)) {
            int k = 0;
            stmt.setInt(++k, grade);
            stmt.setInt(++k, courseId);
            stmt.setInt(++k, studentId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    /**
     * Helping methods
     */
    private Course mapRowCourse(ResultSet rs) throws SQLException {
        return Course.builder()
                .id(rs.getInt(COURSE_ID))
                .title(rs.getString(COURSE_TITLE))
                .duration(rs.getInt(COURSE_DURATION))
                .startDate(rs.getDate(COURSE_START_DATE).toLocalDate())
                .amountStudents(rs.getInt(COURSE_AMOUNT_STUDENTS))
                .description(rs.getString(COURSE_DESCRIPTION))
                .status(Status.valueOf(rs.getString(STATUS_TITLE)))
                .category(mapRowCategory(rs))
                .teacher(rs.getString(TEACHER_ID) != null ? mapRowTeacher(rs) : null)
                .build();
    }

    private Category mapRowCategory(ResultSet rs) throws SQLException {
        return Category.builder()
                .id(rs.getInt(CATEGORY_ID))
                .title(rs.getString(CATEGORY_TITLE))
                .description(rs.getString(CATEGORY_DESCRIPTION))
                .build();
    }

    private Teacher mapRowTeacher(ResultSet rs) throws SQLException {
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

    private int setStatementFields(Course course, PreparedStatement stmt) throws SQLException {
        int k = 0;
        stmt.setString(++k, course.getTitle());
        stmt.setInt(++k, course.getDuration());
        stmt.setDate(++k, Date.valueOf(course.getStartDate()));
        stmt.setString(++k, course.getDescription());
        stmt.setInt(++k, course.getCategory().getId());
        stmt.setInt(++k, course.getStatus().getId());
        return k;
    }


    private Integer setFoundRows(PreparedStatement stmt) throws SQLException {
        ResultSet rs = stmt.executeQuery(SELECT_FOUND_ROWS);
        if (rs.next()) {
            return rs.getInt(1);
        }
        return 0;
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
