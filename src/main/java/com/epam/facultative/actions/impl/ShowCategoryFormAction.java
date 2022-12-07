package com.epam.facultative.actions.impl;

import com.epam.facultative.actions.Action;
import jakarta.servlet.http.HttpServletRequest;

import static com.epam.facultative.actions.impl.Constants.*;

public class ShowCategoryFormAction implements Action {
    @Override
    public String execute(HttpServletRequest req) {
        String categoryId = req.getParameter("category_id");
        req.setAttribute("category_id", categoryId);
        return CATEGORY_FORM_PAGE;
    }
}
