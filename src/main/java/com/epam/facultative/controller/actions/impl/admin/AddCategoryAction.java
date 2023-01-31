package com.epam.facultative.controller.actions.impl.admin;

import com.epam.facultative.controller.actions.Action;
import com.epam.facultative.controller.app_context.AppContext;
import com.epam.facultative.model.dto.CategoryDTO;
import com.epam.facultative.model.exception.ServiceException;
import com.epam.facultative.model.exception.ValidateException;
import com.epam.facultative.model.service.AdminService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import static com.epam.facultative.controller.constants.ActionNameConstants.ADD_CATEGORY_ACTION;
import static com.epam.facultative.controller.actions.ActionUtils.*;
import static com.epam.facultative.controller.constants.PageNameConstants.*;
import static com.epam.facultative.controller.constants.AttributeConstants.*;

/**
 * Accessible by admin. Allows to add category from database. Implements PRG pattern
 *
 * @author Oleksandr Panchenko
 * @version 1.0
 */
public class AddCategoryAction implements Action {

    private final AdminService adminService;

    public AddCategoryAction(AppContext appContext) {
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
     * @return add category page after trying to add category
     */
    private String executeGet(HttpServletRequest req) {
        transferAttributeFromSessionToRequest(req, ERROR, MESSAGE, CATEGORY);
        return ADD_CATEGORY_PAGE;
    }

    /**
     * Called from doPost method in front-controller. Tries to add category from database.
     * Return input in case if not able
     *
     * @param req to get users id and set message in case of successful added
     * @return path to redirect to executeGet method through front-controller
     */
    private String executePost(HttpServletRequest req) throws ServiceException {
        CategoryDTO category = getCategoryFromParameter(req);
        try {
            adminService.addCategory(category);
            req.getSession().setAttribute(MESSAGE, SUCCESSFUL);
        } catch (ValidateException e) {
            req.getSession().setAttribute(CATEGORY, category);
            req.getSession().setAttribute(ERROR, e.getMessage());
        }
        return getGetAction(ADD_CATEGORY_ACTION);
    }

    private CategoryDTO getCategoryFromParameter(HttpServletRequest req) {
        String title = req.getParameter(TITLE);
        String description = req.getParameter(DESCRIPTION);
        return CategoryDTO.builder()
                .id(0)
                .title(title)
                .description(description)
                .build();
    }
}
