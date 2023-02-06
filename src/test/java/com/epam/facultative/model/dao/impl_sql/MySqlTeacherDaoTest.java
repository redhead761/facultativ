package com.epam.facultative.model.dao.impl_sql;

import com.epam.facultative.model.dao.TeacherDao;
import com.epam.facultative.model.entities.Teacher;
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
import static com.epam.facultative.model.dao.impl_sql.сonstants.SQLRequestConstants.SELECT_ALL_CATEGORIES;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.doNothing;

class MySqlTeacherDaoTest {
    private static final DataSource dataSource = mock(DataSource.class);
    Connection con = mock(Connection.class);
    PreparedStatement stmt = mock(PreparedStatement.class);
    ResultSet resultSet = mock(ResultSet.class);
    private static Teacher teacher;
    private static Map.Entry<Integer, List<Teacher>> teachers;
    private static TeacherDao teacherDao;
    Blob blob = mock(Blob.class);
    InputStream inputStream = mock(InputStream.class);

    @BeforeAll
    static void init() {
        TestServiceUtil testServiceUtil = new TestServiceUtil();
        teacher = testServiceUtil.getTeacher();
        teacherDao = new MySqlTeacherDao(dataSource);
        List<Teacher> teacherList = new ArrayList<>();
        teacherList.add(teacher);
        teachers = Map.entry(1, teacherList);
    }

    @Test
    void getSuccessful() throws SQLException, DAOException, IOException {
        when(dataSource.getConnection()).thenReturn(con);
        when(con.prepareStatement(anyString())).thenReturn(stmt);
        when(stmt.executeQuery()).thenReturn(resultSet);
        setResultSet();
        Teacher resultTeacher = teacherDao.get(SELECT_TEACHER).orElse(null);
        assertNotNull(resultTeacher);
        assertEquals(teacher, resultTeacher);
    }

    @Test
    void getAbsent() throws SQLException, DAOException {
        when(dataSource.getConnection()).thenReturn(con);
        when(con.prepareStatement(anyString())).thenReturn(stmt);
        when(stmt.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(false);
        Teacher resultTeacher = teacherDao.get(SELECT_TEACHER).orElse(null);
        assertNull(resultTeacher);
    }

    @Test
    void getFailed() throws SQLException {
        when(dataSource.getConnection()).thenReturn(con);
        when(con.prepareStatement(anyString())).thenReturn(stmt);
        when(stmt.executeQuery()).thenThrow(SQLException.class);
        assertThrows(DAOException.class, () -> teacherDao.get(SELECT_TEACHER));
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
        assertDoesNotThrow(() -> teacherDao.add(teacher));
    }

    @Test
    void addCopy() throws SQLException {
        when(dataSource.getConnection()).thenReturn(con);
        when(con.prepareStatement(anyString(), anyInt())).thenReturn(stmt);
        when(stmt.executeUpdate()).thenThrow(SQLIntegrityConstraintViolationException.class);
        assertThrows(ValidateException.class, () -> teacherDao.add(teacher));
    }

    @Test
    void addFailed() throws SQLException {
        when(dataSource.getConnection()).thenReturn(con);
        when(con.prepareStatement(anyString(), anyInt())).thenReturn(stmt);
        when(stmt.executeUpdate()).thenThrow(SQLException.class);
        assertThrows(DAOException.class, () -> teacherDao.add(teacher));
    }

    @Test
    void rollbackFailed() throws SQLException {
        when(dataSource.getConnection()).thenReturn(con);
        when(con.prepareStatement(anyString(), anyInt())).thenReturn(stmt);
        when(stmt.executeUpdate()).thenThrow(SQLException.class);
        doThrow(SQLException.class).when(con).rollback();
        assertThrows(DAOException.class, () -> teacherDao.add(teacher));
    }

    @Test
    void closeFailed() throws SQLException {
        when(dataSource.getConnection()).thenReturn(con);
        when(con.prepareStatement(anyString(), anyInt())).thenReturn(stmt);
        when(stmt.executeUpdate()).thenThrow(SQLException.class);
        doThrow(SQLException.class).when(con).close();
        assertThrows(DAOException.class, () -> teacherDao.add(teacher));
    }

    @Test
    void updateSuccessful() throws SQLException {
        when(dataSource.getConnection()).thenReturn(con);
        when(con.prepareStatement(anyString())).thenReturn(stmt);
        when(stmt.executeUpdate()).thenReturn(1);
        setStmt();
        assertDoesNotThrow(() -> teacherDao.update(teacher));
    }

    @Test
    void updateCopy() throws SQLException {
        when(dataSource.getConnection()).thenReturn(con);
        when(con.prepareStatement(anyString())).thenReturn(stmt);
        when(stmt.executeUpdate()).thenThrow(SQLIntegrityConstraintViolationException.class);
        assertThrows(ValidateException.class, () -> teacherDao.update(teacher));
    }

    @Test
    void updateFailed() throws SQLException {
        when(dataSource.getConnection()).thenReturn(con);
        when(con.prepareStatement(anyString())).thenReturn(stmt);
        when(stmt.executeUpdate()).thenThrow(SQLException.class);
        assertThrows(DAOException.class, () -> teacherDao.update(teacher));
    }

    @Test
    void deleteSuccessful() throws SQLException {
        when(dataSource.getConnection()).thenReturn(con);
        when(con.prepareStatement(anyString())).thenReturn(stmt);
        when(stmt.executeUpdate()).thenReturn(1);
        setStmt();
        assertDoesNotThrow(() -> teacherDao.delete(1));
    }

    @Test
    void deleteNoCascade() throws SQLException {
        when(dataSource.getConnection()).thenReturn(con);
        when(con.prepareStatement(anyString())).thenReturn(stmt);
        when(stmt.executeUpdate()).thenThrow(SQLIntegrityConstraintViolationException.class);
        assertThrows(ValidateException.class, () -> teacherDao.delete(1));
    }

    @Test
    void deleteFailed() throws SQLException {
        when(dataSource.getConnection()).thenReturn(con);
        when(con.prepareStatement(anyString())).thenReturn(stmt);
        when(stmt.executeUpdate()).thenThrow(SQLException.class);
        assertThrows(DAOException.class, () -> teacherDao.delete(1));
    }

    @Test
    void getAllSuccessful() throws SQLException, IOException, DAOException {
        when(dataSource.getConnection()).thenReturn(con);
        when(con.prepareStatement(anyString())).thenReturn(stmt);
        when(stmt.executeQuery()).thenReturn(resultSet);
        setResultSet();
        when(stmt.executeQuery(SELECT_FOUND_ROWS)).thenReturn(resultSet);
        when(resultSet.getInt(1)).thenReturn(1);
        Map.Entry<Integer, List<Teacher>> resultTeachers = teacherDao.getAll(SELECT_ALL_TEACHERS);
        assertEquals(teachers, resultTeachers);
    }

    @Test
    void getAllFailed() throws SQLException {
        when(dataSource.getConnection()).thenReturn(con);
        when(con.prepareStatement(anyString())).thenReturn(stmt);
        when(stmt.executeQuery()).thenThrow(SQLException.class);
        assertThrows(DAOException.class, () -> teacherDao.getAll(SELECT_ALL_CATEGORIES));
    }

    private void setStmt() throws SQLException {
        doNothing().when(stmt).setInt(anyInt(), anyInt());
        doNothing().when(stmt).setString(anyInt(), anyString());
    }

    private void setResultSet() throws SQLException, IOException {
        when(resultSet.next()).thenReturn(true).thenReturn(false).thenReturn(true).thenReturn(false);
        when(resultSet.getInt(TEACHER_ID)).thenReturn(teacher.getId());
        when(resultSet.getString(USER_LOGIN)).thenReturn(teacher.getLogin());
        when(resultSet.getString(USER_PASSWORD)).thenReturn(teacher.getPassword());
        when(resultSet.getString(USER_FIRST_NAME)).thenReturn(teacher.getName());
        when(resultSet.getString(USER_FAMILY_NAME)).thenReturn(teacher.getSurname());
        when(resultSet.getString(USER_EMAIL)).thenReturn(teacher.getEmail());
        when(resultSet.getString(ROLE_NAME)).thenReturn(String.valueOf(teacher.getRole()));
        when(resultSet.getString(TEACHER_DEGREE)).thenReturn(String.valueOf(teacher.getDegree()));
        when(resultSet.getBlob(USER_AVATAR)).thenReturn(blob);
        when(blob.getBinaryStream()).thenReturn(inputStream);
        when(inputStream.read(new byte[4096])).thenReturn(1).thenReturn(-1);
    }
}