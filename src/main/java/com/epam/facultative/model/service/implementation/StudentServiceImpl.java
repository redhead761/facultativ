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
import lombok.RequiredArgsConstructor;

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

/**
 * Implementation of StudentService interface.
 *
 * @author Oleksandr Panchenko
 * @version 1.0
 */
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {
    private final CourseDao courseDao;
    private final StudentDao studentDao;

    /**
     * Gets parameter from action and calls DAO to get relevant entities and count rows. Convert entity to DTO.
     *
     * @param param - parameters to get
     * @return Map.Entry<Integer, List < CourseDTO>> - return relevant DTO and count rows
     * @throws ServiceException - may wrap DAOException or be thrown by another mistakes
     */
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

    /**
     * Gets courseId and userId from action and calls DAO to add student to course.
     *
     * @param courseId,userId- use to find and add
     * @throws ServiceException - may wrap DAOException or be thrown by another mistakes
     */
    @Override
    public void enroll(int courseId, int userId) throws ServiceException {
        try {
            courseDao.insertJournal(courseId, userId);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Gets StudentDTO from action and calls DAO to add relevant entity. Validate student's fields. Convert DTO to entity.
     * Encode password.
     *
     * @param studentDTO - DTO to be added as Student to database
     * @throws ServiceException  - may wrap DAOException or be thrown by another mistakes
     * @throws ValidateException - occurs in case of non-validation of data
     */
    @Override
    public void addStudent(StudentDTO studentDTO) throws ServiceException, ValidateException {
        validateStudentData(studentDTO);
        validatePassword(studentDTO.getPassword());
        studentDTO.setRole(Role.STUDENT);
        studentDTO.setPassword(encode(studentDTO.getPassword()));
        try {
            studentDao.add(convertDTOToStudent(studentDTO));
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Gets courseId and userId from action and calls DAO to get grade.
     *
     * @param courseId,userId - parameters to get
     * @return grade - return found grade
     * @throws ServiceException - may wrap DAOException or be thrown by another mistakes
     */
    @Override
    public int getGrade(int courseId, int userId) throws ServiceException {
        try {
            return studentDao.getGrade(courseId, userId);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Gets studentDTO,courseId, grade and AppContext from action and calls DAO to get relevant entity.
     * Calls PdfCreator to create certificate with course and grade.
     *
     * @param courseId         - used to find
     * @param appContext       - used to call PdfCreator
     * @param studentDTO,grade - used to create certificate
     * @return ByteArrayOutputStream - return table
     * @throws ServiceException  - may wrap DAOException or be thrown by another mistakes
     * @throws ValidateException - occurs in case of non-validation of data
     */
    @Override
    public ByteArrayOutputStream downloadCertificate(StudentDTO studentDTO, int courseId, int grade, AppContext appContext) throws ServiceException, ValidateException {
        try {
            Course course = courseDao
                    .get(courseParamBuilderForQuery().setIdCourseFilter(String.valueOf(courseId)).getParam())
                    .orElseThrow(() -> new ValidateException(LOGIN_NOT_EXIST_MESSAGE));
            PdfCreator pdfCreator = appContext.getPdfCreator();
            return pdfCreator.createCertificateForDownload(studentDTO, course, grade);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }


    /**
     * Gets studentDTO,courseId, grade and AppContext from action and calls DAO to get relevant entity.
     * Calls PdfCreator to create certificate with course and grade. Calls EmailSender to send cerfiticate
     *
     * @param courseId         - used to find
     * @param appContext       - used to call PdfCreator and EmailSender
     * @param studentDTO,grade - used to create certificate
     * @throws ServiceException  - may wrap DAOException or be thrown by another mistakes
     * @throws ValidateException - occurs in case of non-validation of data
     */
    @Override
    public void sendCertificate(StudentDTO studentDTO, int courseId, int grade, AppContext appContext) throws ValidateException, ServiceException {
        PdfCreator pdfCreator = appContext.getPdfCreator();
        try {
            Course course = courseDao
                    .get(courseParamBuilderForQuery().setIdCourseFilter(String.valueOf(courseId)).getParam())
                    .orElseThrow(() -> new ValidateException(LOGIN_NOT_EXIST_MESSAGE));
            String certificate = pdfCreator.createCertificateForSend(studentDTO, course, grade);
            EmailSender emailSender = appContext.getEmailSender();
            emailSender.sendCertificate(studentDTO.getEmail(), EMAIL_SUBJECT_FOR_CERTIFICATE, EMAIL_MESSAGE_FOR_CERTIFICATE, certificate);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }


    /**
     * Gets StudentDTO from action and calls DAO to update relevant entity. Validate student's fields. Convert DTO to entity.
     *
     * @param studentDTO - DTO to be updated as Student to database
     * @throws ServiceException  - may wrap DAOException or be thrown by another mistakes
     * @throws ValidateException - occurs in case of non-validation of data
     */
    @Override
    public StudentDTO updateStudent(StudentDTO studentDTO) throws ValidateException, ServiceException {
        validateStudentData(studentDTO);
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

    private void validateStudentData(StudentDTO studentDTO) throws ValidateException {
        validateLogin(studentDTO.getLogin());
        validateName(studentDTO.getName());
        validateName(studentDTO.getSurname());
        validateEmail(studentDTO.getEmail());
    }
}