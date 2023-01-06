package com.epam.facultative.actions;

import com.epam.facultative.actions.impl.admin.*;
import com.epam.facultative.actions.impl.general.*;
import com.epam.facultative.actions.impl.student.*;
import com.epam.facultative.actions.impl.teacher.*;
import com.epam.facultative.controller.AppContext;

import java.util.*;

import static com.epam.facultative.actions.ActionNameConstants.*;

public class ActionFactory {
    private static final ActionFactory ACTION_FACTORY = new ActionFactory();

    private static final Map<String, Action> ACTION_MAP = new HashMap<>();
    private static final AppContext APP_CONTEXT = AppContext.getAppContext();

    static {
        ACTION_MAP.put(MY_CABINET_ACTION, new MyCabinetAction());
        ACTION_MAP.put(AUTH_ACTION, new AuthAction(APP_CONTEXT));

        ACTION_MAP.put(MANAGE_COURSES_ACTION, new ManageCoursesAction(APP_CONTEXT));
        ACTION_MAP.put(MANAGE_CATEGORIES_ACTION, new ManageCategoriesAction(APP_CONTEXT));
        ACTION_MAP.put(MANAGE_TEACHERS_ACTION, new ManageTeachersAction(APP_CONTEXT));
        ACTION_MAP.put(MANAGE_STUDENTS_ACTION, new ManageStudentsAction(APP_CONTEXT));

        ACTION_MAP.put(SELECT_COURSES_ACTION, new SelectCoursesAction(APP_CONTEXT));
        ACTION_MAP.put(SORT_ACTION, new SortAction(APP_CONTEXT));

        ACTION_MAP.put(SHOW_CATEGORY_FORM_ACTION, new ShowCategoryFormAction(APP_CONTEXT));
        ACTION_MAP.put(ADD_CATEGORY_ACTION, new AddCategoryAction(APP_CONTEXT));
        ACTION_MAP.put(UPDATE_CATEGORY_ACTION, new UpdateCategoryAction(APP_CONTEXT));
        ACTION_MAP.put(DELETE_CATEGORY_ACTION, new DeleteCategoryAction(APP_CONTEXT));

        ACTION_MAP.put(SHOW_ADD_COURSE_ACTION, new ShowAddCourseAction(APP_CONTEXT));
        ACTION_MAP.put(SHOW_EDIT_COURSE_ACTION, new ShowEditCourseAction(APP_CONTEXT));
        ACTION_MAP.put(ADD_COURSE_ACTION, new AddCourseAction(APP_CONTEXT));
        ACTION_MAP.put(UPDATE_COURSE_ACTION, new UpdateCourseAction(APP_CONTEXT));
        ACTION_MAP.put(DELETE_COURSE_ACTION, new DeleteCourseAction(APP_CONTEXT));
        ACTION_MAP.put(SHOW_ASSIGN_PAGE_ACTION, new ShowAssignPageAction(APP_CONTEXT));
        ACTION_MAP.put(ASSIGN_ACTION, new AssignAction(APP_CONTEXT));
        ACTION_MAP.put(ADD_TEACHER_ACTION, new AddTeacherAction(APP_CONTEXT));

        ACTION_MAP.put(BLOCK_ACTION, new BlockStudentAction(APP_CONTEXT));
        ACTION_MAP.put(UNBLOCK_ACTION, new UnblockStudentAction(APP_CONTEXT));

        ACTION_MAP.put(SHOW_TEACHER_COURSES_ACTION, new ShowTeacherCoursesAction(APP_CONTEXT));
        ACTION_MAP.put(SHOW_JOURNAL_ACTION, new ShowJournalAction(APP_CONTEXT));
        ACTION_MAP.put(GRADE_ACTION, new GradeAction(APP_CONTEXT));
        ACTION_MAP.put(ALL_COURSE_ACTION, new AllCoursesAction(APP_CONTEXT));

        ACTION_MAP.put(LOG_OUT_ACTION, new LogOutAction());
        ACTION_MAP.put(REGISTER_ACTION, new RegisterAction(APP_CONTEXT));

        ACTION_MAP.put(ENROLL_ACTION, new EnrollAction(APP_CONTEXT));
        ACTION_MAP.put(SHOW_STUDENT_COURSES_ACTION, new ShowStudentCoursesAction(APP_CONTEXT));
        ACTION_MAP.put(SHOW_COMING_SOON_COURSES_ACTION, new ShowComingSoonCoursesAction(APP_CONTEXT));
        ACTION_MAP.put(SHOW_PROGRESS_COURSES_ACTION, new ShowInProgressCourses(APP_CONTEXT));
        ACTION_MAP.put(SHOW_COMPLETED_COURSES_ACTION, new ShowCompletedCoursesAction(APP_CONTEXT));
        ACTION_MAP.put(SHOW_ALL_COURSES_ACTION, new ShowAllCoursesAction(APP_CONTEXT));
        ACTION_MAP.put(SHOW_RESULT_ACTION, new ShowResultAction(APP_CONTEXT));
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
