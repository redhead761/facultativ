package com.epam.facultative.daos.impl;

import com.epam.facultative.entity.Category;
import com.epam.facultative.entity.Role;
import com.epam.facultative.entity.User;
import org.apache.ibatis.jdbc.ScriptRunner;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

class Preparation {
    String TEST_DB_URL = "jdbc:mysql://localhost:3306/facultative_test?user=root&password=132465";
    String TEST_DB_PATH = "sql/facultative_test.sql";

    void refreshDB() throws SQLException, FileNotFoundException {
        Connection con = DriverManager.getConnection(TEST_DB_URL);
        ScriptRunner sr = new ScriptRunner(con);
        Reader reader = new BufferedReader(new FileReader(TEST_DB_PATH));
        sr.setLogWriter(null);
        sr.runScript(reader);
    }

    Category getTestCategory() {
        Category testCategory = new Category();
        testCategory.setTitle("testCategory");
        testCategory.setDescription("test description");
        return testCategory;
    }

    User getTestUser() {
        User testUser = new User();
        testUser.setLogin("testLogin");
        testUser.setPassword("testPassword");
        testUser.setName("testName");
        testUser.setSurname("testSurname");
        testUser.setEmail("test@test.com");
        testUser.setBlock(false);
        testUser.setRole(Role.ADMIN);
        return testUser;
    }

}
