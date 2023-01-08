package com.epam.facultative.service.implementation;

import com.epam.facultative.data_layer.daos.*;
import com.epam.facultative.data_layer.entities.*;
import com.epam.facultative.dto.*;
import com.epam.facultative.exception.DAOException;
import com.epam.facultative.exception.ServiceException;
import com.epam.facultative.exception.ValidateException;
import com.epam.facultative.service.AdminService;

import java.util.*;

import static com.epam.facultative.dto.Converter.*;
import static com.epam.facultative.utils.HashPassword.*;
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
        try {
            validateCourseData(courseDTO.getTitle(), courseDTO.getDescription(), courseDTO.getDuration());
            courseDao.add(convertDTOToCourse(courseDTO));
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void updateCourse(CourseDTO courseDTO) throws ServiceException, ValidateException {
        try {
            validateCourseData(courseDTO.getTitle(), courseDTO.getDescription(), courseDTO.getDuration());
            courseDao.update(convertDTOToCourse(courseDTO));
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
    public void addCategory(CategoryDTO categoryDTO) throws ServiceException, ValidateException {
        try {
            validateCategoryData(categoryDTO.getTitle(), categoryDTO.getDescription());
            categoryDao.add(convertDTOToCategory(categoryDTO));
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void updateCategory(CategoryDTO categoryDTO) throws ServiceException, ValidateException {
        try {
            validateCategoryData(categoryDTO.getTitle(), categoryDTO.getDescription());
            categoryDao.update(convertDTOToCategory(categoryDTO));
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
            List<CategoryDTO> categories = new ArrayList<>();
            for (Category category : categoryDao.getAllPagination(offset, numberOfRows)) {
                categories.add(convertCategoryToDTO(category));
            }
            return categories;
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
    public void addTeacher(TeacherDTO teacherDTO) throws ServiceException, ValidateException {
        try {
            validateLogin(teacherDTO.getLogin());
            validatePassword(teacherDTO.getPassword());
            validateNameAndSurname(teacherDTO.getName(), teacherDTO.getSurname());
            validateEmail(teacherDTO.getEmail());
            teacherDTO.setRole(Role.TEACHER);
            teacherDTO.setPassword(encode(teacherDTO.getPassword()));
            teacherDao.add(convertDTOToTeacher(teacherDTO));
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
    public CategoryDTO getCategory(int id) throws ServiceException {
        try {
            return convertCategoryToDTO(categoryDao.getById(id));
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public CourseDTO getCourse(int id) throws ServiceException {
        try {
            Course course = courseDao.getById(id);
            return convertCourseToDTO(course);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public TeacherDTO getTeacher(int id) throws ServiceException {
        try {
            return convertTeacherToDTO(teacherDao.getById(id));
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
            result.add(convertTeacherToDTO(teacher));
        }
        return result;
    }

    private List<StudentDTO> prepareStudent(List<Student> students) {
        List<StudentDTO> result = new ArrayList<>();
        for (Student student : students) {
            result.add(convertStudentToDTO(student));
        }
        return result;
    }
}