package com.epam.facultative.actions;

import com.epam.facultative.actions.impl.admin.*;
import com.epam.facultative.actions.impl.general.*;
import com.epam.facultative.actions.impl.student.*;
import com.epam.facultative.actions.impl.teacher.*;

import java.util.*;

import static com.epam.facultative.actions.ActionNameConstants.*;

public class ActionFactory {
    private static final ActionFactory ACTION_FACTORY = new ActionFactory();

    private static final Map<String, Action> ACTION_MAP = new HashMap<>();

    static {
        ACTION_MAP.put(MY_CABINET_ACTION, new MyCabinetAction());
        ACTION_MAP.put(AUTH_ACTION, new AuthAction());

        ACTION_MAP.put("show_admin_profile", new ShowAdminProfileAction());
        ACTION_MAP.put(MANAGE_COURSES_ACTION, new ManageCoursesAction());
        ACTION_MAP.put(MANAGE_CATEGORIES_ACTION, new ManageCategoriesAction());
        ACTION_MAP.put(MANAGE_TEACHERS_ACTION, new ManageTeachersAction());
        ACTION_MAP.put(MANAGE_STUDENTS_ACTION, new ManageStudentsAction());

        ACTION_MAP.put(SELECT_COURSES_ACTION, new SelectCoursesAction());
        ACTION_MAP.put(SORT_ACTION, new SortAction());

        ACTION_MAP.put(SHOW_CATEGORY_FORM_ACTION, new ShowCategoryFormAction());
        ACTION_MAP.put(ADD_CATEGORY_ACTION, new AddCategoryAction());
        ACTION_MAP.put(UPDATE_CATEGORY_ACTION, new UpdateCategoryAction());
        ACTION_MAP.put(DELETE_CATEGORY_ACTION, new DeleteCategoryAction());

        ACTION_MAP.put(SHOW_ADD_COURSE_ACTION, new ShowAddCourseAction());
        ACTION_MAP.put(SHOW_EDIT_COURSE_ACTION, new ShowEditCourseAction());
        ACTION_MAP.put(ADD_COURSE_ACTION, new AddCourseAction());
        ACTION_MAP.put(UPDATE_COURSE_ACTION, new UpdateCourseAction());
        ACTION_MAP.put(DELETE_COURSE_ACTION, new DeleteCourseAction());
        ACTION_MAP.put(SHOW_ASSIGN_PAGE_ACTION, new ShowAssignPageAction());
        ACTION_MAP.put(ASSIGN_ACTION, new AssignAction());
        ACTION_MAP.put(ADD_TEACHER_ACTION, new AddTeacherAction());
        ACTION_MAP.put("show_add_teacher_form", new ShowAddTeacherFormAction());

        ACTION_MAP.put(BLOCK_ACTION, new BlockStudentAction());
        ACTION_MAP.put(UNBLOCK_ACTION, new UnblockStudentAction());

        ACTION_MAP.put(SHOW_TEACHER_COURSES_ACTION, new ShowTeacherCoursesAction());
        ACTION_MAP.put(SHOW_JOURNAL_ACTION, new ShowJournalAction());
        ACTION_MAP.put(GRADE_ACTION, new GradeAction());
        ACTION_MAP.put(ALL_COURSE_ACTION, new AllCoursesAction());

        ACTION_MAP.put(LOG_OUT_ACTION, new LogOutAction());
        ACTION_MAP.put(REGISTER_ACTION, new RegisterAction());

        ACTION_MAP.put(ENROLL_ACTION, new EnrollAction());
        ACTION_MAP.put(SHOW_STUDENT_COURSES_ACTION, new ShowStudentCoursesAction());
        ACTION_MAP.put(SHOW_COMING_SOON_COURSES_ACTION, new ShowComingSoonCoursesAction());
        ACTION_MAP.put(SHOW_PROGRESS_COURSES_ACTION, new ShowInProgressCourses());
        ACTION_MAP.put(SHOW_COMPLETED_COURSES_ACTION, new ShowCompletedCoursesAction());
        ACTION_MAP.put(SHOW_STUDENT_CABINET_ACTION, new ShowStudentCabinetAction());
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
