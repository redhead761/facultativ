package com.epam.facultative.service;

import com.epam.facultative.dto.CourseDTO;
import com.epam.facultative.dto.StudentDTO;
import com.epam.facultative.exception.ServiceException;

import java.util.List;

public interface TeacherService {
    void grading(int courseId, int userId, int grade) throws ServiceException;

    List<StudentDTO> getStudentsByCourse(int courseId, int offset, int numberOfRows) throws ServiceException;

    List<CourseDTO> getTeacherCourses(int teacherId, int offset, int numberOfRows) throws ServiceException;

    int getNoOfRecordsCourses();
    int getNoOfRecordsStudents();
}