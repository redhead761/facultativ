package com.epam.facultative.utils.param_builders;

import java.util.HashSet;
import java.util.Set;

import static com.epam.facultative.data_layer.daos.impl.FieldsConstants.*;
import static com.epam.facultative.data_layer.daos.impl.FieldsConstants.USER_LOGIN;

public class UserParamBuilderForQuery extends ParamBuilderForQuery {

    private static final Set<String> USER_SORT_FIELDS = new HashSet<>();

    static {
        USER_SORT_FIELDS.add(USER_LOGIN);
        USER_SORT_FIELDS.add(USER_FIRST_NAME);
        USER_SORT_FIELDS.add(USER_FAMILY_NAME);
        USER_SORT_FIELDS.add(USER_EMAIL);
    }

    @Override
    protected String checkSortField(String sortField) {
        if (USER_SORT_FIELDS.contains(sortField.toLowerCase())) {
            return sortField;
        }
        return null;
    }
}
