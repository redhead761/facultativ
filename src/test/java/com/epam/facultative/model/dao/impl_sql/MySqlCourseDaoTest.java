package com.epam.facultative.model.dao.impl_sql;

import com.epam.facultative.model.dao.CourseDao;
import com.epam.facultative.model.entities.Category;
import com.epam.facultative.model.entities.Course;
import com.epam.facultative.model.entities.Teacher;
import com.epam.facultative.model.exception.DAOException;
import com.epam.facultative.model.exception.ValidateException;
import com.epam.facultative.model.service.implementation.TestServiceUtil;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;
import java.util.Map;

import static com.epam.facultative.model.dao.impl_sql.сonstants.FieldsConstants.*;
import static com.epam.facultative.model.dao.impl_sql.сonstants.SQLRequestConstants.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

class MySqlCourseDaoTest {
    private static final DataSource dataSource = mock(DataSource.class);
    Connection con = mock(Connection.class);
    PreparedStatement stmt = mock(PreparedStatement.class);
    ResultSet resultSet = mock(ResultSet.class);
    private static Course course;
    private static Map.Entry<Integer, List<Course>> courses;
    private static CourseDao courseDao;
    private static Teacher teacher;
    private static Category category;

    @BeforeAll
    static void init() {
        TestServiceUtil testServiceUtil = new TestServiceUtil();
        course = testServiceUtil.getCourse();
        courseDao = new MySqlCourseDao(dataSource);
        courses = testServiceUtil.getCourses();
        teacher = testServiceUtil.getTeacher();
        category = testServiceUtil.getCategory();
    }

    @Test
    void getSuccessful() throws SQLException, DAOException {
        when(dataSource.getConnection()).thenReturn(con);
        when(con.prepareStatement(anyString())).thenReturn(stmt);
        when(stmt.executeQuery()).thenReturn(resultSet);
        setResultSet();
        Course resultCourse = courseDao.get(SELECT_COURSE).orElse(null);
        assertNotNull(resultCourse);
        assertEquals(course, resultCourse);
    }

    @Test
    void getAbsent() throws SQLException, DAOException {
        when(dataSource.getConnection()).thenReturn(con);
        when(con.prepareStatement(anyString())).thenReturn(stmt);
        when(stmt.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(false);
        Course resultCourse = courseDao.get(SELECT_COURSE).orElse(null);
        assertNull(resultCourse);
    }

    @Test
    void getFailed() throws SQLException {
        when(dataSource.getConnection()).thenReturn(con);
        when(con.prepareStatement(anyString())).thenReturn(stmt);
        when(stmt.executeQuery()).thenThrow(SQLException.class);
        assertThrows(DAOException.class, () -> courseDao.get(SELECT_COURSE));
    }

    @Test
    void addSuccessful() throws SQLException {
        when(dataSource.getConnection()).thenReturn(con);
        when(con.prepareStatement(anyString())).thenReturn(stmt);
        when(stmt.executeUpdate()).thenReturn(1);
        setStmt();
        assertDoesNotThrow(() -> courseDao.add(course));
    }

    @Test
    void addCopy() throws SQLException {
        when(dataSource.getConnection()).thenReturn(con);
        when(con.prepareStatement(anyString())).thenReturn(stmt);
        when(stmt.executeUpdate()).thenThrow(SQLIntegrityConstraintViolationException.class);
        assertThrows(ValidateException.class, () -> courseDao.add(course));
    }

    @Test
    void addFailed() throws SQLException {
        when(dataSource.getConnection()).thenReturn(con);
        when(con.prepareStatement(anyString())).thenReturn(stmt);
        when(stmt.executeUpdate()).thenThrow(SQLException.class);
        assertThrows(DAOException.class, () -> courseDao.add(course));
    }

    @Test
    void updateSuccessful() throws SQLException {
        when(dataSource.getConnection()).thenReturn(con);
        when(con.prepareStatement(anyString())).thenReturn(stmt);
        when(stmt.executeUpdate()).thenReturn(1);
        setStmt();
        assertDoesNotThrow(() -> courseDao.update(course));
    }

    @Test
    void updateCopy() throws SQLException {
        when(dataSource.getConnection()).thenReturn(con);
        when(con.prepareStatement(anyString())).thenReturn(stmt);
        when(stmt.executeUpdate()).thenThrow(SQLIntegrityConstraintViolationException.class);
        assertThrows(ValidateException.class, () -> courseDao.update(course));
    }

    @Test
    void updateFailed() throws SQLException {
        when(dataSource.getConnection()).thenReturn(con);
        when(con.prepareStatement(anyString())).thenReturn(stmt);
        when(stmt.executeUpdate()).thenThrow(SQLException.class);
        assertThrows(DAOException.class, () -> courseDao.update(course));
    }

    @Test
    void deleteSuccessful() throws SQLException {
        when(dataSource.getConnection()).thenReturn(con);
        when(con.prepareStatement(anyString())).thenReturn(stmt);
        when(stmt.executeUpdate()).thenReturn(1);
        setStmt();
        assertDoesNotThrow(() -> courseDao.delete(1));
    }

    @Test
    void deleteFailed() throws SQLException {
        when(dataSource.getConnection()).thenReturn(con);
        when(con.prepareStatement(anyString())).thenReturn(stmt);
        when(stmt.executeUpdate()).thenThrow(SQLException.class);
        assertThrows(DAOException.class, () -> courseDao.delete(1));
    }

    @Test
    void getAllSuccessful() throws SQLException, DAOException {
        when(dataSource.getConnection()).thenReturn(con);
        when(con.prepareStatement(anyString())).thenReturn(stmt);
        when(stmt.executeQuery()).thenReturn(resultSet);
        setResultSet();
        when(stmt.executeQuery(SELECT_FOUND_ROWS)).thenReturn(resultSet);
        when(resultSet.getInt(1)).thenReturn(1);
        Map.Entry<Integer, List<Course>> resultCourse = courseDao.getAll(SELECT_ALL_COURSES);
        assertEquals(courses, resultCourse);
    }

    @Test
    void getAllFailed() throws SQLException {
        when(dataSource.getConnection()).thenReturn(con);
        when(con.prepareStatement(anyString())).thenReturn(stmt);
        when(stmt.executeQuery()).thenThrow(SQLException.class);
        assertThrows(DAOException.class, () -> courseDao.getAll(SELECT_ALL_COURSES));
    }

    private void setStmt() throws SQLException {
        doNothing().when(stmt).setInt(anyInt(), anyInt());
        doNothing().when(stmt).setString(anyInt(), anyString());
    }

    private void setResultSet() throws SQLException {
        when(resultSet.next()).thenReturn(true).thenReturn(false).thenReturn(true).thenReturn(false);
        when(resultSet.getInt(COURSE_ID)).thenReturn(course.getId());
        when(resultSet.getString(COURSE_TITLE)).thenReturn(course.getTitle());
        when(resultSet.getInt(COURSE_DURATION)).thenReturn(course.getDuration());
        when(resultSet.getDate(COURSE_START_DATE)).thenReturn(Date.valueOf(course.getStartDate()));
        when(resultSet.getInt(COURSE_AMOUNT_STUDENTS)).thenReturn(course.getAmountStudents());
        when(resultSet.getString(CATEGORY_DESCRIPTION)).thenReturn(course.getDescription());
        when(resultSet.getString(STATUS_TITLE)).thenReturn(String.valueOf(course.getStatus()));
        when(resultSet.getString(CATEGORY_DESCRIPTION)).thenReturn(course.getDescription());
        when(resultSet.getInt(TEACHER_ID)).thenReturn(teacher.getId());
        when(resultSet.getString(USER_LOGIN)).thenReturn(teacher.getLogin());
        when(resultSet.getString(USER_PASSWORD)).thenReturn(teacher.getPassword());
        when(resultSet.getString(USER_FIRST_NAME)).thenReturn(teacher.getName());
        when(resultSet.getString(USER_FAMILY_NAME)).thenReturn(teacher.getSurname());
        when(resultSet.getString(USER_EMAIL)).thenReturn(teacher.getEmail());
        when(resultSet.getString(ROLE_NAME)).thenReturn(String.valueOf(teacher.getRole()));
        when(resultSet.getString(TEACHER_DEGREE)).thenReturn(String.valueOf(teacher.getDegree()));
        when(resultSet.getInt(CATEGORY_ID)).thenReturn(category.getId());
        when(resultSet.getString(CATEGORY_TITLE)).thenReturn(category.getTitle());
        when(resultSet.getString(CATEGORY_DESCRIPTION)).thenReturn(category.getDescription());
    }

}