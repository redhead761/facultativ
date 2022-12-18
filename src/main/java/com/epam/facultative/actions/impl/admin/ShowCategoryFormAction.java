package com.epam.facultative.actions.impl.admin;

import com.epam.facultative.actions.Action;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


import static com.epam.facultative.actions.Constants.*;

public class ShowCategoryFormAction implements Action {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        int categoryId = Integer.parseInt(req.getParameter("category_id"));
        req.getSession().setAttribute("category_id", categoryId);
        return CATEGORY_FORM_PAGE;
    }
}
