package com.epam.facultative.daos.impl;

/**
 * Holder for fields names of DB tables and beans.
 *
 * @author O.Panchenko
 */

class Fields {

    //Category fields
    static final String CATEGORY_ID = "category.id";
    static final String CATEGORY_TITLE = "category.title";
    static final String CATEGORY_DESCRIPTION = "category.description";

    //Course fields
    static final String COURSE_ID = "course.id";
    static final String COURSE_TITLE = "course.title";
    static final String COURSE_DURATION = "course.duration";
    static final String COURSE_START_DATE = "course.start_date";
    static final String COURSE_AMOUNT_STUDENTS = "course.amount_students";
    static final String COURSE_DESCRIPTION = "course.description";
    static final String CATEGORY_ID = "category.id";
    static final String COURSE_STATUS = "status.title";
    static final String COURSE_GRADE = "grade";

    //User fields
    static final String USER_ID = "user.id";
    static final String USER_LOGIN = "login";
    static final String USER_PASSWORD = "password";
    static final String USER_FIRST_NAME = "name";
    static final String USER_FAMILY_NAME = "surname";
    static final String USER_EMAIL = "email";
    static final String USER_IS_BLOCK = "block";
    static final String USER_ROLE_NAME = "role_name";

}
