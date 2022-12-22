package com.epam.facultative.repositories;

import com.epam.facultative.daos.DaoFactory;
import com.epam.facultative.repositories.implementation.AdminRepositoryImpl;
import com.epam.facultative.repositories.implementation.GeneralRepositoryImpl;
import com.epam.facultative.repositories.implementation.StudentRepositoryImpl;
import com.epam.facultative.repositories.implementation.TeacherRepositoryImpl;

public class RepositoryFactory {

    private static final RepositoryFactory INSTANCE = new RepositoryFactory();
    private final GeneralRepository generalRepository;
    private final AdminRepository adminRepository;
    private final TeacherRepository teacherRepository;
    private final StudentRepository studentRepository;

    public RepositoryFactory() {
        this.generalRepository = new GeneralRepositoryImpl(DaoFactory.getInstance().getCourseDao(),
                DaoFactory.getInstance().getUserDao(),
                DaoFactory.getInstance().getCategoryDao());
        this.adminRepository = new AdminRepositoryImpl(DaoFactory.getInstance().getCourseDao(),
                DaoFactory.getInstance().getCategoryDao(),
                DaoFactory.getInstance().getUserDao());
        this.teacherRepository = new TeacherRepositoryImpl(DaoFactory.getInstance().getCourseDao(),
                DaoFactory.getInstance().getUserDao());
        this.studentRepository = new StudentRepositoryImpl(DaoFactory.getInstance().getCourseDao(),
                DaoFactory.getInstance().getUserDao());
    }
    public static RepositoryFactory getInstance() {
        return INSTANCE;
    }

    public GeneralRepository getGeneralRepository() {
        return generalRepository;
    }

    public AdminRepository getAdminRepository() {
        return adminRepository;
    }

    public TeacherRepository getTeacherRepository() {
        return teacherRepository;
    }

    public StudentRepository getStudentRepository() {
        return studentRepository;
    }

}
