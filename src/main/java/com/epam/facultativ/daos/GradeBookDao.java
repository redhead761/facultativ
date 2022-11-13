package com.epam.facultativ.daos;

import com.epam.facultativ.DataSource;
import com.epam.facultativ.entity.GradeBook;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.epam.facultativ.daos.Fields.*;

public class GradeBookDao {
    private static final String SELECT_All_GRADE_BOOK = "SELECT * FROM grade_book";
    private static final String SELECT_GRADE_BOOK_BY_USER_ID =
            "SELECT * FROM grade_book WHERE student_id=?";
    private static final String SELECT_GRADE_BOOK_BY_COURSE_ID =
            "SELECT * FROM grade_book WHERE course_id=?";
    private static final String UPDATE_GRADE_BOOK =
            "UPDATE grade_book SET course_id=?, grade=?, WHERE student_id=?";
    private static final String INSERT_GRADE_BOOK = "INSERT INTO grade_book VALUES (?,?,?)";
    private static final String DELETE_GRADE_BOOK = "DELETE FROM grade_book WHERE student_id=?";

    public List<GradeBook> findAll() {
        List<GradeBook> gradeBooks = new ArrayList<>();
        try (Connection con = DataSource.getConnection();
             Statement stmt = con.createStatement()) {
            ResultSet rs = stmt.executeQuery(SELECT_All_GRADE_BOOK);
            while (rs.next()) {
                gradeBooks.add(mapRow(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return gradeBooks;
    }


    public List<GradeBook> findByUserId(int id) {
        List<GradeBook> gradeBooks = new ArrayList<>();
        try (Connection con = DataSource.getConnection();
             PreparedStatement stmt = con.prepareStatement(SELECT_GRADE_BOOK_BY_USER_ID)) {
            stmt.setString(1, String.valueOf(id));
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                gradeBooks.add(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return gradeBooks;
    }


    public List<GradeBook> findByCourseId(int id) {
        List<GradeBook> gradeBooks = new ArrayList<>();
        try (Connection con = DataSource.getConnection();
             PreparedStatement stmt = con.prepareStatement(SELECT_GRADE_BOOK_BY_COURSE_ID)) {
            stmt.setString(1, String.valueOf(id));
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                gradeBooks.add(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return gradeBooks;
    }


    public void update(GradeBook gradeBook) {
        try (Connection con = DataSource.getConnection();
             PreparedStatement stmt = con.prepareStatement(UPDATE_GRADE_BOOK)) {
            int k = 0;
            stmt.setInt(++k, gradeBook.getCourse().getId());
            stmt.setInt(++k, gradeBook.getGrade());
            stmt.setInt(++k, gradeBook.getUser().getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public void insert(GradeBook gradeBook) {
        try (Connection con = DataSource.getConnection();
             PreparedStatement stmt = con.prepareStatement(INSERT_GRADE_BOOK)) {
            int k = 0;
            stmt.setInt(++k, gradeBook.getCourse().getId());
            stmt.setInt(++k, gradeBook.getUser().getId());
            stmt.setInt(++k, gradeBook.getGrade());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public void delete(GradeBook gradeBook) {
        try (Connection con = DataSource.getConnection();
             PreparedStatement stmt = con.prepareStatement(DELETE_GRADE_BOOK)) {
            stmt.setString(1, String.valueOf(gradeBook.getUser().getId()));

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
    private GradeBook mapRow(ResultSet rs) {
        try {
            GradeBook gradeBook = new GradeBook();
            gradeBook.setUser(new UserDao().findById((rs.getInt(USER_ID))));
            gradeBook.setCourse(new CourseDao().findById(rs.getInt(COURSE_ID)));
            gradeBook.setGrade(rs.getInt(GRADE));
            return gradeBook;
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }
}
