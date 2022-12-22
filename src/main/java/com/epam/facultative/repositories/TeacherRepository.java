package com.epam.facultative.repositories;

import com.epam.facultative.entities.Course;
import com.epam.facultative.entities.Student;
import com.epam.facultative.exception.DAOException;

import java.util.List;

public interface TeacherRepository {
    void grading(int courseId, int userId, int grade) throws DAOException;

    List<Student> getStudentsByCourse(int courseId,int offset, int numberOfRows) throws DAOException;

    List<Course> getTeacherCourses(int teacherId, int offset, int numberOfRows) throws DAOException;

    int getNoOfRecordsCourses();

    int getNoOfRecordsStudents();
}
