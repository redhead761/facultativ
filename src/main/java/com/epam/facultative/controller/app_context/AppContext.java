package com.epam.facultative.controller.app_context;

import com.epam.facultative.model.dao.DaoFactory;
import com.epam.facultative.model.connection.MyDataSource;
import com.epam.facultative.model.service.*;
import com.epam.facultative.model.utils.email_sender.EmailSender;
import com.epam.facultative.model.utils.pdf_creator.PdfCreator;
import com.epam.facultative.model.utils.recaptcha.Recaptcha;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Containing all required to correct application work objects
 *
 * @author Oleksandr Panchenko
 * @version 1.0
 */
@Getter
@Slf4j
public class AppContext {
    @Getter
    private static AppContext appContext;
    private final GeneralService generalService;
    private final AdminService adminService;
    private final StudentService studentService;
    private final TeacherService teacherService;
    private final Recaptcha recaptcha;
    private final EmailSender emailSender;
    private final PdfCreator pdfCreator;

    private AppContext(String confPropertiesFile) {
        pdfCreator = new PdfCreator();
        Properties properties = getProperties(confPropertiesFile);
        recaptcha = new Recaptcha(properties);
        emailSender = new EmailSender(properties);
        DataSource dataSource = MyDataSource.getDataSource(properties);
        DaoFactory daoFactory = DaoFactory.getInstance(dataSource);
        ServiceFactory serviceFactory = ServiceFactory.getInstance(daoFactory);
        generalService = serviceFactory.getGeneralService();
        adminService = serviceFactory.getAdminService();
        studentService = serviceFactory.getStudentService();
        teacherService = serviceFactory.getTeacherService();
    }

    /**
     * Creates instance of AppContext to use in Actions. Configure all required classes. Loads properties.
     *
     * @param confPropertiesFile - to configure DataSource, EmailSender and Captcha
     */
    public static void createAppContext(String confPropertiesFile) {
        appContext = new AppContext(confPropertiesFile);
    }

    private static Properties getProperties(String confPropertiesFile) {
        Properties properties = new Properties();
        try (InputStream inputStream = AppContext.class
                .getClassLoader()
                .getResourceAsStream(confPropertiesFile)) {
            properties.load(inputStream);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        return properties;
    }
}
