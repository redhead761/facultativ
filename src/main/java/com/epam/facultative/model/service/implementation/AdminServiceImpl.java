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

import java.util.*;

import static com.epam.facultative.model.utils.converter.Converter.*;
import static com.epam.facultative.model.utils.email_sender.EmailConstants.EMAIL_MESSAGE_FOR_CHANGE_COURSE;
import static com.epam.facultative.model.utils.email_sender.EmailConstants.EMAIL_SUBJECT_FOR_CHANGE_COURSE;
import static com.epam.facultative.model.utils.hash_password.HashPassword.encode;
import static com.epam.facultative.model.utils.param_builder.ParamBuilderForQueryUtil.*;
import static com.epam.facultative.model.utils.validator.ValidateExceptionMessageConstants.*;
import static com.epam.facultative.model.utils.validator.Validator.*;

public class AdminServiceImpl implements AdminService {
    private final CourseDao courseDao;
    private final CategoryDao categoryDao;
    private final StudentDao studentDao;
    private final TeacherDao teacherDao;

    public AdminServiceImpl(CourseDao courseDao, CategoryDao categoryDao, StudentDao studentDao, TeacherDao teacherDao) {
        this.courseDao = courseDao;
        this.categoryDao = categoryDao;
        this.studentDao = studentDao;
        this.teacherDao = teacherDao;
    }

    @Override
    public void addCourse(CourseDTO courseDTO) throws ServiceException, ValidateException {
        validateCourseData(courseDTO.getTitle(), courseDTO.getDescription(), courseDTO.getDuration());
        validateDate(courseDTO.getStartDate());
        Course course = convertDTOToCourse(courseDTO);
        try {
            courseDao.add(course);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void updateCourse(CourseDTO courseDTO) throws ServiceException, ValidateException {
        validateCourseData(courseDTO.getTitle(), courseDTO.getDescription(), courseDTO.getDuration());
        Course course = convertDTOToCourse(courseDTO);
        try {
            courseDao.update(course);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void courseLaunchNewsLetter(CourseDTO courseDTO) throws ServiceException {
        int courseId = courseDTO.getId();
        List<Student> students;
        EmailSender emailSender = AppContext.getAppContext().getEmailSender();
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

    @Override
    public void deleteCourse(int courseId) throws ServiceException, ValidateException {
        try {
            courseDao.delete(courseId);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void addCategory(CategoryDTO categoryDTO) throws ServiceException, ValidateException {
        validateCategoryData(categoryDTO.getTitle(), categoryDTO.getDescription());
        Category category = convertDTOToCategory(categoryDTO);
        try {
            categoryDao.add(category);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void updateCategory(CategoryDTO categoryDTO) throws ServiceException, ValidateException {
        validateCategoryData(categoryDTO.getTitle(), categoryDTO.getDescription());
        Category category = convertDTOToCategory(categoryDTO);
        try {
            categoryDao.update(category);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteCategory(int categoryId) throws ServiceException, ValidateException {
        try {
            categoryDao.delete(categoryId);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void assigned(int courseId, int userId) throws ServiceException, ValidateException {
        try {
            Course course = courseDao
                    .get(courseParamBuilderForQuery().setIdCourseFilter(String.valueOf(courseId)).getParam())
                    .orElseThrow(() -> new ValidateException(COURSE_NOT_FOUND_MESSAGE));
            Teacher teacher = teacherDao
                    .get(userParamBuilderForQuery().setUserIdFilter(String.valueOf(userId)).getParam())
                    .orElseThrow(() -> new ValidateException(TEACHER_NOT_FOUND_MESSAGE));
            course.setTeacher(teacher);
            courseDao.update(course);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void blockStudent(int userId) throws ServiceException {
        try {
            studentDao.updateBlock(userId, true);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void unblockStudent(int userId) throws ServiceException {
        try {
            studentDao.updateBlock(userId, false);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void addTeacher(TeacherDTO teacherDTO) throws ServiceException, ValidateException {
        validateLogin(teacherDTO.getLogin());
        validatePassword(teacherDTO.getPassword());
        validateNameAndSurname(teacherDTO.getName(), teacherDTO.getSurname());
        validateEmail(teacherDTO.getEmail());
        teacherDTO.setRole(Role.TEACHER);
        teacherDTO.setPassword(encode(teacherDTO.getPassword()));
        Teacher teacher = convertDTOToTeacher(teacherDTO);
        try {
            teacherDao.add(teacher);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

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

    @Override
    public void deleteTeacher(int id) throws ValidateException, ServiceException {
        try {
            teacherDao.delete(id);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

}