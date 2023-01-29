package com.epam.facultative.controller.actions.impl.admin;

import com.epam.facultative.controller.actions.ActionUtils;
import com.epam.facultative.controller.app_context.AppContext;
import com.epam.facultative.model.exception.ServiceException;
import com.epam.facultative.model.service.GeneralService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import static com.epam.facultative.controller.actions.ActionUtils.*;
import static com.epam.facultative.controller.constants.PageNameConstants.MANAGE_TEACHERS_PAGE;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;

class ManageTeachersActionTest {
    private final HttpServletRequest req = mock(HttpServletRequest.class);
    private final HttpServletResponse resp = mock(HttpServletResponse.class);
    private final AppContext appContext = mock(AppContext.class);

    @Test
    void executePostSuccessful() throws ServiceException {
        try (MockedStatic<ActionUtils> actionUtils = mockStatic(ActionUtils.class)) {
            actionUtils.when(() -> transferAttributeFromSessionToRequest(isA(HttpServletRequest.class), anyString())).thenAnswer(invocation -> null);
            actionUtils.when(() -> setAllTeachers(isA(HttpServletRequest.class), isA(GeneralService.class))).thenAnswer(invocation -> null);

            String path = new ManageTeachersAction(appContext).execute(req, resp);
            assertEquals(MANAGE_TEACHERS_PAGE, path);
        }
    }
}