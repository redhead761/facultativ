//package com.epam.facultative.service.implementation;
//
//import com.epam.facultative.daos.CategoryDao;
//import com.epam.facultative.daos.CourseDao;
//import com.epam.facultative.daos.UserDao;
//import com.epam.facultative.entities.Category;
//import com.epam.facultative.entities.Course;
//import com.epam.facultative.entities.User;
//import com.epam.facultative.exception.DAOException;
//import com.epam.facultative.exception.ValidateException;
//import com.epam.facultative.service.AdminService;
//import org.junit.jupiter.api.Test;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//class AdminServiceImplTest {
//
//    private final CourseDao courseDao = mock(CourseDao.class);
//    private final CategoryDao categoryDao = mock(CategoryDao.class);
//    private final UserDao userDao = mock(UserDao.class);
//    private final AdminService adminService = new AdminServiceImpl(courseDao, categoryDao, userDao);
//    private final Preparation preparation = new Preparation();
//
//    @Test
//    void correctAddCourse() throws DAOException {
//        doNothing().when(courseDao).add(isA(Course.class));
//        assertDoesNotThrow(() -> adminService.addCourse(preparation.getTestCourse()));
//    }
//
//    @Test
//    void addCourseByWrongTitle() throws DAOException {
//        doNothing().when(courseDao).add(isA(Course.class));
//        Course testCourse = preparation.getTestCourse();
//        testCourse.setTitle("Title with !@$%^&*");
//        assertThrows(ValidateException.class, () -> adminService.addCourse(testCourse));
//
//        testCourse.setTitle("");
//        assertThrows(ValidateException.class, () -> adminService.addCourse(testCourse));
//
//        testCourse.setTitle("Title biggest then thirty words");
//        assertThrows(ValidateException.class, () -> adminService.addCourse(testCourse));
//    }
//
//    @Test
//    void addCourseByWrongDescription() throws DAOException {
//        doNothing().when(courseDao).add(isA(Course.class));
//        Course testCourse = preparation.getTestCourse();
//        testCourse.setTitle("Title biggest then two hundred words 12345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234");
//        assertThrows(ValidateException.class, () -> adminService.addCourse(testCourse));
//    }
//
//    @Test
//    void addCourseByWrongDuration() throws DAOException {
//        doNothing().when(courseDao).add(isA(Course.class));
//        Course testCourse = preparation.getTestCourse();
//        testCourse.setDuration(-1);
//        assertThrows(ValidateException.class, () -> adminService.addCourse(testCourse));
//
//        testCourse.setDuration(0);
//        assertThrows(ValidateException.class, () -> adminService.addCourse(testCourse));
//    }
//
//    @Test
//    void correctAddCategory() throws DAOException {
//        doNothing().when(categoryDao).add(isA(Category.class));
//        assertDoesNotThrow(() -> adminService.addCategory(preparation.getTestCategory()));
//    }
//
//    @Test
//    void addCategoryByWrongTitle() throws DAOException {
//        doNothing().when(categoryDao).add(isA(Category.class));
//        Category testCategory = preparation.getTestCategory();
//        testCategory.setTitle("Title with !@$%^&*");
//        assertThrows(ValidateException.class, () -> adminService.addCategory(testCategory));
//
//        testCategory.setTitle("");
//        assertThrows(ValidateException.class, () -> adminService.addCategory(testCategory));
//
//        testCategory.setTitle("Title biggest then thirty words");
//        assertThrows(ValidateException.class, () -> adminService.addCategory(testCategory));
//    }
//
//    @Test
//    void addCategoryByWrongDescription() throws DAOException {
//        doNothing().when(categoryDao).add(isA(Category.class));
//        Category testCategory = preparation.getTestCategory();
//        testCategory.setTitle("Title biggest then two hundred words 12345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234");
//        assertThrows(ValidateException.class, () -> adminService.addCategory(testCategory));
//    }
//
//    @Test
//    void correctAddTeacher() throws DAOException {
//        doNothing().when(userDao).add(isA(User.class));
//        assertDoesNotThrow(() -> adminService.addTeacher(preparation.getTestUser()));
//    }
//
//    @Test
//    void addTeacherByWrongLogin() throws DAOException {
//        doNothing().when(userDao).add(isA(User.class));
//        User user = preparation.getTestUser();
//        user.setLogin("login with space");
//        assertThrows(ValidateException.class, () -> adminService.addTeacher(user));
//
//        user.setLogin("-~`!@#$^&*()={}");
//        assertThrows(ValidateException.class, () -> adminService.addTeacher(user));
//
//        user.setLogin("s");
//        assertThrows(ValidateException.class, () -> adminService.addTeacher(user));
//
//        user.setLogin("loginBiggestSixteenthWords");
//        assertThrows(ValidateException.class, () -> adminService.addTeacher(user));
//    }
//
//
//    @Test
//    void addTeacherByWrongPassword() throws DAOException {
//        doNothing().when(userDao).add(isA(User.class));
//        User user = preparation.getTestUser();
//        user.setPassword("password space");
//        assertThrows(ValidateException.class, () -> adminService.addTeacher(user));
//
//        user.setPassword("withoutDigits");
//        assertThrows(ValidateException.class, () -> adminService.addTeacher(user));
//
//        user.setPassword("withoutcamel1");
//        assertThrows(ValidateException.class, () -> adminService.addTeacher(user));
//
//        user.setPassword("ONLYCAMEL1");
//        assertThrows(ValidateException.class, () -> adminService.addTeacher(user));
//
//        user.setPassword("s");
//        assertThrows(ValidateException.class, () -> adminService.addTeacher(user));
//
//        user.setPassword("passwordBiggestEighteenthWords12345");
//        assertThrows(ValidateException.class, () -> adminService.addTeacher(user));
//    }
//
//    @Test
//    void addTeacherByWrongNameAndSurname() throws DAOException {
//        doNothing().when(userDao).add(isA(User.class));
//        User user = preparation.getTestUser();
//        user.setName("name with space");
//        user.setSurname("surname with space");
//        assertThrows(ValidateException.class, () -> adminService.addTeacher(user));
//
//        user.setName("nameWith123");
//        user.setSurname("surnameWith123");
//        assertThrows(ValidateException.class, () -> adminService.addTeacher(user));
//
//        user.setName("nameWith!@#$%");
//        user.setSurname("surnameWith!@##$%");
//        assertThrows(ValidateException.class, () -> adminService.addTeacher(user));
//
//        user.setName("");
//        user.setSurname("");
//        assertThrows(ValidateException.class, () -> adminService.addTeacher(user));
//
//        user.setName("nameBiggestThenThirtyWordsThirtyWords");
//        user.setSurname("surnameBiggestThenThirtyWordsThirtyWords");
//        assertThrows(ValidateException.class, () -> adminService.addTeacher(user));
//    }
//
//    @Test
//    void addTeacherByWrongEmail() throws DAOException {
//        doNothing().when(userDao).add(isA(User.class));
//        User user = preparation.getTestUser();
//        user.setEmail("email with space@test.com");
//        assertThrows(ValidateException.class, () -> adminService.addTeacher(user));
//
//        user.setEmail("emailWith!@#$%^&*@test.com");
//        assertThrows(ValidateException.class, () -> adminService.addTeacher(user));
//
//        user.setEmail("emailWithoutDomen");
//        assertThrows(ValidateException.class, () -> adminService.addTeacher(user));
//
//        user.setEmail("@test.com");
//        assertThrows(ValidateException.class, () -> adminService.addTeacher(user));
//    }
//}