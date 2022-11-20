package com.epam.facultative.daos;

/**
 * Holder for SQL request.
 *
 * @author O.Panchenko
 */

class Constants {

    //SQL request for Category DAO
    static final String SELECT_All_CATEGORIES = "SELECT * FROM categories";
    static final String SELECT_CATEGORY_BY_ID = "SELECT * FROM categories WHERE id=?";
    static final String UPDATE_CATEGORY = "UPDATE categories SET title=?, description=? WHERE id=?";
    static final String INSERT_CATEGORY = "INSERT INTO categories VALUES (DEFAULT,?,?)";
    static final String DELETE_CATEGORY = "DELETE FROM categories WHERE id=?";
    static final String SELECT_CATEGORY_BY_TITLE = "SELECT * FROM categories WHERE title=?";

    //SQL request for Course DAO
    static final String SELECT_All_COURSES = "SELECT * FROM courses";
    static final String SELECT_COURSE_BY_ID = "SELECT * FROM courses WHERE id=?";
    static final String SELECT_COURSE_BY_TITLE = "SELECT * FROM courses WHERE title=?";
    static final String INSERT_COURSE = "INSERT INTO courses VALUES (DEFAULT,?,?,?,?,0,?,?)";
    static final String DELETE_COURSE = "DELETE FROM courses WHERE id=?";
    static final String SELECT_COURSE_BY_CATEGORY = "SELECT * FROM courses WHERE category_id=?";
    static final String INSERT_USERS_COURSE = "INSERT INTO users_course VALUES(?,?,null)";
    static final String UPDATE_COURSE = "UPDATE courses SET title=?, duration=?, " +
            "course_start_date=?, description=?, category_id=?, course_status_id=?,students_on_course=0 WHERE id=?";
    static final String SELECT_COURSES_USER =
            "SELECT * FROM courses JOIN users_course ON courses.id = users_course.course_id WHERE user_id=?";
    static final String ADD_NUMBER_STUDENTS_TO_COURSE =
            "UPDATE courses SET students_on_course = students_on_course + 1 WHERE id=?";

    //SQL request for Role DAO
    static final String SELECT_All_ROLES = "SELECT * FROM roles";
    static final String SELECT_ROLE_BY_ID = "SELECT * FROM roles WHERE id=?";
    static final String UPDATE_ROLE = "UPDATE roles SET name=? WHERE id=?";
    static final String INSERT_ROLE = "INSERT INTO roles VALUES (DEFAULT,?)";
    static final String DELETE_ROLE = "DELETE FROM roles WHERE id=?";
    static final String SELECT_ROLE_BY_TITLE = "SELECT * FROM roles WHERE name=?";

    //SQL request for Status DAO
    static final String SELECT_All_STATUSES = "SELECT * FROM course_statuses";
    static final String SELECT_STATUS_BY_ID = "SELECT * FROM course_statuses WHERE id=?";
    static final String UPDATE_STATUS = "UPDATE course_statuses SET title=? WHERE id=?";
    static final String INSERT_STATUS = "INSERT INTO course_statuses VALUES (DEFAULT,?)";
    static final String DELETE_STATUS = "DELETE FROM course_statuses WHERE id=?";
    static final String SELECT_STATUS_BY_TITLE = "SELECT * FROM course_statuses WHERE title=?";

    //SQL request for User DAO
    static final String SELECT_All_USERS = "SELECT * FROM users";
    static final String SELECT_USER_BY_ID = "SELECT * FROM users WHERE id=?";
    static final String SELECT_USER_BY_LOGIN = "SELECT * FROM users WHERE login=?";
    static final String SELECT_USERS_BY_ROLE = "SELECT * FROM users WHERE role_id=?";
    static final String INSERT_USER = "INSERT INTO users VALUES (DEFAULT,?,?,?,?,?,?,?)";
    static final String DELETE_USER = "DELETE FROM users WHERE id=?";
    static final String SELECT_USERS_COURSE =
            "SELECT * FROM users JOIN users_course ON users.id = users_course.user_id WHERE course_id=?";
    static final String UPDATE_USER = "UPDATE users SET login=?, password=?, first_name=?, " +
            "last_name=?, email=?, block=?, role_id=? WHERE id=?";
}
