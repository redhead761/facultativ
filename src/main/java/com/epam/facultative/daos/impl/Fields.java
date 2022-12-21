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
    static final String COURSE_DESCRIPTION = "course.description";

    //User fields
    static final String USER_ID = "user.id";
    static final String USER_LOGIN = "user.login";
    static final String USER_PASSWORD = "user.password";
    static final String USER_FIRST_NAME = "user.name";
    static final String USER_FAMILY_NAME = "user.surname";
    static final String USER_EMAIL = "user.email";

    //Teacher fields
    static final String TEACHER_ID = "teacher.id";
    static final String TEACHER_RANK = "teacher.rank";

    //Student fields
    static final String STUDENT_ID = "student.id";
    static final String STUDENT_COURSE_NUMBER = "student.course_number";
    static final String STUDENT_BLOCK = "student.block";

    //Role fields
    static final String ROLE_ID = "role.id";
    static final String ROLE_NAME = "role.name";

    //Status fields
    static final String STATUS_ID = "status.id";
    static final String STATUS_TITLE = "status.title";

    //Journal fields
    static final String GRADE = "journal.grade";
}
