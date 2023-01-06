package com.epam.facultative.daos.impl;

import com.epam.facultative.daos.CourseDao;
import com.epam.facultative.entities.*;
import com.epam.facultative.exception.DAOException;
import com.epam.facultative.exception.ValidateException;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.epam.facultative.daos.impl.SQLRequestConstants.*;
import static com.epam.facultative.daos.impl.FieldsConstants.*;
import static com.epam.facultative.utils.validator.ValidateExceptionMessageConstants.*;

public class MySqlCourseDao implements CourseDao {
    private final DataSource dataSource;

    private int noOfRecords;

    public MySqlCourseDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<Course> getAll() throws DAOException {
        List<Course> courses = new ArrayList<>();
        try (Connection con = dataSource.getConnection();
             Statement stmt = con.createStatement()) {
            ResultSet rs = stmt.executeQuery(SELECT_All_COURSES);
            while (rs.next()) {
                courses.add(mapRowCourse(rs));
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return courses;
    }

    @Override
    public Course getById(int id) throws DAOException {
        Course course = null;
        try (Connection con = dataSource.getConnection();
             PreparedStatement stmt = con.prepareStatement(SELECT_COURSE_BY_ID)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                course = mapRowCourse(rs);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return course;
    }

    @Override
    public Course getByName(String name) throws DAOException {
        Course course = null;
        try (Connection con = dataSource.getConnection();
             PreparedStatement stmt = con.prepareStatement(SELECT_COURSE_BY_TITLE)) {
            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                course = mapRowCourse(rs);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return course;
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
    public List<Course> getByTeacher(int userId, int offset, int numberOfRows) throws DAOException {
        List<Course> courses = new ArrayList<>();
        try (Connection con = dataSource.getConnection();
             PreparedStatement stmt = con.prepareStatement(SELECT_COURSES_TEACHER)) {
            int k = 0;
            stmt.setInt(++k, userId);
            setLimitRows(stmt, offset, numberOfRows, k);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                courses.add(mapRowCourse(rs));
            }
            setFoundRows(rs, stmt);
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return courses;
    }

    @Override
    public List<Course> getByStudent(int userId, int offset, int numberOfRows) throws DAOException {
        List<Course> courses = new ArrayList<>();
        try (Connection con = dataSource.getConnection();
             PreparedStatement stmt = con.prepareStatement(SELECT_COURSES_STUDENT)) {
            int k = 0;
            stmt.setInt(++k, userId);
            setLimitRows(stmt, offset, numberOfRows, k);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                courses.add(mapRowCourse(rs));
            }
            setFoundRows(rs, stmt);
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return courses;
    }

    @Override
    public List<Course> getByCategory(int categoryId, int offset, int numberOfRows) throws DAOException {
        List<Course> courses = new ArrayList<>();
        try (Connection con = dataSource.getConnection();
             PreparedStatement stmt = con.prepareStatement(SELECT_COURSE_BY_CATEGORY)) {
            int k = 0;
            stmt.setInt(++k, categoryId);
            setLimitRows(stmt, offset, numberOfRows, k);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                courses.add(mapRowCourse(rs));
            }
            setFoundRows(rs, stmt);
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return courses;
    }

    @Override
    public List<Course> getByStatus(int userId, Status status, int offset, int numberOfRows) throws DAOException {
        List<Course> courses = new ArrayList<>();
        try (Connection con = dataSource.getConnection();
             PreparedStatement stmt = con.prepareStatement(SELECT_COURSE_BY_STATUS)) {
            int k = 0;
            stmt.setInt(++k, userId);
            stmt.setInt(++k, status.getId());
            setLimitRows(stmt, offset, numberOfRows, k);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                courses.add(mapRowCourse(rs));
            }
            setFoundRows(rs, stmt);
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return courses;
    }

    @Override
    public List<Course> getAllPagination(int offset, int numberOfRows) throws DAOException {
        List<Course> courses = new ArrayList<>();
        try (Connection con = dataSource.getConnection();
             PreparedStatement stmt = con.prepareStatement(SELECT_ALL_PAGINATION)) {
            setLimitRows(stmt, offset, numberOfRows, 0);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                courses.add(mapRowCourse(rs));
            }
            setFoundRows(rs, stmt);
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return courses;
    }

    @Override
    public int getNoOfRecords() {
        return noOfRecords;
    }

    @Override
    public List<Course> getAllSortPagination(int offset, int numberOfRows, String sortBy) throws DAOException {
        List<Course> courses = new ArrayList<>();
        try (Connection con = dataSource.getConnection();
             PreparedStatement stmt = con.prepareStatement(getQuery(sortBy))) {
            setLimitRows(stmt, offset, numberOfRows, 0);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                courses.add(mapRowCourse(rs));
            }
            setFoundRows(rs, stmt);
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return courses;
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
                .teacher(mapRowTeacher(rs))
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

    private String getQuery(String sortBy) {
        return "SELECT SQL_CALC_FOUND_ROWS * FROM course JOIN category ON category_id = category.id JOIN status ON status_id = status.id LEFT JOIN teacher ON teacher_id = user_id LEFT JOIN user ON teacher.user_id = user.id ORDER BY " + sortBy + " LIMIT ?,?";
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


    private void setFoundRows(ResultSet rs, PreparedStatement stmt) throws SQLException {
        rs.close();
        rs = stmt.executeQuery(SELECT_FOUND_ROWS);
        if (rs.next())
            this.noOfRecords = rs.getInt(1);
    }

    private void setLimitRows(PreparedStatement stmt, int offset, int numberOfRows, int k) throws SQLException {
        stmt.setInt(++k, offset);
        stmt.setInt(++k, numberOfRows);
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
