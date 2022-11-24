package com.epam.facultative.service.implementation;

import com.epam.facultative.daos.*;
import com.epam.facultative.dto.*;
import com.epam.facultative.entity.*;
import com.epam.facultative.service.GeneralService;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class GeneralServiceImpl implements GeneralService {
    private final CourseDao courseDao;
    private final UserDao userDao;
    private final Converter converter;

    public GeneralServiceImpl(CourseDao courseDao, UserDao userDao) {
        this.courseDao = courseDao;
        this.userDao = userDao;
        this.converter = new Converter();
    }

    @Override
    public List<CourseDTO> sortByAlphabet() {
        return prepareCourses(courseDao.getAll()).stream()
                .sorted(Comparator.comparing(CourseDTO::getTitle))
                .collect(Collectors.toList());
    }

    @Override
    public List<CourseDTO> sortByAlphabetReverse() {
        return prepareCourses(courseDao.getAll()).stream()
                .sorted((o1, o2) -> o2.getTitle().compareTo(o1.getTitle()))
                .collect(Collectors.toList());
    }

    @Override
    public List<CourseDTO> sortByDuration() {
        return prepareCourses(courseDao.getAll()).stream()
                .sorted(Comparator.comparing(CourseDTO::getDuration))
                .collect(Collectors.toList());
    }

    @Override
    public List<CourseDTO> sortByNumberStudentsOnCourse() {
        return prepareCourses(courseDao.getAll()).stream()
                .sorted(Comparator.comparing(CourseDTO::getAmountStudents))
                .collect(Collectors.toList());
    }

    @Override
    public List<CourseDTO> getCoursesByCategory(int categoryId) {
        return prepareCourses(courseDao.getByCategory(categoryId));
    }

    @Override
    public List<CourseDTO> getCoursesByTeacher(int teacherId) {
        return prepareCourses(courseDao.getByUser(teacherId));
    }

    private List<CourseDTO> prepareCourses(List<Course> courses) {
        List<CourseDTO> coursesDTO = new ArrayList<>();
        for (Course course : courses) {
            List<User> users = userDao.getUsersByCourse(course.getId());
            for (User user : users) {
                if (user.getRole().equals(Role.TEACHER)) {
                    UserDTO teacher = converter.userToDTO(user);
                    coursesDTO.add(converter.courseToDTO(course, teacher));
                }
            }
        }
        return coursesDTO;
    }

}
