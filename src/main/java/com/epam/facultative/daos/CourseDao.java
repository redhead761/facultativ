package com.epam.facultative.daos;

import com.epam.facultative.connection.DataSource;
import com.epam.facultative.entity.Category;
import com.epam.facultative.entity.Course;
import com.epam.facultative.entity.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.epam.facultative.daos.Constants.*;
import static com.epam.facultative.daos.Fields.*;

public class CourseDao implements Dao<Course> {

    @Override
    public List<Course> findAll() {
        List<Course> courses = new ArrayList<>();
        try (Connection con = DataSource.getConnection();
             Statement stmt = con.createStatement()) {
            ResultSet rs = stmt.executeQuery(SELECT_All_COURSES);
            while (rs.next()) {
                courses.add(mapRow(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return courses;
    }

    @Override
    public Course findById(int id) {
        Course course = null;
        try (Connection con = DataSource.getConnection();
             PreparedStatement stmt = con.prepareStatement(SELECT_COURSE_BY_ID)) {
            stmt.setString(1, String.valueOf(id));
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                course = mapRow(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return course;
    }

    @Override
    public Course findByName(String name) {
        Course course = null;
        try (Connection con = DataSource.getConnection();
             PreparedStatement stmt = con.prepareStatement(SELECT_COURSE_BY_TITLE)) {
            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                course = mapRow(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return course;
    }

    @Override
    public void update(Course course) {
        try (Connection con = DataSource.getConnection();
             PreparedStatement stmt = con.prepareStatement(UPDATE_COURSE)) {
            int k = 0;
            stmt.setString(++k, course.getTitle());
            stmt.setInt(++k, course.getDuration());
            stmt.setDate(++k, Date.valueOf(course.getStartDate()));
            stmt.setString(++k, course.getDescription());
            stmt.setInt(++k, new CategoryDao().findByName(course.getCategory().getTitle()).getId());
            stmt.setInt(++k, new StatusDao().findByName(course.getStatus().getTitle()).getId());
            stmt.setString(++k, String.valueOf(course.getId()));

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void insert(Course course) {
        try (Connection con = DataSource.getConnection();
             PreparedStatement stmt = con.prepareStatement(INSERT_COURSE, Statement.RETURN_GENERATED_KEYS)) {
            int k = 0;
            stmt.setString(++k, course.getTitle());
            stmt.setInt(++k, course.getDuration());
            stmt.setDate(++k, Date.valueOf(course.getStartDate()));
            stmt.setString(++k, course.getDescription());
            stmt.setInt(++k, new CategoryDao().findByName(course.getCategory().getTitle()).getId());
            stmt.setInt(++k, new StatusDao().findByName(course.getStatus().getTitle()).getId());

            stmt.executeUpdate();
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    course.setId(generatedKeys.getInt(1));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Course course) {
        try (Connection con = DataSource.getConnection();
             PreparedStatement stmt = con.prepareStatement(DELETE_COURSE)) {
            stmt.setString(1, String.valueOf(course.getId()));

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Course> findByCategory(Category category) {
        List<Course> courses = new ArrayList<>();
        try (Connection con = DataSource.getConnection();
             PreparedStatement stmt = con.prepareStatement(SELECT_COURSE_BY_CATEGORY)) {
            stmt.setString(1, String.valueOf(category.getId()));
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                courses.add(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return courses;
    }

    public List<Course> findByUser(int id) {
        List<Course> courses = new ArrayList<>();
        try (Connection con = DataSource.getConnection();
             PreparedStatement stmt = con.prepareStatement(SELECT_COURSES_USER)) {
            stmt.setString(1, String.valueOf(id));
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                courses.add(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return courses;
    }

    public void insertUserToCourse(User user, Course... courses) {
        try (Connection con = DataSource.getConnection();
             PreparedStatement stmt = con.prepareStatement(INSERT_USERS_COURSE)) {
            for (Course course : courses) {
                stmt.setInt(1, course.getId());
                stmt.setInt(2, user.getId());
                stmt.executeUpdate();
                addNumberStudentsToCourse(course);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void addNumberStudentsToCourse(Course course) {
        try (Connection con = DataSource.getConnection();
             PreparedStatement stmt = con.prepareStatement(ADD_NUMBER_STUDENTS_TO_COURSE)) {
            stmt.setInt(1, course.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /*
   *******
   helper methods
   *******
    */
    private Course mapRow(ResultSet rs) {
        try {
            Course course = new Course();
            course.setId(rs.getInt(COURSE_ID));
            course.setTitle(rs.getString(COURSE_TITLE));
            course.setDuration(rs.getInt(COURSE_DURATION));
            course.setStartDate(rs.getDate(COURSE_START_DATE).toLocalDate());
            course.setDescription(rs.getString(COURSE_DESCRIPTION));
            course.setStudentsOnCourse(rs.getInt(NUMBER_STUDENTS_ON_COURSE));
            course.setCategory(new CategoryDao().findById(rs.getInt(COURSE_CATEGORY_ID)));
            course.setStatus(new StatusDao().findById(rs.getInt(COURSE_STATUS_ID)));
            return course;
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

}
