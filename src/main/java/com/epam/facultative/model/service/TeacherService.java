package com.epam.facultative.model.service;

import com.epam.facultative.model.dto.StudentDTO;
import com.epam.facultative.model.dto.TeacherDTO;
import com.epam.facultative.model.exception.ServiceException;
import com.epam.facultative.model.exception.ValidateException;

import java.util.List;
import java.util.Map;

public interface TeacherService {
    void grading(int courseId, int userId, int grade) throws ServiceException;

    Map.Entry<Integer, List<StudentDTO>> getStudentsByCourse(String param) throws ServiceException;

    TeacherDTO updateTeacher(TeacherDTO teacherDTO) throws ValidateException, ServiceException;
}