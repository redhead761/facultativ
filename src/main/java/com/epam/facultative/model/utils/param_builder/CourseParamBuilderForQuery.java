package com.epam.facultative.model.utils.param_builder;

import java.util.HashSet;
import java.util.Set;

import static com.epam.facultative.model.dao.impl_sql.сonstants.FieldsConstants.*;

/**
 * Class makes it possible to build param for query to obtain sorted, ordered and limited list of events
 *
 * @author Oleksandr Panchenko
 * @version 1.0
 */
public class CourseParamBuilderForQuery extends ParamBuilderForQuery {
    private static final Set<String> COURSE_SORT_FIELDS = new HashSet<>();

    static {
        COURSE_SORT_FIELDS.add(COURSE_TITLE);
        COURSE_SORT_FIELDS.add(COURSE_DURATION);
        COURSE_SORT_FIELDS.add(COURSE_START_DATE);
        COURSE_SORT_FIELDS.add(COURSE_AMOUNT_STUDENTS);
    }

    @Override
    protected String checkSortField(String sortField) {
        if (COURSE_SORT_FIELDS.contains(sortField.toLowerCase())) {
            return sortField;
        }
        return null;
    }
}
