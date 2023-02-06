package com.epam.facultative.model.dao.impl_sql;

import com.epam.facultative.model.dao.UserDao;
import com.epam.facultative.model.entities.User;
import com.epam.facultative.model.exception.DAOException;
import com.epam.facultative.model.service.implementation.TestServiceUtil;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.epam.facultative.model.dao.impl_sql.сonstants.FieldsConstants.*;
import static com.epam.facultative.model.dao.impl_sql.сonstants.SQLRequestConstants.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.doNothing;

class MySqlUserDaoTest {

    private static final DataSource dataSource = mock(DataSource.class);
    Connection con = mock(Connection.class);
    PreparedStatement stmt = mock(PreparedStatement.class);
    ResultSet resultSet = mock(ResultSet.class);
    private static User user;
    private static Map.Entry<Integer, List<User>> users;
    private static UserDao userDao;
    private static InputStream avatar;

    @BeforeAll
    static void init() {
        TestServiceUtil testServiceUtil = new TestServiceUtil();
        user = testServiceUtil.getAdmin();
        userDao = new MySqlUserDao(dataSource);
        List<User> userList = new ArrayList<>();
        userList.add(user);
        users = Map.entry(1, userList);
        avatar = new InputStream() {
            @Override
            public int read() {
                return 0;
            }
        };
    }

    @Test
    void getSuccessful() throws SQLException, DAOException {
        when(dataSource.getConnection()).thenReturn(con);
        when(con.prepareStatement(anyString())).thenReturn(stmt);
        when(stmt.executeQuery()).thenReturn(resultSet);
        setResultSet();
        User resultUser = userDao.get(SELECT_USER).orElse(null);
        assertNotNull(resultUser);
        assertEquals(user, resultUser);
    }

    @Test
    void getAbsent() throws SQLException, DAOException {
        when(dataSource.getConnection()).thenReturn(con);
        when(con.prepareStatement(anyString())).thenReturn(stmt);
        when(stmt.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(false);
        User resultUser = userDao.get(SELECT_USER).orElse(null);
        assertNull(resultUser);
    }

    @Test
    void getFailed() throws SQLException {
        when(dataSource.getConnection()).thenReturn(con);
        when(con.prepareStatement(anyString())).thenReturn(stmt);
        when(stmt.executeQuery()).thenThrow(SQLException.class);
        assertThrows(DAOException.class, () -> userDao.get(SELECT_USER));
    }

    @Test
    void addSuccessful() throws SQLException {
        when(dataSource.getConnection()).thenReturn(con);
        when(con.prepareStatement(anyString())).thenReturn(stmt);
        when(stmt.executeUpdate()).thenReturn(1);
        setStmt();
        assertDoesNotThrow(() -> userDao.add(user));
    }

    @Test
    void addFailed() throws SQLException {
        when(dataSource.getConnection()).thenReturn(con);
        when(con.prepareStatement(anyString())).thenReturn(stmt);
        when(stmt.executeUpdate()).thenThrow(SQLException.class);
        assertThrows(DAOException.class, () -> userDao.add(user));
    }

    @Test
    void updateSuccessful() throws SQLException {
        when(dataSource.getConnection()).thenReturn(con);
        when(con.prepareStatement(anyString())).thenReturn(stmt);
        when(stmt.executeUpdate()).thenReturn(1);
        setStmt();
        assertDoesNotThrow(() -> userDao.update(user));
    }

    @Test
    void updateFailed() throws SQLException {
        when(dataSource.getConnection()).thenReturn(con);
        when(con.prepareStatement(anyString())).thenReturn(stmt);
        when(stmt.executeUpdate()).thenThrow(SQLException.class);
        assertThrows(DAOException.class, () -> userDao.update(user));
    }

    @Test
    void deleteSuccessful() throws SQLException {
        when(dataSource.getConnection()).thenReturn(con);
        when(con.prepareStatement(anyString())).thenReturn(stmt);
        when(stmt.executeUpdate()).thenReturn(1);
        setStmt();
        assertDoesNotThrow(() -> userDao.delete(1));
    }

    @Test
    void deleteFailed() throws SQLException {
        when(dataSource.getConnection()).thenReturn(con);
        when(con.prepareStatement(anyString())).thenReturn(stmt);
        when(stmt.executeUpdate()).thenThrow(SQLException.class);
        assertThrows(DAOException.class, () -> userDao.delete(1));
    }

    @Test
    void getAllSuccessful() throws SQLException, DAOException {
        when(dataSource.getConnection()).thenReturn(con);
        when(con.prepareStatement(anyString())).thenReturn(stmt);
        when(stmt.executeQuery()).thenReturn(resultSet);
        setResultSet();
        when(stmt.executeQuery(SELECT_FOUND_ROWS)).thenReturn(resultSet);
        when(resultSet.getInt(1)).thenReturn(1);
        Map.Entry<Integer, List<User>> resultUsers = userDao.getAll(SELECT_ALL_USERS);
        assertEquals(users, resultUsers);
    }

    @Test
    void getAllFailed() throws SQLException {
        when(dataSource.getConnection()).thenReturn(con);
        when(con.prepareStatement(anyString())).thenReturn(stmt);
        when(stmt.executeQuery()).thenThrow(SQLException.class);
        assertThrows(DAOException.class, () -> userDao.getAll(SELECT_ALL_USERS));
    }

    @Test
    void addAvatarSuccessful() throws SQLException {
        when(dataSource.getConnection()).thenReturn(con);
        when(con.prepareStatement(anyString())).thenReturn(stmt);
        when(stmt.executeUpdate()).thenReturn(1);
        setStmt();
        doNothing().when(stmt).setBlob(anyInt(), isA(Blob.class));
        assertDoesNotThrow(() -> userDao.addAvatar(1, avatar));
    }

    @Test
    void addAvatarFailed() throws SQLException {
        when(dataSource.getConnection()).thenReturn(con);
        when(con.prepareStatement(anyString())).thenReturn(stmt);
        when(stmt.executeUpdate()).thenThrow(SQLException.class);
        assertThrows(DAOException.class, () -> userDao.addAvatar(1, avatar));
    }

    private void setStmt() throws SQLException {
        doNothing().when(stmt).setInt(anyInt(), anyInt());
        doNothing().when(stmt).setString(anyInt(), anyString());
    }

    private void setResultSet() throws SQLException {
        when(resultSet.next()).thenReturn(true).thenReturn(false).thenReturn(true).thenReturn(false);
        when(resultSet.getInt(USER_ID)).thenReturn(user.getId());
        when(resultSet.getString(USER_LOGIN)).thenReturn(user.getLogin());
        when(resultSet.getString(USER_PASSWORD)).thenReturn(user.getPassword());
        when(resultSet.getString(USER_FIRST_NAME)).thenReturn(user.getName());
        when(resultSet.getString(USER_FAMILY_NAME)).thenReturn(user.getSurname());
        when(resultSet.getString(USER_EMAIL)).thenReturn(user.getEmail());
        when(resultSet.getString(ROLE_NAME)).thenReturn(String.valueOf(user.getRole()));
    }

}