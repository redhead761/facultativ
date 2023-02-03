package com.epam.facultative.model.service.implementation;

import com.epam.facultative.controller.app_context.AppContext;
import com.epam.facultative.model.dto.StudentDTO;
import com.epam.facultative.model.entities.Student;
import com.epam.facultative.model.exception.DAOException;
import com.epam.facultative.model.exception.ServiceException;
import com.epam.facultative.model.exception.ValidateException;
import com.epam.facultative.model.dao.CategoryDao;
import com.epam.facultative.model.dao.CourseDao;
import com.epam.facultative.model.dao.StudentDao;
import com.epam.facultative.model.dao.TeacherDao;
import com.epam.facultative.model.entities.Category;
import com.epam.facultative.model.entities.Course;
import com.epam.facultative.model.entities.Teacher;
import com.epam.facultative.model.dto.CategoryDTO;
import com.epam.facultative.model.dto.CourseDTO;
import com.epam.facultative.model.dto.TeacherDTO;
import com.epam.facultative.model.service.AdminService;
import com.epam.facultative.model.utils.converter.Converter;
import com.epam.facultative.model.utils.hash_password.HashPassword;
import com.epam.facultative.model.utils.validator.Validator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;


class AdminServiceImplTest {
    private final CourseDao courseDao = mock(CourseDao.class);
    private final CategoryDao categoryDao = mock(CategoryDao.class);
    private final StudentDao studentDao = mock(StudentDao.class);
    private final TeacherDao teacherDao = mock(TeacherDao.class);
    private final AppContext appContext = mock(AppContext.class);
    AdminService adminService = new AdminServiceImpl(courseDao, categoryDao, studentDao, teacherDao);
    private static Category category;
    private static Teacher teacher;
    private static CourseDTO courseDTO;
    private static Course course;
    private static CategoryDTO categoryDTO;
    private static TeacherDTO teacherDTO;
    private static Map.Entry<Integer, List<Student>> students;
    private static Map.Entry<Integer, List<StudentDTO>> studentDTOS;
    private final String TEST_PASSWORD = "Test1234";
    private final String TEST_PARAM = "LIMIT 1,5";

    @BeforeAll
    static void beforeAll() {
        TestServiceUtil testServiceUtil = new TestServiceUtil();
        course = testServiceUtil.getCourse();
        category = testServiceUtil.getCategory();
        teacher = testServiceUtil.getTeacher();
        courseDTO = testServiceUtil.getCourseDTO();
        categoryDTO = testServiceUtil.getCategoryDTO();
        teacherDTO = testServiceUtil.getTeacherDTO();
        students = testServiceUtil.getStudents();
        studentDTOS = testServiceUtil.getStudentDTOS();
    }

    @Test
    void addCourseSuccessful() throws DAOException, ValidateException {
        try (MockedStatic<Validator> validator = mockStatic(Validator.class);
             MockedStatic<Converter> converter = mockStatic(Converter.class)) {
            validator.when(() -> Validator.validateTitle(anyString())).thenAnswer(invocation -> null);
            validator.when(() -> Validator.validateDescription(anyString())).thenAnswer(invocation -> null);
            validator.when(() -> Validator.validateStartDate(isA(LocalDate.class))).thenAnswer(invocation -> null);
            converter.when(() -> Converter.convertDTOToCourse(courseDTO)).thenReturn(course);
            doNothing().when(courseDao).add(course);
            assertDoesNotThrow(() -> adminService.addCourse(courseDTO));
        }
    }

    @Test
    void addCourseWithInvalidateTitle() throws DAOException, ValidateException {
        try (MockedStatic<Validator> validator = mockStatic(Validator.class);
             MockedStatic<Converter> converter = mockStatic(Converter.class)) {
            validator.when(() -> Validator.validateTitle(anyString())).thenThrow(ValidateException.class);
            validator.when(() -> Validator.validateDescription(anyString())).thenAnswer(invocation -> null);
            validator.when(() -> Validator.validateStartDate(isA(LocalDate.class))).thenAnswer(invocation -> null);
            converter.when(() -> Converter.convertDTOToCourse(courseDTO)).thenReturn(course);
            doNothing().when(courseDao).add(course);
            assertThrows(ValidateException.class, () -> adminService.addCourse(courseDTO));
        }
    }

    @Test
    void addCourseWithInvalidateDescription() throws DAOException, ValidateException {
        try (MockedStatic<Validator> validator = mockStatic(Validator.class);
             MockedStatic<Converter> converter = mockStatic(Converter.class)) {
            validator.when(() -> Validator.validateTitle(anyString())).thenAnswer(invocation -> null);
            validator.when(() -> Validator.validateDescription(anyString())).thenThrow(ValidateException.class);
            validator.when(() -> Validator.validateStartDate(isA(LocalDate.class))).thenAnswer(invocation -> null);
            converter.when(() -> Converter.convertDTOToCourse(courseDTO)).thenReturn(course);
            doNothing().when(courseDao).add(course);
            assertThrows(ValidateException.class, () -> adminService.addCourse(courseDTO));
        }
    }

    @Test
    void addCourseWithInvalidateDate() throws DAOException, ValidateException {
        try (MockedStatic<Validator> validator = mockStatic(Validator.class);
             MockedStatic<Converter> converter = mockStatic(Converter.class)) {
            validator.when(() -> Validator.validateTitle(anyString())).thenAnswer(invocation -> null);
            validator.when(() -> Validator.validateDescription(anyString())).thenAnswer(invocation -> null);
            validator.when(() -> Validator.validateStartDate(isA(LocalDate.class))).thenThrow(ValidateException.class);
            converter.when(() -> Converter.convertDTOToCourse(courseDTO)).thenReturn(course);
            doNothing().when(courseDao).add(course);
            assertThrows(ValidateException.class, () -> adminService.addCourse(courseDTO));
        }
    }

    @Test
    void addCourseWithNotUniqueTitle() throws DAOException, ValidateException {
        try (MockedStatic<Validator> validator = mockStatic(Validator.class);
             MockedStatic<Converter> converter = mockStatic(Converter.class)) {
            validator.when(() -> Validator.validateTitle(anyString())).thenAnswer(invocation -> null);
            validator.when(() -> Validator.validateDescription(anyString())).thenAnswer(invocation -> null);
            validator.when(() -> Validator.validateStartDate(isA(LocalDate.class))).thenAnswer(invocation -> null);
            converter.when(() -> Converter.convertDTOToCourse(courseDTO)).thenReturn(course);
            doThrow(ValidateException.class).when(courseDao).add(course);
            assertThrows(ValidateException.class, () -> adminService.addCourse(courseDTO));
        }
    }

    @Test
    void addCourseFailed() throws DAOException, ValidateException {
        try (MockedStatic<Validator> validator = mockStatic(Validator.class);
             MockedStatic<Converter> converter = mockStatic(Converter.class)) {
            validator.when(() -> Validator.validateTitle(anyString())).thenAnswer(invocation -> null);
            validator.when(() -> Validator.validateDescription(anyString())).thenAnswer(invocation -> null);
            validator.when(() -> Validator.validateStartDate(isA(LocalDate.class))).thenAnswer(invocation -> null);
            converter.when(() -> Converter.convertDTOToCourse(courseDTO)).thenReturn(course);
            doThrow(DAOException.class).when(courseDao).add(course);
            assertThrows(ServiceException.class, () -> adminService.addCourse(courseDTO));
        }
    }

    @Test
    void updateCourseSuccessful() throws DAOException, ValidateException {
        try (MockedStatic<Validator> validator = mockStatic(Validator.class);
             MockedStatic<Converter> converter = mockStatic(Converter.class)) {
            validator.when(() -> Validator.validateTitle(anyString())).thenAnswer(invocation -> null);
            validator.when(() -> Validator.validateDescription(anyString())).thenAnswer(invocation -> null);
            validator.when(() -> Validator.validateStartDate(isA(LocalDate.class))).thenAnswer(invocation -> null);
            converter.when(() -> Converter.convertDTOToCourse(courseDTO)).thenReturn(course);
            doNothing().when(courseDao).update(course);
            assertDoesNotThrow(() -> adminService.updateCourse(courseDTO));
        }
    }

    @Test
    void updateCourseWithInvalidateTitle() throws DAOException, ValidateException {
        try (MockedStatic<Validator> validator = mockStatic(Validator.class);
             MockedStatic<Converter> converter = mockStatic(Converter.class)) {
            validator.when(() -> Validator.validateTitle(anyString())).thenThrow(ValidateException.class);
            validator.when(() -> Validator.validateDescription(anyString())).thenAnswer(invocation -> null);
            validator.when(() -> Validator.validateStartDate(isA(LocalDate.class))).thenAnswer(invocation -> null);
            converter.when(() -> Converter.convertDTOToCourse(courseDTO)).thenReturn(course);
            doNothing().when(courseDao).update(course);
            assertThrows(ValidateException.class, () -> adminService.updateCourse(courseDTO));
        }
    }

    @Test
    void updateCourseWithInvalidateDescription() throws DAOException, ValidateException {
        try (MockedStatic<Validator> validator = mockStatic(Validator.class);
             MockedStatic<Converter> converter = mockStatic(Converter.class)) {
            validator.when(() -> Validator.validateTitle(anyString())).thenAnswer(invocation -> null);
            validator.when(() -> Validator.validateDescription(anyString())).thenThrow(ValidateException.class);
            validator.when(() -> Validator.validateStartDate(isA(LocalDate.class))).thenAnswer(invocation -> null);
            converter.when(() -> Converter.convertDTOToCourse(courseDTO)).thenReturn(course);
            doNothing().when(courseDao).update(course);
            assertThrows(ValidateException.class, () -> adminService.updateCourse(courseDTO));
        }
    }

    @Test
    void updateCourseWithNotUniqueTitle() throws DAOException, ValidateException {
        try (MockedStatic<Validator> validator = mockStatic(Validator.class);
             MockedStatic<Converter> converter = mockStatic(Converter.class)) {
            validator.when(() -> Validator.validateTitle(anyString())).thenAnswer(invocation -> null);
            validator.when(() -> Validator.validateDescription(anyString())).thenAnswer(invocation -> null);
            validator.when(() -> Validator.validateStartDate(isA(LocalDate.class))).thenAnswer(invocation -> null);
            converter.when(() -> Converter.convertDTOToCourse(courseDTO)).thenReturn(course);
            doThrow(ValidateException.class).when(courseDao).update(course);
            assertThrows(ValidateException.class, () -> adminService.updateCourse(courseDTO));
        }
    }

    @Test
    void updateCourseFailed() throws DAOException, ValidateException {
        try (MockedStatic<Validator> validator = mockStatic(Validator.class);
             MockedStatic<Converter> converter = mockStatic(Converter.class)) {
            validator.when(() -> Validator.validateTitle(anyString())).thenAnswer(invocation -> null);
            validator.when(() -> Validator.validateDescription(anyString())).thenAnswer(invocation -> null);
            validator.when(() -> Validator.validateStartDate(isA(LocalDate.class))).thenAnswer(invocation -> null);
            converter.when(() -> Converter.convertDTOToCourse(courseDTO)).thenReturn(course);
            doThrow(DAOException.class).when(courseDao).update(course);
            assertThrows(ServiceException.class, () -> adminService.updateCourse(courseDTO));
        }
    }

    @Test
    void deleteCourseSuccessful() throws DAOException, ValidateException {
        doNothing().when(courseDao).delete(anyInt());
        assertDoesNotThrow(() -> adminService.deleteCourse(1));
    }

    @Test
    void deleteCourseWithIllegalArgument() throws DAOException, ValidateException {
        doThrow(DAOException.class).when(courseDao).delete(anyInt());
        assertThrows(ServiceException.class, () -> adminService.deleteCourse(-1));
    }

    @Test
    void addCategorySuccessful() throws DAOException, ValidateException {
        try (MockedStatic<Validator> validator = mockStatic(Validator.class);
             MockedStatic<Converter> converter = mockStatic(Converter.class)) {
            validator.when(() -> Validator.validateTitle(anyString())).thenAnswer(invocation -> null);
            validator.when(() -> Validator.validateDescription(anyString())).thenAnswer(invocation -> null);
            converter.when(() -> Converter.convertDTOToCategory(categoryDTO)).thenReturn(category);
            doNothing().when(categoryDao).add(category);
            assertDoesNotThrow(() -> adminService.addCategory(categoryDTO));
        }
    }

    @Test
    void addCategoryWithInvalidateTitle() throws DAOException, ValidateException {
        try (MockedStatic<Validator> validator = mockStatic(Validator.class);
             MockedStatic<Converter> converter = mockStatic(Converter.class)) {
            validator.when(() -> Validator.validateTitle(anyString())).thenThrow(ValidateException.class);
            validator.when(() -> Validator.validateDescription(anyString())).thenAnswer(invocation -> null);
            converter.when(() -> Converter.convertDTOToCategory(categoryDTO)).thenReturn(category);
            doNothing().when(categoryDao).add(category);
            assertThrows(ValidateException.class, () -> adminService.addCategory(categoryDTO));
        }
    }

    @Test
    void addCategoryWithInvalidateDescription() throws DAOException, ValidateException {
        try (MockedStatic<Validator> validator = mockStatic(Validator.class);
             MockedStatic<Converter> converter = mockStatic(Converter.class)) {
            validator.when(() -> Validator.validateTitle(anyString())).thenAnswer(invocation -> null);
            validator.when(() -> Validator.validateDescription(anyString())).thenThrow(ValidateException.class);
            converter.when(() -> Converter.convertDTOToCategory(categoryDTO)).thenReturn(category);
            doNothing().when(categoryDao).add(category);
            assertThrows(ValidateException.class, () -> adminService.addCategory(categoryDTO));
        }
    }


    @Test
    void addCategoryWithNotUniqueTitle() throws DAOException, ValidateException {
        try (MockedStatic<Validator> validator = mockStatic(Validator.class);
             MockedStatic<Converter> converter = mockStatic(Converter.class)) {
            validator.when(() -> Validator.validateTitle(anyString())).thenAnswer(invocation -> null);
            validator.when(() -> Validator.validateDescription(anyString())).thenAnswer(invocation -> null);
            converter.when(() -> Converter.convertDTOToCategory(categoryDTO)).thenReturn(category);
            doThrow(ValidateException.class).when(categoryDao).add(category);
            assertThrows(ValidateException.class, () -> adminService.addCategory(categoryDTO));
        }
    }

    @Test
    void addCategoryFailed() throws DAOException, ValidateException {
        try (MockedStatic<Validator> validator = mockStatic(Validator.class);
             MockedStatic<Converter> converter = mockStatic(Converter.class)) {
            validator.when(() -> Validator.validateTitle(anyString())).thenAnswer(invocation -> null);
            validator.when(() -> Validator.validateDescription(anyString())).thenAnswer(invocation -> null);
            converter.when(() -> Converter.convertDTOToCategory(categoryDTO)).thenReturn(category);
            doThrow(DAOException.class).when(categoryDao).add(category);
            assertThrows(ServiceException.class, () -> adminService.addCategory(categoryDTO));
        }
    }

    @Test
    void updateCategorySuccessful() throws DAOException, ValidateException {
        try (MockedStatic<Validator> validator = mockStatic(Validator.class);
             MockedStatic<Converter> converter = mockStatic(Converter.class)) {
            validator.when(() -> Validator.validateTitle(anyString())).thenAnswer(invocation -> null);
            validator.when(() -> Validator.validateDescription(anyString())).thenAnswer(invocation -> null);
            converter.when(() -> Converter.convertDTOToCategory(categoryDTO)).thenReturn(category);
            doNothing().when(categoryDao).update(category);
            assertDoesNotThrow(() -> adminService.updateCategory(categoryDTO));
        }
    }

    @Test
    void updateCategoryWithInvalidateTitle() throws DAOException, ValidateException {
        try (MockedStatic<Validator> validator = mockStatic(Validator.class);
             MockedStatic<Converter> converter = mockStatic(Converter.class)) {
            validator.when(() -> Validator.validateTitle(anyString())).thenThrow(ValidateException.class);
            validator.when(() -> Validator.validateDescription(anyString())).thenAnswer(invocation -> null);
            converter.when(() -> Converter.convertDTOToCategory(categoryDTO)).thenReturn(category);
            doNothing().when(categoryDao).update(category);
            assertThrows(ValidateException.class, () -> adminService.updateCategory(categoryDTO));
        }
    }

    @Test
    void updateCategoryWithInvalidateDescription() throws DAOException, ValidateException {
        try (MockedStatic<Validator> validator = mockStatic(Validator.class);
             MockedStatic<Converter> converter = mockStatic(Converter.class)) {
            validator.when(() -> Validator.validateTitle(anyString())).thenAnswer(invocation -> null);
            validator.when(() -> Validator.validateDescription(anyString())).thenThrow(ValidateException.class);
            converter.when(() -> Converter.convertDTOToCategory(categoryDTO)).thenReturn(category);
            doNothing().when(categoryDao).update(category);
            assertThrows(ValidateException.class, () -> adminService.updateCategory(categoryDTO));
        }
    }

    @Test
    void updateCategoryWithNotUniqueTitle() throws DAOException, ValidateException {
        try (MockedStatic<Validator> validator = mockStatic(Validator.class);
             MockedStatic<Converter> converter = mockStatic(Converter.class)) {
            validator.when(() -> Validator.validateTitle(anyString())).thenAnswer(invocation -> null);
            validator.when(() -> Validator.validateDescription(anyString())).thenThrow(ValidateException.class);
            converter.when(() -> Converter.convertDTOToCategory(categoryDTO)).thenReturn(category);
            doThrow(ValidateException.class).when(categoryDao).update(category);
            assertThrows(ValidateException.class, () -> adminService.updateCategory(categoryDTO));
        }
    }

    @Test
    void updateCategoryFailed() throws DAOException, ValidateException {
        try (MockedStatic<Validator> validator = mockStatic(Validator.class);
             MockedStatic<Converter> converter = mockStatic(Converter.class)) {
            validator.when(() -> Validator.validateTitle(anyString())).thenAnswer(invocation -> null);
            validator.when(() -> Validator.validateDescription(anyString())).thenAnswer(invocation -> null);
            converter.when(() -> Converter.convertDTOToCategory(categoryDTO)).thenReturn(category);
            doThrow(DAOException.class).when(categoryDao).update(category);
            assertThrows(ServiceException.class, () -> adminService.updateCategory(categoryDTO));
        }
    }

    @Test
    void deleteCategorySuccessful() throws DAOException, ValidateException {
        doNothing().when(categoryDao).delete(anyInt());
        assertDoesNotThrow(() -> adminService.deleteCategory(1));
    }

    @Test
    void deleteCategoryWithIllegalArgument() throws DAOException, ValidateException {
        doThrow(DAOException.class).when(categoryDao).delete(anyInt());
        assertThrows(ServiceException.class, () -> adminService.deleteCategory(-1));
    }

    @Test
    void blockStudentSuccessful() throws DAOException {
        doNothing().when(studentDao).updateBlock(anyInt(), anyBoolean());
        assertDoesNotThrow(() -> adminService.blockStudent(1));
    }

    @Test
    void blockStudentWithIllegalArgument() throws DAOException {
        doThrow(DAOException.class).when(studentDao).updateBlock(anyInt(), anyBoolean());
        assertThrows(ServiceException.class, () -> adminService.blockStudent(-1));
    }

    @Test
    void unblockStudentSuccessful() throws DAOException {
        doNothing().when(studentDao).updateBlock(anyInt(), anyBoolean());
        assertDoesNotThrow(() -> adminService.unblockStudent(1));
    }

    @Test
    void unblockStudentWithIllegalArgument() throws DAOException {
        doThrow(DAOException.class).when(studentDao).updateBlock(anyInt(), anyBoolean());
        assertThrows(ServiceException.class, () -> adminService.unblockStudent(-1));
    }

    @Test
    void addTeacherSuccessful() throws DAOException, ValidateException {
        try (MockedStatic<Validator> validator = mockStatic(Validator.class);
             MockedStatic<Converter> converter = mockStatic(Converter.class);
             MockedStatic<HashPassword> hashPassword = mockStatic(HashPassword.class)) {
            validator.when(() -> Validator.validateLogin(anyString())).thenAnswer(invocation -> null);
            validator.when(() -> Validator.validatePassword(anyString())).thenAnswer(invocation -> null);
            validator.when(() -> Validator.validateName(anyString())).thenAnswer(invocation -> null);
            validator.when(() -> Validator.validateEmail(anyString())).thenAnswer(invocation -> null);
            hashPassword.when(() -> HashPassword.encode(anyString())).thenReturn(TEST_PASSWORD);
            converter.when(() -> Converter.convertDTOToTeacher(teacherDTO)).thenReturn(teacher);
            doNothing().when(teacherDao).add(teacher);
            assertDoesNotThrow(() -> adminService.addTeacher(teacherDTO));
        }
    }

    @Test
    void addTeacherWithInvalidateLogin() throws DAOException, ValidateException {
        try (MockedStatic<Validator> validator = mockStatic(Validator.class);
             MockedStatic<Converter> converter = mockStatic(Converter.class);
             MockedStatic<HashPassword> hashPassword = mockStatic(HashPassword.class)) {
            validator.when(() -> Validator.validateLogin(anyString())).thenThrow(ValidateException.class);
            validator.when(() -> Validator.validatePassword(anyString())).thenAnswer(invocation -> null);
            validator.when(() -> Validator.validateName(anyString())).thenAnswer(invocation -> null);
            validator.when(() -> Validator.validateEmail(anyString())).thenAnswer(invocation -> null);
            hashPassword.when(() -> HashPassword.encode(anyString())).thenReturn(TEST_PASSWORD);
            converter.when(() -> Converter.convertDTOToTeacher(teacherDTO)).thenReturn(teacher);
            doNothing().when(teacherDao).add(teacher);
            assertThrows(ValidateException.class, () -> adminService.addTeacher(teacherDTO));
        }
    }

    @Test
    void addTeacherWithInvalidatePassword() throws DAOException, ValidateException {
        try (MockedStatic<Validator> validator = mockStatic(Validator.class);
             MockedStatic<Converter> converter = mockStatic(Converter.class);
             MockedStatic<HashPassword> hashPassword = mockStatic(HashPassword.class)) {
            validator.when(() -> Validator.validateLogin(anyString())).thenAnswer(invocation -> null);
            validator.when(() -> Validator.validatePassword(anyString())).thenThrow(ValidateException.class);
            validator.when(() -> Validator.validateName(anyString())).thenAnswer(invocation -> null);
            validator.when(() -> Validator.validateEmail(anyString())).thenAnswer(invocation -> null);
            hashPassword.when(() -> HashPassword.encode(anyString())).thenReturn(TEST_PASSWORD);
            converter.when(() -> Converter.convertDTOToTeacher(teacherDTO)).thenReturn(teacher);
            doNothing().when(teacherDao).add(teacher);
            assertThrows(ValidateException.class, () -> adminService.addTeacher(teacherDTO));
        }
    }

    @Test
    void addTeacherWithInvalidateName() throws DAOException, ValidateException {
        try (MockedStatic<Validator> validator = mockStatic(Validator.class);
             MockedStatic<Converter> converter = mockStatic(Converter.class);
             MockedStatic<HashPassword> hashPassword = mockStatic(HashPassword.class)) {
            validator.when(() -> Validator.validateLogin(anyString())).thenAnswer(invocation -> null);
            validator.when(() -> Validator.validatePassword(anyString())).thenAnswer(invocation -> null);
            validator.when(() -> Validator.validateName(anyString())).thenThrow(ValidateException.class);
            validator.when(() -> Validator.validateEmail(anyString())).thenAnswer(invocation -> null);
            hashPassword.when(() -> HashPassword.encode(anyString())).thenReturn(TEST_PASSWORD);
            converter.when(() -> Converter.convertDTOToTeacher(teacherDTO)).thenReturn(teacher);
            doNothing().when(teacherDao).add(teacher);
            assertThrows(ValidateException.class, () -> adminService.addTeacher(teacherDTO));
        }
    }

    @Test
    void addTeacherWithInvalidateEmail() throws DAOException, ValidateException {
        try (MockedStatic<Validator> validator = mockStatic(Validator.class);
             MockedStatic<Converter> converter = mockStatic(Converter.class);
             MockedStatic<HashPassword> hashPassword = mockStatic(HashPassword.class)) {
            validator.when(() -> Validator.validateLogin(anyString())).thenAnswer(invocation -> null);
            validator.when(() -> Validator.validatePassword(anyString())).thenAnswer(invocation -> null);
            validator.when(() -> Validator.validateName(anyString())).thenAnswer(invocation -> null);
            validator.when(() -> Validator.validateEmail(anyString())).thenThrow(ValidateException.class);
            hashPassword.when(() -> HashPassword.encode(anyString())).thenReturn(TEST_PASSWORD);
            converter.when(() -> Converter.convertDTOToTeacher(teacherDTO)).thenReturn(teacher);
            doNothing().when(teacherDao).add(teacher);
            assertThrows(ValidateException.class, () -> adminService.addTeacher(teacherDTO));
        }
    }

    @Test
    void addTeacherWithNotUniqueLogin() throws DAOException, ValidateException {
        try (MockedStatic<Validator> validator = mockStatic(Validator.class);
             MockedStatic<Converter> converter = mockStatic(Converter.class);
             MockedStatic<HashPassword> hashPassword = mockStatic(HashPassword.class)) {
            validator.when(() -> Validator.validateLogin(anyString())).thenAnswer(invocation -> null);
            validator.when(() -> Validator.validatePassword(anyString())).thenAnswer(invocation -> null);
            validator.when(() -> Validator.validateName(anyString())).thenAnswer(invocation -> null);
            validator.when(() -> Validator.validateEmail(anyString())).thenAnswer(invocation -> null);
            hashPassword.when(() -> HashPassword.encode(anyString())).thenReturn(TEST_PASSWORD);
            converter.when(() -> Converter.convertDTOToTeacher(teacherDTO)).thenReturn(teacher);
            doThrow(ValidateException.class).when(teacherDao).add(teacher);
            assertThrows(ValidateException.class, () -> adminService.addTeacher(teacherDTO));
        }
    }

    @Test
    void addTeacherFailed() throws DAOException, ValidateException {
        try (MockedStatic<Validator> validator = mockStatic(Validator.class);
             MockedStatic<Converter> converter = mockStatic(Converter.class);
             MockedStatic<HashPassword> hashPassword = mockStatic(HashPassword.class)) {
            validator.when(() -> Validator.validateLogin(anyString())).thenAnswer(invocation -> null);
            validator.when(() -> Validator.validatePassword(anyString())).thenAnswer(invocation -> null);
            validator.when(() -> Validator.validateName(anyString())).thenAnswer(invocation -> null);
            validator.when(() -> Validator.validateEmail(anyString())).thenAnswer(invocation -> null);
            hashPassword.when(() -> HashPassword.encode(anyString())).thenReturn(TEST_PASSWORD);
            converter.when(() -> Converter.convertDTOToTeacher(teacherDTO)).thenReturn(teacher);
            doThrow(DAOException.class).when(teacherDao).add(teacher);
            assertThrows(ServiceException.class, () -> adminService.addTeacher(teacherDTO));
        }
    }

    @Test
    void getStudentsSuccessful() throws DAOException, ServiceException {
        try (MockedStatic<Converter> converter = mockStatic(Converter.class)) {
            converter.when(() -> Converter.prepareStudents(students.getValue())).thenReturn(studentDTOS.getValue());
            when(studentDao.getAll(anyString())).thenReturn(students);
            assertEquals(studentDTOS, adminService.getStudents(TEST_PARAM));
        }
    }

    @Test
    void getStudentsWithIllegalArgument() throws DAOException {
        try (MockedStatic<Converter> converter = mockStatic(Converter.class)) {
            converter.when(() -> Converter.prepareStudents(students.getValue())).thenReturn(studentDTOS.getValue());
            doThrow(DAOException.class).when(studentDao).getAll(anyString());
            assertThrows(ServiceException.class, () -> adminService.getStudents(TEST_PARAM));
        }
    }

    @Test
    void getCategorySuccessful() throws DAOException, ServiceException, ValidateException {
        try (MockedStatic<Converter> converter = mockStatic(Converter.class)) {
            converter.when(() -> Converter.convertCategoryToDTO(category)).thenReturn(categoryDTO);
            when(categoryDao.get(anyString())).thenReturn(Optional.ofNullable(category));
            assertEquals(categoryDTO, adminService.getCategory(1));
        }

    }

    @Test
    void getCategoryWithIllegalArgument() throws DAOException {
        when(categoryDao.get(anyString())).thenReturn(Optional.empty());
        assertThrows(ValidateException.class, () -> adminService.getCategory(-1));
    }

    @Test
    void getCategoryFailed() throws DAOException {
        doThrow(DAOException.class).when(categoryDao).get(anyString());
        assertThrows(ServiceException.class, () -> adminService.getCategory(-1));
    }

    @Test
    void getCourseSuccessful() throws DAOException, ValidateException, ServiceException {
        try (MockedStatic<Converter> converter = mockStatic(Converter.class)) {
            converter.when(() -> Converter.convertCourseToDTO(course)).thenReturn(courseDTO);
            when(courseDao.get(anyString())).thenReturn(Optional.ofNullable(course));
            assertEquals(courseDTO, adminService.getCourse(1));
        }
    }

    @Test
    void getCourseWithIllegalArgument() throws DAOException {
        when(courseDao.get(anyString())).thenReturn(Optional.empty());
        assertThrows(ValidateException.class, () -> adminService.getCourse(-1));
    }

    @Test
    void getCourseFailed() throws DAOException {
        doThrow(DAOException.class).when(courseDao).get(anyString());
        assertThrows(ServiceException.class, () -> adminService.getCourse(-1));
    }

    @Test
    void getTeacherSuccessful() throws DAOException, ServiceException, ValidateException {
        try (MockedStatic<Converter> converter = mockStatic(Converter.class)) {
            converter.when(() -> Converter.convertTeacherToDTO(teacher)).thenReturn(teacherDTO);
            when(teacherDao.get(anyString())).thenReturn(Optional.ofNullable(teacher));
            assertEquals(teacherDTO, adminService.getTeacher(1));
        }
    }

    @Test
    void getTeacherWithIllegalArgument() throws DAOException {
        when(teacherDao.get(anyString())).thenReturn(Optional.empty());
        assertThrows(ValidateException.class, () -> adminService.getTeacher(-1));
    }

    @Test
    void getTeacherFailed() throws DAOException {
        doThrow(DAOException.class).when(teacherDao).get(anyString());
        assertThrows(ServiceException.class, () -> adminService.getTeacher(-1));
    }

    @Test
    void deleteTeacherSuccessful() throws DAOException, ValidateException {
        doNothing().when(teacherDao).delete(anyInt());
        assertDoesNotThrow(() -> adminService.deleteTeacher(1));
    }

    @Test
    void deleteTeacherWithIllegalArgument() throws DAOException, ValidateException {
        doThrow(DAOException.class).when(teacherDao).delete(anyInt());
        assertThrows(ServiceException.class, () -> adminService.deleteTeacher(-1));
    }

    @Test
    void courseLaunchNewsLetterSuccessful() throws DAOException {
        when(studentDao.getStudentsByCourse(anyString())).thenReturn(students);
        assertDoesNotThrow(() -> adminService.courseLaunchNewsLetter(courseDTO, appContext));
    }

    @Test
    void courseLaunchNewsLetterFailed() throws DAOException {
        doThrow(DAOException.class).when(studentDao).getStudentsByCourse(anyString());
        assertThrows(ServiceException.class, () -> adminService.courseLaunchNewsLetter(courseDTO, appContext));
    }
}