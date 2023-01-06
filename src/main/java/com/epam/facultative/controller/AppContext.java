package com.epam.facultative.controller;

import com.epam.facultative.daos.DaoFactory;
import com.epam.facultative.daos.connection.MyDataSource;
import com.epam.facultative.service.*;
import lombok.Getter;

import javax.sql.DataSource;

@Getter
public class AppContext {
    @Getter
    private static AppContext appContext;
    private final GeneralService generalService;
    private final AdminService adminService;
    private final StudentService studentService;
    private final TeacherService teacherService;

    private AppContext() {
        DataSource dataSource = MyDataSource.getDataSource();
        DaoFactory daoFactory = DaoFactory.getInstance(dataSource);
        ServiceFactory serviceFactory = ServiceFactory.getInstance(daoFactory);
        generalService = serviceFactory.getGeneralService();
        adminService = serviceFactory.getAdminService();
        studentService = serviceFactory.getStudentService();
        teacherService = serviceFactory.getTeacherService();
    }

    public static void createAppContext() {
        appContext = new AppContext();
    }
}
