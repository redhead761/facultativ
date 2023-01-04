package com.epam.facultative.service.implementation;

import com.epam.facultative.daos.CourseDao;
import com.epam.facultative.daos.StudentDao;
import com.epam.facultative.daos.UserDao;
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
    private final UserDao userDao;
    private final Converter converter;
    private final StudentDao studentDao;

    public TeacherServiceImpl(CourseDao courseDao, UserDao userDao, StudentDao studentDao) {
        this.courseDao = courseDao;
        this.userDao = userDao;
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
        List<StudentDTO> students = new ArrayList<>();
        List<Student> users;
        try {
            users = studentDao.getStudentsByCourse(courseId, offset, numberOfRows);
            for (Student student : users) {
                students.add(converter.studentToDTO(student));
            }
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return students;
    }

    @Override
    public List<CourseDTO> getTeacherCourses(int teacherId, int offset, int numberOfRows) throws ServiceException {
        List<CourseDTO> coursesDTO = new ArrayList<>();
        List<Course> courses;
        try {
            courses = courseDao.getByTeacher(teacherId, offset, numberOfRows);
            for (Course course : courses) {
                coursesDTO.add(converter.courseToDTO(course));
            }
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return coursesDTO;
    }

    @Override
    public int getNoOfRecordsCourses() {
        return courseDao.getNoOfRecords();
    }
}