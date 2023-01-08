package com.epam.facultative.service.implementation;

import com.epam.facultative.data_layer.daos.CourseDao;
import com.epam.facultative.data_layer.daos.StudentDao;
import com.epam.facultative.dto.CourseDTO;
import com.epam.facultative.dto.StudentDTO;
import com.epam.facultative.data_layer.entities.Course;
import com.epam.facultative.data_layer.entities.Student;
import com.epam.facultative.exception.DAOException;
import com.epam.facultative.exception.ServiceException;
import com.epam.facultative.service.TeacherService;

import java.util.ArrayList;
import java.util.List;

import static com.epam.facultative.dto.Converter.*;

public class TeacherServiceImpl implements TeacherService {
    private final CourseDao courseDao;
    private final StudentDao studentDao;

    public TeacherServiceImpl(CourseDao courseDao, StudentDao studentDao) {
        this.courseDao = courseDao;
        this.studentDao = studentDao;
    }

    @Override
    public void grading(int courseId, int userId, int grade) throws ServiceException {
        try {
            courseDao.updateJournal(courseId, userId, grade);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<StudentDTO> getStudentsByCourse(int courseId, int offset, int numberOfRows) throws ServiceException {
        try {
            List<Student> students = studentDao.getStudentsByCourse(courseId, offset, numberOfRows);
            return prepareStudents(students);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<CourseDTO> getTeacherCourses(int teacherId, int offset, int numberOfRows) throws ServiceException {
        try {
            List<Course> courses = courseDao.getByTeacher(teacherId, offset, numberOfRows);
            return prepareCourses(courses);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public int getNoOfRecordsCourses() {
        return courseDao.getNoOfRecords();
    }


}