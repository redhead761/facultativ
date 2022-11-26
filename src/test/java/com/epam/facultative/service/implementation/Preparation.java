package com.epam.facultative.service.implementation;

import com.epam.facultative.entity.*;

import java.time.LocalDate;

class Preparation {
    Category getTestCategory() {
        Category category = new Category();
        category.setTitle("testCategory");
        category.setDescription("test description");
        return category;
    }

    User getTestUser() {
        User user = new User();
        user.setLogin("testLogin");
        user.setPassword("testPassword123");
        user.setName("testName");
        user.setSurname("testSurname");
        user.setEmail("test@test.com");
        user.setBlock(false);
        return user;
    }

    Course getTestCourse() {
        Course course = new Course();
        course.setTitle("testTitle");
        course.setDuration(100);
        course.setStartDate(LocalDate.now());
        course.setDescription("testDescription");
        course.setStatus(Status.COMING_SOON);
        return course;
    }
}
