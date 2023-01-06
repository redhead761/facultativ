package com.epam.facultative.controller.filters;

import java.util.HashSet;
import java.util.Set;

public class FilterConstants {

    static Set<String> ADMIN_ACTIONS = new HashSet<>();
    static Set<String> TEACHER_ACTIONS = new HashSet<>();
    static Set<String> STUDENT_ACTIONS = new HashSet<>();

    static {
        ADMIN_ACTIONS.add("register");
        ADMIN_ACTIONS.add("manage_teachers");
        ADMIN_ACTIONS.add("show_course_form");
        ADMIN_ACTIONS.add("sort");
        ADMIN_ACTIONS.add("select_courses");
        ADMIN_ACTIONS.add("show_assign_page");
        ADMIN_ACTIONS.add("delete_course");
        ADMIN_ACTIONS.add("manage_courses");
        ADMIN_ACTIONS.add("delete_category");
        ADMIN_ACTIONS.add("block");
        ADMIN_ACTIONS.add("unblock");
        ADMIN_ACTIONS.add("assign");
        ADMIN_ACTIONS.add("update_category");
        ADMIN_ACTIONS.add("add_category");
        ADMIN_ACTIONS.add("manage_categories");
        ADMIN_ACTIONS.add("update_course");
        ADMIN_ACTIONS.add("add_course");
        ADMIN_ACTIONS.add("manage_students");
        ADMIN_ACTIONS.add("log_out");
        ADMIN_ACTIONS.add("show_category_form");
        ADMIN_ACTIONS.add("auth");
        ADMIN_ACTIONS.add("show_add_teacher_form");
        ADMIN_ACTIONS.add("show_admin_profile");

        TEACHER_ACTIONS.add("show_teacher_courses");
        TEACHER_ACTIONS.add("grade");
        TEACHER_ACTIONS.add("log_out");
        TEACHER_ACTIONS.add("sort");
        TEACHER_ACTIONS.add("select_courses");
        TEACHER_ACTIONS.add("show_assign_page");
        TEACHER_ACTIONS.add("all_courses");
        TEACHER_ACTIONS.add("show_grade_list");
        TEACHER_ACTIONS.add("auth");

        STUDENT_ACTIONS.add("show_coming_soon_courses");
        STUDENT_ACTIONS.add("show_progress_courses");
        STUDENT_ACTIONS.add("show_completed_courses");
        STUDENT_ACTIONS.add("show_student_courses");
        STUDENT_ACTIONS.add("log_out");
        STUDENT_ACTIONS.add("sort");
        STUDENT_ACTIONS.add("select_courses");
        STUDENT_ACTIONS.add("enroll");
        STUDENT_ACTIONS.add("auth");
        STUDENT_ACTIONS.add("show_student_cabinet");
    }
}