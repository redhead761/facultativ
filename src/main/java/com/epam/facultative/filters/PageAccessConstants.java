package com.epam.facultative.filters;

import java.util.HashSet;
import java.util.Set;

import static com.epam.facultative.actions.PageNameConstants.*;

public final class PageAccessConstants {
    private PageAccessConstants() {
    }

    private static final Set<String> NO_LOGGED_USER_PAGES = new HashSet<>();
    private static final Set<String> ADMIN_PAGES = new HashSet<>();
    private static final Set<String> TEACHER_PAGES = new HashSet<>();
    private static final Set<String> STUDENT_PAGES = new HashSet<>();

    static {
        NO_LOGGED_USER_PAGES.add(ABOUT_US_PAGE);
        NO_LOGGED_USER_PAGES.add(AUTH_PAGE);
        NO_LOGGED_USER_PAGES.add(CONTACTS_PAGE);
        NO_LOGGED_USER_PAGES.add(COURSES_PAGE);
        NO_LOGGED_USER_PAGES.add(ERROR_PAGE);
        NO_LOGGED_USER_PAGES.add(INDEX_PAGE);
        NO_LOGGED_USER_PAGES.add(REGISTER_PAGE);
        NO_LOGGED_USER_PAGES.add(TEACHERS_PAGE);
    }

    static {
        ADMIN_PAGES.addAll(NO_LOGGED_USER_PAGES);
        ADMIN_PAGES.remove(AUTH_PAGE);
        ADMIN_PAGES.add(ADD_COURSE_PAGE);
        ADMIN_PAGES.add(ADD_TEACHER_PAGE);
        ADMIN_PAGES.add(ADMIN_PROFILE_PAGE);
        ADMIN_PAGES.add(ASSIGN_PAGE);
        ADMIN_PAGES.add(CATEGORY_FORM_PAGE);
        ADMIN_PAGES.add(EDIT_COURSE_PAGE);
        ADMIN_PAGES.add(MANAGE_CATEGORIES_PAGE);
        ADMIN_PAGES.add(MANAGE_COURSES_PAGE);
        ADMIN_PAGES.add(MANAGE_STUDENTS_PAGE);
        ADMIN_PAGES.add(MANAGE_TEACHERS_PAGE);
    }

    static {
        TEACHER_PAGES.addAll(NO_LOGGED_USER_PAGES);
        TEACHER_PAGES.remove(AUTH_PAGE);
        TEACHER_PAGES.add(JOURNAL_PAGE);
        TEACHER_PAGES.add(TEACHER_PAGE);
        TEACHER_PAGES.add(TEACHER_COURSES_PAGE);
        TEACHER_PAGES.add(TEACHER_PROFILE_PAGE);
    }

    static {
        STUDENT_PAGES.addAll(NO_LOGGED_USER_PAGES);
        STUDENT_PAGES.remove(AUTH_PAGE);
        STUDENT_PAGES.add(COMING_SOON_COURSES_PAGE);
        STUDENT_PAGES.add(COMPLETED_COURSES_PAGE);
        STUDENT_PAGES.add(IN_PROGRESS_COURSES_PAGE);
        STUDENT_PAGES.add(STUDENT_PAGE);
        STUDENT_PAGES.add(STUDENT_COURSES_PAGE);
        STUDENT_PAGES.add(STUDENT_PROFILE_PAGE);
    }

    public static Set<String> getNoLoggedUserPages() {
        return NO_LOGGED_USER_PAGES;
    }

    public static Set<String> getAdminPages() {
        return ADMIN_PAGES;
    }

    public static Set<String> getTeacherPages() {
        return TEACHER_PAGES;
    }

    public static Set<String> getStudentPages() {
        return STUDENT_PAGES;
    }


}
