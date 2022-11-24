package com.epam.facultative.service;

import com.epam.facultative.dto.CourseDTO;

import java.util.List;

public interface GeneralService {

    List<CourseDTO> sortByAlphabet();

    List<CourseDTO> sortByAlphabetReverse();

    List<CourseDTO> sortByDuration();

    List<CourseDTO> sortByNumberStudentsOnCourse();

    List<CourseDTO> getCoursesByCategory(int categoryId);

    List<CourseDTO> getCoursesByTeacher(int teacherId);
}
