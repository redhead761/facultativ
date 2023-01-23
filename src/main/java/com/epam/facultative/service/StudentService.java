package com.epam.facultative.service;

import com.epam.facultative.dto.CourseDTO;
import com.epam.facultative.dto.StudentDTO;
import com.epam.facultative.exception.ServiceException;
import com.epam.facultative.exception.ValidateException;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Map;

public interface StudentService {
    Map.Entry<Integer, List<CourseDTO>> getCoursesByJournal(String param) throws ServiceException;

    void enroll(int courseId, int userId) throws ServiceException;

    void addStudent(StudentDTO student) throws ServiceException, ValidateException;

    int getGrade(int courseId, int userId) throws ServiceException;

    ByteArrayOutputStream downloadCertificate(StudentDTO studentDTO, int courseId, int grade) throws ServiceException, ValidateException;

    void sendCertificate(StudentDTO studentDTO, int courseId, int grade) throws ValidateException, ServiceException;
}