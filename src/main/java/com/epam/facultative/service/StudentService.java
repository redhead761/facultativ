package com.epam.facultative.service;

import com.epam.facultative.dto.CourseDTO;
import com.epam.facultative.entity.User;

import java.util.List;

public interface StudentService {
    List<CourseDTO> getCoursesByStudent(int studentId);

    List<CourseDTO> getCoursesComingSoon(int studentId);

    List<CourseDTO> getCoursesInProgress(int studentId);

    List<CourseDTO> getCoursesCompleted(int studentId);

    void enroll(int courseId, int userId);

    void addStudent(User user);
}
