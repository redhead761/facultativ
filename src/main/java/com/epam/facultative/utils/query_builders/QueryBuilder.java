package com.epam.facultative.utils.query_builders;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

import static com.epam.facultative.data_layer.daos.impl.FieldsConstants.*;

public abstract class QueryBuilder {
    private final List<String> filters = new ArrayList<>();
    private String sortField;
    private String order = "ASC";
    private int offset = 1;
    private int records = 5;

    public QueryBuilder setSortFieldForCourse(String sortField) {
        if (sortField != null) {
            this.sortField = checkSortField(sortField);
        }
        return this;
    }

    public QueryBuilder setCategoryFilterForCourse(String categoryId) {
        if (categoryId != null && isPositiveInt(categoryId)) {
            filters.add(CATEGORY_ID + "=" + categoryId);
        }
        return this;
    }

    public QueryBuilder setTeacherFilterForCourse(String teacherId) {
        if (teacherId != null && isPositiveInt(teacherId)) {
            filters.add(USER_ID + "=" + teacherId);
        }
        return this;
    }

    public QueryBuilder setIdFilterForTeacher(String teacherId) {
        if (teacherId != null && isPositiveInt(teacherId)) {
            filters.add(USER_ID + "+" + teacherId);
        }
        return this;
    }

    public QueryBuilder setIdFilterForStudent(String studentId) {
        if (studentId != null && isPositiveInt(studentId)) {
            filters.add(USER_ID + "+" + studentId);
        }
        return this;
    }

    public QueryBuilder setNameFilter(String nameFilter) {
        if (nameFilter != null) {
            filters.add("login=");
        }
        return this;
    }

    public QueryBuilder setOrder(String order) {
        if (order != null && order.equalsIgnoreCase("DESC")) {
            this.order = "DESC";
        }
        return this;
    }

    public QueryBuilder setLimits(String offset, String records) {
        if (offset != null && isPositiveInt(offset)) {
            this.offset = Integer.parseInt(offset);
            System.out.println("Limit in builder =" + offset);
        }
        if (records != null && isPositiveInt(records)) {
            this.records = Integer.parseInt(records);
            System.out.println("Records in builder =" + records);
        }
        return this;
    }

    public String getQuery() {
        return getFilterQuery() + getSortQuery() + getLimitQuery();
    }

    private String getLimitQuery() {
        return " LIMIT " + (offset - 1) * records + "," + records;
    }

    private String getSortQuery() {
        if (sortField != null) {
            return " ORDER BY " + sortField + " " + order;
        }
        return "";
    }

    private String getFilterQuery() {
        StringJoiner stringJoiner = new StringJoiner(" AND ", " WHERE ", " ").setEmptyValue("");
        filters.forEach(stringJoiner::add);
        return stringJoiner.toString();
    }

    private boolean isPositiveInt(String intString) {
        try {
            int i = Integer.parseInt(intString);
            if (i < 0) {
                return false;
            }
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    protected abstract String checkSortField(String sortField);
}
