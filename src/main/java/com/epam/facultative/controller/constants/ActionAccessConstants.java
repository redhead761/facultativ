package com.epam.facultative.controller.constants;

import java.util.HashSet;
import java.util.Set;

import static com.epam.facultative.controller.constants.ActionNameConstants.*;

/**
 * Containing all action names for filter
 *
 * @author Oleksandr Panchenko
 * @version 1.0
 */
public class ActionAccessConstants {
    private static final Set<String> NO_LOGGED_USER_ACTIONS = new HashSet<>();
    private static final Set<String> ADMIN_ACTIONS = new HashSet<>();
    private static final Set<String> TEACHER_ACTIONS = new HashSet<>();
    private static final Set<String> STUDENT_ACTIONS = new HashSet<>();
    private static final Set<String> COMMON_ACTIONS = new HashSet<>();
    private static final Set<String> COMMON_LOGGED_ACTION = new HashSet<>();

    static {
        COMMON_ACTIONS.add(COURSES_ACTION);
        COMMON_ACTIONS.add(TEACHERS_ACTION);
        COMMON_ACTIONS.add(DOWNLOAD_COURSES_ACTION);
    }

    static {
        COMMON_LOGGED_ACTION.add(LOG_OUT_ACTION);
        COMMON_LOGGED_ACTION.add(MY_CABINET_ACTION);
        COMMON_LOGGED_ACTION.add(CHANGE_PASSWORD_ACTION);
        COMMON_LOGGED_ACTION.add(ADD_AVATAR_ACTION);
    }

    static {
        NO_LOGGED_USER_ACTIONS.addAll(COMMON_ACTIONS);
        NO_LOGGED_USER_ACTIONS.add(AUTH_ACTION);
        NO_LOGGED_USER_ACTIONS.add(REGISTER_ACTION);
        NO_LOGGED_USER_ACTIONS.add(RECOVERY_PASSWORD_ACTION);
    }


    static {
        ADMIN_ACTIONS.addAll(COMMON_ACTIONS);
        ADMIN_ACTIONS.addAll(COMMON_LOGGED_ACTION);
        ADMIN_ACTIONS.add(MANAGE_COURSES_ACTION);
        ADMIN_ACTIONS.add(MANAGE_CATEGORIES_ACTION);
        ADMIN_ACTIONS.add(MANAGE_TEACHERS_ACTION);
        ADMIN_ACTIONS.add(MANAGE_STUDENTS_ACTION);
        ADMIN_ACTIONS.add(ADD_CATEGORY_ACTION);
        ADMIN_ACTIONS.add(UPDATE_CATEGORY_ACTION);
        ADMIN_ACTIONS.add(DELETE_CATEGORY_ACTION);
        ADMIN_ACTIONS.add(ADD_COURSE_ACTION);
        ADMIN_ACTIONS.add(UPDATE_COURSE_ACTION);
        ADMIN_ACTIONS.add(DELETE_COURSE_ACTION);
        ADMIN_ACTIONS.add(ASSIGN_ACTION);
        ADMIN_ACTIONS.add(UPDATE_BLOCK_ACTION);
        ADMIN_ACTIONS.add(ADD_TEACHER_ACTION);
        ADMIN_ACTIONS.add(DELETE_TEACHER_ACTION);
    }

    static {
        TEACHER_ACTIONS.addAll(COMMON_ACTIONS);
        TEACHER_ACTIONS.addAll(COMMON_LOGGED_ACTION);
        TEACHER_ACTIONS.add(SHOW_TEACHER_COURSES_ACTION);
        TEACHER_ACTIONS.add(SHOW_JOURNAL_ACTION);
        TEACHER_ACTIONS.add(GRADE_ACTION);
        TEACHER_ACTIONS.add(EDIT_TEACHER_ACTION);
    }

    static {
        STUDENT_ACTIONS.addAll(COMMON_ACTIONS);
        STUDENT_ACTIONS.addAll(COMMON_LOGGED_ACTION);
        STUDENT_ACTIONS.add(ENROLL_ACTION);
        STUDENT_ACTIONS.add(SHOW_STUDENT_COURSES_ACTION);
        STUDENT_ACTIONS.add(SHOW_ALL_COURSES_ACTION);
        STUDENT_ACTIONS.add(SHOW_RESULT_ACTION);
        STUDENT_ACTIONS.add(CERTIFICATE_ACTION);
        STUDENT_ACTIONS.add(EDIT_STUDENT_ACTION);
    }
    /**
     *
     *
     * @return {@code Set<String>} - with no logged user actions
     */
    public static Set<String> getNoLoggedUserActions() {
        return NO_LOGGED_USER_ACTIONS;
    }

    /**
     * @return {@code Set<String>} - with admin actions
     */
    public static Set<String> getAdminActions() {
        return ADMIN_ACTIONS;
    }
    /**
     * @return {@code Set<String>} - with teacher actions
     */
    public static Set<String> getTeacherActions() {
        return TEACHER_ACTIONS;
    }
    /**
     * @return {@code Set<String>} - with student actions
     */
    public static Set<String> getStudentActions() {
        return STUDENT_ACTIONS;
    }
}
