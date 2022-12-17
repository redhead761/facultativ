package com.epam.facultative.daos.impl;

/**
 * Holder for SQL request.
 *
 * @author O.Panchenko
 */

class Constants {

    //SQL request for Category DAO
    static final String SELECT_All_CATEGORIES = "SELECT * FROM category";
    static final String SELECT_CATEGORY_BY_ID = "SELECT * FROM category WHERE id=?";
    static final String UPDATE_CATEGORY = "UPDATE category SET title=?, description=? WHERE id=?";
    static final String INSERT_CATEGORY = "INSERT INTO category VALUES (DEFAULT,?,?)";
    static final String DELETE_CATEGORY = "DELETE FROM category WHERE id=?";
    static final String SELECT_CATEGORY_BY_TITLE = "SELECT * FROM category WHERE title=?";

    //SQL request for Course DAO
    static final String SELECT_All_COURSES =
            "SELECT course.id, course.title, course.duration, course.start_date, course.amount_students, course.description, course.category_id, " +
                    "status.title AS course_status, category.title AS course_category, category.description AS category_description " +
                    "FROM course JOIN category ON course.category_id = category.id JOIN status ON course.status_id = status.id";
    static final String SELECT_COURSE_BY_ID =
            "SELECT course.id, course.title, course.duration, course.start_date, course.amount_students, course.description, course.category_id," +
                    " status.title AS course_status, category.title AS course_category, category.description AS category_description " +
                    "FROM course JOIN category ON course.category_id = category.id JOIN status ON course.status_id = status.id WHERE course.id=?";
    static final String SELECT_COURSE_BY_TITLE =
            "SELECT course.id, course.title, course.duration, course.start_date, course.amount_students, course.description, course.category_id," +
                    " status.title AS course_status, category.title AS course_category, category.description AS category_description " +
                    "FROM course JOIN category ON course.category_id = category.id JOIN status ON course.status_id = status.id WHERE course.title=?";
    static final String INSERT_COURSE = "INSERT INTO course VALUES (DEFAULT,?,?,?,0,?,?,?)";
    static final String DELETE_COURSE = "DELETE FROM course WHERE id=?";
    static final String SELECT_COURSE_BY_CATEGORY =
            "SELECT SQL_CALC_FOUND_ROWS course.id, course.title, course.duration , course.start_date, course.amount_students, course.description, course.category_id, " +
                    "status.title AS course_status, category.title AS course_category, category.description AS category_description " +
                    "FROM course JOIN category ON course.category_id = category.id " +
                    "JOIN status ON course.status_id = status.id WHERE course.category_id=? LIMIT ?,?";
    static final String INSERT_USERS_COURSE = "INSERT INTO users_course VALUES(?,?,null)";
    static final String UPDATE_USERS_COURSE = "UPDATE users_course SET grade=? WHERE course_id=? AND user_id=?";
    static final String UPDATE_COURSE = "UPDATE course SET title=?, duration=?, " +
            "start_date=?,amount_students=?, description=?, category_id=?, status_id=? WHERE id=?";
    static final String SELECT_COURSES_USER =
            "SELECT SQL_CALC_FOUND_ROWS course.id, course.title, course.duration, course.start_date, course.amount_students, course.description, course.category_id," +
                    " status.title AS course_status, category.title AS course_category, category.description AS category_description " +
                    "FROM course JOIN category ON course.category_id = category.id " +
                    "JOIN status ON course.status_id = status.id " +
                    "JOIN users_course ON course.id = users_course.course_id WHERE user_id=? LIMIT ?,?";
    static final String ADD_NUMBER_STUDENTS_TO_COURSE =
            "UPDATE course SET amount_students = amount_students + 1 WHERE id=?";

    static final String GET_GRADE = "SELECT grade FROM users_course WHERE course_id=? AND user_id=?";
    static final String SELECT_COURSE_BY_STATUS = """
            SELECT course.id, course.title, course.duration, course.start_date, course.amount_students, course.description, course.category_id,
                                 status.title AS course_status, category.title AS course_category, category.description AS category_description
                                FROM course JOIN category ON course.category_id = category.id
                                JOIN status ON course.status_id = status.id
                                JOIN users_course ON course.id = users_course.course_id WHERE user_id=? AND status.id = ?""";

    //SQL request for User DAO
    static final String SELECT_All_USERS =
            "SELECT user.id, user.login, user.password, user.name, user.surname, user.email, user.block, role.name AS role_name " +
                    "FROM user  JOIN role ON role.id = user.role_id";
    static final String SELECT_USER_BY_ID =
            "SELECT user.id, user.login, user.password, user.name, user.surname, user.email, user.block, role.name AS role_name " +
                    "FROM user  JOIN role ON role.id = user.role_id WHERE user.id = ?";
    static final String SELECT_USER_BY_LOGIN =
            "SELECT user.id, user.login, user.password, user.name, user.surname, user.email, user.block, role.name AS role_name " +
                    "FROM user JOIN role ON role.id = user.role_id WHERE user.login=?";
    static final String SELECT_USERS_BY_ROLE =
            "SELECT user.id, user.login, user.password, user.name, user.surname, user.email, user.block, role.name AS role_name " +
                    "FROM user  JOIN role ON role.id = user.role_id WHERE user.role_id=?";
    static final String INSERT_USER = "INSERT INTO user VALUES (DEFAULT,?,?,?,?,?,?,?)";
    static final String DELETE_USER = "DELETE FROM user WHERE id=?";
    static final String SELECT_USERS_COURSE =
            "SELECT user.id, user.login, user.password, user.name, user.surname, user.email, user.block, role.name AS role_name " +
                    "FROM user JOIN role ON user.role_id = role.id JOIN users_course ON user.id = users_course.user_id WHERE course_id=?";
    static final String UPDATE_USER = "UPDATE user SET login=?, password=?, name=?, " +
            "surname=?, email=?, block=?, role_id=? WHERE id=?";

    static final String BLOCK_UNBLOCK_USER = "UPDATE user SET block=? where id=?";
}
