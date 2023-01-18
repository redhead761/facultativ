package com.epam.facultative.service.implementation;

import com.epam.facultative.controller.AppContext;
import com.epam.facultative.data_layer.daos.CourseDao;
import com.epam.facultative.data_layer.daos.StudentDao;
import com.epam.facultative.dto.CourseDTO;
import com.epam.facultative.data_layer.entities.Course;
import com.epam.facultative.data_layer.entities.Role;
import com.epam.facultative.data_layer.entities.Status;
import com.epam.facultative.dto.StudentDTO;
import com.epam.facultative.exception.DAOException;
import com.epam.facultative.exception.ServiceException;
import com.epam.facultative.exception.ValidateException;
import com.epam.facultative.service.StudentService;
import com.epam.facultative.utils.email_sender.EmailSender;
import com.epam.facultative.utils.pdf_creator.PdfCreator;

import static com.epam.facultative.dto.Converter.*;
import static com.epam.facultative.utils.hash_password.HashPassword.*;
import static com.epam.facultative.utils.validator.ValidateExceptionMessageConstants.LOGIN_NOT_EXIST_MESSAGE;
import static com.epam.facultative.utils.validator.Validator.*;

import java.io.ByteArrayOutputStream;
import java.util.*;

public class StudentServiceImpl implements StudentService {
    private final CourseDao courseDao;
    private final StudentDao studentDao;

    public StudentServiceImpl(CourseDao courseDao, StudentDao studentDao) {
        this.courseDao = courseDao;
        this.studentDao = studentDao;
    }

    @Override
    public List<CourseDTO> getCoursesByStudent(int studentId, int offset, int numberOfRows) throws ServiceException {
        try {
            List<Course> courses = courseDao.getByStudent(studentId, offset, numberOfRows);
            return prepareCourses(courses);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<CourseDTO> getCoursesComingSoon(int studentId, int offset, int numberOfRows) throws ServiceException {
        try {
            List<Course> courses = courseDao.getByStatus(studentId, Status.COMING_SOON, offset, numberOfRows);
            return prepareCourses(courses);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<CourseDTO> getCoursesInProgress(int studentId, int offset, int numberOfRows) throws ServiceException {
        try {
            List<Course> courses = courseDao.getByStatus(studentId, Status.IN_PROCESS, offset, numberOfRows);
            return prepareCourses(courses);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<CourseDTO> getCoursesCompleted(int studentId, int offset, int numberOfRows) throws ServiceException {
        try {
            List<Course> courses = courseDao.getByStatus(studentId, Status.COMPLETED, offset, numberOfRows);
            return prepareCourses(courses);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void enroll(int courseId, int userId) throws ServiceException {
        try {
            courseDao.insertJournal(courseId, userId);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void addStudent(StudentDTO studentDTO) throws ServiceException, ValidateException {
        validateLogin(studentDTO.getLogin());
        validatePassword(studentDTO.getPassword());
        validateNameAndSurname(studentDTO.getName(), studentDTO.getSurname());
        validateEmail(studentDTO.getEmail());
        studentDTO.setRole(Role.STUDENT);
        studentDTO.setPassword(encode(studentDTO.getPassword()));
        try {
            studentDao.add(convertDTOToStudent(studentDTO));
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public int getGrade(int courseId, int userId) throws ServiceException {
        try {
            return studentDao.getGrade(courseId, userId);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public ByteArrayOutputStream downloadCertificate(StudentDTO studentDTO, int courseId, int grade) throws ServiceException, ValidateException {
        try {
            Course course = courseDao.getById(courseId).orElseThrow(() -> new ValidateException(LOGIN_NOT_EXIST_MESSAGE));
            PdfCreator pdfCreator = new PdfCreator();
            return pdfCreator.createCertificateForDownload(studentDTO, course, grade);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void sendCertificate(StudentDTO studentDTO, int courseId, int grade) throws ValidateException, ServiceException {
        PdfCreator pdfCreator = new PdfCreator();
        try {
            Course course = courseDao.getById(courseId).orElseThrow(() -> new ValidateException(LOGIN_NOT_EXIST_MESSAGE));
            String certificate = pdfCreator.createCertificateForSend(studentDTO, course, grade);
            AppContext appContext = AppContext.getAppContext();
            EmailSender emailSender = appContext.getEmailSender();
            emailSender.sendCertificate(studentDTO.getEmail(), "Certificate", "Your certificate", certificate);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }

    }

    @Override
    public int getNoOfRecordsCourses() {
        return courseDao.getNoOfRecords();
    }
}