package com.epam.facultative.repositories;

import com.epam.facultative.dto.CourseDTO;
import com.epam.facultative.dto.UserDTO;
import com.epam.facultative.entities.Course;
import com.epam.facultative.entities.Student;
import com.epam.facultative.entities.User;
import com.epam.facultative.exception.DAOException;
import com.epam.facultative.exception.ServiceException;
import com.epam.facultative.exception.ValidateException;

import java.util.List;

public interface StudentRepository {
    List<Course> getCoursesByStudent(int studentId, int offset, int numberOfRows) throws DAOException;

    List<Course> getCoursesComingSoon(int studentId,int offset,int numberOfRows) throws DAOException;

    List<Course> getCoursesInProgress(int studentId,int offset,int numberOfRows) throws DAOException;

    List<Course> getCoursesCompleted(int studentId, int offset, int numberOfRows) throws DAOException;

    void enroll(int courseId, int userId) throws DAOException;

    void addStudent(Student student) throws DAOException;
    int getNoOfRecordsCourses();
}
