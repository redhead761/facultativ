package com.epam.facultative.service.implementation;

import com.epam.facultative.data_layer.daos.CourseDao;
import com.epam.facultative.data_layer.daos.StudentDao;
import com.epam.facultative.service.ServiceFactory;
import com.epam.facultative.service.TeacherService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;

class TeacherServiceImplTest {
    @Mock
    private CourseDao courseDao;
    @Mock
    private StudentDao studentDao;

    TeacherService teacherService = new TeacherServiceImpl(courseDao, studentDao);

    @Test
    void grading() {

    }

    @Test
    void getStudentsByCourse() {
    }

    @Test
    void getTeacherCourses() {
    }

    @Test
    void getNoOfRecordsCourses() {
    }
}