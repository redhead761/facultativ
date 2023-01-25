package com.epam.facultative.service;

import com.epam.facultative.dto.StudentDTO;
import com.epam.facultative.dto.TeacherDTO;
import com.epam.facultative.exception.ServiceException;
import com.epam.facultative.exception.ValidateException;

import java.util.List;
import java.util.Map;

public interface TeacherService {
    void grading(int courseId, int userId, int grade) throws ServiceException;

    Map.Entry<Integer, List<StudentDTO>> getStudentsByCourse(String param) throws ServiceException;

    TeacherDTO updateTeacher(TeacherDTO teacherDTO) throws ValidateException, ServiceException;
}