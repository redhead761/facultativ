package com.epam.facultative.model.utils.converter;

import com.epam.facultative.model.dto.*;
import com.epam.facultative.model.entities.*;
import com.epam.facultative.model.service.implementation.TestServiceUtil;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ConverterTest {

    static TestServiceUtil testServiceUtil;

    @BeforeAll
    static void setUp() {
        testServiceUtil = new TestServiceUtil();
    }

    @Test
    void convertUserToDTO() {
        UserDTO expectUserDTO = testServiceUtil.getUserDTO();
        UserDTO actualUserDTO = Converter.convertUserToDTO(testServiceUtil.getUser());
        assertNull(actualUserDTO.getPassword());
        assertNotEquals(expectUserDTO.getPassword(), actualUserDTO.getPassword());
        assertEquals(expectUserDTO, actualUserDTO);
    }

    @Test
    void convertTeacherToDTO() {
        TeacherDTO expectTeacherDTO = testServiceUtil.getTeacherDTO();
        TeacherDTO actualTeacherDTO = Converter.convertTeacherToDTO(testServiceUtil.getTeacher());
        assertNull(actualTeacherDTO.getPassword());
        assertNotEquals(expectTeacherDTO.getPassword(), actualTeacherDTO.getPassword());
        assertEquals(expectTeacherDTO, actualTeacherDTO);
    }

    @Test
    void convertStudentToDTO() {
        StudentDTO expectStudentDTO = testServiceUtil.getStudentDTO();
        StudentDTO actualStudentDTO = Converter.convertStudentToDTO(testServiceUtil.getStudent());
        assertNull(actualStudentDTO.getPassword());
        assertNotEquals(expectStudentDTO.getPassword(), actualStudentDTO.getPassword());
        assertEquals(expectStudentDTO, actualStudentDTO);
    }

    @Test
    void convertCourseToDTO() {
        CourseDTO expectCourseDTO = testServiceUtil.getCourseDTO();
        CourseDTO actualCourseDTO = Converter.convertCourseToDTO(testServiceUtil.getCourse());
        assertEquals(expectCourseDTO, actualCourseDTO);
    }

    @Test
    void convertCategoryToDTO() {
        CategoryDTO expectCategoryDTO = testServiceUtil.getCategoryDTO();
        CategoryDTO actualCategoryDTO = Converter.convertCategoryToDTO(testServiceUtil.getCategory());
        assertEquals(expectCategoryDTO, actualCategoryDTO);
    }

    @Test
    void convertDTOToUser() {
        User expectUser = testServiceUtil.getUser();
        User actualUser = Converter.convertDTOToUser(testServiceUtil.getUserDTO());
        assertNotEquals(expectUser.getPassword(), actualUser.getPassword());
        assertEquals(expectUser, actualUser);
    }

    @Test
    void convertDTOToTeacher() {
        Teacher expectTeacher = testServiceUtil.getTeacher();
        Teacher actualTeacher = Converter.convertDTOToTeacher(testServiceUtil.getTeacherDTO());
        assertNotEquals(expectTeacher.getPassword(), actualTeacher.getPassword());
        assertEquals(expectTeacher, actualTeacher);
    }

    @Test
    void convertDTOToStudent() {
        Student expectStudent = testServiceUtil.getStudent();
        Student actualStudent = Converter.convertDTOToStudent(testServiceUtil.getStudentDTO());
        assertNotEquals(expectStudent.getPassword(), actualStudent.getPassword());
        assertEquals(expectStudent, actualStudent);
    }

    @Test
    void convertDTOToCourse() {
        Course expectCourse = testServiceUtil.getCourse();
        Course actualCourse = Converter.convertDTOToCourse(testServiceUtil.getCourseDTO());
        assertEquals(expectCourse, actualCourse);
    }

    @Test
    void convertDTOToCategory() {
        Category expectCategory = testServiceUtil.getCategory();
        Category actualCategory = Converter.convertDTOToCategory(testServiceUtil.getCategoryDTO());
        assertEquals(expectCategory, actualCategory);
    }

    @Test
    void prepareTeachers() {
        List<TeacherDTO> expectTeachers = testServiceUtil.getTeacherDTOS().getValue();
        List<TeacherDTO> actualTeachers = Converter.prepareTeachers(testServiceUtil.getTeachers().getValue());
        assertIterableEquals(expectTeachers, actualTeachers);
    }

    @Test
    void prepareStudents() {
        List<StudentDTO> expectStudents = testServiceUtil.getStudentDTOS().getValue();
        List<StudentDTO> actualStudents = Converter.prepareStudents(testServiceUtil.getStudents().getValue());
        assertIterableEquals(expectStudents, actualStudents);
    }

    @Test
    void prepareCategories() {
        List<CategoryDTO> expectCategory = testServiceUtil.getCategoryDTOS().getValue();
        List<CategoryDTO> actualCategory = Converter.prepareCategories(testServiceUtil.getCategories().getValue());
        assertIterableEquals(expectCategory, actualCategory);
    }

    @Test
    void prepareCourses() {
        List<CourseDTO> expectCourse = testServiceUtil.getTCourseDTOS().getValue();
        List<CourseDTO> actualCourse = Converter.prepareCourses(testServiceUtil.getCourses().getValue());
        assertIterableEquals(expectCourse, actualCourse);
    }
}