package com.epam.facultative.repositories.implementation;

import com.epam.facultative.daos.*;
import com.epam.facultative.entities.*;
import com.epam.facultative.exception.DAOException;
import com.epam.facultative.repositories.AdminRepository;

import java.util.List;

public class AdminRepositoryImpl implements AdminRepository {
    private final CourseDao courseDao;
    private final CategoryDao categoryDao;
    private final TeacherDao teacherDao;
    private final StudentDao studentDao;
    private final UserDao userDao;

    public AdminRepositoryImpl(CourseDao courseDao, CategoryDao categoryDao, TeacherDao teacherDao, StudentDao studentDao, UserDao userDao) {
        this.courseDao = courseDao;
        this.categoryDao = categoryDao;
        this.teacherDao = teacherDao;
        this.studentDao = studentDao;
        this.userDao = userDao;
    }

    @Override
    public void addCourse(Course course) throws DAOException {
        courseDao.add(course);
    }

    @Override
    public void updateCourse(Course course) throws DAOException {
        courseDao.update(course);
    }

    @Override
    public void deleteCourse(int courseId) throws DAOException {
        courseDao.delete(courseId);
    }

    @Override
    public Course getCourse(int id) throws DAOException {
        return courseDao.getById(id);
    }

    @Override
    public void addCategory(Category category) throws DAOException {
        categoryDao.add(category);
    }

    @Override
    public void updateCategory(Category category) throws DAOException {
        categoryDao.update(category);
    }

    @Override
    public void deleteCategory(int categoryId) throws DAOException {
        categoryDao.delete(categoryId);
    }

    @Override
    public List<Category> getAllCategoriesPagination(int offset, int numberOfRows) throws DAOException {
        return categoryDao.getAllPagination(offset, numberOfRows);
    }

    @Override
    public Category getCategory(int id) throws DAOException {
        return categoryDao.getById(id);
    }

    @Override
    public int getNoOfRecordsCategories() {
        return categoryDao.getNoOfRecords();
    }

    @Override
    public void assigned(int courseId, int userId) throws DAOException {
        Course course = courseDao.getById(courseId);
        Teacher teacher = teacherDao.getById(userId);
        course.setTeacher(teacher);
        courseDao.update(course);
    }

    @Override
    public void blockStudent(int userId) throws DAOException {
        Student student = studentDao.getById(userId);
        student.setBlock(true);
        studentDao.update(student);
    }

    @Override
    public void unblockStudent(int userId) throws DAOException {
        Student student = studentDao.getById(userId);
        student.setBlock(false);
        studentDao.update(student);
    }

    @Override
    public void addTeacher(Teacher teacher) throws DAOException {
        userDao.add(teacher);
        User user = userDao.getByName(teacher.getLogin());
        teacher.setId(user.getId());
        teacherDao.add(teacher);
    }

    @Override
    public List<Student> getAllStudentsPagination(int offset, int noOfRecords) throws DAOException {
        return studentDao.getAllPagination(offset, noOfRecords);
    }

    @Override
    public List<Teacher> getAllTeachersPagination(int offset, int noOfRecords) throws DAOException {
        return teacherDao.getAllPagination(offset, noOfRecords);
    }

    @Override
    public Teacher getTeacher(int id) throws DAOException {
        return teacherDao.getById(id);
    }

    @Override
    public int getNoOfRecordsTeachers() {
        return teacherDao.getNoOfRecords();
    }

    @Override
    public int getNoOfRecordsStudents() {
        return studentDao.getNoOfRecords();
    }
}
