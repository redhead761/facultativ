package com.epam.facultative.service;

import com.epam.facultative.dto.*;

import java.util.List;

public interface TeacherService {
    void grading(int courseId, int userId, int grade);

    List<UserDTO> getStudentsByCourse(int courseId);

    List<CourseDTO> getTeacherCourses(int teacherId);
}
