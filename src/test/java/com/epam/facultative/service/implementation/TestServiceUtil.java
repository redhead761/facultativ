package com.epam.facultative.service.implementation;

import com.epam.facultative.data_layer.entities.*;
import com.epam.facultative.dto.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TestServiceUtil {
    public UserDTO getAdminDTO() {
        return UserDTO.builder()
                .login("admin")
                .password("Admin1234")
                .name("Admin")
                .surname("Admin")
                .email("admin@admin.com")
                .role(Role.ADMIN)
                .build();
    }

    public CourseDTO getCourseDTO() {
        return CourseDTO.builder()
                .id(0)
                .title("Test course")
                .duration(100)
                .startDate(LocalDate.parse("2013-01-08"))
                .status(Status.COMING_SOON)
                .teacher(getTeacherDTO())
                .category(getCategoryDTO())
                .description("Test desc")
                .build();
    }

    public CategoryDTO getCategoryDTO() {
        return CategoryDTO.builder()
                .id(0)
                .title("Test title")
                .description("Test desc")
                .build();
    }

    public TeacherDTO getTeacherDTO() {
        return TeacherDTO.builder()
                .id(0)
                .login("testlogin")
                .password("Test1234")
                .name("Testname")
                .surname("Testsurname")
                .email("test@fa.ve")
                .role(Role.TEACHER)
                .degree("Prof")
                .build();
    }

    public StudentDTO getStudentDTO() {
        return StudentDTO.builder()
                .id(0)
                .login("testlogin")
                .password("Test1234")
                .name("Testname")
                .surname("Testsurname")
                .email("test@fa.ve")
                .role(Role.STUDENT)
                .grade(1)
                .courseNumber(1)
                .block(false)
                .build();
    }

    public Course getCourse() {
        return Course.builder()
                .id(0)
                .title("Test course")
                .duration(100)
                .startDate(LocalDate.parse("2013-01-08"))
                .status(Status.COMING_SOON)
                .teacher(getTeacher())
                .category(getCategory())
                .description("Test desc")
                .build();
    }

    public Category getCategory() {
        return Category.builder()
                .id(0)
                .title("Test title")
                .description("Test desc")
                .build();
    }

    public Teacher getTeacher() {
        return Teacher.builder()
                .id(0)
                .login("testlogin")
                .password("Test1234")
                .name("Testname")
                .surname("Testsurname")
                .email("test@fa.ve")
                .role(Role.TEACHER)
                .degree("Prof")
                .build();
    }

    public Student getStudent() {
        return Student.builder()
                .id(0)
                .login("testlogin")
                .password("Test1234")
                .name("Testname")
                .surname("Testsurname")
                .email("test@fa.ve")
                .role(Role.STUDENT)
                .grade(1)
                .courseNumber(1)
                .block(false)
                .build();
    }

    public List<Category> getCategories() {
        List<Category> categories = new ArrayList<>();
        categories.add(getCategory());
        return categories;
    }

    public List<CategoryDTO> getCategoryDTOS() {
        List<CategoryDTO> categoryDTOS = new ArrayList<>();
        categoryDTOS.add(getCategoryDTO());
        return categoryDTOS;
    }

    public List<Student> getStudents() {
        List<Student> students = new ArrayList<>();
        students.add(getStudent());
        return students;
    }

    public List<StudentDTO> getStudentDTOS() {
        List<StudentDTO> studentDTOS = new ArrayList<>();
        studentDTOS.add(getStudentDTO());
        return studentDTOS;
    }

    public List<Teacher> getTeachers() {
        List<Teacher> teachers = new ArrayList<>();
        teachers.add(getTeacher());
        return teachers;
    }

    public List<TeacherDTO> getTeacherDTOS() {
        List<TeacherDTO> teacherDTOS = new ArrayList<>();
        teacherDTOS.add(getTeacherDTO());
        return teacherDTOS;
    }

    public List<Course> getCourses() {
        List<Course> courses = new ArrayList<>();
        courses.add(getCourse());
        return courses;
    }

    public List<CourseDTO> getTCourseDTOS() {
        List<CourseDTO> courseDTOS = new ArrayList<>();
        courseDTOS.add(getCourseDTO());
        return courseDTOS;
    }
}
