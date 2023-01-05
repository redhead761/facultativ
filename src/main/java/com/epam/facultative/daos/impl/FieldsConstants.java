package com.epam.facultative.daos.impl;

/**
 * Holder for fields names of DB tables and beans.
 *
 * @author O.Panchenko
 */

public class FieldsConstants {

    //Category fields
    public static final String CATEGORY_ID = "category.id";
    public static final String CATEGORY_TITLE = "category.title";
    public static final String CATEGORY_DESCRIPTION = "category.description";

    //Course fields
    public static final String COURSE_ID = "course.id";
    public static final String COURSE_TITLE = "course.title";
    public static final String COURSE_DURATION = "course.duration";
    public static final String COURSE_START_DATE = "course.start_date";
    public static final String COURSE_AMOUNT_STUDENTS = "course.amount_students";
    public static final String COURSE_DESCRIPTION = "course.description";

    //User fields
    public static final String USER_ID = "user.id";
    public static final String USER_LOGIN = "user.login";
    public static final String USER_PASSWORD = "user.password";
    public static final String USER_FIRST_NAME = "user.name";
    public static final String USER_FAMILY_NAME = "user.surname";
    public static final String USER_EMAIL = "user.email";

    //Teacher fields
    public static final String TEACHER_ID = "user_id";
    public static final String TEACHER_DEGREE = "teacher.degree";

    //Student fields
    public static final String STUDENT_ID = "user_id";
    public static final String STUDENT_COURSE_NUMBER = "student.course_number";
    public static final String STUDENT_BLOCK = "student.block";

    //Role fields
    public static final String ROLE_ID = "role.id";
    public static final String ROLE_NAME = "role.name";

    //Status fields
    public static final String STATUS_ID = "status.id";
    public static final String STATUS_TITLE = "status.title";

    //Journal fields
    public static final String GRADE = "journal.grade";
}
