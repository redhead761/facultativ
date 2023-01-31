package com.epam.facultative.model.utils.converter;

import com.epam.facultative.model.dto.*;
import com.epam.facultative.model.entities.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Converts DTO to Entities and vise versa
 *
 * @author Oleksandr Panchenko
 * @version 1.0
 */
public class Converter {
    /**
     * Converts User into UserDTO
     *
     * @param user to convert
     * @return UserDTO entity
     */
    public static UserDTO convertUserToDTO(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .login(user.getLogin())
                .name(user.getName())
                .surname(user.getSurname())
                .email(user.getEmail())
                .role(user.getRole())
                .avatar(user.getAvatar())
                .build();
    }

    /**
     * Converts Teacher into TeacherDTO
     *
     * @param teacher to convert
     * @return TeacherDTO entity
     */
    public static TeacherDTO convertTeacherToDTO(Teacher teacher) {
        return TeacherDTO.builder()
                .id(teacher.getId())
                .login(teacher.getLogin())
                .name(teacher.getName())
                .surname(teacher.getSurname())
                .email(teacher.getEmail())
                .role(teacher.getRole())
                .degree(teacher.getDegree())
                .avatar(teacher.getAvatar())
                .build();
    }

    /**
     * Converts Student into StudentDTO
     *
     * @param student to convert
     * @return StudentDTO entity
     */
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
                .avatar(student.getAvatar())
                .build();
    }

    /**
     * Converts Course into CourseDTO
     *
     * @param course to convert
     * @return CourseDTO entity
     */
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
                .teacher(course.getTeacher() != null ? convertTeacherToDTO(course.getTeacher()) : null)
                .build();
    }

    /**
     * Converts Category into CategoryDTO
     *
     * @param category to convert
     * @return CategoryDTO entity
     */
    public static CategoryDTO convertCategoryToDTO(Category category) {
        return CategoryDTO.builder()
                .id(category.getId())
                .title(category.getTitle())
                .description(category.getDescription())
                .build();
    }

    /**
     * Converts UserDTO into User
     *
     * @param userDTO to convert
     * @return User entity
     */
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

    /**
     * Converts TeacherDTO into Teacher
     *
     * @param teacherDTO to convert
     * @return Teacher entity
     */
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
                .avatar(teacherDTO.getAvatar())
                .build();
    }

    /**
     * Converts StudentDTO into Student
     *
     * @param studentDTO to convert
     * @return Student entity
     */
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
                .avatar(studentDTO.getAvatar())
                .build();
    }

    /**
     * Converts CourseDTO into Course
     *
     * @param courseDTO to convert
     * @return Course entity
     */
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

    /**
     * Converts CategoryDTO into Category
     *
     * @param categoryDTO to convert
     * @return Category entity
     */
    public static Category convertDTOToCategory(CategoryDTO categoryDTO) {
        return Category.builder()
                .id(categoryDTO.getId())
                .title(categoryDTO.getTitle())
                .description(categoryDTO.getDescription())
                .build();
    }

    /**
     * Converts {@code List<Teacher>} into {@code List<TeacherDTO>}
     *
     * @param teachers to convert
     * @return {@code List<TeacherDTO>} entity
     */
    public static List<TeacherDTO> prepareTeachers(List<Teacher> teachers) {
        List<TeacherDTO> result = new ArrayList<>();
        for (Teacher teacher : teachers) {
            result.add(convertTeacherToDTO(teacher));
        }
        return result;
    }

    /**
     * Converts {@code List<Student>} into {@code List<StudentDTO>}
     *
     * @param students to convert
     * @return {@code List<StudentDTO>} entity
     */
    public static List<StudentDTO> prepareStudents(List<Student> students) {
        List<StudentDTO> result = new ArrayList<>();
        for (Student student : students) {
            result.add(convertStudentToDTO(student));
        }
        return result;
    }

    /**
     * Converts {@code List<Category>} into {@code List<CategoryDTO>}
     *
     * @param categories to convert
     * @return {@code List<CategoryDTO>} entity
     */
    public static List<CategoryDTO> prepareCategories(List<Category> categories) {
        List<CategoryDTO> result = new ArrayList<>();
        for (Category category : categories) {
            result.add(convertCategoryToDTO(category));
        }
        return result;
    }

    /**
     * Converts {@code List<Course>} into {@code List<CourseDTO>}
     *
     * @param courses to convert
     * @return {@code List<CourseDTO>} entity
     */
    public static List<CourseDTO> prepareCourses(List<Course> courses) {
        List<CourseDTO> coursesDTO = new ArrayList<>();
        for (Course course : courses) {
            coursesDTO.add(convertCourseToDTO(course));
        }
        return coursesDTO;
    }
}
