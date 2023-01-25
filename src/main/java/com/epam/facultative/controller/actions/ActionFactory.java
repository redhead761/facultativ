package com.epam.facultative.controller.actions;

import com.epam.facultative.controller.AppContext;
import com.epam.facultative.controller.actions.impl.admin.*;
import com.epam.facultative.controller.actions.impl.general.*;
import com.epam.facultative.controller.actions.impl.student.*;
import com.epam.facultative.controller.actions.impl.teacher.EditTeacherAction;
import com.epam.facultative.controller.actions.impl.teacher.GradeAction;
import com.epam.facultative.controller.actions.impl.teacher.ShowJournalAction;
import com.epam.facultative.controller.actions.impl.teacher.ShowTeacherCoursesAction;

import java.util.*;

import static com.epam.facultative.controller.actions.ActionNameConstants.*;

public class ActionFactory {
    private static final ActionFactory ACTION_FACTORY = new ActionFactory();
    private static final Map<String, Action> ACTION_MAP = new HashMap<>();
    private static final AppContext APP_CONTEXT = AppContext.getAppContext();

    static {
        ACTION_MAP.put(MY_CABINET_ACTION, new MyCabinetAction());
        ACTION_MAP.put(AUTH_ACTION, new AuthAction(APP_CONTEXT));
        ACTION_MAP.put(COURSES_ACTION, new CoursesAction(APP_CONTEXT));
        ACTION_MAP.put(TEACHERS_ACTION, new TeachersAction(APP_CONTEXT));
        ACTION_MAP.put(RECOVERY_PASSWORD_ACTION, new RecoveryPasswordAction(APP_CONTEXT));

        ACTION_MAP.put(MANAGE_COURSES_ACTION, new ManageCoursesAction(APP_CONTEXT));
        ACTION_MAP.put(MANAGE_CATEGORIES_ACTION, new ManageCategoriesAction(APP_CONTEXT));
        ACTION_MAP.put(MANAGE_TEACHERS_ACTION, new ManageTeachersAction(APP_CONTEXT));
        ACTION_MAP.put(MANAGE_STUDENTS_ACTION, new ManageStudentsAction(APP_CONTEXT));

        ACTION_MAP.put(ADD_CATEGORY_ACTION, new AddCategoryAction(APP_CONTEXT));
        ACTION_MAP.put(UPDATE_CATEGORY_ACTION, new UpdateCategoryAction(APP_CONTEXT));
        ACTION_MAP.put(DELETE_CATEGORY_ACTION, new DeleteCategoryAction(APP_CONTEXT));

        ACTION_MAP.put(ADD_COURSE_ACTION, new AddCourseAction(APP_CONTEXT));
        ACTION_MAP.put(UPDATE_COURSE_ACTION, new UpdateCourseAction(APP_CONTEXT));
        ACTION_MAP.put(DELETE_COURSE_ACTION, new DeleteCourseAction(APP_CONTEXT));

        ACTION_MAP.put(ASSIGN_ACTION, new AssignAction(APP_CONTEXT));
        ACTION_MAP.put(ADD_TEACHER_ACTION, new AddTeacherAction(APP_CONTEXT));
        ACTION_MAP.put(UPDATE_BLOCK_ACTION, new UpdateBlockAction(APP_CONTEXT));
        ACTION_MAP.put(DELETE_TEACHER_ACTION, new DeleteTeacherAction(APP_CONTEXT));

        ACTION_MAP.put(SHOW_TEACHER_COURSES_ACTION, new ShowTeacherCoursesAction(APP_CONTEXT));
        ACTION_MAP.put(SHOW_JOURNAL_ACTION, new ShowJournalAction(APP_CONTEXT));
        ACTION_MAP.put(GRADE_ACTION, new GradeAction(APP_CONTEXT));

        ACTION_MAP.put(LOG_OUT_ACTION, new LogOutAction());
        ACTION_MAP.put(REGISTER_ACTION, new RegisterAction(APP_CONTEXT));

        ACTION_MAP.put(ENROLL_ACTION, new EnrollAction(APP_CONTEXT));
        ACTION_MAP.put(SHOW_STUDENT_COURSES_ACTION, new ShowStudentCoursesAction(APP_CONTEXT));
        ACTION_MAP.put(SHOW_ALL_COURSES_ACTION, new ShowAllCoursesAction(APP_CONTEXT));
        ACTION_MAP.put(SHOW_RESULT_ACTION, new ShowResultAction(APP_CONTEXT));

        ACTION_MAP.put(DOWNLOAD_COURSES_ACTION, new DownloadCoursesAction(APP_CONTEXT));
        ACTION_MAP.put(CERTIFICATE_ACTION, new CertificateAction(APP_CONTEXT));

        ACTION_MAP.put(EDIT_TEACHER_ACTION, new EditTeacherAction(APP_CONTEXT));
        ACTION_MAP.put(EDIT_STUDENT_ACTION, new EditStudentAction(APP_CONTEXT));
        ACTION_MAP.put(CHANGE_PASSWORD_ACTION, new ChangePasswordAction(APP_CONTEXT));
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
