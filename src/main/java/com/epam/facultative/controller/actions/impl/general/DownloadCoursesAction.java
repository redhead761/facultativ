package com.epam.facultative.controller.actions.impl.general;

import com.epam.facultative.controller.AppContext;
import com.epam.facultative.controller.actions.Action;
import com.epam.facultative.exception.ServiceException;
import com.epam.facultative.service.GeneralService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import static com.epam.facultative.controller.actions.PageNameConstants.INDEX_PAGE;

public class DownloadCoursesAction implements Action {
    private final GeneralService generalService;

    public DownloadCoursesAction(AppContext appContext) {
        generalService = appContext.getGeneralService();
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServiceException {
        String locale = (String) req.getSession().getAttribute("language");
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
