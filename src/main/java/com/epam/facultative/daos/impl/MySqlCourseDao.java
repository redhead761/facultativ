package com.epam.facultative.daos.impl;

import com.epam.facultative.daos.connection.DataSource;
import com.epam.facultative.daos.CourseDao;
import com.epam.facultative.entity.*;
import com.epam.facultative.exception.DAOException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.epam.facultative.daos.impl.Constants.*;
import static com.epam.facultative.daos.impl.Fields.*;

public class MySqlCourseDao implements CourseDao {

    private int noOfRecords;

    @Override
    public List<Course> getAll() throws DAOException {
        List<Course> courses = new ArrayList<>();
        try (Connection con = DataSource.getConnection();
             Statement stmt = con.createStatement()) {
            ResultSet rs = stmt.executeQuery(SELECT_All_COURSES);
            while (rs.next()) {
                courses.add(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return courses;
    }

    @Override
    public Course getById(int id) throws DAOException {
        Course course = null;
        try (Connection con = DataSource.getConnection();
             PreparedStatement stmt = con.prepareStatement(SELECT_COURSE_BY_ID)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                course = mapRow(rs);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return course;
    }

    @Override
    public Course getByName(String name) throws DAOException {
        Course course = null;
        try (Connection con = DataSource.getConnection();
             PreparedStatement stmt = con.prepareStatement(SELECT_COURSE_BY_TITLE)) {
            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                course = mapRow(rs);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return course;
    }

    @Override
    public void add(Course course) throws DAOException {
        try (Connection con = DataSource.getConnection();
             PreparedStatement stmt = con.prepareStatement(INSERT_COURSE, Statement.RETURN_GENERATED_KEYS)) {
            int k = 0;
            stmt.setString(++k, course.getTitle());
            stmt.setInt(++k, course.getDuration());
            stmt.setDate(++k, Date.valueOf(course.getStartDate()));
            stmt.setString(++k, course.getDescription());
            stmt.setInt(++k, course.getCategory().getId());
            stmt.setInt(++k, course.getStatus().getId());
            stmt.executeUpdate();
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    course.setId(generatedKeys.getInt(1));
                }
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public void update(Course course) throws DAOException {
        try (Connection con = DataSource.getConnection();
             PreparedStatement stmt = con.prepareStatement(UPDATE_COURSE)) {
            int k = 0;
            stmt.setString(++k, course.getTitle());
            stmt.setInt(++k, course.getDuration());
            stmt.setDate(++k, Date.valueOf(course.getStartDate()));
            stmt.setInt(++k, course.getAmountStudents());
            stmt.setString(++k, course.getDescription());
            stmt.setInt(++k, course.getCategory().getId());
            stmt.setInt(++k, course.getStatus().getId());
            stmt.setString(++k, String.valueOf(course.getId()));
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public void delete(int id) throws DAOException {
        try (Connection con = DataSource.getConnection();
             PreparedStatement stmt = con.prepareStatement(DELETE_COURSE)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public List<Course> getByUser(int userId, int offset, int numberOfRows) throws DAOException {
        List<Course> courses = new ArrayList<>();
        try (Connection con = DataSource.getConnection();
             PreparedStatement stmt = con.prepareStatement(SELECT_COURSES_USER)) {
            int k = 0;
            stmt.setInt(++k, userId);
            stmt.setInt(++k, offset);
            stmt.setInt(++k, numberOfRows);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                courses.add(mapRow(rs));
            }
            rs.close();

            rs = stmt.executeQuery("SELECT FOUND_ROWS()");
            if (rs.next())
                this.noOfRecords = rs.getInt(1);
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return courses;
    }

    @Override
    public List<Course> getByCategory(int categoryId, int offset, int numberOfRows) throws DAOException {
        List<Course> courses = new ArrayList<>();
        try (Connection con = DataSource.getConnection();
             PreparedStatement stmt = con.prepareStatement(SELECT_COURSE_BY_CATEGORY)) {
            int k = 0;
            stmt.setInt(++k, categoryId);
            stmt.setInt(++k, offset);
            stmt.setInt(++k, numberOfRows);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                courses.add(mapRow(rs));
            }
            rs.close();

            rs = stmt.executeQuery("SELECT FOUND_ROWS()");
            if (rs.next())
                this.noOfRecords = rs.getInt(1);
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return courses;
    }

    @Override
    public void addUserToCourse(int courseId, int userId) throws DAOException {
        try (Connection con = DataSource.getConnection();
             PreparedStatement stmt = con.prepareStatement(INSERT_USERS_COURSE)) {
            stmt.setInt(1, courseId);
            stmt.setInt(2, userId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public void updateUsersCourse(int courseId, int userId, int grade) throws DAOException {
        try (Connection con = DataSource.getConnection();
             PreparedStatement stmt = con.prepareStatement(UPDATE_USERS_COURSE)) {
            stmt.setInt(1, grade);
            stmt.setInt(2, courseId);
            stmt.setInt(3, userId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public int getGrade(int courseId, int userId) throws DAOException {
        int grade = 0;
        try (Connection con = DataSource.getConnection();
             PreparedStatement stmt = con.prepareStatement(GET_GRADE)) {
            stmt.setInt(1, courseId);
            stmt.setInt(2, userId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                grade = rs.getInt(COURSE_GRADE);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return grade;
    }

    @Override
    public List<Course> getByStatus(int userId, Status status, int offset, int numberOfRows) throws DAOException {
        List<Course> courses = new ArrayList<>();
        try (Connection con = DataSource.getConnection();
             PreparedStatement stmt = con.prepareStatement(SELECT_COURSE_BY_STATUS)) {
            int k = 0;
            stmt.setInt(++k, userId);
            stmt.setInt(++k, status.getId());
            stmt.setInt(++k, offset);
            stmt.setInt(++k, numberOfRows);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                courses.add(mapRow(rs));
            }
            rs.close();

            rs = stmt.executeQuery("SELECT FOUND_ROWS()");
            if (rs.next())
                this.noOfRecords = rs.getInt(1);
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return courses;
    }

    @Override
    public void addNumberStudentsToCourse(int id) throws DAOException {
        try (Connection con = DataSource.getConnection();
             PreparedStatement stmt = con.prepareStatement(ADD_NUMBER_STUDENTS_TO_COURSE)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public List<Course> getAllPagination(int offset, int numberOfRows) throws DAOException {
        String query = "SELECT SQL_CALC_FOUND_ROWS course.id, course.title,duration,start_date,amount_students,course.description," +
                " category_id,status.title AS course_status, category.title AS course_category," +
                " category.description AS category_description " +
                "FROM course JOIN category ON course.category_id = category.id JOIN status ON course.status_id = status.id " +
                "LIMIT ?,?";
        List<Course> courses = new ArrayList<>();
        try (Connection con = DataSource.getConnection();
             PreparedStatement stmt = con.prepareStatement(query)) {
            int k = 0;
            stmt.setInt(++k, offset);
            stmt.setInt(++k, numberOfRows);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                courses.add(mapRow(rs));
            }
            rs.close();

            rs = stmt.executeQuery("SELECT FOUND_ROWS()");
            if (rs.next())
                this.noOfRecords = rs.getInt(1);
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
        String query = "SELECT SQL_CALC_FOUND_ROWS course.id, course.title,duration,start_date,amount_students,course.description," +
                " category_id,status.title AS course_status, category.title AS course_category," +
                " category.description AS category_description " +
                "FROM course JOIN category ON course.category_id = category.id JOIN status ON course.status_id = status.id " +
                "ORDER BY " + sortBy + " LIMIT ?,?";
        List<Course> courses = new ArrayList<>();
        try (Connection con = DataSource.getConnection();
             PreparedStatement stmt = con.prepareStatement(query)) {
            int k = 0;
            stmt.setInt(++k, offset);
            stmt.setInt(++k, numberOfRows);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                courses.add(mapRow(rs));
            }
            rs.close();

            rs = stmt.executeQuery("SELECT FOUND_ROWS()");
            if (rs.next())
                this.noOfRecords = rs.getInt(1);
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return courses;
    }

    private Course mapRow(ResultSet rs) throws DAOException {
        try {
            Course course = new Course();
            course.setId(rs.getInt(COURSE_ID));
            course.setTitle(rs.getString(COURSE_TITLE));
            course.setDuration(rs.getInt(COURSE_DURATION));
            course.setStartDate(rs.getDate(COURSE_START_DATE).toLocalDate());
            course.setAmountStudents(rs.getInt(COURSE_AMOUNT_STUDENTS));
            course.setDescription(rs.getString(COURSE_DESCRIPTION));
            course.setCategory(mapRowCategory(rs));
            course.setStatus(Status.valueOf(rs.getString(COURSE_STATUS)));
            return course;
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    private Category mapRowCategory(ResultSet rs) throws DAOException {
        try {
            Category category = new Category();
            category.setId(rs.getInt(COURSE_CATEGORY_ID));
            category.setTitle(rs.getString(COURSE_CATEGORY));
            category.setDescription(rs.getString(COURSE_CATEGORY_DESCRIPTION));
            return category;
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }
}
