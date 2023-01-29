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
    private final AppContext appContext;

    public DownloadCoursesAction(AppContext appContext) {
        generalService = appContext.getGeneralService();
        this.appContext = appContext;
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServiceException, IOException {
        String locale = (String) req.getSession().getAttribute(LANGUAGE);
        ByteArrayOutputStream usersPdf = generalService.downloadAllCoursesInPdf(locale, appContext);
        setResponse(resp, usersPdf);
        return INDEX_PAGE;
    }

    private void setResponse(HttpServletResponse resp, ByteArrayOutputStream output) throws IOException {
        resp.setContentType("application/pdf");
        resp.setContentLength(output.size());
        resp.setHeader("Content-Disposition", "attachment; filename=\"courses.pdf\"");
        OutputStream outputStream = resp.getOutputStream();
        output.writeTo(outputStream);
        outputStream.flush();
    }
}
