package com.epam.facultative.utils.param_builders;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

import static com.epam.facultative.data_layer.daos.impl.FieldsConstants.*;

public abstract class ParamBuilderForQuery {
    private final List<String> filters = new ArrayList<>();
    private String sortField;
    private String order = "ASC";
    private int page = 1;
    private int records = 5;

    public ParamBuilderForQuery setSortFieldForCourse(String sortField) {
        if (sortField != null) {
            this.sortField = checkSortField(sortField);
        }
        return this;
    }

    public ParamBuilderForQuery setCategoryFilterForCourse(String categoryId) {
        if (isPositiveInt(categoryId)) {
            filters.add(CATEGORY_ID + "=" + categoryId);
        }
        return this;
    }

    public ParamBuilderForQuery setUserIdFilter(String userId) {
        if (isPositiveInt(userId)) {
            filters.add(USER_ID + "=" + userId);
        }
        return this;
    }

    public ParamBuilderForQuery setIdFilterForStudent(String studentId) {
        if (isPositiveInt(studentId)) {
            filters.add("student_id" + "=" + studentId);
        }
        return this;
    }

    public ParamBuilderForQuery setStatusFilterForCourse(String statusId) {
        if (isPositiveInt(statusId)) {
            filters.add(STATUS_ID + "=" + statusId);
        }
        return this;
    }

    public ParamBuilderForQuery setIdFilterCourseForStudent(String courseId) {
        if (isPositiveInt(courseId)) {
            filters.add("course_id" + "=" + courseId);
        }
        return this;
    }

    public ParamBuilderForQuery setIdCourseFilter(String courseId) {
        if (isPositiveInt(courseId)) {
            filters.add(COURSE_ID + "=" + courseId);
        }
        return this;
    }

    public ParamBuilderForQuery setIdCategoryFilter(String categoryId) {
        if (isPositiveInt(categoryId)) {
            filters.add(CATEGORY_ID + "=" + categoryId);
        }
        return this;
    }

    public ParamBuilderForQuery setUserLoginFilter(String login) {
        if (login != null) {
            filters.add(USER_LOGIN + "=" + "\"" + login + "\"");
        }
        return this;
    }

    public ParamBuilderForQuery setUserEmailFilter(String email) {
        if (email != null) {
            filters.add(USER_EMAIL + "=" + "\"" + email + "\"");
        }
        return this;
    }



    public ParamBuilderForQuery setOrder(String order) {
        if (order != null && order.equalsIgnoreCase("DESC")) {
            this.order = "DESC";
        }
        return this;
    }

    public ParamBuilderForQuery setLimits(String page, String records) {
        if (isPositiveInt(page)) {
            this.page = Integer.parseInt(page);
        }
        if (isPositiveInt(records)) {
            this.records = Integer.parseInt(records);
        }
        return this;
    }

    public String getParam() {
        return getFilterParam() + getSortParam() + getLimitParam();
    }

    private String getLimitParam() {
        return " LIMIT " + (page - 1) * records + "," + records;
    }

    private String getSortParam() {
        if (sortField != null) {
            return " ORDER BY " + sortField + " " + order;
        }
        return "";
    }

    private String getFilterParam() {
        StringJoiner stringJoiner = new StringJoiner(" AND ", " WHERE ", " ").setEmptyValue("");
        filters.forEach(stringJoiner::add);
        return stringJoiner.toString();
    }

    private boolean isPositiveInt(String intString) {
        try {
            int i = Integer.parseInt(intString);
            if (i <= 0) {
                return false;
            }
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    protected abstract String checkSortField(String sortField);
}
