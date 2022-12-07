package com.epam.facultative.actions;

import com.epam.facultative.actions.impl.*;

import java.util.HashMap;
import java.util.Map;

public class ActionFactory {
    private static final ActionFactory ACTION_FACTORY = new ActionFactory();

    private static final Map<String, Action> ACTION_MAP = new HashMap<>();

    static {
        ACTION_MAP.put("auth", new AuthAction());

        ACTION_MAP.put("manage_courses", new ManageCoursesAction());
        ACTION_MAP.put("manage_categories", new ManageCategoriesAction());
        ACTION_MAP.put("manage_teachers", new ManageTeachersAction());
        ACTION_MAP.put("manage_students", new ManageStudentsAction());

        ACTION_MAP.put("select_courses", new SelectCoursesAction());
        ACTION_MAP.put("sort", new SortAction());

        ACTION_MAP.put("show_category_form", new ShowCategoryFormAction());
        ACTION_MAP.put("add_category", new AddCategoryAction());
        ACTION_MAP.put("update_category", new UpdateCategoryAction());
        ACTION_MAP.put("delete_category", new DeleteCategoryAction());

        ACTION_MAP.put("show_course_form", new ShowCourseFormAction());
        ACTION_MAP.put("add_course", new AddCourseAction());
        ACTION_MAP.put("update_course", new UpdateCourseAction());
        ACTION_MAP.put("delete_course", new DeleteCourseAction());
        ACTION_MAP.put("show_assign_page", new ShowAssignPageAction());
        ACTION_MAP.put("assign", new AssignAction());

        ACTION_MAP.put("block", new BlockStudentAction());
        ACTION_MAP.put("unblock", new UnblockStudentAction());

        ACTION_MAP.put("show_teacher_courses", new ShowTeacherCoursesAction());
        ACTION_MAP.put("show_grade_list", new ShowGradeListAction());
        ACTION_MAP.put("grade", new GradeAction());
        ACTION_MAP.put("all_courses", new AllCoursesAction());

        ACTION_MAP.put("log_out", new LogOutAction());
        ACTION_MAP.put("student", new StudentAction());
        ACTION_MAP.put("register", new RegisterAction());

        ACTION_MAP.put("enroll", new EnrollAction());
        ACTION_MAP.put("show_student_courses", new ShowStudentCoursesAction());
        ACTION_MAP.put("show_coming_soon_courses", new ShowComingSoonCoursesAction());
        ACTION_MAP.put("show_progress_courses", new ShowInProgressCourses());
        ACTION_MAP.put("show_completed_courses", new ShowCompletedCoursesAction());
        ACTION_MAP.put("show_student_cabinet", new ShowStudentCabinetAction());
    }

    private ActionFactory() {
    }

    public static ActionFactory getActionFactory() {
        return ACTION_FACTORY;
    }

    public Action getAction(String actionName) {
        return ACTION_MAP.get(actionName);
    }

}
