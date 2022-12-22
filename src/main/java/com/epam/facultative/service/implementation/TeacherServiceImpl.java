package com.epam.facultative.service.implementation;

import com.epam.facultative.dto.Converter;
import com.epam.facultative.dto.CourseDTO;
import com.epam.facultative.dto.UserDTO;
import com.epam.facultative.entities.Course;
import com.epam.facultative.entities.Role;
import com.epam.facultative.entities.Student;
import com.epam.facultative.entities.User;
import com.epam.facultative.exception.DAOException;
import com.epam.facultative.exception.ServiceException;
import com.epam.facultative.repositories.TeacherRepository;
import com.epam.facultative.service.TeacherService;

import java.util.ArrayList;
import java.util.List;

public class TeacherServiceImpl implements TeacherService {
    private final TeacherRepository teacherRepository;
    private final Converter converter;

    public TeacherServiceImpl(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
        this.converter = new Converter();
    }

    @Override
    public void grading(int courseId, int userId, int grade) throws ServiceException {
        try {
            teacherRepository.grading(courseId, userId, grade);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<UserDTO> getStudentsByCourse(int courseId, int offset, int numberOfRows) throws ServiceException {
        List<UserDTO> students = new ArrayList<>();
        List<Student> users;
        try {
            users = teacherRepository.getStudentsByCourse(courseId, offset, numberOfRows);
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
            courses = teacherRepository.getTeacherCourses(teacherId, offset, numberOfRows);
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
        return teacherRepository.getNoOfRecordsCourses();
    }
}
