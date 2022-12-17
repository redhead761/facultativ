package com.epam.facultative.service;

import com.epam.facultative.dto.*;
import com.epam.facultative.exception.ServiceException;

import java.util.List;

public interface TeacherService {
    void grading(int courseId, int userId, int grade) throws ServiceException;

    List<UserDTO> getStudentsByCourse(int courseId) throws ServiceException;

    List<CourseDTO> getTeacherCourses(int teacherId, int offset, int numberOfRows) throws ServiceException;
}
