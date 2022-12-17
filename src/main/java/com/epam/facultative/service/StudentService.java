package com.epam.facultative.service;

import com.epam.facultative.dto.CourseDTO;
import com.epam.facultative.dto.UserDTO;
import com.epam.facultative.entity.User;
import com.epam.facultative.exception.DAOException;
import com.epam.facultative.exception.ServiceException;
import com.epam.facultative.exception.ValidateException;

import java.util.List;

public interface StudentService {
    List<CourseDTO> getCoursesByStudent(int studentId, int offset,int numberOfRows) throws ServiceException;

    List<CourseDTO> getCoursesComingSoon(int studentId) throws ServiceException;

    List<CourseDTO> getCoursesInProgress(int studentId) throws ServiceException;

    List<UserDTO> getCoursesCompleted(int studentId) throws ServiceException;

    void enroll(int courseId, int userId) throws ServiceException;

    void addStudent(User user) throws ServiceException, ValidateException;
}
