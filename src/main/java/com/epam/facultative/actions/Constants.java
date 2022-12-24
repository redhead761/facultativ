package com.epam.facultative.actions;

public class Constants {

    public static final String AUTH_PAGE = "auth.jsp";
    public static final String ERROR_PAGE = "error.jsp";
    public static final String REGISTER_PAGE = "register.jsp";

    //ADMIN
    public static final String ADMIN_PROFILE_PAGE = "admin/admin.jsp";
    public static final String MANAGE_COURSES_PAGE = "admin/manage_courses.jsp";
    public static final String COURSE_FORM_PAGE = "admin/course_form.jsp";
    public static final String CATEGORY_FORM_PAGE = "admin/category_form.jsp";
    public static final String MANAGE_CATEGORIES_PAGE = "admin/admin_categories.jsp";
    public static final String MANAGE_STUDENTS_PAGE = "admin/admin_students.jsp";
    public static final String MANAGE_TEACHERS_PAGE = "admin/admin_teachers.jsp";
    public static final String ADD_TEACHER_PAGE = "admin/add_teacher.jsp";
    public static final String ASSIGN_PAGE = "admin/assign_page.jsp";


    //TEACHER
    public static final String TEACHER_PAGE = "teacher/teacher.jsp";
    public static final String GRADE_PAGE = "teacher/grade_list.jsp";
    public static final String TEACHER_COURSES_PAGE = "teacher/teacher_courses.jsp";


    //STUDENT
    public static final String STUDENT_PAGE = "student/student.jsp";
    public static final String STUDENT_COURSES_PAGE = "student/student_courses.jsp";
    public static final String COMING_SOON_COURSES_PAGE = "student/coming_soon_courses.jsp";
    public static final String IN_PROGRESS_COURSES_PAGE = "student/in_progress_courses.jsp";
    public static final String COMPLETED_COURSES_PAGE = "student/completed_courses.jsp";


    public static final String MANAGE_COURSES_ACTION = "controller?action=manage_courses";
    public static final String COURSE_FORM_ACTION = "controller?action=show_course_form";
    public static final String MANAGE_CATEGORIES_ACTION = "controller?action=manage_categories";
    public static final String SHOW_TEACHER_COURSES_ACTION = "controller?action=show_teacher_courses";
    public static final String ALL_COURSES_ACTION = "controller?action=all_courses";
    public static final String SHOW_STUDENT_CABINET_ACTION = "controller?action=show_student_cabinet";
}
