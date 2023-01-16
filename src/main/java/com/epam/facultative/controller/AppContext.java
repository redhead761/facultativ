package com.epam.facultative.controller;

import com.epam.facultative.data_layer.daos.DaoFactory;
import com.epam.facultative.data_layer.connection.MyDataSource;
import com.epam.facultative.service.*;
import com.epam.facultative.utils.EmailSender;
import com.epam.facultative.utils.Recaptcha;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Getter
public class AppContext {
    private static final Logger logger = LoggerFactory.getLogger(MyDataSource.class);
    @Getter
    private static AppContext appContext;
    private final GeneralService generalService;
    private final AdminService adminService;
    private final StudentService studentService;
    private final TeacherService teacherService;
    private final Recaptcha recaptcha;
    private final EmailSender emailSender;

    private AppContext(String confPropertiesFile) {
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
            logger.error(e.getMessage());
        }
        return properties;
    }
}
