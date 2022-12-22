package com.epam.facultative.repositories.implementation;

import com.epam.facultative.daos.CategoryDao;
import com.epam.facultative.daos.CourseDao;
import com.epam.facultative.daos.TeacherDao;
import com.epam.facultative.daos.UserDao;
import com.epam.facultative.entities.Category;
import com.epam.facultative.entities.Course;
import com.epam.facultative.entities.Teacher;
import com.epam.facultative.entities.User;
import com.epam.facultative.exception.DAOException;
import com.epam.facultative.repositories.GeneralRepository;

import java.util.List;

public class GeneralRepositoryImpl implements GeneralRepository {
    private final CourseDao courseDao;
    private final CategoryDao categoryDao;
    private final UserDao userDao;
    private final TeacherDao teacherDao;

    public GeneralRepositoryImpl(CourseDao courseDao, UserDao userDao, CategoryDao categoryDao, TeacherDao teacherDao) {
        this.courseDao = courseDao;
        this.categoryDao = categoryDao;
        this.userDao = userDao;
        this.teacherDao = teacherDao;
    }

    @Override
    public User authorization(String login) throws DAOException {
        return userDao.getByName(login);
    }

    @Override
    public List<Course> getAllCourses(int offset, int numberOfRows) throws DAOException {
        return courseDao.getAllPagination(offset, numberOfRows);
    }

    @Override
    public List<Course> sortCoursesByAlphabet(int offset, int numberOfRows) throws DAOException {
        return courseDao.getAllSortPagination(offset, numberOfRows, "title");
    }

    @Override
    public List<Course> sortCoursesByAlphabetReverse(int offset, int numberOfRows) throws DAOException {
        return courseDao.getAllSortPagination(offset, numberOfRows, "title DESC");
    }

    @Override
    public List<Course> sortCoursesByDuration(int offset, int numberOfRows) throws DAOException {
        return courseDao.getAllSortPagination(offset, numberOfRows, "duration");
    }

    @Override
    public List<Course> sortCoursesBuAmountOfStudents(int offset, int numberOfRows) throws DAOException {
        return courseDao.getAllSortPagination(offset, numberOfRows, "amount_students");
    }

    @Override
    public List<Course> getCoursesByCategory(int categoryId, int offset, int numberOfRows) throws DAOException {
        return courseDao.getByCategory(categoryId, offset, numberOfRows);
    }

    @Override
    public List<Course> getCoursesByTeacher(int teacherId, int offset, int numberOfRows) throws DAOException {
        return courseDao.getByUser(teacherId, offset, numberOfRows);
    }

    @Override
    public List<Category> getAllCategories() throws DAOException {
        return categoryDao.getAll();
    }

    @Override
    public List<Teacher> getAllTeachers() throws DAOException {
        return teacherDao.getAll();
    }

    @Override
    public int getNoOfRecordsCourses() {
        return courseDao.getNoOfRecords();
    }
}
