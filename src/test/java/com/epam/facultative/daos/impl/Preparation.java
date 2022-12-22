//package com.epam.facultative.daos.impl;
//
//import com.epam.facultative.entities.*;
//import org.apache.ibatis.jdbc.ScriptRunner;
//
//import java.io.BufferedReader;
//import java.io.FileNotFoundException;
//import java.io.FileReader;
//import java.io.Reader;
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.SQLException;
//import java.time.LocalDate;
//
//class Preparation {
//    String TEST_DB_URL = "jdbc:mysql://localhost:3306/facultative_test?user=root&password=132465";
//    String TEST_DB_PATH = "sql/facultative_test.sql";
//
//    void refreshDB() throws SQLException, FileNotFoundException {
//        Connection con = DriverManager.getConnection(TEST_DB_URL);
//        ScriptRunner sr = new ScriptRunner(con);
//        Reader reader = new BufferedReader(new FileReader(TEST_DB_PATH));
//        sr.setLogWriter(null);
//        sr.runScript(reader);
//    }
//
//    Category getTestCategory() {
//        Category category = new Category();
//        category.setTitle("testCategory");
//        category.setDescription("test description");
//        return category;
//    }
//
//    User getTestUser() {
//        User user = new User();
//        user.setLogin("testLogin");
//        user.setPassword("testPassword");
//        user.setName("testName");
//        user.setSurname("testSurname");
//        user.setEmail("test@test.com");
//        user.setBlock(false);
//        user.setRole(Role.ADMIN);
//        return user;
//    }
//
//    Course getTestCourse() {
//        Course course = new Course();
//        course.setTitle("testTitle");
//        course.setDuration(100);
//        course.setStartDate(LocalDate.now());
//        course.setDescription("testDescription");
//        course.setStatus(Status.COMING_SOON);
//        return course;
//    }
//
//}
