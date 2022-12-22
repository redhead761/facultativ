package com.epam.facultative.repositories.implementation;

import com.epam.facultative.daos.CourseDao;
import com.epam.facultative.daos.StudentDao;
import com.epam.facultative.daos.TeacherDao;
import com.epam.facultative.daos.UserDao;
import com.epam.facultative.entities.Course;
import com.epam.facultative.entities.Student;
import com.epam.facultative.entities.User;
import com.epam.facultative.exception.DAOException;
import com.epam.facultative.repositories.TeacherRepository;

import java.util.List;

public class TeacherRepositoryImpl implements TeacherRepository {
    private final CourseDao courseDao;
    private final TeacherDao teacherDao;
    private final StudentDao studentDao;

    public TeacherRepositoryImpl(CourseDao courseDao, TeacherDao teacherDao, StudentDao studentDao) {
        this.courseDao = courseDao;
        this.teacherDao = teacherDao;
        this.studentDao = studentDao;
    }

    @Override
    public void grading(int courseId, int userId, int grade) throws DAOException {
        teacherDao.updateJournal(courseId, userId, grade);
    }

    @Override
    public List<Student> getStudentsByCourse(int courseId, int offset, int numberOfRows) throws DAOException {
        return studentDao.getStudentsByCourse(courseId, offset, numberOfRows);
    }

    @Override
    public List<Course> getTeacherCourses(int teacherId, int offset, int numberOfRows) throws DAOException {
        return courseDao.getByUser(teacherId, offset, numberOfRows);
    }

    @Override
    public int getNoOfRecordsCourses() {
        return courseDao.getNoOfRecords();
    }

    @Override
    public int getNoOfRecordsStudents() {
        return studentDao.getNoOfRecords();
    }
}
