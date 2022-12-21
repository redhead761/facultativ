package com.epam.facultative.service.implementation;

import com.epam.facultative.daos.CourseDao;
import com.epam.facultative.daos.UserDao;
import com.epam.facultative.dto.Converter;
import com.epam.facultative.dto.CourseDTO;
import com.epam.facultative.dto.UserDTO;
import com.epam.facultative.entity.Course;
import com.epam.facultative.entity.Role;
import com.epam.facultative.entity.User;
import com.epam.facultative.exception.DAOException;
import com.epam.facultative.exception.ServiceException;
import com.epam.facultative.service.TeacherService;

import java.util.ArrayList;
import java.util.List;

public class TeacherServiceImpl implements TeacherService {
    private final CourseDao courseDao;
    private final UserDao userDao;
    private final Converter converter;

    public TeacherServiceImpl(CourseDao courseDao, UserDao userDao) {
        this.courseDao = courseDao;
        this.userDao = userDao;
        this.converter = new Converter();
    }

    @Override
    public void grading(int courseId, int userId, int grade) throws ServiceException {
        try {
            courseDao.updateUsersCourse(courseId, userId, grade);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<UserDTO> getStudentsByCourse(int courseId) throws ServiceException {
        List<UserDTO> students = new ArrayList<>();
        List<User> users;
        try {
            users = userDao.getUsersByCourse(courseId);
            for (User user : users) {
                if (user.getRole().equals(Role.STUDENT))
                    students.add(converter.userToDTO(user));
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
            courses = courseDao.getByUser(teacherId, offset, numberOfRows);
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
