package com.epam.facultative.controller.actions.impl.admin;

import com.epam.facultative.controller.actions.Action;
import com.epam.facultative.controller.app_context.AppContext;
import com.epam.facultative.model.dto.CategoryDTO;
import com.epam.facultative.model.exception.ServiceException;
import com.epam.facultative.model.exception.ValidateException;
import com.epam.facultative.model.service.AdminService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import static com.epam.facultative.controller.constants.AttributeConstants.*;
import static com.epam.facultative.controller.constants.ActionNameConstants.*;
import static com.epam.facultative.controller.actions.ActionUtils.*;
import static com.epam.facultative.controller.constants.PageNameConstants.EDIT_CATEGORY_PAGE;

/**
 * Accessible by admin. Allows to update category from database. Implements PRG pattern
 *
 * @author Oleksandr Panchenko
 * @version 1.0
 */
public class UpdateCategoryAction implements Action {
    private final AdminService adminService;

    public UpdateCategoryAction(AppContext appContext) {
        adminService = appContext.getAdminService();
    }

    /**
     * Checks method and calls required implementation
     *
     * @param req to get method, session and set all required attributes
     * @return path to redirect or forward by front-controller
     * @throws ServiceException to call error page in front-controller
     */
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServiceException {
        return isPostMethod(req) ? executePost(req) : executeGet(req);
    }

    /**
     * Called from doGet method in front-controller. Obtains required path and transfer attributes from session
     * to request
     *
     * @param req to get message attribute from session and put it in request
     * @return update category page after trying to update category
     */
    private String executeGet(HttpServletRequest req) throws ServiceException {
        transferAttributeFromSessionToRequest(req, ERROR, MESSAGE);
        String categoryId = req.getParameter(CATEGORY_ID);
        try {
            CategoryDTO category = adminService.getCategory(Integer.parseInt(categoryId));
            req.setAttribute(CATEGORY, category);
        } catch (ValidateException e) {
            req.setAttribute(ERROR, e.getMessage());
        }
        return EDIT_CATEGORY_PAGE;
    }

    /**
     * Called from doPost method in front-controller. Tries to update category from database.
     * Return input in case if not able
     *
     * @param req to get users id and set message in case of successful updated
     * @return path to redirect to executeGet method through front-controller
     */
    private String executePost(HttpServletRequest req) throws ServiceException {
        String categoryId = req.getParameter(CATEGORY_ID);
        CategoryDTO category = getCategoryFromParameter(req);
        try {
            adminService.updateCategory(category);
            req.getSession().setAttribute(MESSAGE, CHANGES_SAVED);
        } catch (ValidateException e) {
            req.getSession().setAttribute(ERROR, e.getMessage());
        }
        return getGetAction(UPDATE_CATEGORY_ACTION, CATEGORY_ID, categoryId);
    }

    private CategoryDTO getCategoryFromParameter(HttpServletRequest req) {
        int id = Integer.parseInt(req.getParameter(CATEGORY_ID));
        String title = req.getParameter(TITLE);
        String description = req.getParameter(DESCRIPTION);
        return CategoryDTO.builder()
                .id(id)
                .title(title)
                .description(description)
                .build();
    }
}
