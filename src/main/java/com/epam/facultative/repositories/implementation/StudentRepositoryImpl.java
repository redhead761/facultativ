package com.epam.facultative.repositories.implementation;

import com.epam.facultative.daos.CourseDao;
import com.epam.facultative.daos.StudentDao;
import com.epam.facultative.daos.UserDao;
import com.epam.facultative.entities.Course;
import com.epam.facultative.entities.Student;
import com.epam.facultative.entities.User;
import com.epam.facultative.exception.DAOException;
import com.epam.facultative.repositories.StudentRepository;

import java.util.List;

import static com.epam.facultative.entities.Status.*;

public class StudentRepositoryImpl implements StudentRepository {
    private final CourseDao courseDao;
    private final UserDao userDao;
    private final StudentDao studentDao;

    public StudentRepositoryImpl(CourseDao courseDao, UserDao userDao, StudentDao studentDao) {
        this.courseDao = courseDao;
        this.userDao = userDao;
        this.studentDao = studentDao;
    }

    @Override
    public List<Course> getCoursesByStudent(int studentId, int offset, int numberOfRows) throws DAOException {
        return courseDao.getByUser(studentId, offset, numberOfRows);
    }

    @Override
    public List<Course> getCoursesComingSoon(int studentId, int offset, int numberOfRows) throws DAOException {
        return courseDao.getByStatus(studentId, COMING_SOON, offset, numberOfRows);
    }

    @Override
    public List<Course> getCoursesInProgress(int studentId, int offset, int numberOfRows) throws DAOException {
        return courseDao.getByStatus(studentId, IN_PROCESS, offset, numberOfRows);

    }

    @Override
    public List<Course> getCoursesCompleted(int studentId, int offset, int numberOfRows) throws DAOException {
        return courseDao.getByStatus(studentId, COMPLETED, offset, numberOfRows);

    }

    @Override
    public void enroll(int courseId, int userId) throws DAOException {
        studentDao.addUserToCourse(courseId, userId);
        courseDao.addNumberStudentsToCourse(courseId);
    }

    @Override
    public void addStudent(Student student) throws DAOException {
        userDao.add(student);
        User user = userDao.getByName(student.getLogin());
        student.setId(user.getId());
        studentDao.add(student);
    }

    @Override
    public Student getByLogin(String login) throws DAOException {
        return studentDao.getByName(login);
    }

    @Override
    public int getNoOfRecordsCourses() {
        return courseDao.getNoOfRecords();
    }
}
