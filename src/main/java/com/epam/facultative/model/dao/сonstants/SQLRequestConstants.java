package com.epam.facultative.model.dao.сonstants;

/**
 * Holder for SQL request.
 *
 * @author O.Panchenko
 */

public class SQLRequestConstants {
    /**
     * //SQL request for Category DAO
     */
    public static final String UPDATE_CATEGORY = "UPDATE category SET title=?, description=? WHERE id=?";
    public static final String INSERT_CATEGORY = "INSERT INTO category VALUES (DEFAULT,?,?)";
    public static final String DELETE_CATEGORY = "DELETE FROM category WHERE id=?";
    public static final String SELECT_CATEGORY = "SELECT * FROM category %s";
    public static final String SELECT_ALL_CATEGORIES_PAGINATION = "SELECT SQL_CALC_FOUND_ROWS * FROM category %s";

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * //SQL request for Teacher DAO
     */
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public static final String INSERT_TEACHER = "INSERT INTO teacher VALUES (?,?)";
    public static final String UPDATE_TEACHER = "UPDATE teacher SET degree=? WHERE user_id=?";
    public static final String SELECT_TEACHER =
            "SELECT * FROM teacher JOIN user ON user_id = user.id %s";
    public static final String DELETE_TEACHER = "DELETE FROM teacher WHERE user_id=?";
    public static final String SELECT_ALL_TEACHERS_PAGINATION =
            "SELECT SQL_CALC_FOUND_ROWS * FROM teacher JOIN user ON user_id = user.id %s";

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * //SQL request for Student DAO
     */
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public static final String INSERT_STUDENT = "INSERT INTO student VALUES (?,?,?)";
    public static final String UPDATE_STUDENT = "UPDATE student SET course_number=?, block=? WHERE user_id=?";
    public static final String SELECT_STUDENT =
            "SELECT * FROM student JOIN user ON user_id = user.id %s";
    public static final String DELETE_STUDENT = "DELETE FROM student WHERE user_id=?";
    public static final String SELECT_ALL_STUDENTS_PAGINATION =
            "SELECT SQL_CALC_FOUND_ROWS * FROM student JOIN user ON user_id = user.id %s";
    public static final String SELECT_STUDENTS_BY_COURSE =
            "SELECT SQL_CALC_FOUND_ROWS * FROM student JOIN user ON user_id = user.id JOIN journal ON student_id = user_id %s";
    public static final String UPDATE_BLOCK = "UPDATE student SET block=? WHERE user_id=?";
    public static final String SELECT_GRADE = "SELECT grade FROM journal WHERE course_id=? AND student_id=?";

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * //SQL request for User DAO
     */
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public static final String SELECT_USER = "SELECT * FROM user JOIN role ON role.id = user.role_id %s";
    public static final String INSERT_USER = "INSERT INTO user VALUES (DEFAULT,?,?,?,?,?,?,DEFAULT)";
    public static final String DELETE_USER = "DELETE FROM user WHERE id=?";
    public static final String UPDATE_USER = "UPDATE user SET login=?, password=?, name=?,surname=?, email=?, role_id=? WHERE id=?";
    public static final String SELECT_FOUND_ROWS = "SELECT FOUND_ROWS()";
    public static final String ADD_AVATAR = "UPDATE user SET avatar=? WHERE id = ?";

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * //SQL request for Course DAO
     */
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public static final String SELECT_COURSE =
            "SELECT * FROM course JOIN category ON category_id = category.id JOIN status ON status_id = status.id " +
                    "LEFT JOIN teacher ON teacher_id = user_id LEFT JOIN user ON teacher.user_id = user.id %s";
    public static final String INSERT_COURSE = "INSERT INTO course VALUES (DEFAULT,?,?,?,DEFAULT,?,?,?,DEFAULT)";
    public static final String DELETE_COURSE = "DELETE FROM course WHERE id=?";
    public static final String UPDATE_COURSE = "UPDATE course SET title=?, duration=?," +
            "start_date=?, description=?, category_id=?, status_id=?, teacher_id=? WHERE id=?";
    public static final String SELECT_COURSE_BY_JOURNAL =
            "SELECT SQL_CALC_FOUND_ROWS * FROM course JOIN category ON category_id = category.id " +
                    "JOIN status ON status_id = status.id LEFT JOIN teacher ON teacher_id = user_id " +
                    "LEFT JOIN user ON teacher.user_id = user.id JOIN journal ON course.id = journal.course_id " +
                    "JOIN student ON journal.student_id = student.user_id %s";
    public static final String SELECT_ALL_PAGINATION =
            "SELECT SQL_CALC_FOUND_ROWS * FROM course JOIN category ON category_id = category.id " +
                    "JOIN status ON status_id = status.id LEFT JOIN teacher ON teacher_id = user_id" +
                    " LEFT JOIN user ON teacher.user_id = user.id %s";

    public static final String INSERT_JOURNAL = "INSERT INTO journal VALUES(?,?,DEFAULT)";
    public static final String UPDATE_JOURNAL = "UPDATE journal SET grade=? WHERE course_id=? AND student_id=?";
    public static final String ADD_NUMBER_STUDENTS_TO_COURSE =
            "UPDATE course SET amount_students = amount_students + 1 WHERE id=?";

}
