package com.epam.facultative.service.implementation;


import com.epam.facultative.data_layer.daos.*;
import com.epam.facultative.data_layer.entities.*;
import com.epam.facultative.dto.*;
import com.epam.facultative.exception.DAOException;
import com.epam.facultative.exception.ServiceException;
import com.epam.facultative.exception.ValidateException;
import com.epam.facultative.service.AdminService;

import java.util.*;

import static com.epam.facultative.utils.HashPassword.*;
import static com.epam.facultative.utils.validator.Validator.*;

public class AdminServiceImpl implements AdminService {
    private final CourseDao courseDao;
    private final CategoryDao categoryDao;
    private final UserDao userDao;
    private final Converter converter;
    private final StudentDao studentDao;
    private final TeacherDao teacherDao;

    public AdminServiceImpl(CourseDao courseDao, CategoryDao categoryDao, UserDao userDao, StudentDao studentDao, TeacherDao teacherDao) {
        this.courseDao = courseDao;
        this.categoryDao = categoryDao;
        this.userDao = userDao;
        this.studentDao = studentDao;
        this.teacherDao = teacherDao;
        this.converter = new Converter();
    }

    @Override
    public void addCourse(Course course) throws ServiceException, ValidateException {
        try {
            validateCourseData(course.getTitle(), course.getDescription(), course.getDuration());
            courseDao.add(course);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void updateCourse(Course course) throws ServiceException, ValidateException {
        try {
            validateCourseData(course.getTitle(), course.getDescription(), course.getDuration());
            courseDao.update(course);
        } catch (DAOException e) {
            throw new ServiceException(e);
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
    public void addCategory(Category category) throws ServiceException, ValidateException {
        try {
            validateCategoryData(category.getTitle(), category.getDescription());
            categoryDao.add(category);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void updateCategory(Category category) throws ServiceException, ValidateException {
        try {
            validateCategoryData(category.getTitle(), category.getDescription());
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
    public List<Category> getAllCategoriesPagination(int offset, int numberOfRows) throws ServiceException {
        try {
            return categoryDao.getAllPagination(offset, numberOfRows);
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
            Course course = courseDao.getById(idCourse);
            Teacher teacher = teacherDao.getById(idUser);
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
    public void addTeacher(Teacher teacher) throws ServiceException, ValidateException {
        try {
            validateLogin(teacher.getLogin());
            validatePassword(teacher.getPassword());
            validateNameAndSurname(teacher.getName(), teacher.getSurname());
            validateEmail(teacher.getEmail());
            teacher.setRole(Role.TEACHER);
            teacher.setPassword(encode(teacher.getPassword()));
            teacherDao.add(teacher);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<StudentDTO> getAllStudentsPagination(int offset, int noOfRecords) throws ServiceException {
        try {
            return prepareStudent(studentDao.getAllPagination(offset, noOfRecords));
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<TeacherDTO> getAllTeachersPagination(int offset, int noOfRecords) throws ServiceException {
        try {
            return prepareTeacher(teacherDao.getAllPagination(offset, noOfRecords));
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Category getCategory(int id) throws ServiceException {
        try {
            return categoryDao.getById(id);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public CourseDTO getCourse(int id) throws ServiceException {
        try {
            Course course = courseDao.getById(id);
            return converter.convertCourseToDTO(course);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public UserDTO getTeacher(int id) throws ServiceException {
        try {
            User teacher = userDao.getById(id);
            return converter.convertUserToDTO(teacher);
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

    private List<TeacherDTO> prepareTeacher(List<Teacher> teachers) {
        List<TeacherDTO> result = new ArrayList<>();
        for (Teacher teacher : teachers) {
            result.add(converter.convertTeacherToDTO(teacher));
        }
        return result;
    }

    private List<StudentDTO> prepareStudent(List<Student> students) {
        List<StudentDTO> result = new ArrayList<>();
        for (Student student : students) {
            result.add(converter.convertStudentToDTO(student));
        }
        return result;
    }
}