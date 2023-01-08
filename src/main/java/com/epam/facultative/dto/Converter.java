package com.epam.facultative.dto;

import com.epam.facultative.data_layer.entities.*;

import java.util.ArrayList;
import java.util.List;

public class Converter {
    public static UserDTO convertUserToDTO(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .login(user.getLogin())
                .name(user.getName())
                .surname(user.getSurname())
                .email(user.getEmail())
                .role(user.getRole())
                .build();
    }

    public static TeacherDTO convertTeacherToDTO(Teacher teacher) {
        return TeacherDTO.builder()
                .id(teacher.getId())
                .login(teacher.getLogin())
                .name(teacher.getName())
                .surname(teacher.getSurname())
                .email(teacher.getEmail())
                .role(teacher.getRole())
                .degree(teacher.getDegree())
                .build();
    }

    public static StudentDTO convertStudentToDTO(Student student) {
        return StudentDTO.builder()
                .id(student.getId())
                .login(student.getLogin())
                .name(student.getName())
                .surname(student.getSurname())
                .email(student.getEmail())
                .role(student.getRole())
                .block(student.isBlock())
                .courseNumber(student.getCourseNumber())
                .grade(student.getGrade())
                .build();
    }

    public static CourseDTO convertCourseToDTO(Course course) {
        return CourseDTO.builder()
                .id(course.getId())
                .title(course.getTitle())
                .duration(course.getDuration())
                .startDate(course.getStartDate())
                .amountStudents(course.getAmountStudents())
                .description(course.getDescription())
                .category(convertCategoryToDTO(course.getCategory()))
                .status(course.getStatus())
                .teacher(convertTeacherToDTO(course.getTeacher()))
                .build();
    }

    public static CategoryDTO convertCategoryToDTO(Category category) {
        return CategoryDTO.builder()
                .id(category.getId())
                .title(category.getTitle())
                .description(category.getDescription())
                .build();
    }

    public static User convertDTOToUser(UserDTO userDTO) {
        return User.builder()
                .id(userDTO.getId())
                .password(userDTO.getPassword())
                .login(userDTO.getLogin())
                .name(userDTO.getName())
                .surname(userDTO.getSurname())
                .email(userDTO.getEmail())
                .role(userDTO.getRole())
                .build();
    }

    public static Teacher convertDTOToTeacher(TeacherDTO teacherDTO) {
        return Teacher.builder()
                .id(teacherDTO.getId())
                .password(teacherDTO.getPassword())
                .login(teacherDTO.getLogin())
                .name(teacherDTO.getName())
                .surname(teacherDTO.getSurname())
                .email(teacherDTO.getEmail())
                .role(teacherDTO.getRole())
                .degree(teacherDTO.getDegree())
                .build();
    }

    public static Student convertDTOToStudent(StudentDTO studentDTO) {
        return Student.builder()
                .id(studentDTO.getId())
                .login(studentDTO.getLogin())
                .password(studentDTO.getPassword())
                .name(studentDTO.getName())
                .surname(studentDTO.getSurname())
                .email(studentDTO.getEmail())
                .role(studentDTO.getRole())
                .block(studentDTO.isBlock())
                .courseNumber(studentDTO.getCourseNumber())
                .grade(studentDTO.getGrade())
                .build();
    }

    public static Course convertDTOToCourse(CourseDTO courseDTO) {
        return Course.builder()
                .id(courseDTO.getId())
                .title(courseDTO.getTitle())
                .duration(courseDTO.getDuration())
                .startDate(courseDTO.getStartDate())
                .amountStudents(courseDTO.getAmountStudents())
                .description(courseDTO.getDescription())
                .category(convertDTOToCategory(courseDTO.getCategory()))
                .status(courseDTO.getStatus())
                .teacher(courseDTO.getTeacher() != null ? convertDTOToTeacher(courseDTO.getTeacher()) : null)
                .build();
    }

    public static Category convertDTOToCategory(CategoryDTO categoryDTO) {
        return Category.builder()
                .id(categoryDTO.getId())
                .title(categoryDTO.getTitle())
                .description(categoryDTO.getDescription())
                .build();
    }

    public static List<TeacherDTO> prepareTeachers(List<Teacher> teachers) {
        List<TeacherDTO> result = new ArrayList<>();
        for (Teacher teacher : teachers) {
            result.add(convertTeacherToDTO(teacher));
        }
        return result;
    }

    public static List<StudentDTO> prepareStudents(List<Student> students) {
        List<StudentDTO> result = new ArrayList<>();
        for (Student student : students) {
            result.add(convertStudentToDTO(student));
        }
        return result;
    }

    public static List<CategoryDTO> prepareCategories(List<Category> categories) {
        List<CategoryDTO> result = new ArrayList<>();
        for (Category category : categories) {
            result.add(convertCategoryToDTO(category));
        }
        return result;
    }

    public static List<CourseDTO> prepareCourses(List<Course> courses) {
        List<CourseDTO> coursesDTO = new ArrayList<>();
        for (Course course : courses) {
            coursesDTO.add(convertCourseToDTO(course));
        }
        return coursesDTO;
    }
}
