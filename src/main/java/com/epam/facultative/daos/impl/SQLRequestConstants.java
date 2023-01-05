package com.epam.facultative.daos.impl;

/**
 * Holder for SQL request.
 *
 * @author O.Panchenko
 */

class SQLRequestConstants {
    /**
     * //SQL request for Category DAO
     */
    static final String SELECT_All_CATEGORIES = "SELECT * FROM category";
    static final String SELECT_CATEGORY_BY_ID = "SELECT * FROM category WHERE id=?";
    static final String UPDATE_CATEGORY = "UPDATE category SET title=?, description=? WHERE id=?";
    static final String INSERT_CATEGORY = "INSERT INTO category VALUES (DEFAULT,?,?)";
    static final String DELETE_CATEGORY = "DELETE FROM category WHERE id=?";
    static final String SELECT_CATEGORY_BY_TITLE = "SELECT * FROM category WHERE title=?";
    static final String SELECT_ALL_CATEGORIES_PAGINATION = "SELECT SQL_CALC_FOUND_ROWS * FROM category LIMIT ?,?";

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * //SQL request for Teacher DAO
     */
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    static final String INSERT_TEACHER = "INSERT INTO teacher VALUES (?,?)";
    static final String UPDATE_TEACHER = "UPDATE teacher SET degree=? WHERE user_id=?";
    static final String SELECT_ALL_TEACHERS = "SELECT * FROM teacher JOIN user ON user_id = user.id";
    static final String SELECT_TEACHER_BY_ID = "SELECT * FROM teacher JOIN user ON user_id = user.id WHERE user_id=?";
    static final String SELECT_TEACHER_BY_LOGIN =
            "SELECT * FROM teacher JOIN user ON user_id = user.id WHERE user.login = ?";
    static final String DELETE_TEACHER = "DELETE FROM teacher WHERE user_id=?";
    static final String SELECT_ALL_TEACHERS_PAGINATION =
            "SELECT SQL_CALC_FOUND_ROWS * FROM teacher JOIN user ON user_id = user.id LIMIT ?,?";

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * //SQL request for Student DAO
     */
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    static final String INSERT_STUDENT = "INSERT INTO student VALUES (?,?,?)";
    static final String UPDATE_STUDENT = "UPDATE student SET course_number=?, block=? WHERE user_id=?";
    static final String SELECT_ALL_STUDENTS = "SELECT * FROM student JOIN user ON user_id = user.id";
    static final String SELECT_STUDENT_BY_ID = "SELECT * FROM student JOIN user ON user_id = user.id WHERE user_id=?";
    static final String SELECT_STUDENT_BY_LOGIN =
            "SELECT * FROM student JOIN user ON user_id = user.id WHERE user.login =?";
    static final String DELETE_STUDENT = "DELETE FROM student WHERE user_id=?";
    static final String SELECT_ALL_STUDENTS_PAGINATION =
            "SELECT SQL_CALC_FOUND_ROWS * FROM student JOIN user ON user_id = user.id LIMIT ?,?";
    static final String SELECT_STUDENTS_BY_COURSE =
            "SELECT SQL_CALC_FOUND_ROWS * FROM student JOIN user ON user_id = user.id JOIN journal ON student_id = user_id WHERE course_id = ? LIMIT ?,?";
    static final String UPDATE_BLOCK = "UPDATE student SET block=? WHERE user_id=?";
    static final String SELECT_GRADE = "SELECT grade FROM journal WHERE course_id=? AND student_id=?";

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * //SQL request for User DAO
     */
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    static final String SELECT_All_USERS = "SELECT * FROM user  JOIN role ON role.id = user.role_id";
    static final String SELECT_USER_BY_ID = "SELECT * FROM user  JOIN role ON role.id = user.role_id WHERE user.id = ?";
    static final String SELECT_USER_BY_LOGIN = "SELECT * FROM user JOIN role ON role.id = user.role_id WHERE user.login = ?";
    static final String INSERT_USER = "INSERT INTO user VALUES (DEFAULT,?,?,?,?,?,?)";
    static final String DELETE_USER = "DELETE FROM user WHERE id=?";
    static final String UPDATE_USER = "UPDATE user SET login=?, password=?, name=?,surname=?, email=?, role_id=? WHERE id=?";
    static final String SELECT_FOUND_ROWS = "SELECT FOUND_ROWS()";

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * //SQL request for Course DAO
     */
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    static final String SELECT_All_COURSES =
            "SELECT * FROM course JOIN category ON category_id = category.id JOIN status ON status_id = status.id" +
                    " LEFT JOIN teacher ON teacher_id = user_id LEFT JOIN user ON teacher.user_id = user.id";
    static final String SELECT_COURSE_BY_ID =
            "SELECT * FROM course JOIN category ON category_id = category.id JOIN status ON status_id = status.id " +
                    "LEFT JOIN teacher ON teacher_id = user_id LEFT JOIN user ON teacher.user_id = user.id WHERE course.id=?";
    static final String SELECT_COURSE_BY_TITLE =
            "SELECT * FROM course JOIN category ON category_id = category.id JOIN status ON status_id = status.id " +
                    "LEFT JOIN teacher ON teacher_id = user_id LEFT JOIN user ON teacher.user_id = user.id WHERE course.title=?";
    static final String INSERT_COURSE = "INSERT INTO course VALUES (DEFAULT,?,?,?,DEFAULT,?,?,?,DEFAULT)";
    static final String DELETE_COURSE = "DELETE FROM course WHERE id=?";
    static final String SELECT_COURSE_BY_CATEGORY =
            "SELECT SQL_CALC_FOUND_ROWS * FROM course JOIN category ON category_id = category.id " +
                    "JOIN status ON status_id = status.id LEFT JOIN teacher ON teacher_id = user_id " +
                    "LEFT JOIN user ON teacher.user_id = user.id WHERE course.category_id=? LIMIT ?,?";
    static final String UPDATE_COURSE = "UPDATE course SET title=?, duration=?," +
            "start_date=?, description=?, category_id=?, status_id=?, teacher_id=? WHERE id=?";
    static final String SELECT_COURSES_TEACHER =
            "SELECT SQL_CALC_FOUND_ROWS * FROM course JOIN category ON category_id = category.id " +
                    "JOIN status ON status_id = status.id LEFT JOIN teacher ON teacher_id = user_id " +
                    "LEFT JOIN user ON teacher.user_id = user.id WHERE user_id=? LIMIT ?,?";
    static final String SELECT_COURSES_STUDENT =
            "SELECT SQL_CALC_FOUND_ROWS * FROM course JOIN category ON category_id = category.id " +
                    "JOIN status ON status_id = status.id LEFT JOIN teacher ON teacher_id = user_id " +
                    "LEFT JOIN user ON teacher.user_id = user.id JOIN journal ON course.id = journal.course_id " +
                    "JOIN student ON journal.student_id = student.user_id WHERE student.user_id=? LIMIT ?,?";
    static final String SELECT_COURSE_BY_STATUS =
            "SELECT SQL_CALC_FOUND_ROWS * FROM course JOIN category ON category_id = category.id " +
                    "JOIN status ON status_id = status.id LEFT JOIN teacher ON teacher_id = user_id " +
                    "LEFT JOIN user ON teacher.user_id = user.id JOIN journal ON course.id = journal.course_id " +
                    "JOIN student ON journal.student_id = student.user_id WHERE student.user_id=? AND status.id=? LIMIT ?,?";
    static final String SELECT_ALL_PAGINATION =
            "SELECT SQL_CALC_FOUND_ROWS * FROM course JOIN category ON category_id = category.id " +
                    "JOIN status ON status_id = status.id LEFT JOIN teacher ON teacher_id = user_id" +
                    " LEFT JOIN user ON teacher.user_id = user.id LIMIT ?,?";

    static final String INSERT_JOURNAL = "INSERT INTO journal VALUES(?,?,DEFAULT)";
    static final String UPDATE_JOURNAL = "UPDATE journal SET grade=? WHERE course_id=? AND student_id=?";
    static final String ADD_NUMBER_STUDENTS_TO_COURSE =
            "UPDATE course SET amount_students = amount_students + 1 WHERE id=?";

}
