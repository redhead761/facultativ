package com.epam.facultative.controller.actions.impl.admin;

import com.epam.facultative.controller.actions.ActionUtils;
import com.epam.facultative.controller.app_context.AppContext;
import com.epam.facultative.model.exception.ServiceException;
import com.epam.facultative.model.service.GeneralService;
import com.epam.facultative.model.service.implementation.TestServiceUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import static com.epam.facultative.controller.actions.ActionUtils.*;
import static com.epam.facultative.controller.constants.AttributeConstants.*;
import static com.epam.facultative.controller.constants.PageNameConstants.MANAGE_CATEGORIES_PAGE;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ManageCategoriesActionTest {
    private final HttpServletRequest req = mock(HttpServletRequest.class);
    private final HttpServletResponse resp = mock(HttpServletResponse.class);
    private final AppContext appContext = mock(AppContext.class);
    private final GeneralService generalService = mock(GeneralService.class);
    private final TestServiceUtil testServiceUtil = new TestServiceUtil();

    @Test
    void executePostSuccessful() throws ServiceException {
        try (MockedStatic<ActionUtils> actionUtils = mockStatic(ActionUtils.class)) {
            actionUtils.when(() -> transferAttributeFromSessionToRequest(isA(HttpServletRequest.class), anyString())).thenAnswer(invocation -> null);
            when(req.getParameter(CURRENT_PAGE)).thenReturn("1");
            when(req.getParameter(RECORDS_PER_PAGE)).thenReturn("1");
            when(appContext.getGeneralService()).thenReturn(generalService);
            when(generalService.getCategories(anyString())).thenReturn(testServiceUtil.getCategoryDTOS());
            actionUtils.when(() -> setUpPaginate(isA(HttpServletRequest.class), anyInt())).thenAnswer(invocation -> null);

            String path = new ManageCategoriesAction(appContext).execute(req, resp);
            assertEquals(MANAGE_CATEGORIES_PAGE, path);
        }
    }
}