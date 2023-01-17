package com.epam.facultative.service.implementation;

import com.epam.facultative.controller.AppContext;
import com.epam.facultative.data_layer.daos.*;
import com.epam.facultative.data_layer.entities.*;
import com.epam.facultative.dto.*;
import com.epam.facultative.exception.DAOException;
import com.epam.facultative.exception.ServiceException;
import com.epam.facultative.exception.ValidateException;
import com.epam.facultative.service.AdminService;
import com.epam.facultative.utils.email_sender.EmailSender;

import java.util.*;

import static com.epam.facultative.dto.Converter.*;
import static com.epam.facultative.utils.HashPassword.*;
import static com.epam.facultative.utils.validator.ValidateExceptionMessageConstants.*;
import static com.epam.facultative.utils.validator.Validator.*;

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
        try {
            students = studentDao.getStudentsByCourse(courseId, 0, Integer.MAX_VALUE);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        EmailSender emailSender = AppContext.getAppContext().getEmailSender();
        for (Student student : students) {
            emailSender.send(student.getEmail(), "Start course", "Hello! Your course:" + courseDTO.getTitle() + " start" + courseDTO.getStartDate());
        }
    }

    @Override
    public void deleteCourse(int courseId) throws ServiceException {
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
    public void deleteCategory(int categoryId) throws ServiceException {
        try {
            categoryDao.delete(categoryId);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<CategoryDTO> getAllCategoriesPagination(int offset, int numberOfRows) throws ServiceException {
        try {
            List<Category> categories = categoryDao.getAllPagination(offset, numberOfRows);
            return prepareCategories(categories);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public int getNoOfRecordsCategories() {
        return categoryDao.getNoOfRecords();
    }

    @Override
    public void assigned(int idCourse, int idUser) throws ServiceException, ValidateException {
        try {
            Course course = courseDao.getById(idCourse).orElseThrow(() -> new ValidateException(COURSE_NOT_FOUND_MESSAGE));
            Teacher teacher = teacherDao.getById(idUser).orElseThrow(() -> new ValidateException(TEACHER_NOT_FOUND_MESSAGE));
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
    public List<StudentDTO> getAllStudentsPagination(int offset, int noOfRecords) throws ServiceException {
        try {
            List<Student> students = studentDao.getAllPagination(offset, noOfRecords);
            return prepareStudents(students);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<TeacherDTO> getAllTeachersPagination(int offset, int noOfRecords) throws ServiceException {
        try {
            List<Teacher> teachers = teacherDao.getAllPagination(offset, noOfRecords);
            return prepareTeachers(teachers);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public CategoryDTO getCategory(int id) throws ServiceException, ValidateException {
        try {
            Category category = categoryDao.getById(id).orElseThrow(() -> new ValidateException(CATEGORY_NOT_FOUND_MESSAGE));
            return convertCategoryToDTO(category);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public CourseDTO getCourse(int id) throws ServiceException, ValidateException {
        try {
            Course course = courseDao.getById(id).orElseThrow(() -> new ValidateException(COURSE_NOT_FOUND_MESSAGE));
            return convertCourseToDTO(course);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public TeacherDTO getTeacher(int id) throws ServiceException, ValidateException {
        try {
            Teacher teacher = teacherDao.getById(id).orElseThrow(() -> new ValidateException(TEACHER_NOT_FOUND_MESSAGE));
            return convertTeacherToDTO(teacher);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public int getNoOfRecordsTeachers() {
        return teacherDao.getNoOfRecords();
    }

    @Override
    public int getNoOfRecordsStudents() {
        return studentDao.getNoOfRecords();
    }

}