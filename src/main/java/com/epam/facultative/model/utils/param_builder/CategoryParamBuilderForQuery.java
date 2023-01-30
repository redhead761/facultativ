package com.epam.facultative.model.utils.param_builder;

import java.util.HashSet;
import java.util.Set;

import static com.epam.facultative.model.dao.impl_sql.сonstants.FieldsConstants.CATEGORY_DESCRIPTION;
import static com.epam.facultative.model.dao.impl_sql.сonstants.FieldsConstants.CATEGORY_TITLE;

public class CategoryParamBuilderForQuery extends ParamBuilderForQuery {
    private static final Set<String> CATEGORY_SORT_FIELDS = new HashSet<>();

    static {
        CATEGORY_SORT_FIELDS.add(CATEGORY_TITLE);
        CATEGORY_SORT_FIELDS.add(CATEGORY_DESCRIPTION);
    }

    @Override
    protected String checkSortField(String sortField) {
        if (CATEGORY_SORT_FIELDS.contains(sortField.toLowerCase())) {
            return sortField;
        }
        return null;
    }
}
