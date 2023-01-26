package com.epam.facultative.controller.actions.impl.general;

import com.epam.facultative.controller.app_context.AppContext;
import com.epam.facultative.controller.actions.Action;
import com.epam.facultative.model.exception.ServiceException;
import com.epam.facultative.model.service.GeneralService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import static com.epam.facultative.controller.constants.AttributeConstants.LANGUAGE;
import static com.epam.facultative.controller.constants.PageNameConstants.INDEX_PAGE;

public class DownloadCoursesAction implements Action {
    private final GeneralService generalService;

    public DownloadCoursesAction(AppContext appContext) {
        generalService = appContext.getGeneralService();
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServiceException {
        String locale = (String) req.getSession().getAttribute(LANGUAGE);
        ByteArrayOutputStream usersPdf = generalService.downloadAllCoursesInPdf(locale);
        setResponse(resp, usersPdf);
        return INDEX_PAGE;
    }

    private void setResponse(HttpServletResponse response, ByteArrayOutputStream output) throws IOException {
        response.setContentType("application/pdf");
        response.setContentLength(output.size());
        response.setHeader("Content-Disposition", "attachment; filename=\"courses.pdf\"");
        OutputStream outputStream = response.getOutputStream();
        output.writeTo(outputStream);
        outputStream.flush();
    }
}
