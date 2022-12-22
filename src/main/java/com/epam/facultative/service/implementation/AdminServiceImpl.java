package com.epam.facultative.service.implementation;

import com.epam.facultative.dto.Converter;
import com.epam.facultative.dto.CourseDTO;
import com.epam.facultative.dto.UserDTO;
import com.epam.facultative.entities.*;
import com.epam.facultative.exception.DAOException;
import com.epam.facultative.exception.ServiceException;
import com.epam.facultative.exception.ValidateException;
import com.epam.facultative.repositories.AdminRepository;
import com.epam.facultative.service.AdminService;

import java.util.*;

import static com.epam.facultative.utils.HashPassword.*;
import static com.epam.facultative.utils.validator.Validator.*;

public class AdminServiceImpl implements AdminService {
    private final AdminRepository adminRepository;
    private final Converter converter;

    public AdminServiceImpl(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
        this.converter = new Converter();
    }

    @Override
    public void addCourse(Course course) throws ServiceException, ValidateException {
        try {
            if (adminRepository.getCourseByTitle(course.getTitle()) != null) {
                throw new ValidateException("Login not unique");
            }
            if (validateCourseData(course.getTitle(), course.getDescription(), course.getDuration())) {
                adminRepository.addCourse(course);
            }
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void updateCourse(Course course) throws ServiceException, ValidateException {
        try {
            if (validateCourseData(course.getTitle(), course.getDescription(), course.getDuration())) {
                adminRepository.updateCourse(course);
            }
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteCourse(int courseId) throws ServiceException {
        try {
            adminRepository.deleteCourse(courseId);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }


    @Override
    public void addCategory(Category category) throws ServiceException, ValidateException {
        try {
            if (adminRepository.getCategoryByTitle(category.getTitle()) != null) {
                throw new ValidateException("Login not unique");
            }
            if (validateCategoryData(category.getTitle(), category.getDescription())) {
                adminRepository.addCategory(category);
            }
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void updateCategory(Category category) throws ServiceException, ValidateException {
        try {
            if (adminRepository.getCategoryByTitle(category.getTitle()) != null) {
                throw new ValidateException("Login not unique");
            }
            if (validateCategoryData(category.getTitle(), category.getDescription())) {
                adminRepository.updateCategory(category);
            }
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteCategory(int categoryId) throws ServiceException {
        try {
            adminRepository.deleteCategory(categoryId);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Category> getAllCategoriesPagination(int offset, int numberOfRows) throws ServiceException {
        try {
            return adminRepository.getAllCategoriesPagination(offset, numberOfRows);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public int getNoOfRecordsCategories() {
        return adminRepository.getNoOfRecordsCategories();
    }

    @Override
    public void assigned(int courseId, int teacherId) throws ServiceException {
        try {
            adminRepository.assigned(courseId, teacherId);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void blockStudent(int studentId) throws ServiceException {
        try {
            adminRepository.blockStudent(studentId);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void unblockStudent(int studentId) throws ServiceException {
        try {
            adminRepository.unblockStudent(studentId);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void addTeacher(User user) throws ServiceException, ValidateException {
        try {
            if (adminRepository.getUserByLogin(user.getLogin()) != null) {
                throw new ValidateException("Login not unique");
            }
            if (validateLogin(user.getLogin())
                    && validatePassword(user.getPassword())
                    && validateNameAndSurname(user.getName(), user.getSurname())
                    && validateEmail(user.getEmail())) {
                user.setRole(Role.TEACHER);
                user.setPassword(encode(user.getPassword()));
                adminRepository.addTeacher((Teacher) user);
            }
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<UserDTO> getAllStudentsPagination(int offset, int noOfRecords) throws ServiceException {
        try {
            List<Student> users = adminRepository.getAllStudentsPagination(offset, noOfRecords);
            List<UserDTO> students = new ArrayList<>();
            for (User user :
                    users) {
                students.add(converter.userToDTO(user));
            }
            return students;
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<UserDTO> getAllTeachersPagination(int offset, int noOfRecords) throws ServiceException {
        try {
            List<Teacher> users = adminRepository.getAllTeachersPagination(offset, noOfRecords);
            List<UserDTO> teachers = new ArrayList<>();
            for (User user :
                    users) {
                teachers.add(converter.userToDTO(user));
            }
            return teachers;
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Category getCategory(int id) throws ServiceException {
        try {
            return adminRepository.getCategory(id);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public CourseDTO getCourse(int id) throws ServiceException {
        try {
            Course course = adminRepository.getCourse(id);
            return converter.courseToDTO(course);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public UserDTO getTeacher(int id) throws ServiceException {
        try {
            User teacher = adminRepository.getTeacher(id);
            return converter.userToDTO(teacher);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public int getNoOfRecordsTeachers() {
        return adminRepository.getNoOfRecordsTeachers();
    }

    @Override
    public int getNoOfRecordsStudents() {
        return adminRepository.getNoOfRecordsStudents();
    }
}
