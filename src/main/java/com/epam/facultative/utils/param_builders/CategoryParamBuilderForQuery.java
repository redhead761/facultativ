package com.epam.facultative.utils.param_builders;

import java.util.HashSet;
import java.util.Set;

import static com.epam.facultative.data_layer.daos.impl.FieldsConstants.*;

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
