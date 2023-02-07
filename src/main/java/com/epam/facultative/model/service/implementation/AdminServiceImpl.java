package com.epam.facultative.model.service.implementation;

import com.epam.facultative.controller.app_context.AppContext;
import com.epam.facultative.model.entities.*;
import com.epam.facultative.model.exception.DAOException;
import com.epam.facultative.model.exception.ServiceException;
import com.epam.facultative.model.exception.ValidateException;
import com.epam.facultative.model.dao.CategoryDao;
import com.epam.facultative.model.dao.CourseDao;
import com.epam.facultative.model.dao.StudentDao;
import com.epam.facultative.model.dao.TeacherDao;
import com.epam.facultative.model.dto.CategoryDTO;
import com.epam.facultative.model.dto.CourseDTO;
import com.epam.facultative.model.dto.StudentDTO;
import com.epam.facultative.model.dto.TeacherDTO;
import com.epam.facultative.model.service.AdminService;
import com.epam.facultative.model.utils.email_sender.EmailSender;
import com.epam.facultative.model.utils.param_builder.ParamBuilderForQuery;
import lombok.RequiredArgsConstructor;

import java.util.*;

import static com.epam.facultative.model.exception.ConstantsValidateMessage.*;
import static com.epam.facultative.model.utils.converter.Converter.*;
import static com.epam.facultative.model.utils.email_sender.EmailConstants.EMAIL_MESSAGE_FOR_CHANGE_COURSE;
import static com.epam.facultative.model.utils.email_sender.EmailConstants.EMAIL_SUBJECT_FOR_CHANGE_COURSE;
import static com.epam.facultative.model.utils.hash_password.HashPassword.encode;
import static com.epam.facultative.model.utils.param_builder.ParamBuilderForQueryUtil.*;
import static com.epam.facultative.model.utils.validator.Validator.*;

/**
 * Implementation of AdminService interface.
 *
 * @author Oleksandr Panchenko
 * @version 1.0
 */
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {
    private final CourseDao courseDao;
    private final CategoryDao categoryDao;
    private final StudentDao studentDao;
    private final TeacherDao teacherDao;

    /**
     * Gets CourseDTO from action and calls DAO to add relevant entity. Validate course's fields. Convert DTO to entity.
     *
     * @param courseDTO - DTO to be added as Course to database
     * @throws ServiceException  - may wrap DAOException or be thrown by another mistakes
     * @throws ValidateException - occurs in case of non-validation of data
     */
    @Override
    public void addCourse(CourseDTO courseDTO) throws ServiceException, ValidateException {
        validateCourseData(courseDTO);
        validateStartDate(courseDTO.getStartDate());
        Course course = convertDTOToCourse(courseDTO);
        try {
            courseDao.add(course);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Gets CourseDTO from action and calls DAO to update relevant entity. Validate course's fields. Convert DTO to entity.
     *
     * @param courseDTO - DTO to be updated as Course to database
     * @throws ServiceException  - may wrap DAOException or be thrown by another mistakes
     * @throws ValidateException - occurs in case of non-validation of data
     */
    @Override
    public void updateCourse(CourseDTO courseDTO) throws ServiceException, ValidateException {
        validateCourseData(courseDTO);
        validateStatus(courseDTO.getStatus(), courseDTO.getStartDate());
        Course course = convertDTOToCourse(courseDTO);
        try {
            courseDao.update(course);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Gets CourseDTO and AppContext from action and calls DAO to search students by course. Send emails about update course.
     *
     * @param courseDTO  - DTO to create message and find students
     * @param appContext - used to create EmailSender
     * @throws ServiceException - may wrap DAOException or be thrown by another mistakes
     */
    @Override
    public void courseLaunchNewsLetter(CourseDTO courseDTO, AppContext appContext) throws ServiceException {
        int courseId = courseDTO.getId();
        List<Student> students;
        EmailSender emailSender = appContext.getEmailSender();
        ParamBuilderForQuery paramBuilder = courseParamBuilderForQuery()
                .setIdFilterCourseForStudent(String.valueOf(courseId))
                .setLimits("1", String.valueOf(Integer.MAX_VALUE));
        try {
            Map.Entry<Integer, List<Student>> studentsWithRows = studentDao.getStudentsByCourse(paramBuilder.getParam());
            students = studentsWithRows.getValue();
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        for (Student student : students) {
            new Thread(() ->
                    emailSender.send(student.getEmail(), EMAIL_SUBJECT_FOR_CHANGE_COURSE, EMAIL_MESSAGE_FOR_CHANGE_COURSE + courseDTO)).start();
        }
    }

    /**
     * Gets courseId from action and calls DAO to delete relevant entity.
     *
     * @param courseId - used to delete
     * @throws ServiceException  - may wrap DAOException or be thrown by another mistakes
     * @throws ValidateException - occurs in case of non-validation of data
     */
    @Override
    public void deleteCourse(int courseId) throws ServiceException, ValidateException {
        try {
            courseDao.delete(courseId);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Gets CategoryDTO from action and calls DAO to add relevant entity. Validate category's fields. Convert DTO to entity.
     *
     * @param categoryDTO - DTO to be added as Category to database
     * @throws ServiceException  - may wrap DAOException or be thrown by another mistakes
     * @throws ValidateException - occurs in case of non-validation of data
     */
    @Override
    public void addCategory(CategoryDTO categoryDTO) throws ServiceException, ValidateException {
        validateCategoryData(categoryDTO);
        Category category = convertDTOToCategory(categoryDTO);
        try {
            categoryDao.add(category);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Gets CategoryDTO from action and calls DAO to update relevant entity. Validate category's fields. Convert DTO to entity.
     *
     * @param categoryDTO - DTO to be updated as Category to database
     * @throws ServiceException  - may wrap DAOException or be thrown by another mistakes
     * @throws ValidateException - occurs in case of non-validation of data
     */
    @Override
    public void updateCategory(CategoryDTO categoryDTO) throws ServiceException, ValidateException {
        validateCategoryData(categoryDTO);
        Category category = convertDTOToCategory(categoryDTO);
        try {
            categoryDao.update(category);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Gets categoryId from action and calls DAO to delete relevant entity.
     *
     * @param categoryId - used to delete
     * @throws ServiceException  - may wrap DAOException or be thrown by another mistakes
     * @throws ValidateException - occurs in case of non-validation of data
     */
    @Override
    public void deleteCategory(int categoryId) throws ServiceException, ValidateException {
        try {
            categoryDao.delete(categoryId);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Gets userId from action and calls DAO to block status student.
     *
     * @param userId- used to find and block
     * @throws ServiceException - may wrap DAOException or be thrown by another mistakes
     */
    @Override
    public void blockStudent(int userId) throws ServiceException {
        try {
            studentDao.updateBlock(userId, true);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Gets userId from action and calls DAO to unblock status student.
     *
     * @param userId- used to find and unblock
     * @throws ServiceException - may wrap DAOException or be thrown by another mistakes
     */
    @Override
    public void unblockStudent(int userId) throws ServiceException {
        try {
            studentDao.updateBlock(userId, false);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Gets TeacherDTO from action and calls DAO to add relevant entity. Validate teacher's fields. Convert DTO to entity.
     * Encode password.
     *
     * @param teacherDTO - DTO to be added as Teacher to database
     * @throws ServiceException  - may wrap DAOException or be thrown by another mistakes
     * @throws ValidateException - occurs in case of non-validation of data
     */
    @Override
    public void addTeacher(TeacherDTO teacherDTO) throws ServiceException, ValidateException {
        validateTeacherData(teacherDTO);
        teacherDTO.setRole(Role.TEACHER);
        teacherDTO.setPassword(encode(teacherDTO.getPassword()));
        Teacher teacher = convertDTOToTeacher(teacherDTO);
        try {
            teacherDao.add(teacher);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Gets parameter from action and calls DAO to get relevant entities and count rows. Convert entity to DTO.
     *
     * @param param - parameters to get
     * @return {@code Map.Entry<Integer, List < StudentDTO>>} - return relevant DTO and count rows
     * @throws ServiceException - may wrap DAOException or be thrown by another mistakes
     */
    @Override
    public Map.Entry<Integer, List<StudentDTO>> getStudents(String param) throws ServiceException {
        try {
            Map.Entry<Integer, List<Student>> studentsWithRows = studentDao.getAll(param);
            List<StudentDTO> students = prepareStudents(studentsWithRows.getValue());
            return Map.entry(studentsWithRows.getKey(), students);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Gets id from action and calls DAO to get relevant entity. Convert entity to DTO.
     *
     * @param id - parameters to get
     * @return CategoryDTO - return relevant DTO
     * @throws ServiceException  - may wrap DAOException or be thrown by another mistakes
     * @throws ValidateException - occurs when the category is not found
     */
    @Override
    public CategoryDTO getCategory(int id) throws ServiceException, ValidateException {
        try {
            Category category = categoryDao
                    .get(categoryParamBuilderForQuery().setIdCategoryFilter(String.valueOf(id)).getParam())
                    .orElseThrow(() -> new ValidateException(CATEGORY_NOT_FOUND_MESSAGE));
            return convertCategoryToDTO(category);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Gets id from action and calls DAO to get relevant entity. Convert entity to DTO.
     *
     * @param id - parameters to get
     * @return CourseDTO - return relevant DTO
     * @throws ServiceException  - may wrap DAOException or be thrown by another mistakes
     * @throws ValidateException - occurs when the category is not found
     */
    @Override
    public CourseDTO getCourse(int id) throws ServiceException, ValidateException {
        try {
            Course course = courseDao
                    .get(courseParamBuilderForQuery().setIdCourseFilter(String.valueOf(id)).getParam())
                    .orElseThrow(() -> new ValidateException(COURSE_NOT_FOUND_MESSAGE));
            return convertCourseToDTO(course);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Gets id from action and calls DAO to get relevant entity. Convert entity to DTO.
     *
     * @param id - parameters to get
     * @return TeacherDTO - return relevant DTO
     * @throws ServiceException  - may wrap DAOException or be thrown by another mistakes
     * @throws ValidateException - occurs when the category is not found
     */
    @Override
    public TeacherDTO getTeacher(int id) throws ServiceException, ValidateException {
        try {
            Teacher teacher = teacherDao
                    .get(userParamBuilderForQuery().setUserIdFilter(String.valueOf(id)).getParam())
                    .orElseThrow(() -> new ValidateException(TEACHER_NOT_FOUND_MESSAGE));
            return convertTeacherToDTO(teacher);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Gets id from action and calls DAO to delete relevant entity.
     *
     * @param id - used to delete
     * @throws ServiceException  - may wrap DAOException or be thrown by another mistakes
     * @throws ValidateException - occurs in case of non-validation of data
     */
    @Override
    public void deleteTeacher(int id) throws ValidateException, ServiceException {
        try {
            teacherDao.delete(id);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Private methods for class maintenance
     */
    private void validateCourseData(CourseDTO courseDTO) throws ValidateException {
        validateTitle(courseDTO.getTitle());
        validateDescription(courseDTO.getDescription());
    }


    private void validateCategoryData(CategoryDTO categoryDTO) throws ValidateException {
        validateTitle(categoryDTO.getTitle());
        validateDescription(categoryDTO.getDescription());
    }


    private void validateTeacherData(TeacherDTO teacherDTO) throws ValidateException {
        validateLogin(teacherDTO.getLogin());
        validatePassword(teacherDTO.getPassword());
        validateName(teacherDTO.getName());
        validateName(teacherDTO.getSurname());
        validateEmail(teacherDTO.getEmail());
    }
}