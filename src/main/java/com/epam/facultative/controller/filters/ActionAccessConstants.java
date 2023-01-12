package com.epam.facultative.controller.filters;

import java.util.HashSet;
import java.util.Set;

import static com.epam.facultative.controller.actions.ActionNameConstants.*;

public class ActionAccessConstants {
    private static final Set<String> NO_LOGGED_USER_ACTIONS = new HashSet<>();
    private static final Set<String> ADMIN_ACTIONS = new HashSet<>();
    private static final Set<String> TEACHER_ACTIONS = new HashSet<>();
    private static final Set<String> STUDENT_ACTIONS = new HashSet<>();
    private static final Set<String> GENERAL_ACTION = new HashSet<>();

    static {
        NO_LOGGED_USER_ACTIONS.add(AUTH_ACTION);
        NO_LOGGED_USER_ACTIONS.add(REGISTER_ACTION);
        NO_LOGGED_USER_ACTIONS.add(COURSES_ACTION);
        NO_LOGGED_USER_ACTIONS.add(TEACHERS_ACTION);
    }

    static {
        GENERAL_ACTION.add(LOG_OUT_ACTION);
        GENERAL_ACTION.add(MY_CABINET_ACTION);
        GENERAL_ACTION.add(COURSES_ACTION);
        GENERAL_ACTION.add(TEACHERS_ACTION);
    }

    static {
        ADMIN_ACTIONS.addAll(GENERAL_ACTION);
        ADMIN_ACTIONS.add(MANAGE_COURSES_ACTION);
        ADMIN_ACTIONS.add(MANAGE_CATEGORIES_ACTION);
        ADMIN_ACTIONS.add(MANAGE_TEACHERS_ACTION);
        ADMIN_ACTIONS.add(MANAGE_STUDENTS_ACTION);
        ADMIN_ACTIONS.add(SHOW_CATEGORY_FORM_ACTION);
        ADMIN_ACTIONS.add(ADD_CATEGORY_ACTION);
        ADMIN_ACTIONS.add(UPDATE_CATEGORY_ACTION);
        ADMIN_ACTIONS.add(DELETE_CATEGORY_ACTION);
        ADMIN_ACTIONS.add(SHOW_EDIT_COURSE_ACTION);
        ADMIN_ACTIONS.add(ADD_COURSE_ACTION);
        ADMIN_ACTIONS.add(UPDATE_COURSE_ACTION);
        ADMIN_ACTIONS.add(DELETE_COURSE_ACTION);
        ADMIN_ACTIONS.add(SHOW_ASSIGN_PAGE_ACTION);
        ADMIN_ACTIONS.add(ASSIGN_ACTION);
        ADMIN_ACTIONS.add(BLOCK_ACTION);
        ADMIN_ACTIONS.add(UNBLOCK_ACTION);
        ADMIN_ACTIONS.add(SHOW_ADD_COURSE_ACTION);
        ADMIN_ACTIONS.add(ADD_TEACHER_ACTION);
    }

    static {
        TEACHER_ACTIONS.addAll(GENERAL_ACTION);
        TEACHER_ACTIONS.add(SHOW_TEACHER_COURSES_ACTION);
        TEACHER_ACTIONS.add(SHOW_JOURNAL_ACTION);
        TEACHER_ACTIONS.add(GRADE_ACTION);
        TEACHER_ACTIONS.add(ALL_COURSE_ACTION);
    }

    static {
        STUDENT_ACTIONS.addAll(GENERAL_ACTION);
        STUDENT_ACTIONS.add(ENROLL_ACTION);
        STUDENT_ACTIONS.add(SHOW_STUDENT_COURSES_ACTION);
        STUDENT_ACTIONS.add(SHOW_COMING_SOON_COURSES_ACTION);
        STUDENT_ACTIONS.add(SHOW_PROGRESS_COURSES_ACTION);
        STUDENT_ACTIONS.add(SHOW_COMPLETED_COURSES_ACTION);
        STUDENT_ACTIONS.add(SHOW_ALL_COURSES_ACTION);
        STUDENT_ACTIONS.add(SHOW_RESULT_ACTION);
    }

    public static Set<String> getNoLoggedUserActions() {
        return NO_LOGGED_USER_ACTIONS;
    }

    public static Set<String> getAdminActions() {
        return ADMIN_ACTIONS;
    }

    public static Set<String> getTeacherActions() {
        return TEACHER_ACTIONS;
    }

    public static Set<String> getStudentActions() {
        return STUDENT_ACTIONS;
    }
}
