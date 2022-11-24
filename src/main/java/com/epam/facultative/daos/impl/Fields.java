package com.epam.facultative.daos.impl;

/**
 * Holder for fields names of DB tables and beans.
 *
 * @author O.Panchenko
 */

class Fields {

    //Category fields
    static final String CATEGORY_ID = "id";
    static final String CATEGORY_TITLE = "title";
    static final String CATEGORY_DESCRIPTION = "description";

    //Course fields
    static final String COURSE_ID = "id";
    static final String COURSE_TITLE = "title";
    static final String COURSE_DURATION = "duration";
    static final String COURSE_START_DATE = "start_date";
    static final String COURSE_AMOUNT_STUDENTS = "amount_students";
    static final String COURSE_DESCRIPTION = "description";
    static final String COURSE_CATEGORY_ID = "category_id";
    static final String COURSE_STATUS = "course_status";
    static final String COURSE_CATEGORY = "course_category";
    static final String COURSE_CATEGORY_DESCRIPTION = "category_description";
    static final String COURSE_GRADE = "grade";

    //User fields
    static final String USER_ID = "id";
    static final String USER_LOGIN = "login";
    static final String USER_PASSWORD = "password";
    static final String USER_FIRST_NAME = "name";
    static final String USER_FAMILY_NAME = "surname";
    static final String USER_EMAIL = "email";
    static final String USER_IS_BLOCK = "block";
    static final String USER_ROLE_NAME = "role_name";

}
