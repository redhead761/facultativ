package com.epam.facultative.actions;

import java.util.HashMap;
import java.util.Map;

public class ActionFactory {
    private static final ActionFactory ACTION_FACTORY = new ActionFactory();

    private static final Map<String, Action> ACTION_MAP = new HashMap<>();

    static {
        ACTION_MAP.put("auth", new AuthAction());
        ACTION_MAP.put("admin_categories", new AdminCategoriesAction());
        ACTION_MAP.put("admin_courses", new AdminCoursesAction());
        ACTION_MAP.put("admin_teachers", new AdminTeachersAction());
        ACTION_MAP.put("admin_students", new AdminStudentsAction());
        ACTION_MAP.put("log_out", new LogOutAction());
        ACTION_MAP.put("student", new StudentAction());
        ACTION_MAP.put("register", new RegisterAction());
        ACTION_MAP.put("add_category", new AddCategoryAction());
        ACTION_MAP.put("add_course", new AddCourseAction());
        ACTION_MAP.put("sort", new SortAction());
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
