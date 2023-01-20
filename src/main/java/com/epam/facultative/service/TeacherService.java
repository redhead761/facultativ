package com.epam.facultative.service;

import com.epam.facultative.dto.CourseDTO;
import com.epam.facultative.dto.StudentDTO;
import com.epam.facultative.exception.ServiceException;

import java.util.List;
import java.util.Map;

public interface TeacherService {
    void grading(int courseId, int userId, int grade) throws ServiceException;

    Map.Entry<Integer, List<StudentDTO>> getStudentsByCourse(int courseId, int offset, int numberOfRows) throws ServiceException;

    Map.Entry<Integer, List<CourseDTO>> getTeacherCourses(int teacherId, int offset, int numberOfRows) throws ServiceException;
}