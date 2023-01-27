package com.epam.facultative.model.service;

import com.epam.facultative.controller.app_context.AppContext;
import com.epam.facultative.model.dto.CategoryDTO;
import com.epam.facultative.model.dto.CourseDTO;
import com.epam.facultative.model.dto.StudentDTO;
import com.epam.facultative.model.dto.TeacherDTO;
import com.epam.facultative.model.exception.ServiceException;
import com.epam.facultative.model.exception.ValidateException;

import java.util.List;
import java.util.Map;

public interface AdminService {
    void courseLaunchNewsLetter(CourseDTO courseDTO, AppContext appContext) throws ServiceException;

    void addCourse(CourseDTO courseDTO) throws ServiceException, ValidateException;

    void updateCourse(CourseDTO courseDTO) throws ServiceException, ValidateException;

    void deleteCourse(int courseId) throws ServiceException, ValidateException;

    void addCategory(CategoryDTO categoryDTO) throws ServiceException, ValidateException;

    void updateCategory(CategoryDTO categoryDTO) throws ServiceException, ValidateException;

    void deleteCategory(int categoryId) throws ServiceException, ValidateException;

    void assigned(int courseId, int userId) throws ServiceException, ValidateException;

    void blockStudent(int userId) throws ServiceException;

    void unblockStudent(int userId) throws ServiceException;

    void addTeacher(TeacherDTO teacherDTO) throws ServiceException, ValidateException;

    Map.Entry<Integer, List<StudentDTO>> getStudents(String param) throws ServiceException;

    CategoryDTO getCategory(int id) throws ServiceException, ValidateException;

    CourseDTO getCourse(int id) throws ServiceException, ValidateException;

    TeacherDTO getTeacher(int id) throws ServiceException, ValidateException;

    void deleteTeacher(int id) throws ValidateException, ServiceException;
}