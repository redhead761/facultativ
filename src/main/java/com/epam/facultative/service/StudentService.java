package com.epam.facultative.service;

import com.epam.facultative.dto.CourseDTO;
import com.epam.facultative.dto.UserDTO;
import com.epam.facultative.entities.User;
import com.epam.facultative.exception.ServiceException;
import com.epam.facultative.exception.ValidateException;

import java.util.List;

public interface StudentService {
    List<CourseDTO> getCoursesByStudent(int studentId, int offset,int numberOfRows) throws ServiceException;

    List<CourseDTO> getCoursesComingSoon(int studentId,int offset,int numberOfRows) throws ServiceException;

    List<CourseDTO> getCoursesInProgress(int studentId,int offset,int numberOfRows) throws ServiceException;

    List<CourseDTO> getCoursesCompleted(int studentId,int offset,int numberOfRows) throws ServiceException;

    void enroll(int courseId, int userId) throws ServiceException;

    void addStudent(User user) throws ServiceException, ValidateException;
    int getNoOfRecordsCourses();
}
