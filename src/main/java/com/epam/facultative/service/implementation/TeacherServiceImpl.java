package com.epam.facultative.service.implementation;

import com.epam.facultative.data_layer.daos.CourseDao;
import com.epam.facultative.data_layer.daos.StudentDao;
import com.epam.facultative.data_layer.entities.Student;
import com.epam.facultative.dto.CourseDTO;
import com.epam.facultative.data_layer.entities.Course;
import com.epam.facultative.dto.StudentDTO;
import com.epam.facultative.exception.DAOException;
import com.epam.facultative.exception.ServiceException;
import com.epam.facultative.service.TeacherService;

import java.util.List;
import java.util.Map;

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
    public Map.Entry<Integer, List<StudentDTO>> getStudentsByCourse(int courseId, int offset, int numberOfRows) throws ServiceException {
        try {
            Map.Entry<Integer, List<Student>> studentsWithRows = studentDao.getStudentsByCourse(courseId, offset, numberOfRows);
            List<StudentDTO> students = prepareStudents(studentsWithRows.getValue());
            return Map.entry(studentsWithRows.getKey(), students);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Map.Entry<Integer, List<CourseDTO>> getTeacherCourses(int teacherId, int offset, int numberOfRows) throws ServiceException {
        try {
            Map.Entry<Integer, List<Course>> coursesWithRows = courseDao.getByTeacher(teacherId, offset, numberOfRows);
            List<CourseDTO> courses = prepareCourses(coursesWithRows.getValue());
            return Map.entry(coursesWithRows.getKey(), courses);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

}