package com.epam.facultative.model.dao.impl_sql;

import com.epam.facultative.model.dao.StudentDao;
import com.epam.facultative.model.entities.Student;
import com.epam.facultative.model.exception.DAOException;
import com.epam.facultative.model.exception.ValidateException;
import com.epam.facultative.model.service.implementation.TestServiceUtil;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.epam.facultative.model.dao.impl_sql.сonstants.FieldsConstants.*;
import static com.epam.facultative.model.dao.impl_sql.сonstants.SQLRequestConstants.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

class MySqlStudentDaoTest {
    private static final DataSource dataSource = mock(DataSource.class);
    Connection con = mock(Connection.class);
    PreparedStatement stmt = mock(PreparedStatement.class);
    ResultSet resultSet = mock(ResultSet.class);
    private static Student student;
    private static Map.Entry<Integer, List<Student>> students;
    private static StudentDao studentDao;
    Blob blob = mock(Blob.class);
    InputStream inputStream = mock(InputStream.class);

    @BeforeAll
    static void init() {
        TestServiceUtil testServiceUtil = new TestServiceUtil();
        student = testServiceUtil.getStudent();
        studentDao = new MySqlStudentDao(dataSource);
        List<Student> studentList = new ArrayList<>();
        studentList.add(student);
        students = Map.entry(1, studentList);
    }

    @Test
    void getSuccessful() throws SQLException, DAOException, IOException {
        when(dataSource.getConnection()).thenReturn(con);
        when(con.prepareStatement(anyString())).thenReturn(stmt);
        when(stmt.executeQuery()).thenReturn(resultSet);
        setResultSet();
        Student resultStudent = studentDao.get(SELECT_STUDENT).orElse(null);
        assertNotNull(resultStudent);
        assertEquals(student, resultStudent);
    }

    @Test
    void getAbsent() throws SQLException, DAOException {
        when(dataSource.getConnection()).thenReturn(con);
        when(con.prepareStatement(anyString())).thenReturn(stmt);
        when(stmt.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(false);
        Student resultStudent = studentDao.get(SELECT_STUDENT).orElse(null);
        assertNull(resultStudent);
    }

    @Test
    void getFailed() throws SQLException {
        when(dataSource.getConnection()).thenReturn(con);
        when(con.prepareStatement(anyString())).thenReturn(stmt);
        when(stmt.executeQuery()).thenThrow(SQLException.class);
        assertThrows(DAOException.class, () -> studentDao.get(SELECT_STUDENT));
    }

    @Test
    void addSuccessful() throws SQLException {
        when(dataSource.getConnection()).thenReturn(con);
        when(con.prepareStatement(anyString(), anyInt())).thenReturn(stmt);
        when(con.prepareStatement(anyString())).thenReturn(stmt);
        when(stmt.executeUpdate()).thenReturn(1);
        setStmt();
        when(stmt.getGeneratedKeys()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getInt(1)).thenReturn(1);
        assertDoesNotThrow(() -> studentDao.add(student));
    }

    @Test
    void addCopy() throws SQLException {
        when(dataSource.getConnection()).thenReturn(con);
        when(con.prepareStatement(anyString(), anyInt())).thenReturn(stmt);
        when(stmt.executeUpdate()).thenThrow(SQLIntegrityConstraintViolationException.class);
        assertThrows(ValidateException.class, () -> studentDao.add(student));
    }

    @Test
    void addFailed() throws SQLException {
        when(dataSource.getConnection()).thenReturn(con);
        when(con.prepareStatement(anyString(), anyInt())).thenReturn(stmt);
        when(stmt.executeUpdate()).thenThrow(SQLException.class);
        assertThrows(DAOException.class, () -> studentDao.add(student));
    }

    @Test
    void rollbackFailed() throws SQLException {
        when(dataSource.getConnection()).thenReturn(con);
        when(con.prepareStatement(anyString(), anyInt())).thenReturn(stmt);
        when(stmt.executeUpdate()).thenThrow(SQLException.class);
        doThrow(SQLException.class).when(con).rollback();
        assertThrows(DAOException.class, () -> studentDao.add(student));
    }

    @Test
    void closeFailed() throws SQLException {
        when(dataSource.getConnection()).thenReturn(con);
        when(con.prepareStatement(anyString(), anyInt())).thenReturn(stmt);
        when(stmt.executeUpdate()).thenThrow(SQLException.class);
        doThrow(SQLException.class).when(con).close();
        assertThrows(DAOException.class, () -> studentDao.add(student));
    }

    @Test
    void updateSuccessful() throws SQLException {
        when(dataSource.getConnection()).thenReturn(con);
        when(con.prepareStatement(anyString())).thenReturn(stmt);
        when(stmt.executeUpdate()).thenReturn(1);
        setStmt();
        assertDoesNotThrow(() -> studentDao.update(student));
    }

    @Test
    void updateCopy() throws SQLException {
        when(dataSource.getConnection()).thenReturn(con);
        when(con.prepareStatement(anyString())).thenReturn(stmt);
        when(stmt.executeUpdate()).thenThrow(SQLIntegrityConstraintViolationException.class);
        assertThrows(ValidateException.class, () -> studentDao.update(student));
    }

    @Test
    void updateFailed() throws SQLException {
        when(dataSource.getConnection()).thenReturn(con);
        when(con.prepareStatement(anyString())).thenReturn(stmt);
        when(stmt.executeUpdate()).thenThrow(SQLException.class);
        assertThrows(DAOException.class, () -> studentDao.update(student));
    }

    @Test
    void deleteSuccessful() throws SQLException {
        when(dataSource.getConnection()).thenReturn(con);
        when(con.prepareStatement(anyString())).thenReturn(stmt);
        when(stmt.executeUpdate()).thenReturn(1);
        setStmt();
        assertDoesNotThrow(() -> studentDao.delete(1));
    }

    @Test
    void deleteFailed() throws SQLException {
        when(dataSource.getConnection()).thenReturn(con);
        when(con.prepareStatement(anyString())).thenReturn(stmt);
        when(stmt.executeUpdate()).thenThrow(SQLException.class);
        assertThrows(DAOException.class, () -> studentDao.delete(1));
    }

    @Test
    void getAllSuccessful() throws SQLException, IOException, DAOException {
        when(dataSource.getConnection()).thenReturn(con);
        when(con.prepareStatement(anyString())).thenReturn(stmt);
        when(stmt.executeQuery()).thenReturn(resultSet);
        setResultSet();
        when(stmt.executeQuery(SELECT_FOUND_ROWS)).thenReturn(resultSet);
        when(resultSet.getInt(1)).thenReturn(1);
        Map.Entry<Integer, List<Student>> resultStudents = studentDao.getAll(SELECT_ALL_STUDENTS);
        assertEquals(students, resultStudents);
    }

    @Test
    void getAllFailed() throws SQLException {
        when(dataSource.getConnection()).thenReturn(con);
        when(con.prepareStatement(anyString())).thenReturn(stmt);
        when(stmt.executeQuery()).thenThrow(SQLException.class);
        assertThrows(DAOException.class, () -> studentDao.getAll(SELECT_ALL_STUDENTS));
    }

    @Test
    void getStudentsByCourseSuccessful() throws SQLException, IOException, DAOException {
        when(dataSource.getConnection()).thenReturn(con);
        when(con.prepareStatement(anyString())).thenReturn(stmt);
        when(stmt.executeQuery()).thenReturn(resultSet);
        setResultSet();
        when(stmt.executeQuery(SELECT_FOUND_ROWS)).thenReturn(resultSet);
        when(resultSet.getInt(1)).thenReturn(1);
        Map.Entry<Integer, List<Student>> resultStudents = studentDao.getStudentsByCourse(SELECT_ALL_STUDENTS);
        assertEquals(students, resultStudents);
    }

    @Test
    void getStudentsByCourseFailed() throws SQLException {
        when(dataSource.getConnection()).thenReturn(con);
        when(con.prepareStatement(anyString())).thenReturn(stmt);
        when(stmt.executeQuery()).thenThrow(SQLException.class);
        assertThrows(DAOException.class, () -> studentDao.getStudentsByCourse(SELECT_ALL_STUDENTS));
    }

    @Test
    void updateBlockSuccessful() throws SQLException {
        when(dataSource.getConnection()).thenReturn(con);
        when(con.prepareStatement(anyString())).thenReturn(stmt);
        when(stmt.executeUpdate()).thenReturn(1);
        setStmt();
        assertDoesNotThrow(() -> studentDao.updateBlock(1, true));
    }

    @Test
    void updateBlockFailed() throws SQLException {
        when(dataSource.getConnection()).thenReturn(con);
        when(con.prepareStatement(anyString())).thenReturn(stmt);
        when(stmt.executeUpdate()).thenThrow(SQLException.class);
        assertThrows(DAOException.class, () -> studentDao.updateBlock(1, true));
    }

    @Test
    void getGradeSuccessful() throws SQLException, DAOException, IOException {
        when(dataSource.getConnection()).thenReturn(con);
        when(con.prepareStatement(anyString())).thenReturn(stmt);
        when(stmt.executeQuery()).thenReturn(resultSet);
        setResultSet();
        int grade = studentDao.getGrade(1, 1);
        assertEquals(0, grade);
    }

    @Test
    void getGradeFailed() throws SQLException {
        when(dataSource.getConnection()).thenReturn(con);
        when(con.prepareStatement(anyString())).thenReturn(stmt);
        when(stmt.executeQuery()).thenThrow(SQLException.class);
        assertThrows(DAOException.class, () -> studentDao.getGrade(1, 1));
    }

    private void setStmt() throws SQLException {
        doNothing().when(stmt).setInt(anyInt(), anyInt());
        doNothing().when(stmt).setString(anyInt(), anyString());
        doNothing().when(stmt).setBoolean(anyInt(), anyBoolean());
    }

    private void setResultSet() throws SQLException, IOException {
        when(resultSet.next()).thenReturn(true).thenReturn(false).thenReturn(true).thenReturn(false);
        when(resultSet.getInt(TEACHER_ID)).thenReturn(student.getId());
        when(resultSet.getString(USER_LOGIN)).thenReturn(student.getLogin());
        when(resultSet.getString(USER_PASSWORD)).thenReturn(student.getPassword());
        when(resultSet.getString(USER_FIRST_NAME)).thenReturn(student.getName());
        when(resultSet.getString(USER_FAMILY_NAME)).thenReturn(student.getSurname());
        when(resultSet.getString(USER_EMAIL)).thenReturn(student.getEmail());
        when(resultSet.getString(ROLE_NAME)).thenReturn(String.valueOf(student.getRole()));
        when(resultSet.getInt(STUDENT_COURSE_NUMBER)).thenReturn(student.getCourseNumber());
        when(resultSet.getBoolean(STUDENT_BLOCK)).thenReturn(student.isBlock());
        when(resultSet.getBlob(USER_AVATAR)).thenReturn(blob);
        when(blob.getBinaryStream()).thenReturn(inputStream);
        when(inputStream.read(new byte[4096])).thenReturn(1).thenReturn(-1);
    }

}