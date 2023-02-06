package com.epam.facultative.controller.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Containing all action names
 *
 * @author Oleksandr Panchenko
 * @version 1.0
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ActionNameConstants {
    /**
     * Any user's actions
     */
    public static String CONTROLLER = "controller?action=";
    public static String AUTH_ACTION = "auth";
    public static String REGISTER_ACTION = "register";
    public static String COURSES_ACTION = "courses";
    public static String TEACHERS_ACTION = "teachers";
    public static String DOWNLOAD_COURSES_ACTION = "download_courses";
    public static String RECOVERY_PASSWORD_ACTION = "recovery_password";
    /**
     * Admin actions
     */
    public static String MANAGE_COURSES_ACTION = "manage_courses";
    public static String MANAGE_CATEGORIES_ACTION = "manage_categories";
    public static String MANAGE_TEACHERS_ACTION = "manage_teachers";
    public static String MANAGE_STUDENTS_ACTION = "manage_students";
    public static String ADD_CATEGORY_ACTION = "add_category";
    public static String UPDATE_CATEGORY_ACTION = "update_category";
    public static String DELETE_CATEGORY_ACTION = "delete_category";
    public static String ADD_COURSE_ACTION = "add_course";
    public static String UPDATE_COURSE_ACTION = "update_course";
    public static String DELETE_COURSE_ACTION = "delete_course";
    public static String UPDATE_BLOCK_ACTION = "update_block";
    public static String ADD_TEACHER_ACTION = "add_teacher";
    public static String DELETE_TEACHER_ACTION = "delete_teacher";
    /**
     * Teacher actions
     */
    public static String SHOW_TEACHER_COURSES_ACTION = "show_teacher_courses";
    public static String SHOW_JOURNAL_ACTION = "show_journal";
    public static String GRADE_ACTION = "grade";
    public static String EDIT_TEACHER_ACTION = "edit_teacher";

    /**
     * Student actions
     */
    public static String ENROLL_ACTION = "enroll";
    public static String SHOW_STUDENT_COURSES_ACTION = "show_student_courses";
    public static String SHOW_ALL_COURSES_ACTION = "show_all_courses";
    public static String SHOW_RESULT_ACTION = "show_result";
    public static String EDIT_STUDENT_ACTION = "edit_student";
    public static String CERTIFICATE_ACTION = "certificate";
    /**
     * General actions
     */
    public static String LOG_OUT_ACTION = "log_out";
    public static String MY_CABINET_ACTION = "my_cabinet";
    public static String CHANGE_PASSWORD_ACTION = "change_password";
    public static String ADD_AVATAR_ACTION = "add_avatar";
}
