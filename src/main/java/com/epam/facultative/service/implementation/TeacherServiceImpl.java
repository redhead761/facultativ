package com.epam.facultative.service.implementation;

import com.epam.facultative.daos.CourseDao;
import com.epam.facultative.daos.StudentDao;
import com.epam.facultative.dto.Converter;
import com.epam.facultative.dto.CourseDTO;
import com.epam.facultative.dto.StudentDTO;
import com.epam.facultative.entities.Course;
import com.epam.facultative.entities.Student;
import com.epam.facultative.exception.DAOException;
import com.epam.facultative.exception.ServiceException;
import com.epam.facultative.service.TeacherService;

import java.util.ArrayList;
import java.util.List;

public class TeacherServiceImpl implements TeacherService {
    private final CourseDao courseDao;
    private final Converter converter;
    private final StudentDao studentDao;

    public TeacherServiceImpl(CourseDao courseDao, StudentDao studentDao) {
        this.courseDao = courseDao;
        this.studentDao = studentDao;
        this.converter = new Converter();
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
            return prepareStudent(studentDao.getStudentsByCourse(courseId, offset, numberOfRows));
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<CourseDTO> getTeacherCourses(int teacherId, int offset, int numberOfRows) throws ServiceException {
        try {
            return prepareCourses(courseDao.getByTeacher(teacherId, offset, numberOfRows));
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public int getNoOfRecordsCourses() {
        return courseDao.getNoOfRecords();
    }

    private List<StudentDTO> prepareStudent(List<Student> students) {
        List<StudentDTO> result = new ArrayList<>();
        for (Student student : students) {
            result.add(converter.studentToDTO(student));
        }
        return result;
    }

    private List<CourseDTO> prepareCourses(List<Course> courses) {
        List<CourseDTO> coursesDTO = new ArrayList<>();
        for (Course course : courses) {
            coursesDTO.add(converter.courseToDTO(course));
        }
        return coursesDTO;
    }
}