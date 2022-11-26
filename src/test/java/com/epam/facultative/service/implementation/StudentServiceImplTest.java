package com.epam.facultative.service.implementation;

import com.epam.facultative.daos.CourseDao;
import com.epam.facultative.daos.UserDao;
import com.epam.facultative.entity.User;
import com.epam.facultative.exception.DAOException;
import com.epam.facultative.exception.ValidateException;
import com.epam.facultative.service.StudentService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class StudentServiceImplTest {

    private final CourseDao courseDao = mock(CourseDao.class);
    private final UserDao userDao = mock(UserDao.class);
    private final StudentService studentService = new StudentServiceImpl(courseDao, userDao);
    private final Preparation preparation = new Preparation();

    @Test
    void correctAddStudent() throws DAOException {
        doNothing().when(userDao).add(isA(User.class));
        assertDoesNotThrow(() -> studentService.addStudent(preparation.getTestUser()));
    }

    @Test
    void addStudentByWrongLogin() throws DAOException {
        doNothing().when(userDao).add(isA(User.class));
        User user = preparation.getTestUser();
        user.setLogin("login with space");
        assertThrows(ValidateException.class, () -> studentService.addStudent(user));

        user.setLogin("-~`!@#$^&*()={}");
        assertThrows(ValidateException.class, () -> studentService.addStudent(user));

        user.setLogin("s");
        assertThrows(ValidateException.class, () -> studentService.addStudent(user));

        user.setLogin("loginBiggestSixteenthWords");
        assertThrows(ValidateException.class, () -> studentService.addStudent(user));
    }

    @Test
    void addStudentByWrongPassword() throws DAOException {
        doNothing().when(userDao).add(isA(User.class));
        User user = preparation.getTestUser();
        user.setPassword("password space");
        assertThrows(ValidateException.class, () -> studentService.addStudent(user));

        user.setPassword("withoutDigits");
        assertThrows(ValidateException.class, () -> studentService.addStudent(user));

        user.setPassword("withoutcamel1");
        assertThrows(ValidateException.class, () -> studentService.addStudent(user));

        user.setPassword("ONLYCAMEL1");
        assertThrows(ValidateException.class, () -> studentService.addStudent(user));

        user.setPassword("s");
        assertThrows(ValidateException.class, () -> studentService.addStudent(user));

        user.setPassword("passwordBiggestEighteenthWords12345");
        assertThrows(ValidateException.class, () -> studentService.addStudent(user));
    }

    @Test
    void addStudentByWrongNameAndSurname() throws DAOException {
        doNothing().when(userDao).add(isA(User.class));
        User user = preparation.getTestUser();
        user.setName("name with space");
        user.setSurname("surname with space");
        assertThrows(ValidateException.class, () -> studentService.addStudent(user));

        user.setName("nameWith123");
        user.setSurname("surnameWith123");
        assertThrows(ValidateException.class, () -> studentService.addStudent(user));

        user.setName("nameWith!@#$%");
        user.setSurname("surnameWith!@##$%");
        assertThrows(ValidateException.class, () -> studentService.addStudent(user));

        user.setName("");
        user.setSurname("");
        assertThrows(ValidateException.class, () -> studentService.addStudent(user));

        user.setName("nameBiggestThenThirtyWordsThirtyWords");
        user.setSurname("surnameBiggestThenThirtyWordsThirtyWords");
        assertThrows(ValidateException.class, () -> studentService.addStudent(user));
    }

    @Test
    void addStudentByWrongEmail() throws DAOException {
        doNothing().when(userDao).add(isA(User.class));
        User user = preparation.getTestUser();
        user.setEmail("email with space@test.com");
        assertThrows(ValidateException.class, () -> studentService.addStudent(user));

        user.setEmail("emailWith!@#$%^&*@test.com");
        assertThrows(ValidateException.class, () -> studentService.addStudent(user));

        user.setEmail("emailWithoutDomen");
        assertThrows(ValidateException.class, () -> studentService.addStudent(user));

        user.setEmail("@test.com");
        assertThrows(ValidateException.class, () -> studentService.addStudent(user));
    }
//
//    @Test
//    void getCoursesByStudent() {
//
//    }
//
//    @Test
//    void getCoursesComingSoon() {
//    }
//
//    @Test
//    void getCoursesInProgress() {
//    }
//
//    @Test
//    void getCoursesCompleted() {
//    }
//
//    @Test
//    void enroll() {
//    }


}