package com.epam.facultative.model.service.implementation;

import com.epam.facultative.model.dto.*;
import com.epam.facultative.model.entities.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.epam.facultative.model.utils.hash_password.HashPassword.encode;

public class TestServiceUtil {
    public User getAdmin() {
        return User.builder()
                .login("admin")
                .password(encode("Admin1234"))
                .name("Admin")
                .surname("Admin")
                .email("admin@admin.com")
                .role(Role.ADMIN)
                .build();
    }

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
                .startDate(LocalDate.parse("2024-01-08"))
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
                .grade(0)
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
                .password(encode("Test1234"))
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
                .password(encode("Test1234"))
                .name("Testname")
                .surname("Testsurname")
                .email("test@fa.ve")
                .role(Role.STUDENT)
                .grade(0)
                .courseNumber(1)
                .block(false)
                .build();
    }

    public Map.Entry<Integer, List<Category>> getCategories() {
        List<Category> categories = new ArrayList<>();
        categories.add(getCategory());
        return Map.entry(1, categories);
    }

    public Map.Entry<Integer, List<CategoryDTO>> getCategoryDTOS() {
        List<CategoryDTO> categoryDTOS = new ArrayList<>();
        categoryDTOS.add(getCategoryDTO());
        return Map.entry(1, categoryDTOS);
    }

    public Map.Entry<Integer, List<Student>> getStudents() {
        List<Student> students = new ArrayList<>();
        students.add(getStudent());
        return Map.entry(1, students);
    }

    public Map.Entry<Integer, List<StudentDTO>> getStudentDTOS() {
        List<StudentDTO> studentDTOS = new ArrayList<>();
        studentDTOS.add(getStudentDTO());
        return Map.entry(1, studentDTOS);
    }

    public Map.Entry<Integer, List<Teacher>> getTeachers() {
        List<Teacher> teachers = new ArrayList<>();
        teachers.add(getTeacher());
        return Map.entry(1, teachers);
    }

    public Map.Entry<Integer, List<TeacherDTO>> getTeacherDTOS() {
        List<TeacherDTO> teacherDTOS = new ArrayList<>();
        teacherDTOS.add(getTeacherDTO());
        return Map.entry(1, teacherDTOS);
    }

    public Map.Entry<Integer, List<Course>> getCourses() {
        List<Course> courses = new ArrayList<>();
        courses.add(getCourse());
        return Map.entry(1, courses);
    }

    public Map.Entry<Integer, List<CourseDTO>> getTCourseDTOS() {
        List<CourseDTO> courseDTOS = new ArrayList<>();
        courseDTOS.add(getCourseDTO());
        return Map.entry(1, courseDTOS);
    }
}
