package com.epam.facultative.model.service.implementation;

import com.epam.facultative.controller.app_context.AppContext;
import com.epam.facultative.model.dao.CourseDao;
import com.epam.facultative.model.dao.StudentDao;
import com.epam.facultative.model.entities.Student;
import com.epam.facultative.model.dto.CourseDTO;
import com.epam.facultative.model.entities.Course;
import com.epam.facultative.model.entities.Role;
import com.epam.facultative.model.dto.StudentDTO;
import com.epam.facultative.model.exception.DAOException;
import com.epam.facultative.model.exception.ServiceException;
import com.epam.facultative.model.exception.ValidateException;
import com.epam.facultative.model.service.StudentService;
import com.epam.facultative.model.utils.email_sender.EmailSender;
import com.epam.facultative.model.utils.pdf_creator.PdfCreator;

import static com.epam.facultative.model.exception.ConstantsValidateMessage.*;
import static com.epam.facultative.model.utils.converter.Converter.*;
import static com.epam.facultative.model.utils.email_sender.EmailConstants.EMAIL_MESSAGE_FOR_CERTIFICATE;
import static com.epam.facultative.model.utils.email_sender.EmailConstants.EMAIL_SUBJECT_FOR_CERTIFICATE;
import static com.epam.facultative.model.utils.hash_password.HashPassword.encode;
import static com.epam.facultative.model.utils.param_builder.ParamBuilderForQueryUtil.courseParamBuilderForQuery;
import static com.epam.facultative.model.utils.param_builder.ParamBuilderForQueryUtil.studentParamBuilderForQuery;
import static com.epam.facultative.model.utils.validator.Validator.*;

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
    public Map.Entry<Integer, List<CourseDTO>> getCoursesByJournal(String param) throws ServiceException {
        try {
            Map.Entry<Integer, List<Course>> coursesWithRows = courseDao.getByJournal(param);
            List<CourseDTO> courses = prepareCourses(coursesWithRows.getValue());
            return Map.entry(coursesWithRows.getKey(), courses);
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
        validateName(studentDTO.getName());
        validateName(studentDTO.getSurname());
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
            Course course = courseDao
                    .get(courseParamBuilderForQuery().setIdCourseFilter(String.valueOf(courseId)).getParam())
                    .orElseThrow(() -> new ValidateException(LOGIN_NOT_EXIST_MESSAGE));
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
            Course course = courseDao
                    .get(courseParamBuilderForQuery().setIdCourseFilter(String.valueOf(courseId)).getParam())
                    .orElseThrow(() -> new ValidateException(LOGIN_NOT_EXIST_MESSAGE));
            String certificate = pdfCreator.createCertificateForSend(studentDTO, course, grade);
            AppContext appContext = AppContext.getAppContext();
            EmailSender emailSender = appContext.getEmailSender();
            emailSender.sendCertificate(studentDTO.getEmail(), EMAIL_SUBJECT_FOR_CERTIFICATE, EMAIL_MESSAGE_FOR_CERTIFICATE, certificate);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }

    }

    @Override
    public StudentDTO updateStudent(StudentDTO studentDTO) throws ValidateException, ServiceException {
        validateLogin(studentDTO.getLogin());
        validateName(studentDTO.getName());
        validateName(studentDTO.getSurname());
        validateEmail(studentDTO.getEmail());
        studentDTO.setRole(Role.STUDENT);
        try {
            Student student = studentDao
                    .get(studentParamBuilderForQuery().setUserIdFilter(String.valueOf(studentDTO.getId())).getParam())
                    .orElseThrow(() -> new ValidateException(LOGIN_NOT_EXIST_MESSAGE));
            student.setLogin(studentDTO.getLogin());
            student.setName(studentDTO.getName());
            student.setSurname(studentDTO.getSurname());
            student.setEmail(studentDTO.getEmail());
            student.setCourseNumber(studentDTO.getCourseNumber());
            studentDao.update(student);
            return convertStudentToDTO(student);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }
}