package com.epam.facultative.daos.impl;

/**
 * Holder for fields names of DB tables and beans.
 *
 * @author O.Panchenko
 */

class Fields {

    //User fields
    static final String USER_ID = "id";
    static final String USER_LOGIN = "login";
    static final String USER_PASSWORD = "password";
    static final String USER_FIRST_NAME = "first_name";
    static final String USER_FAMILY_NAME = "last_name";
    static final String USER_EMAIL = "email";
    static final String USER_ROLE_ID = "role_id";
    static final String USER_IS_BLOCK = "block";

    //Roles fields
    static final String ROLE_ID = "id";
    static final String ROLE_TITLE = "name";

    //Courses fields
    static final String COURSE_ID = "id";
    static final String COURSE_TITLE = "title";
    static final String COURSE_DURATION = "duration";
    static final String COURSE_START_DATE = "course_start_date";
    static final String COURSE_DESCRIPTION = "description";
    static final String COURSE_CATEGORY_ID = "category_id";
    static final String COURSE_STATUS_ID = "course_status_id";
    static final String NUMBER_STUDENTS_ON_COURSE = "students_on_course";

    //Categories fields
    static final String CATEGORY_ID = "id";
    static final String CATEGORY_TITLE = "title";
    static final String CATEGORY_DESCRIPTION = "description";

    //Course statuses fields
    static final String STATUS_ID = "id";
    static final String STATUS_TITLE = "title";

    //Grade book fields
    static final String GRADE = "grade";


}
