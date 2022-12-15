package com.epam.facultative.actions.impl.admin;

import com.epam.facultative.actions.Action;
import jakarta.servlet.http.*;

import static com.epam.facultative.actions.Constants.*;

public class ShowCategoryFormAction implements Action {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        String categoryId = req.getParameter("category_id");
        req.setAttribute("category_id", categoryId);
        return CATEGORY_FORM_PAGE;
    }
}
