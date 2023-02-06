package com.epam.facultative.model.dao.impl_sql.сonstants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Class containing My SQL database fields
 *
 * @author Oleksandr Panchenko
 * @version 1.0
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FieldsConstants {

    /**
     * Fields for category table
     */
    public static final String CATEGORY_ID = "category.id";
    public static final String CATEGORY_TITLE = "category.title";
    public static final String CATEGORY_DESCRIPTION = "category.description";

    /**
     * Fields for course table
     */
    public static final String COURSE_ID = "course.id";
    public static final String COURSE_TITLE = "course.title";
    public static final String COURSE_DURATION = "course.duration";
    public static final String COURSE_START_DATE = "course.start_date";
    public static final String COURSE_AMOUNT_STUDENTS = "course.amount_students";
    public static final String COURSE_DESCRIPTION = "course.description";

    /**
     * Fields for user table
     */
    public static final String USER_ID = "user.id";
    public static final String USER_LOGIN = "user.login";
    public static final String USER_PASSWORD = "user.password";
    public static final String USER_FIRST_NAME = "user.name";
    public static final String USER_FAMILY_NAME = "user.surname";
    public static final String USER_EMAIL = "user.email";
    public static final String USER_AVATAR = "avatar";

    /**
     * Fields for teacher table
     */
    public static final String TEACHER_ID = "user_id";
    public static final String TEACHER_DEGREE = "teacher.degree";

    /**
     * Fields for student table
     */
    public static final String STUDENT_ID = "user_id";
    public static final String STUDENT_COURSE_NUMBER = "student.course_number";
    public static final String STUDENT_BLOCK = "student.block";

    /**
     * Fields for role table
     */
    public static final String ROLE_NAME = "role.name";

    /**
     * Fields for status table
     */
    public static final String STATUS_ID = "status.id";
    public static final String STATUS_TITLE = "status.title";

    /**
     * Fields for journal table
     */
    public static final String GRADE = "journal.grade";
}
